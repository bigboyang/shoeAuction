<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
   request.setCharacterEncoding("utf-8");

if (loginMember == null) {
	out.println("<script>");
	out.println("alert('로그인 후 사용하실 수 있습니다.');");
	out.println("location.href='../login_form.jsp?url=member/mypage.mem';");
	out.println("</script>");
	out.close();
}

String[] arrPhone = loginMember.getMi_phone().split("-");
String[] arrEmail = loginMember.getMi_email().split("@");

String[] arrDomain = new String[5];
arrDomain[0] = "naver.com";
arrDomain[1] = "nate.com";
arrDomain[2] = "gmail.com";
arrDomain[3] = "daum.net";
arrDomain[4] = "yahoo.com";
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
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   #outer {width: 1200px; align:center; position:absolute; padding-bottom:150px; padding-left:150px; }
   #center {float:left; width:700px; margin-top:0px;}
   #footer {position:absolute; left:250px; bottom:-500px;}
</style>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function byeBye() {
	if (confirm("정말 탈퇴하시겠습니까?\n탈퇴시 적립된 포인트가 모두 소멸됩니다.")) {
		location.href = "proc.mem?wtype=del";
	}
}

function postcode() {
    new daum.Postcode({
        oncomplete: function(data) {
           var addr = ''; 
            var extraAddr = '';
            
           if (data.userSelectedType === 'R') { 
                addr = data.roadAddress;
            } else { 
                addr = data.jibunAddress;
            }

            if(data.userSelectedType === 'R'){
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if(extraAddr !== ''){
                    extraAddr = '(' + extraAddr + ')';
                }
                document.getElementById("mi_addr2").value = extraAddr;              
            } else {
                document.getElementById("mi_addr2").value = '';
            }
            document.getElementById("mi_zip").value = data.zonecode;
            document.getElementById("mi_addr1").value = addr;
            document.getElementById("mi_addr2").focus();
        }
    }).open();
}
</script>
</head>
<body>
<div id="wrapper">
<%@ include file="../../inc/main_header.jsp" %>
<div id="outer">
	<%@ include file="../../inc/mypage_left.jsp" %>
	<div id="center">
		<h2>정보 수정</h2>
		<form name="frmJoin" action="infochg.mem" method="post" >
		<input type="hidden" name="wtype" value="up" />
		<table border="1" cellpadding="5" width="700" class = "table table-bordered table-hover table-condensed" >
		<tr>
		<th width="20%">이름</th>
		<td width="*"><%=loginMember.getMi_name() %></td>
		</tr>
		
		<tr>
		<th width="20%">아이디</th>
		<td width="*"><%=loginMember.getMi_id() %></td>
		</tr>
		
		<tr>
		<th>전화번호</th>
		<td>
			<select name="p1">
				<option value="010" <% if (arrPhone[0].equals("010")) { %>selected="selected"<% } %>>010</option>
				<option value="011" <% if (arrPhone[0].equals("011")) { %>selected="selected"<% } %>>011</option>
				<option value="016" <% if (arrPhone[0].equals("016")) { %>selected="selected"<% } %>>016</option>
				<option value="019" <% if (arrPhone[0].equals("019")) { %>selected="selected"<% } %>>019</option>
			</select> -
			<input type="text" name="p2" size="4" maxlength="4" value="<%=arrPhone[1] %>" /> -
			<input type="text" name="p3" size="4" maxlength="4" value="<%=arrPhone[2] %>" />
		</td>
		</tr>
		
		<tr>
		<th>이메일</th>
		<td>
			<input type="text" name="e1" value="<%=arrEmail[0] %>" /> @
			<select name="e2" onchange="setDomain(this.value);">
				<option value="">도메인 선택</option>
		<% for (int i = 0 ; i < arrDomain.length ; i++) { %>
				<option value="<%=arrDomain[i] %>" <% if (arrEmail[1].equals(arrDomain[i])) { %>selected="selected"<% } %>><%=arrDomain[i] %></option>
		<% } %>
				<option value="direct">직접 입력</option>
			</select>
			<input type="text" name="e3" value="<%=arrEmail[1] %>" />
		</td>
		</tr>
		
		<tr>
		<th>주소</th>
		<td><input type="text" name="mi_zip"  id = "mi_zip" value="<%=loginMember.getMi_zip() %>" size="5"/>&nbsp;&nbsp;<input type="button" value="우편번호 찾기" onclick = "postcode();"/><br /><br />
		   <input type="text" name="mi_addr1" id = "mi_addr1" value="<%=loginMember.getMi_addr1() %>" />
		   <input type="text" name="mi_addr2" id = "mi_addr2" value="<%=loginMember.getMi_addr2() %>" />
		</td>
		</tr>
		
		<tr>
		<th>거래계좌(선택)</th>
		<td><select name="mi_rebank" >
				<option value="국민">국민은행</option>
				<option value="우리">우리은행</option>
				<option value="농협">농협</option>
				<option value="카카오뱅크">카카오뱅크</option>
			</select><br />
		<input type="text" name="mi_account" value=<%=loginMember.getMi_account() %> /><br />
		상품을 판매 할 경우 반드시 필요합니다.<br />
		"-"를 제외하고 입력해주세요.<br />
		잘못된 계좌를 입력 시 본사는 책임을 지지 않습니다.
		</td>
		</tr>
		
		<tr>
		<th>SNS 수신</th>
		<td>
		<input type="radio" name="mi_issms" value="y" <%if(loginMember.getMi_issms().equals("y")) { %> checked="checked" <% } %> />SMS 수신 
		<input type="radio" name="mi_issms" value="n" <%if(loginMember.getMi_issms().equals("n")) { %> checked="checked" <% } %> />SMS 수신안함
		</td>
		</tr>
		
		<tr>
		<th>이메일 수신</th>
		<td>
		<input type="radio" name="mi_ismail" value="y" <%if(loginMember.getMi_ismail().equals("y")) { %> checked="checked" <% } %> />이메일 수신 
		<input type="radio" name="mi_ismail" value="n" <%if(loginMember.getMi_ismail().equals("n")) { %> checked="checked" <% } %> />이메일 수신안함
		</td>
		</tr>
		
		</table>
		<p style="width:700px; text-align:center;">
			<input type="submit" value="정보 수정" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" value="다시 입력" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="회원 탈퇴" onclick="byeBye();" />
		</p>
		</form>
	</div>
	<%@ include file="../../inc/footer.jsp" %>
</div>
</div>
</body>
</html>
