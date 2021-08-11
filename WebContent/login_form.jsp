<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
if (loginMember != null) {
	out.println("<script>");
	out.println("alert('잘못된 경로로 들어오셨습니다.');");
	out.println("history.back();");
	out.println("</script>");
	out.close();
}

request.setCharacterEncoding("utf-8");
String url = request.getParameter("url");	// 로그인 후 이동할 페이지 주소(없는 경우도 있음)
if (url == null)	url = "";	// url값이 없으면 빈 문자열로 변환하여 사용

String tmpID = "", cook = request.getHeader("Cookie");

if (cook != null) {
	Cookie[] cookies = request.getCookies(); 
	for (int i = 0 ; i < cookies.length ; i++) {
		if (cookies[i].getName().equals("saveID"))	tmpID = cookies[i].getValue();
	}
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function chkVal(frm) {
	var uid = frm.uid.value;	var pwd = frm.pwd.value;
	if (uid == "") {
		alert("아이디를 입력하세요.");	frm.uid.focus();	return false;
	} else if (uid.length < 4) {
		alert("아이디는 4자 이상 입력해야 합니다.");	frm.uid.select();	return false;
	}
	if (pwd == "") {
		alert("비밀번호를 입력하세요.");
		frm.pwd.focus();	return false;
	} else if (pwd.length < 4) {
		alert("비밀번호는 4자 이상 입력해야 합니다.");
		frm.pwd.select();	return false;
	}

	return true;
}
</script>
<style>
#wrapper {
	width:800px; height:500px; margin : 0 auto; align : center; border : 1px solid black;
}
#header {
	width:1000px; height:100px; border:1px solid red;
	
}
#content {
	widht:800px; height:500px; margin : 0 auto; 
	
}
#footer {
	width:1000px; height:100px;;
}
#login{
	width:800px; height:400px;
	padding-top:60px;
	padding-left:230px;
}
.textinput {
	width:200px;height:50px;font-size:30px;
}
.a {
	font-size:5px;
}
#joinbtn {
	width:60px; height:20px; font-size:5px;
}
	a:link { color:#000; text-decoration:none; }
	a:visited { color:#000; text-decoration:none; }
	a:hover { color:#000; text-decoration:none;}
	a:focus { color:#000; text-decoration:none;}
	a:active { color:#000; text-decoration:none;}
	
	.form-group { width : 300px;}
	
	.form-check { text-align : left; width : 300px;}
	
	.fm { float : right;}
	
	.loginbt { width : 300px; height : 50px;}
	
	#joinbtn {
    width: 100px;
    height: 30px;
    font-size: 10px;
    text-align: center;
    vertical-align: middle;
}
	
	
	#labelleft { font-size : 15px; float : left;}
	.btright { float : right;}
</style>
</head>
<body>
<%@ include file="inc/main_header.jsp" %>

<div id="wrapper"> 

<div id="content">

<form name="frmLogin" action="login" method="post" onsubmit="return chkVal(this);" align = "center">
<input type="hidden" name="url" value="<%=url %>" />
<div id="login">

	<div class="form-group">
	 	<label class="col-form-label col-form-label-lg mt-4" id = "labelleft" for="uid">아이디</label>
		<input type="text" style = "text-align:center;" name="uid" id="uid" maxlength="20" value="test1" class="textinput form-control form-control-lg"/></td>
	</div>
	
	<div class = "form-group">
		<label class="col-form-label col-form-label-lg mt-4" id = "labelleft" for="pwd">비밀번호</label>
		<input type="password" style = "text-align:center;" name="pwd" id="pwd" maxlength="20" value="1111" class="textinput form-control form-control-lg"/></td>
	</div>
	
	<div class="form-check">
		<input class="form-check-input" type="checkbox" value="y" id="flexCheckChecked" name = "isSave" <% if (!tmpID.equals("")) { %>checked="checked"<% } %> />
		<label class="form-check-label" for="flexCheckChecked">
			아이디 저장
		</label>
		<span class = "fm"><a href="find_member.jsp">아이디/비밀번호 찾기</a></span> 
	</div>
	
	<div class = "form-group">
		<button type="submit" class="btn btn-secondary loginbt">로그인</button>
	</div>
	<div class = "form-group">
		아직 아이디가 없으신가요?
		<div class = "btright">
			<button type="button" class="btn btn-secondary loginbt joinbtn" id = "joinbtn" onclick = "location.href='join_form.jsp';">회원가입</button>
		</div>
	</div>
</div>
</form>

</div>
</div>
</body>
</html>
