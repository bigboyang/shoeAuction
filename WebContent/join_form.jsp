<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");

request.setCharacterEncoding("utf-8");
Calendar today = Calendar.getInstance();
int year = today.get(Calendar.YEAR); //올해 연도
int month = today.get(Calendar.MONTH) + 1; //현재 월
int day = today.get(Calendar.DATE); //현재 일

String[] arrDomain = new String[5];
arrDomain[0] = "naver.com";
arrDomain[1] = "nate.com";
arrDomain[2] = "gmail.com";
arrDomain[3] = "daum.com";
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
		#footer { position:relative; left:500px; }
	
</style>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	function chkValue(frm) {
	   var name = frm.mi_name; 
	   var id = frm.mi_id.value;
	   var pwd = frm.mi_pwd.value; 
	   var pwd2 = frm.mi_pwd2.value;
	   var p2 = frm.p2.value;
	   var p3 = frm.p3.value; 
	   var e1 = frm.e1.value; 
	   var e3 = frm.e3.value 
	   var addr2 = frm.mi_addr2.value;
	   
	   if (name.value == "") {
	      alert("이름을 입력하세요.");   
	      name.focus();      return false; 
	   } else if (id == "") {
	      alert("아이디를 입력하세요.");   
	      frm.mi_id.focus();      return false;
	   } else if (id.length < 4) {
	     alert("아이디는 4자 이상 입력해야 합니다.");   
	     frm.mi_id.select();   return false;
	   } else if (pwd == "") {
	      alert("비밀번호를 입력하세요.");      
	      frm.mi_pwd.focus();   return false;
	   } else if (pwd.length < 4) {
	     alert("비밀번호는 4자 이상 입력해야 합니다.");
	     frm.mi_pwd.select();   return false;
	   } else if (pwd2 == "") {
	      alert("비밀번호 확인을 입력하세요.");   
	      frm.mi_pwd2.focus();   return false;
	   } else if (pwd != pwd2) {
	      alert("비밀번호가 다릅니다.");   pwd2 = ""; 
	      frm.mi_pwd2.focus();   return false;
	   } else if (p2 == "") {
	      alert("전화번호를 입력하세요.");      
	      frm.p2.focus();      return false;
	   } else if (p3 == "") {
	      alert("전화번호를 입력하세요.");      
	      frm.p3.focus();      return false;
	   } else if (e1 == "") {
	      alert("이메일을 입력하세요.");      
	      frm.e1.focus();      return false;
	   } else if (e3 == "") {
	      alert("이메일을 입력하세요.");      
	      frm.e3.focus();      return false;
	   } else if (addr2 == "") {
	      alert("상세 주소를 입력하세요.");      
	      frm.addr2.focus();   return false;
	   }
	   
	   return true;
	}

	function setDomain(domain) {
		var e3 = document.frmJoin.e3;
		if (domain == "direct") {
			e3.value = "";		e3.focus();
		} else {
			e3.value = domain;
		}
	}

	function chkAgree(frm) {
		var agree = frm.agree.value;
		if (agree == "n") {
			alert("약관에 동의해야 회원가입이 가능합니다.");
			frm.agree.focus();		return false;
		}
	
		return true;
	}
    
    function postcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = '(' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("mi_addr2").value = extraAddr + " ";
                
                } else {
                    document.getElementById("mi_addr2").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById("mi_zip").value = data.zonecode;
                document.getElementById("mi_addr1").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("mi_addr2").focus();
            }
        }).open();
    }
</script>
</head>
<body>
<%@ include file="inc/main_header.jsp" %>

