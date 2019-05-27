package com.internousdev.leo.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class CreateUserAction extends ActionSupport implements SessionAware {
	private int backFlag;
	private Map<String, Object> session;

	//ユーザー情報入力確認画面から戻ってきていない場合
	//sessionのユーザー情報を削除(画面遷移時は常に未入力状態)
	public String execute() {
		if (backFlag != 1) {
			session.remove("familyName");
			session.remove("firstName");
			session.remove("familyNameKana");
			session.remove("firstNameKana");
			session.remove("sex");
			session.remove("sexList");
			session.remove("email");
			session.remove("userIdForCreateUser");
			session.remove("password");
		}

		//性別をListに格納
		//順番があるのでListを使用する
		List<String> sexList = new ArrayList<String>();

		// 画面表示時に選択されている性別を作成(男性に設定）
		if(!session.containsKey("sex")) {
			session.put("sex", "男性");
		}else {

			//valueOfでString型に変換
			session.put("sex", String.valueOf(session.get("sex")));
		}
		sexList.add("男性");
		sexList.add("女性");
		session.put("sexList", sexList);

		return SUCCESS;
	}

	//backFlagの入手
	//ユーザー情報入力確認画面から戻ってきた場合、1の値が入る
	public int getBackFlag() {
		return backFlag;
	}

	public void setBackFlag(int backFlag) {
		this.backFlag = backFlag;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
