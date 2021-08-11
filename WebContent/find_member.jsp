<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<% 
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>

</script>
<style>
#wrapper {
	width:1000px; height:700px; border:1px solid black;
}
#header {
	width:1000px; height:100px; border:1px solid black;
	
}
#content {
	widht:1000px; height:500px;  border:1px solid black;
	
}
#find_id{
	width:500px; height:350px;
	 margin-top:118px;
}
#find_pwd{
	 width:500px; height:350px;
	 position:absolute; top:220px; left:510px;
}
.find_id_header{
	text-size:20px; font:bold;
}
.find_id_button{
	width:150px; height:50px;
}
.find_pwd_button{
	width:150px; height:50px;
	margin-top:38px;
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

<div id = "find_id">
<form name="find_id" action = "loginfind"  method="post">
<input type="hidden" name ="wtype" value = "find_id"/>
	<p class="find_id_header">아이디 찾기</p>
	<p> 이름 <input type = "text" name = "name"/> </p>
	<br />
	<p> 전화번호 
		<select name="p1">
			<option value="010">010</option>
			<option value="011">011</option>
			<option value="016">016</option>
			<option value="019">019</option>
		</select> -
		<input type="text" name="p2" size="4" maxlength="4" /> -
		<input type="text" name="p3" size="4" maxlength="4" />
	</p>
	<br />
	<br />
	<br />
	<p> <input type = "submit" name = "find" value="아이디 찾기" class="find_id_button"/> </p>
</form>

</div>

<div id = "find_pwd">
<form name="find_pwd" action = "loginfind"  method="post">
<input type="hidden" name ="wtype" value = "find_pwd"/>
	<p class="find_id_header">비밀번호 찾기</p>
	
	<p> 이름 <input type = "text" name = "name"/> </p>
	<p> 아이디 <input type = "text" name = "id"/> </p>
	
	<p> 전화번호 
		<select name="p1">
			<option value="010">010</option>
			<option value="011">011</option>
			<option value="016">016</option>
			<option value="019">019</option>
		</select> -
		<input type="text" name="p2" size="4" maxlength="4" /> -
		<input type="text" name="p3" size="4" maxlength="4" />
	</p>
	<br />
	<p> <input type = "submit" name = "find" value="비밀번호 찾기" class="find_pwd_button"/> </p>
</form>
</div>

</div>

<div id = "fotter">
	<%@ include file="inc/footer.jsp" %>
	</div>

</div>
</body>
</html>
