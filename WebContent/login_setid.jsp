<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import = "java.util.*" %>
<%
MemberInfo loginMember = (MemberInfo)request.getAttribute("loginMember");
request.setCharacterEncoding("utf-8");
MemberInfo findMember = (MemberInfo)request.getAttribute("findInfo");


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>

#joinbtn {
	width:60px; height:20px; font-size:5px;
}
	
	a:link { color:#4f4f4f; text-decoration:none; }
	a:visited { color:#8a2e91; text-decoration:none; }
	a:hover { color:#000; text-decoration:none; font-weight:bold; }
	a:focus { color:#000; text-decoration:none; font-weight:bold; }
	a:active { color:#00; text-decoration:none; font-weight:bold; }
</style>
</head>
<body>
<%@ include file="inc/main_header.jsp" %>
<div id="wrapper"> 
<div id="content">
<div>아이디 찾기 결과</div>
<br/>
<div>회원님의 아이디는 <%= findMember.getMi_id() %>   입니다.</div>
<br/>
<div>
	<input type= "button" value = "비밀번호찾기" width="50" height = "50" class="joinbtn" onclick = "location.href='find_member.jsp'"/>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="로그인" class="joinbtn" onclick = "location.href='login_form.jsp'"/>
</div>

</div>
	<%@ include file="inc/footer.jsp" %>
</div>
</body>
</html>
