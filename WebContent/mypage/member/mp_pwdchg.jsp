<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
if (loginMember == null) {
   out.println("<script>");
   out.println("alert('로그인 후 사용하실 수 있습니다.');");
   out.println("location.href='login_form.jsp?url=mypage';");
   out.println("</script>");
   out.close();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>

   a:link { color:#4f4f4f; text-decoration:none; }
   a:visited { color:#8a2e91; text-decoration:none; }
   a:hover { color:#000; text-decoration:none; font-weight:bold; }
   a:focus { color:#000; text-decoration:none; font-weight:bold; }
   a:active { color:#00; text-decoration:none; font-weight:bold; }
#wrapper {width: 1200px; margin : 0 auto;}
       #outer {width: 1200px; align:center; position:absolute; padding-bottom:150px; padding-left:150px; }
   #center {float:left; width:700px; margin-top:0px;}
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   
</style>
<script>
function chkValue(frm) {
   var oldpwd = frm.oldpwd.value;
    var pwd = frm.pwd.value; 
    var pwd2 = frm.pwd2.value;
    
     if (oldpwd == "") {
       alert("이전 비밀번호를 입력하세요.");      
       frm.oldpwd.focus();   return false;
    } else if (oldpwd.length < 4) {
      alert("비밀번호는 4자 이상 입력해야 합니다.");
      frm.oldpwd.select();   return false;
    } else if (oldpwd !=  <%= loginMember.getMi_pwd()%>) {
       alert("이전 비밀번호가 다릅니다.");   
       frm.oldpwd.focus();   return false;
    } else if (pwd == "") {
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
</head>
<body>
<div id="wrapper">
<%@ include file="../../inc/main_header.jsp" %>
<div id="outer">
   <%@ include file="../../inc/mypage_left.jsp" %>
      <div id="center">
         <form name="frm" action="pwdchg.mem" method="post" onsubmit="return chkValue(this);" >
         <input type="hidden" name="id" value="<%=loginMember.getMi_id() %>" />
      <table width="500">
         <div class="form-group">
           <label for="oldpwd" class="form-label mt-4">이전 비밀번호</label>
           <input type="password" maxlength="20" class="form-control" id="oldpwd" name = "oldpwd" placeholder="Password">
         </div>
         <div class="form-group">
           <label for="pwd" class="form-label mt-4">바꿀비밀번호</label>
           <input type="password" maxlength="20" class="form-control" id="pwd" name = "pwd" placeholder="Password">
         </div>
         <div class="form-group">
           <label for="pwd2" class="form-label mt-4">비밀번호확인</label>
           <input type="password" maxlength="20" class="form-control" id="pwd2" name = "pwd2" placeholder="Password">
         </div>
      <div class="form-group" align = "center">
          <button type="button" class="btn btn-secondary" onclick = "history.back();">취소</button>
          <button type="submit" class="btn btn-secondary">변경</button>
      </div>
      
      
      </table>
      </form>
   </div>
</div>
</div>
</body>
</html>