<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");

if (loginMember == null) {
   out.println("<script>");
   out.println("alert('먼저 로그인을 해야 합니다.');");
   out.println("location.href = 'login_form.jsp?url=order_form.etc?id=" + pdtInfo.getPi_id() + "';");
   out.println("</script>");
   out.close();
}

if (pdtInfo == null) {
   out.println("<script>");
   out.println("alert('잘못된 경로로 들어오셨습니다.');");
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
<style>

#wrapper {width: 1200px; margin : 0 auto;}
.bd {
   width : 800px !important;
   min-width : 800px !important;
}
.bottombd {
   border-bottom : 1.5px solid black;
}
.leftbd {
   border-left : 1px solid black;
}
.bt {
   cursor : pointer;
}
</style>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function chkfrm(frm){
   var oiname = frm.oi_name.value;
   var oizip = frm.oi_zip.value;
   var oiaddr1 = frm.oi_addr1.value;
   var p2 = frm.p2.value;
   var p3 = frm.p3.value;
   var payment = frm.oi_payment.value;
   var chk = document.getElementById("chk").checked;
   
   if (oiname == ""){
      alert("받는분 성함은 필수입력사항입니다.");
      frm.oi_name.focus();
      return false;
   }
   if (oizip == ""){
      alert("우편번호는 필수입력사항입니다.");
      frm.oi_zip.focus();
      return false;
   }
   if (oiaddr1 == ""){
      alert("기본주소는 필수 입력사항입니다.");
      frm.oi_addr1.focus();
      return false;
   }
   if (p2 == ""){
      alert("연락처를 입력해주세요.");
      frm.p2.focus();
      return false;
   }
   if (p3 == ""){
      alert("연락처를 입력해주세요.");
      frm.p3.focus();
      return false;
   }
   if (payment == ""){
      alert("결제수단을 선택해주세요.");
      return false;
   }
   if (chk == false){
      alert("약관에 동의해주세요.");
      return false;
   }
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
                addr += extraAddr + " ";
            
            } else {
                addr += '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById("oi_zip").value = data.zonecode;
            document.getElementById("oi_addr1").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("oi_addr2").focus();
        }
    }).open();
}

function onlyNum(obj) {
   if (isNaN(obj.value )) {
      obj.value = "";
   }
}

function myaddress(frm){
   var chk = document.getElementById("myaddr").checked;
   var parr = "<%=loginMember.getMi_phone() %>";
   var parr = parr.split("-");
   if (chk == true) {
      frm.oi_name.value = '<%=loginMember.getMi_name() %>';
      frm.oi_zip.value = '<%=loginMember.getMi_zip() %>';
      frm.oi_addr1.value = '<%=loginMember.getMi_addr1() %>';
      frm.oi_addr2.value = '<%=loginMember.getMi_addr2() %>';
      
      frm.p1.value = parr[0];
      frm.p2.value = parr[1];
      frm.p3.value = parr[2];
   } else {
      frm.oi_name.value = '';
      frm.oi_zip.value = '';
      frm.oi_addr1.value = '';
      frm.oi_addr2.value = '';
      frm.p1.value = "010";
      frm.p2.value = '';
      frm.p3.value = '';
   }
}

function chkPoint(pnt){
   var pntv = pnt.value;
   var avlpoint = <%=loginMember.getMi_point()%>;
   var price = <%=pdtInfo.getPw_price() %>;
   
   if (avlpoint < pntv){
      document.order.oi_usepnt.value = avlpoint;
      alert("사용 가능한 포인트보다 높게 입력할 수 없습니다.");
   }
   if (price + 3000 < pntv) {
      document.order.oi_usepnt.value = price + 3000;
      alert("상품 가격보다 높게 입력할 수 없습니다.");
   }
   
   if (pntv == ""){
      document.order.oi_usepnt.value = 0;
      pntv = 0;
   }
   
   document.getElementById("usepnt").innerHTML = pntv;
   document.getElementById("oipay").innerHTML = <%=pdtInfo.getPw_price()%> + 3000 - pntv;
}

function chgpnt(value){
   if (value == 0){
      document.order.oi_usepnt.value = "";
   }
}

