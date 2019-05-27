package com.internousdev.leo.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.leo.dao.CartInfoDAO;
import com.internousdev.leo.dao.UserInfoDAO;
import com.internousdev.leo.dto.CartInfoDTO;
import com.internousdev.leo.dto.UserInfoDTO;
import com.internousdev.leo.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements SessionAware {

	private String userId;
	private String password;
	private boolean savedUserIdFlag;
	private List<String> userIdErrorMessageList;
	private List<String> passwordErrorMessageList;
	private String isNotUserInfoMessage;
	private List<CartInfoDTO> cartInfoDTOList;
	private int totalPrice;
	private Map<String, Object> session;

	public String execute() {

		// ユーザー登録画面から遷移した場合にユーザーIDとパスワードがsessionに入っているため削除
		session.remove("userIdForCreateUser");
		session.remove("password");

		//ログイン画面へ遷移
		String result = ERROR;

		//ユーザーID保持を選択している場合
		//ユーザーIDをsessionで保持する
		if(savedUserIdFlag) {
			session.put("savedUserIdFlag", true);
			session.put("savedUserId", userId);

		//ユーザーID保持を選択していない場合
		//sessionのユーザーIDとフラグを削除
		} else {
			session.remove("savedUserIdFlag");
			session.remove("savedUserId");
		}

		//入力されたユーザーIDとパスワードが正規表現にマッチするかを検証
		InputChecker inC = new InputChecker();
		userIdErrorMessageList = inC.doCheck("ユーザーID", userId, 1, 8, true, false, false, true, false, false, false);
		passwordErrorMessageList = inC.doCheck("パスワード", password, 1, 16, true, false, false, true, false, false, false);

		//マッチしない入力がある場合
		//判別した項目に応じてエラーメッセージをログイン画面で表示する
		if(userIdErrorMessageList.size() > 0
		|| passwordErrorMessageList.size() > 0) {
			//未ログイン状態
			session.put("logined", 0);
			return result;
		}

		//入力されたユーザーIDとパスワードをUserInfoDAOに格納する
		UserInfoDAO userInfoDAO = new UserInfoDAO();

		//DBに一致する会員情報がある場合(認証成功)
		if(userInfoDAO.isExistsUserInfo(userId, password)){
			if(userInfoDAO.login(userId, password) > 0) {

				// カートの情報をユーザーに紐づける
				//DBからカート情報を取得
				CartInfoDAO cartInfoDAO = new CartInfoDAO();
				List<CartInfoDTO> cartInfoDTOListBySession = (List<CartInfoDTO>) cartInfoDAO.selectCartInfoList(session.get("tempUserId").toString());

				//カート情報の紐付け失敗
				if (cartInfoDTOListBySession != null) {
					boolean cartresult = changeCartInfo(cartInfoDTOListBySession);
					if (!cartresult) {

						//システムエラー画面へ遷移
						return "DBError";
					}
				}
				// 次の遷移先を設定
				//カート内に商品が入っている場合
				if (session.containsKey("cartFlag")) {

					//cartFlagを削除
					session.remove("cartFlag");
					//カート画面に遷移
					result = "cart";

				//カート内に商品がない場合
				} else {
					//ホーム画面に遷移
					result = SUCCESS;
				}

				// ユーザー情報をsessionに登録する
				UserInfoDTO userInfoDTO = userInfoDAO.getUserInfo(userId, password);
				session.put("userId", userInfoDTO.getUserId());
				//ログインフラグ(ログイン済みにする・ヘッダーの表記がログインからログアウトに変わる）
				session.put("logined", 1);
		}
		} else {

			//認証失敗
			isNotUserInfoMessage = "ユーザーIDまたはパスワードが異なります。";
		}
		//ログイン画面へ遷移
		return result;
	}

	/**
	 * DBのカート情報を更新/作成する
	 * @param cartInfoDTOListBySession
	 */

	//未ログイン時にカート内に商品を入れている時、以下の処理を行う（tempUserId利用時）
	private boolean changeCartInfo(List<CartInfoDTO> cartInfoDTOListBySession) {
		CartInfoDAO cartInfoDAO = new CartInfoDAO();

		//tempUserIdのカート内の商品個数を0に設定
		int count = 0;
     	String tempUserId = session.get("tempUserId").toString();
     	boolean result = false;

		for (CartInfoDTO dto : cartInfoDTOListBySession) {

			// sessionに入っている(画面に表示されている)カート情報と同じ商品のデータがすでにDBに存在するかをチェックする
			if (cartInfoDAO.isExistsInfo(userId, dto.getProductId())) {

				//存在する場合は、カート情報テーブルの購入個数を更新し、tempUserIdのデータは削除する
				//以前にカート内に商品を入れている可能性があるので、今回入れた分と合算する必要がある
				count += cartInfoDAO.updateCartInfo(userId, dto.getProductId(), dto.getProductCount());

				/*tempUserIdに紐付けられていたカート情報は元々あったuserIdに引き継がれ、、
					合算前のtempUserIdのデータは不要になるためtempUserIdのカート情報を削除する */
				cartInfoDAO.deleteCartInfo(String.valueOf(dto.getProductId()), tempUserId);

			} else {
				//存在しない場合は、ユーザーIDをtempUserIdからuserIdに変更する
				//合算する必要がないため、tempUserIdのカート情報をそのままuserIdに変更すれば良い
				count += cartInfoDAO.updateCartInfoAfterLogined(userId,tempUserId, dto.getProductId());
			}
		}

		//ログイン済でカート内に商品が入っている場合
		if (count == cartInfoDTOListBySession.size()) {
			cartInfoDTOList = cartInfoDAO.selectCartInfoList(userId);
			totalPrice = cartInfoDAO.totalPrice(userId);
			result = true;
		}

		//falseかtrueかの値を返す
		return result;
	}

	//getterとsetterで値の取得をし、各遷移先に渡す
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSavedUserIdFlag() {
		return savedUserIdFlag;
	}

	public void setSavedUserIdFlag(boolean savedUserIdFlag) {
		this.savedUserIdFlag = savedUserIdFlag;
	}

	public List<String> getUserIdErrorMessageList() {
		return userIdErrorMessageList;
	}

	public void setUserIdErrorMessageList(List<String> userIdErrorMessageList) {
		this.userIdErrorMessageList = userIdErrorMessageList;
	}
	public List<String> getPasswordErrorMessageList() {
		return passwordErrorMessageList;
	}
	public void setPasswordErrorMessageList(List<String> passwordErrorMessageList) {
		this.passwordErrorMessageList = passwordErrorMessageList;
	}
	public String getIsNotUserInfoMessage() {
		return isNotUserInfoMessage;
	}

	public void setIsNotUserInfoMessage(String isNotUserInfoMessage) {
		this.isNotUserInfoMessage = isNotUserInfoMessage;
	}

	public List<CartInfoDTO> getCartInfoDTOList() {
		return cartInfoDTOList;
	}

	public void setCartInfoDTOList(List<CartInfoDTO> cartInfoDTOList) {
		this.cartInfoDTOList = cartInfoDTOList;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
