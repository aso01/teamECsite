<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/style.css">
<link rel="stylesheet" href="./css/form.css">
<title>ログイン画面</title>
</head>
<body>
<script type="text/javascript" src= "./js/login.js"></script>
<jsp:include page="header.jsp"/>

	<div id="contents">
		<h1>ログイン画面</h1>

		<!-- ユーザーID入力エラーメッセージ -->
		<s:if test="userIdErrorMessageList!=null && userIdErrorMessageList.size()>0">
			<div class="error_box">
				<s:iterator value="userIdErrorMessageList"><s:property /><br></s:iterator>
			</div>
		</s:if>

		<!-- パスワード入力エラーメッセージ -->
		<s:if test="passwordErrorMessageList!=null && passwordErrorMessageList.size()>0">
			<div class="error_box">
					<s:iterator value="passwordErrorMessageList"><s:property/><br></s:iterator>
			</div>
			</s:if>

		<!-- 認証失敗メッセージ -->
		<s:if test="isNotUserInfoMessage!=null && !isNotUserInfoMessage.isEmpty()">
			<div class="error_box">
					<s:property  value="isNotUserInfoMessage"/>
			</div>
		</s:if>

		<!-- ユーザーIDとパスワードの入力 -->
		<s:form id="loginForm">
			<table class="T_box">
				<tr>
					<th scope="row"><s:label value="ユーザーID"/></th>

					<!-- ユーザーID保存のチェックボックスにチェックが入っている場合 sessionでユーザーIDを保持する -->
					<s:if test="#session.savedUserIdFlag==true">
					<td><s:textfield name="userId" class="txt_login" placeholder="ユーザーID" value='%{#session.savedUserId}' autocomplete="off"/></td>
					</s:if>

					<!-- ユーザーID保存のチェックボックスにチェックが入っていない場合 -->
					<s:else>
					<td><s:textfield name="userId" class="txt_login" placeholder="ユーザーID" value='%{userId}' autocomplete="off"/></td>
					</s:else>
				</tr>

				<!--パスワードの入力 -->
				<tr>
					<th scope="row"><s:label value="パスワード"/></th>
					<td><s:password name="password" class="txt_login" placeholder="パスワード" autocomplete="off"/></td>
				</tr>
			</table>

		<!-- ユーザーID保存機能 -->
			<div class="box">
				<!-- ユーザーID保存のチェックボックスにチェックを入れる場合 -->
				<s:if test="#session.savedUserIdFlag==true && #session.savedUserId!=null && !#session.savedUserId.isEmpty()">
					<s:checkbox name="savedUserIdFlag" checked="checked" class="checkbox"/>
				</s:if>

				<!-- ユーザーID保存のチェックボックスにチェックを入れない場合 -->
				<s:else>
					<s:checkbox name="savedUserIdFlag"/>
				</s:else>
				<s:label value="ユーザーID保存"/><br>
			</div>

		<!-- ボタン3種作製  -->
			<div class="submit_btn_box">
				<s:submit value="ログイン" class="btn" onclick="goLoginAction()" />
			</div>
			<div class="submit_btn_box">
					<s:submit value="新規ユーザー登録" class="btn" onclick="goCreateUserAction()" />
			</div>
			<div class="submit_btn_box">
					<s:submit value="パスワード再設定" class="btn" onclick="goResetPasswordAction()" />
			</div>
		</s:form>
	</div>
</body>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/style.css">
<link rel="stylesheet" href="./css/form.css">
<title>ログイン画面</title>
</head>
<body>
<script type="text/javascript" src= "./js/login.js"></script>
<jsp:include page="header.jsp"/>

	<div id="contents">
		<h1>ログイン画面</h1>

		<!-- ユーザーID入力エラーメッセージ -->
		<s:if test="userIdErrorMessageList!=null && userIdErrorMessageList.size()>0">
			<div class="error_box">
				<s:iterator value="userIdErrorMessageList"><s:property /><br></s:iterator>
			</div>
		</s:if>

		<!-- パスワード入力エラーメッセージ -->
		<s:if test="passwordErrorMessageList!=null && passwordErrorMessageList.size()>0">
			<div class="error_box">
					<s:iterator value="passwordErrorMessageList"><s:property/><br></s:iterator>
			</div>
			</s:if>

		<!-- 認証失敗メッセージ -->
		<s:if test="isNotUserInfoMessage!=null && !isNotUserInfoMessage.isEmpty()">
			<div class="error_box">
					<s:property  value="isNotUserInfoMessage"/>
			</div>
		</s:if>

		<!-- ユーザーIDとパスワードの入力 -->
		<s:form id="loginForm">
			<table class="T_box">
				<tr>
					<th scope="row"><s:label value="ユーザーID"/></th>

					<!-- ユーザーID保存のチェックボックスにチェックが入っている場合 sessionでユーザーIDを保持する -->
					<s:if test="#session.savedUserIdFlag==true">
					<td><s:textfield name="userId" class="txt_login" placeholder="ユーザーID" value='%{#session.savedUserId}' autocomplete="off"/></td>
					</s:if>

					<!-- ユーザーID保存のチェックボックスにチェックが入っていない場合 -->
					<s:else>
					<td><s:textfield name="userId" class="txt_login" placeholder="ユーザーID" value='%{userId}' autocomplete="off"/></td>
					</s:else>
				</tr>

				<!--パスワードの入力 -->
				<tr>
					<th scope="row"><s:label value="パスワード"/></th>
					<td><s:password name="password" class="txt_login" placeholder="パスワード" autocomplete="off"/></td>
				</tr>
			</table>

		<!-- ユーザーID保存機能 -->
			<div class="box">
				<!-- ユーザーID保存のチェックボックスにチェックを入れる場合 -->
				<s:if test="#session.savedUserIdFlag==true && #session.savedUserId!=null && !#session.savedUserId.isEmpty()">
					<s:checkbox name="savedUserIdFlag" checked="checked" class="checkbox"/>
				</s:if>

				<!-- ユーザーID保存のチェックボックスにチェックを入れない場合 -->
				<s:else>
					<s:checkbox name="savedUserIdFlag"/>
				</s:else>
				<s:label value="ユーザーID保存"/><br>
			</div>

		<!-- ボタン3種作製  -->
			<div class="submit_btn_box">
				<s:submit value="ログイン" class="btn" onclick="goLoginAction()" />
			</div>
			<div class="submit_btn_box">
					<s:submit value="新規ユーザー登録" class="btn" onclick="goCreateUserAction()" />
			</div>
			<div class="submit_btn_box">
					<s:submit value="パスワード再設定" class="btn" onclick="goResetPasswordAction()" />
			</div>
		</s:form>
	</div>
</body>
>>>>>>> 3adaadc5932ada62718d131e9cb50fd74cd3249f
</html>