$(document).ready(function(){
   document.getElementById("oipay").innerHTML = <%=pdtInfo.getPw_price() %> + 3000;
});
</script>
</head>
<body>
<div id="wrapper">
<%@ include file="../../inc/main_header.jsp" %>
<form name = "order" action = "order_proc.etc" method = "post" onsubmit="return chkfrm(this);">
<input type = "hidden" name = "pisize" value = "<%=pdtInfo.getPi_size() %>" />
<input type = "hidden" name = "piquaility" value = "<%=pdtInfo.getPi_quaility() %>" />
<input type = "hidden" name = "bname" value = "<%=pdtInfo.getB_name() %>" />
<input type = "hidden" name = "miid" value = "<%=loginMember.getMi_id() %>" />
<input type = "hidden" name = "piid" value = "<%=pdtInfo.getPi_id() %>" />
<input type = "hidden" name = "oi_pdtname" value = "<%=pdtInfo.getPi_name() %>" />
<input type = "hidden" name = "oi_pdtimg" value = "<%=pdtInfo.getPp_top() %>" />
<input type = "hidden" name = "pi_price" value = "<%=pdtInfo.getPw_price() %>" />
   <table width = "800" cellpadding = "5" cellspacing = "0" align = "center" class = "bd  table table-bordered table-hover table-condensed" style = "border-collapse: collapse;">
      <tr height = "20"></tr>
      <tr>
         <th colspan = "2" align = "left" class = "bottombd">
            상품 주문 정보
         </th>
      </tr>
      <tr>
         <td align = "center" width = "20%" class = "bottombd">
            <img src = "product/shoePic/<%=pdtInfo.getPp_top() %>" width="80" height="90"/>
         </td>
         <td width = "*" class = "bottombd leftbd">
            
            상품이름 : <%=pdtInfo.getPi_name() %><br />
            상품브랜드 : <%=pdtInfo.getB_name() %><br />
            상품사이즈 : <%=pdtInfo.getPi_size() %><br />
            상품퀄리티 : <%=pdtInfo.getPi_quaility() %>
         </td>
      </tr>
      <tr height = "20"></tr>
      <tr>
         <th colspan = "2" align = "left" class = "bottombd">
            주문자 정보
         </th>
      </tr>
      <tr>
         <td colspan = "2" class = "bottombd leftbd">
            <table width = "800">
               <tr>
                  <th width = "20%" align = "left">
                     이름
                  </th>
                  <td width = "*">
                     <%=loginMember.getMi_name() %>
                  </td>
               </tr>
               <tr>
                  <th align = "left">
                     전화번호
                  </th>
                  <td width = "*">
                     <%=loginMember.getMi_phone() %>
                  </td>
               </tr>
               <tr>
                  <th align = "left">
                     이메일
                  </th>
                  <td width = "*">
                     <%=loginMember.getMi_email() %>
                  </td>
               </tr>
            </table>
         </td>
      </tr>
      <tr height = "20"></tr>
      <tr>
         <th colspan = "2" align = "left" class = "bottombd">
            수취인 정보
         </th>
      </tr>
      <tr>
         <td colspan = "2" class = "bottombd">
            <table width = "800">
               <tr>
                  <th width = "20%" align = "left">
                     받는분 성함
                  </th>
                  <td width = "*">
                      <input type = "text" name = "oi_name" maxlength = "20" />
                      <span style = "float : right;"><input type = "checkbox" onclick = "myaddress(this.form);" id = "myaddr" name = "myaddr"/><label for = "myaddr" style = "align : right;">주문자와 동일</label></span><br/>
                  </td>
               </tr>
               <tr>
                  <th align = "left">
                     우편번호
                  </th>
                  <td>
                     <input type = "text" name = "oi_zip" id = "oi_zip" size = "5" maxlength = "5" />
                     <input type = "button" name = "findZip" value = "우편번호 찾기" onclick = "postcode();" />
                  </td>
               </tr>
               <tr>
                  <th align = "left">
                     받는분 주소
                  </th>
                  <td>
                     <table width = "*">
                        <tr>
                           <td>
                              <input type = "text" name = "oi_addr1" id = "oi_addr1" size = "40" placeholder = "기본주소" maxlength = "50" />
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <input type = "text" name = "oi_addr2" id = "oi_addr2" size = "30" placeholder = "상세주소" maxlength = "50" />
                           </td>
                        </tr>
                     </table>
                  </td>
               </tr>
               <tr>
                  <th width = "20%" align = "left">
                     연락처
                  </th>
                  <td width = "*">
                     <table>
                        <tr>
                           <td>
                              <select name = "p1">
                                 <option value = "010">010</option>
                                 <option value = "011">011</option>
                                 <option value = "016">016</option>
                                 <option value = "019">019</option>
                              </select> -
                              <input type = "text" name = "p2" size = "4" maxlength = "4" onkeyup = "onlyNum(this);" /> -
                              <input type = "text" name = "p3" size = "4" maxlength = "4" onkeyup = "onlyNum(this);" />
                           </td>
                        </tr>
                     </table>
                  </td>
               </tr>
               <tr>
                  <th align = "left">
                     배송요청사항
                  </th>
                  <td>
                     <input type = "text" name = "oi_comment" size = "70" maxlength = "100" />
                  </td>
               </tr>
            </table>
         </td>
      </tr>
      <tr height = "20"></tr>
      <tr>
         <th colspan = "2" align = "left" class = "bottombd">
            포인트
         </th>
      </tr>
      <tr>
         <th width = "20%" align = "left">
            보유 포인트
         </th>
         <td width = "*" class = "leftbd">
            <%=loginMember.getMi_point() %> point
         </td>
      </tr>
      <tr>
         <th width = "20%" align = "left" class = "bottombd" name = "exitpnt">
            사용할 포인트
         </th>
         <td width = "*" class = "bottombd leftbd">
            <input type = "text" name = "oi_usepnt" id = "usepoint" onkeyup = "onlyNum(this); chkPoint(this);" onblur = "chkPoint(this);" onfocus = "chgpnt(this.value);" size = "5" value = "0" /> point
         </td>
      </tr>
      <tr height = "20"></tr>
      <tr>
         <th colspan = "2" align = "left" class = "bottombd">
            결제정보
         </th>
      </tr>
      <tr>
         <th width = "20%" align = "left">
            상품 금액
         </th>
         <td width = "*" align = "right" class = "leftbd">
            <%=pdtInfo.getPw_price() %>원
         </td>
      </tr>
      <tr>
         <th width = "20%" align = "left">
            배송비
         </th>
         <td width = "*" align = "right" class = "leftbd">
            + <span id = "oi_delipay">3000</span>원
         </td>
      </tr>
      <tr>
         <th width = "20%" align = "left" class = "bottombd">
            사용 포인트
         </th>
         <td width = "*" align = "right" class = "bottombd leftbd">
            <span id = "usepnt">0</span> 포인트
         </td>
      </tr>
      <tr>
         <th width = "20%" align = "left" class = "bottombd">
            결제 금액
         </th>
         <td width = "*" align = "right" class = "bottombd leftbd">
            <span id = "oipay">0</span>원
         </td>
      </tr>
      <tr height = "20"></tr>
      <tr>
         <th colspan = "2" align = "left" class = "bottombd">
            결제 수단
         </th>
      </tr>
      <tr>
         <td colspan = "2" class = "bottombd">
            <input type = "radio" name = "oi_payment" id = "card" value = "a" /><label for = "card">신용카드</label>&nbsp;&nbsp;&nbsp;
            <input type = "radio" name = "oi_payment" id = "phone" value = "b" /><label for = "phone">핸드폰결제</label>&nbsp;&nbsp;&nbsp;
            <input type = "radio" name = "oi_payment" id = "kakao" value = "c" /><label for = "kakao">카카오페이</label>
         </td>
      </tr>
      <tr height = "20"></tr>
      <tr>
          <th align = "left" colspan = "2" class = "bottombd">
             약관
          </th>
       </tr>
       <tr>
          <td align = "left" colspan = "2">
            <div id="agreement1" style="width:'*'px; height: 100px; overflow: auto; border : 1px solid black;">
             약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관
            내용<br /> 약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관
            내용<br />약관 내용<br /> 약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관
            내용<br />약관 내용<br />약관 내용<br />
            </div>
         </td>
      </tr>
      <tr>
         <td colspan = "2">
            <input type = "checkbox" name = "chk" id = "chk" /><label for = "chk">위 약관에 동의합니다.</label>
         </td>
      </tr>
      <tr height = "20"></tr>
      <tr>
         <td colspan = "2" align = "center">
            <div class="form-group" align = "center">
                <button type="button" class="btn btn-secondary btn-lg" onclick = "history.back();">주문취소</button>
                <button type="submit" class="btn btn-secondary btn-lg">주문하기</button>
             </div>
         </td>
      </tr>
   </table>
</form>
</div>
</body>
</html>