<h2 align="center">회원 가입</h2>
<form name="frmJoin" action="proc.mem" method="post" onsubmit="return chkValue(this);">
<input type="hidden" name="wtype" value="in" />
<table border="1" cellpadding="5" width="1000" align="center">
<tr>
<th width="20%">이름</th>
<td width="*"><input type="text" name="mi_name" /></td>
</tr>
<tr>
<th width="20%">아이디</th>
<td width="*"><input type="text" name="mi_id" /><br />
8~20자 이내의 영문 대소문자,숫자만 입력 가능합니다.</td>
</tr>
<tr>
<th>비밀번호</th>
<td><input type="password" name="mi_pwd" /><br />
8~20자 이내의 영문 대소문자,숫자를 하나씩 포함해야 합니다.</td>
</tr>
<tr>
<th>비밀번호 확인</th>
<td><input type="password" name="mi_pwd2" /></td>
</tr>
<tr>
<th>전화번호</th>
<td>
   <select name="p1">
      <option value="010">010</option>
      <option value="011">011</option>
      <option value="016">016</option>
      <option value="019">019</option>
   </select> - 
   <input type="text" name="p2" size="4" maxlength="4" /> - 
   <input type="text" name="p3" size="4" maxlength="4" />
</td>
</tr>
<tr>
<th>이메일</th>
<td>
   <input type="text" name="e1" /> @ 
   <select name="e2" onchange="setDomain(this.value);">
      <option value="">도메인 선택</option>
      <% for (int i = 0; i < arrDomain.length; i++) { %>
      <option value="<%=arrDomain[i]%>"><%=arrDomain[i]%></option>
      <% } %>
      <option value="direct">직접 입력</option>
   </select> 
   <input type="text" name="e3" />
   <input type="button" value="인증메일 발송" />
</td>
</tr>
<tr>
<th>주소</th>
<td><input type="text" name="mi_zip" id = "mi_zip" size = "5"/>&nbsp;&nbsp;<input type="button" value="우편번호 찾기" onclick = "postcode();"/><br />
<input type="text" name="mi_addr1" id = "mi_addr1" size = "30" placeholder="일반주소" />
<input type="text" name="mi_addr2" id = "mi_addr2" size = "30" placeholder="상세주소" />
</tr>
<tr>
<th>거래계좌(선택)</th>
<td>
   <select name="mi_rebank">
   	  <option value = "">은행선택</option>
      <option value="국민">국민은행</option>
      <option value="우리">우리은행</option>
      <option value="농협">농협</option>
      <option value="카카오뱅크">카카오뱅크</option>
   </select><br />
   <input type="text" name="mi_account" />
   <p>
   상품을 판매 할 경우 반드시 필요합니다.<br />
   "-"를 제외하고 입력해주세요.<br />
   잘못된 계좌를 입력 시 본사는 책임을 지지 않습니다.
   </p>
</td>
</tr>
</table>
<br />

<table cellpadding="5" width="700" align="center">
<tr>
<th width="20%" rowspan="3" valign="top">약관안내</th>
<td>
   <input type="radio" name="chk1" value="y" checked="checked" />
   이용약관(필수), 개인정보처리동의(필수)에 전부 동의 합니다.
</td>
</tr>
<tr>
<td>
   <input type="radio" name="chk2" value="y" checked="checked" />이용약관(필수)
   <div id="agreement1" style="width:'*'px; height: 100px; overflow: auto;">
      약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관
      내용<br /> 약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관
      내용<br />약관 내용<br /> 약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관
      내용<br />약관 내용<br />약관 내용<br />
   </div>
</td>
</tr>
<tr>
<td>
   <input type="radio" name="chk3" value="y" checked="checked" />개인정보처리동의(필수)
   <div id="agreement2" style="width:'*'px; height: 100px; overflow: auto;">
      약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관
      내용<br /> 약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관
      내용<br />약관 내용<br /> 약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관
      내용<br />약관 내용<br />약관 내용<br />
   </div>
</td>
</tr>
<tr>
<th>이메일 수신</th>
<td>
   <input type="radio" name="mi_ismail" value="y" checked="checked" />이메일 수신&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <input type="radio" name="mi_ismail" value="n" />이메일 수신안함
</td>
</tr>
<tr>
<th>SMS 수신</th>
<td>
   <input type="radio" name="mi_sms" value="y" checked="checked" />SMS 수신&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <input type="radio" name="mi_sms" value="n" />SMS 수신안함
</td>
</tr>
</table>
<br />
<div width="700" align="center">
<input type="submit" value="회원 가입" /> &nbsp;&nbsp;
<input type="reset" value="취소" />
</div>
</form>
<%@ include file="inc/footer.jsp" %>

</body>
</html>