<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)request.getAttribute("loginMember");
MemberInfo findMember = (MemberInfo)request.getAttribute("findInfo");

request.setCharacterEncoding("utf-8");
if(loginMember != null) {
   out.println("<script>");
   out.println("alert('잘못된 접근입니다');");
   out.println("history.back();");
   out.println("</script>");
   out.close();
}
if(findMember == null) {
	   out.println("<script>");
	   out.println("alert('해당 회원이 없습니다');");
	   out.println("history.back();");
	   out.println("</script>");
	   out.close();
	}


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
   function chkValue(frm) {
      var pwd = frm.pwd.value; 
      var pwd2 = frm.pwd2.value;
      
       if (pwd == "") {
         alert("비밀번호를 입력하세요.");      
         frm.pwd.focus();   return false;
      } else if (pwd.length < 4) {
        alert("비밀번호는 4자 이상 입력해야 합니다.");
        frm.pwd.select();   return false;
      } else if (pwd2 == "") {
         alert("비밀번호 확인을 입력하세요.");   
         frm.pwd2.focus();   return false;
      } else if (pwd != pwd2) {
         alert("비밀번호가 다릅니다.");   pwd2 = ""; 
         frm.pwd2.focus();   return false;
      } 
       
      return true;
   }
</script>
<style>
#wrapper {
	width:1000px; height:700px; border:1px solid black;
}
#header {
	width:1000px; height:100px; border:1px solid black;
	
}
#content {
	widht:1000px; height:500px; 
	
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
<%@ include file="inc/main_header.jsp" %>

<form name="update_pwd" action="loginfind" method="post" onsubmit="return chkValue(this);">
 <input type="hidden" name ="mid" value = "<%=findMember.getMi_id() %>"/> 
 <input type="hidden" name ="wtype" value = "update_pwd"/>

<div id="wrapper"> 
<div id="header">
	LOGO
</div>

<div id="content">


<div>LOGO</div>
<br/>
<div>비밀번호 변경</div>
<br/>
<div>
새 비밀번호 <input type = "password" name = "pwd"/>
<br/> 8~20자이내의 영문 대소문자, 숫자를 하나씩 포함해야 합니다.
</div>
<br/>
<div>
새 비밀번호 확인  <input type = "password" name = "pwd2"/>	
</div>
<br/>
<br/>
<div>
<input type = "submit" value = "변경" />	
</div>

</div>
	<%@ include file="inc/footer.jsp" %>
</div>
</form>
</body>
</html>
