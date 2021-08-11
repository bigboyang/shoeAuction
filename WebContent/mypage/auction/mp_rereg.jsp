<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "vo.*" %>
<%
request.setCharacterEncoding("utf-8");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");
if (loginMember == null) {
	out.println("<script>");
	out.println("alert('먼저 로그인을 해야 합니다.');");
	out.println("location.href = 'login_form.jsp?url=mp_rereg_form.auc';");	
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
.myaddr { align : center; }
   
#wrapper {width: 800px; margin : 0 auto;}
</style>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function chkfrm(frm){
	var price = frm.pi_sprice.value;
	var desc = frm.pi_desc.value;
	var zip = frm.pi_zip.value;
	var addr2 = frm.pi_addr2.value;
	var chk = frm.chk.checked;
	
	if (price == ""){
		alert("입찰시작금액은 필수입력사항입니다.");
		frm.pi_sprice.focus();
		return false;
	} else if (desc == ""){
		alert("상품설명은 필수입력사항입니다.");
		frm.pi_desc.focus();
		return false;
	} else if (zip == ""){
		alert("반송 받을 주소는 필수 입력사항입니다.");
		return false;
	} else if (addr2 == ""){
		alert("반송 받을 주소는 필수 입력사항입니다.");
		frm.pi_addr2.focus();
		return false;
	} else if (chk == false){
		alert("약관에 동의해주세요.");
		return false;
	} else {
		if (confirm("등록하시겠습니까?")) {
			return true;
		} else {
			return false;
		}
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
            document.getElementById("mi_zip").value = data.zonecode;
            document.getElementById("mi_addr1").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("mi_addr2").focus();
            document.getElementById("myaddr").checked = false;
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
	
	if (chk == true) {
		frm.pi_zip.value = '<%=loginMember.getMi_zip() %>';
		frm.pi_addr1.value = '<%=loginMember.getMi_addr1() %>';
		frm.pi_addr2.value = '<%=loginMember.getMi_addr2() %>';
	} else {
		frm.pi_zip.value = '';
		frm.pi_addr1.value = '';
		frm.pi_addr2.value = '';
	}
}

function mychk(frm) {
	frm.myaddr.checked = false;
}

</script>
</head>
<body>
<div id="wrapper">
<%@ include file="../../inc/main_header.jsp" %>
<br />
<div id="outer">
	<div id="center">
		<form name="aucRereg" action="mp_rereg_proc.auc" method="post" onsubmit="return chkfrm(this);">
		<input type="hidden" name="old_piid" value="<%=pdtInfo.getPi_id() %>" />
		<table width="800" cellpadding="5" cellspacing="0" align="center" class = "table table-bordered table-hover table-condensed">
			<tr>
			<th width="20%">기본정보	</th>
			<th width="*" align="right">
				<span style="color:red;">*</span> 표시는 필수 입력사항입니다.
			</th>
			</tr>
			<tr>
			<th>상품 등급</th><input type="hidden" name="pi_quaility" value="<%=pdtInfo.getPi_quaility() %>" />
			<td><%=pdtInfo.getPi_quaility() %></td>
			</tr>
			<tr>
			<th>브랜드</th><input type="hidden" name="b_id" value="<%=pdtInfo.getB_id() %>" />
			<td><%=pdtInfo.getB_name() %></td>
			</tr>
			<tr>
			<th>상품이름</th><input type="hidden" name="pi_name" value="<%=pdtInfo.getPi_name() %>" />
			<td><%=pdtInfo.getPi_name() %></td>
			</tr>
	<!-- 		
			<tr>
			<th>상품태그</th>
			<td>
				<input type="text" name="pi_tag" placeholder="예)#ex1 #ex2 #ex3 형식으로 #으로 나뉩니다.">
			</td>
			</tr>
	-->
			<tr>
			<th>사이즈</th><input type="hidden" name="pi_size" value="<%=pdtInfo.getPi_size() %>" />
			<td><%=pdtInfo.getPi_size() %></td>
			</tr>
			<tr>
			<th><span style = "color : red;">*</span> 입찰시작금액</th>
			<td>
				<input type = "text" name = "pi_sprice" value="<%=pdtInfo.getPi_sprice() %>" onkeyup="onlyNum(this);" maxlength="8" style="text-align:right;"/> 원
			</td>
			</tr>
			<tr>
			<th><span style = "color : red;">*</span> 입찰기간	</th>
			<td>
				<input type="radio" name="pi_period" value="1" id="period1" <% if (pdtInfo.getPi_period() == 1) { %>checked="checked"<% } %> />
				<label for="period1">1일</label>
				<input type="radio" name="pi_period" value="3" id="period2" <% if (pdtInfo.getPi_period() == 3) { %>checked="checked"<% } %> />
				<label for="period2">3일</label>
				<input type="radio" name="pi_period" value="7" id="period3" <% if (pdtInfo.getPi_period() == 7) { %>checked="checked"<% } %> />
				<label for="period3">7일</label>
				<input type="radio" name="pi_period" value="15" id="period4" <% if (pdtInfo.getPi_period() == 15) { %>checked="checked"<% } %> />
				<label for="period4">15일</label>
				<input type="radio" name="pi_period" value="30" id="period5" <% if (pdtInfo.getPi_period() == 30) { %>checked="checked"<% } %> />
				<label for="period5">30일</label>
			</td>
			</tr>
			<tr>
			<th>상품 이미지</th>
			<td>
				※ 사진은 기타를 제외하고 필수로 하나를 첨부해야 합니다.<br />
				사진은 최대 12개까지 첨부가 가능합니다. 사진은 최대 5MB까지 가능합니다.
			</td>
			</tr>
			<tr>
			<th>상품 평면</th>
			<td>
				<input type="hidden" name="pp_top" value="<%=pdtInfo.getPp_top() %>" />
				<img src="product/shoePic/<%=pdtInfo.getPp_top() %>" height="80" weight="70" />
			</td>
			</tr>
			<tr>
			<th>상품 정면</th>
			<td>
				<input type="hidden" name="pp_front" value="<%=pdtInfo.getPp_front() %>" />
				<img src="product/shoePic/<%=pdtInfo.getPp_front() %>" height="80" weight="70" />
			</td>
			</tr>
			<tr>
			<th>상품 후면</th>
			<td>
				<input type="hidden" name="pp_back" value="<%=pdtInfo.getPp_back() %>" />
				<img src="product/shoePic/<%=pdtInfo.getPp_back() %>" height="80" weight="70" />
			</td>
			</tr>
			<tr>
			<th>상품 좌측</th>
			<td>
				<input type="hidden" name="pp_left" value="<%=pdtInfo.getPp_left() %>" />
				<img src="product/shoePic/<%=pdtInfo.getPp_left() %>" height="80" weight="70" />
			</td>
			</tr>
			<tr>
			<th>상품 우측</th>
			<td>
				<input type="hidden" name="pp_right" value="<%=pdtInfo.getPp_right() %>" />
				<img src="product/shoePic/<%=pdtInfo.getPp_right() %>" height="80" weight="70" />
			</td>
			</tr>
			<tr>
			<th>상품 밑창</th>
			<td>
				<input type="hidden" name="pp_bottom" value="<%=pdtInfo.getPp_bottom() %>" />
				<img src="product/shoePic/<%=pdtInfo.getPp_bottom() %>" height="80" weight="70" />
			</td>
			</tr>
			<% 
			String[] arrPic = {pdtInfo.getPp_etc1(), pdtInfo.getPp_etc2(), pdtInfo.getPp_etc3(), pdtInfo.getPp_etc4(), pdtInfo.getPp_etc5(), pdtInfo.getPp_etc6()};
			for (int i = 1; i <= 6; i++){ %>
			<tr>
			<th>기타이미지<%=i %></th>
			<td>
				<input type="hidden" name = "pp_etc<%=i %>" />
				<% if (arrPic[i-1] != null && !arrPic[i-1].equals("")) { %>
				<img src="product/shoePic/<%=arrPic[i - 1] %>" height="80" weight="70" />
				<% } %>
			</td>
			</tr>
			<%} %>
			<tr>
			<th><span style="color:red;">*</span> 상품 설명</th>
			<td><textarea rows="10" cols="80" name="pi_desc"><%=pdtInfo.getPi_desc() %></textarea></td>
			</tr>	
			<tr>
			<th><span style="color:red;">*</span> 반송 시 주소 </th>
			<td>
				<input type="text" maxlength="5" size="5" name="pi_zip" id="mi_zip" style="height:20px;" onchange="mychk(this.form);" />&nbsp;&nbsp;&nbsp;
				<input type="button" name = "schaddr" value = "우편번호 찾기" onclick = "postcode();" />
				<span style="float:right;"><input type = "checkbox" onclick = "myaddress(this.form);" id = "myaddr" name = "myaddr"/>
				<label for = "myaddr" style = "align : right;">내 주소와 동일</label></span><br/>
				<input type="text" name="pi_addr1" id = "mi_addr1" size = "40" maxlength = "50" style = "height : 20px;" onchange = "mychk(this.form);"/>
				<input type="text" name="pi_addr2" id = "mi_addr2" size = "30" maxlength = "50" style = "height : 20px;" />
			</td>
			</tr>
			<tr>
			<th><span style = "color : red;">*</span> 판매약관	</th>
			<td>
				<div id="agreement1" style="width:'*'px; height: 100px; overflow: auto; border : 1px solid black;">
			      약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관
			      내용<br /> 약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관
			      내용<br />약관 내용<br /> 약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관
			      내용<br />약관 내용<br />약관 내용<br />
			   </div>
			</td>
			</tr>
			<tr>
			<td>
			</td>
			<td>
				<input type="checkbox" name="chk" id="chk" /><label for="chk">위 약관에 동의합니다.</label>
			</td>
			</tr>
			<tr><td colspan="2" align="right">
				<input type="submit" value="판매 등록" />
				<input type="button" value="취소" onclick="location.href='mypage';" />
			</td></tr>
		</table>
		</form>
	</div>
</div>
</div>
</body>
</html>