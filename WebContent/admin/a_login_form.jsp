<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%

request.setCharacterEncoding("utf-8");
String url = request.getParameter("url");   // 로그인 후 이동할 페이지 주소(없는 경우도 있음)
if (url == null)   url = "";   // url값이 없으면 빈 문자열로 변환하여 사용

String tmpID = "", cook = request.getHeader("Cookie");

if (cook != null) {
   Cookie[] cookies = request.getCookies(); 
   for (int i = 0 ; i < cookies.length ; i++) {
      if (cookies[i].getName().equals("saveID"))   tmpID = cookies[i].getValue();
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
   var aiid = frm.aiid.value;   var pwd = frm.pwd.value;
   if (aiid == "") {
      alert("아이디를 입력하세요.");   frm.aiid.focus();   return false;
   } else if (aiid.length < 4) {
      alert("아이디는 4자 이상 입력해야 합니다.");   frm.aiid.select();   return false;
   }
   if (pwd == "") {
      alert("비밀번호를 입력하세요.");
      frm.pwd.focus();   return false;
   } else if (pwd.length < 4) {
      alert("비밀번호는 4자 이상 입력해야 합니다.");
      frm.pwd.select();   return false;
   }

   return true;
}
</script>
<style>
#wrapper {
   width:1000px; height:700px; margin:0 auto;
}
#header {
   width:1000px; height:100px; border:1px solid black;
   
}
#content {
   widht:1000px; height:500px; 
   
}
#fotter {
   width:1000px; height:100px; border:1px solid black;
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
   
   a:link { color:#4f4f4f; text-decoration:none; }
   a:visited { color:#8a2e91; text-decoration:none; }
   a:hover { color:#000; text-decoration:none; font-weight:bold; }
   a:focus { color:#000; text-decoration:none; font-weight:bold; }
   a:active { color:#00; text-decoration:none; font-weight:bold; }
</style>
</head>
<body>

<div id="wrapper"> 
<div id="content">

<form name="frmLogin" action="../alogin" method="post" onsubmit="return chkVal(this);">
<input type="hidden" name="url" value="<%=url %>" />
<table cellpadding="5" id="login">
<tr>
   <th>LOGO</th>
   <th></th>
</tr>

<tr>
<th></th>
<td><input type="text" style = "text-align:center;" name="aiid" id="aiid" maxlength="20" value="admin0" class="textinput"/></td>
</tr>

<tr>
<th></th>
<td><input type="password" style = "text-align:center;" name="pwd" id="pwd" maxlength="20" value="1111" class="textinput"/></td>
</tr>

<tr>
<td></td>
<td class="a">
 아이디 저장<input type="checkbox" name="isSave" value="y" <% if (!tmpID.equals("")) { %>checked="checked"<% } %> /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <a href="find_member.jsp">아이디/비밀번호 찾기</a> 
</td>
</tr>

<tr>
<th></th>
<td>
   <input type="submit" value="로그인" class="textinput"/>&nbsp;&nbsp;
</td>
</tr>
<tr>
<td></td>
</tr>
</table>
</form>

</div>
</div>
</body>
</html>