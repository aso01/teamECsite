<<<<<<< HEAD
package com.internousdev.leo.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class GoLoginAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session;

	//cartFlagを削除してログイン画面に遷移
	public String execute() {
		session.remove("cartFlag");
		return SUCCESS;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
=======
package com.internousdev.leo.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class GoLoginAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session;

	//cartFlagを削除してログイン画面に遷移
	public String execute() {
		session.remove("cartFlag");
		return SUCCESS;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
>>>>>>> 3adaadc5932ada62718d131e9cb50fd74cd3249f
