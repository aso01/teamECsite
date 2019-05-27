package com.internousdev.leo.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class ResetPasswordAction extends ActionSupport implements SessionAware {
	private int backFlag;
	private Map<String, Object> session;

	public String execute() {

		/*パスワード再設定確認画面から戻ってきていない場合、
			sessionのユーザーIDを削除*/
		if (backFlag != 1) {
			session.remove("userIdForResetPassword");
		}

		//パスワード再設定画面へ遷移
		return SUCCESS;
	}

	//backFlagの入手
	//パスワード再設定確認画面から戻ってきた場合、1の値が入る
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
