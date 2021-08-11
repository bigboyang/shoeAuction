<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<BrandInfo> brandList = (ArrayList<BrandInfo>)request.getAttribute("brandList");
request.setCharacterEncoding("utf-8");
if (loginMember == null) {
	out.println("<script>");
	out.println("alert('먼저 로그인을 해야 합니다.');");
	out.println("location.href = 'login_form.jsp?url=pdt_reg_form.pdt';");	// 나중에 링크 붙이기
	out.println("</script>");
	out.close();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function chkfrm(frm){
 	var brand = frm.b_id.value;
	var name = frm.pi_name.value;
	var size = frm.pi_size.value;
	var price = frm.pi_sprice.value;
	var top = frm.pp_top.value;
	var front  = frm.pp_front.value;
	var back = frm.pp_back.value;
	var left = frm.pp_left.value;
	var right = frm.pp_right.value;
	var bottom = frm.pp_bottom.value;
	var desc = frm.pi_desc.value;
	
 	var etc1 = frm.pp_etc1.value;
	var etc2 = frm.pp_etc2.value;
	var etc3 = frm.pp_etc3.value;
	var etc4 = frm.pp_etc4.value;
	var etc5 = frm.pp_etc5.value;
	var etc6 = frm.pp_etc6.value;
	
	/* var zip = frm.pi_rezip.value;
	var addr1 = frm.pi_readdr1.value; */
	
	var chk = document.getElementById("chk").checked;
	
	var topchk = top.substring(top.lastIndexOf(".") + 1);
	var frontchk = front.substring(front.lastIndexOf(".") + 1);
	var backchk = back.substring(back.lastIndexOf(".") + 1);
	var leftchk = left.substring(left.lastIndexOf(".") + 1);
	var rightchk = right.substring(right.lastIndexOf(".") + 1);
	var bottomchk = bottom.substring(bottom.lastIndexOf(".") + 1);

 	var etc1chk = etc1.substring(etc1.lastIndexOf(".") + 1);
	var etc2chk = etc2.substring(etc2.lastIndexOf(".") + 1);
	var etc3chk = etc3.substring(etc3.lastIndexOf(".") + 1);
	var etc4chk = etc4.substring(etc4.lastIndexOf(".") + 1);
	var etc5chk = etc5.substring(etc5.lastIndexOf(".") + 1);
	var etc6chk = etc6.substring(etc6.lastIndexOf(".") + 1);
	

	if(brand == ""){
		alert("브랜드는 필수선택사항입니다.");
		frm.b_id.focus();
		return false;
	}
	if(name == "") {
		alert("상품이름은 필수입력사항입니다.");
		frm.pi_name.focus();
		return false;
	}
	if (size == "") {
		alert("상품사이즈는 필수선택사항입니다.");
		frm.pi_size.focus();
		return false;
	}
	if (price == ""){
		alert("입찰시작금액은 필수입력사항입니다.");
		frm.pi_sprice.focus();
		return false;
	}
 	if (top == ""){
		alert("상품평면사진은 필수입력사항입니다.");
		frm.pp_top.focus();
		return false;
	} else if (!(topchk == "jpg")) {
		alert("상품평면사진은 jpg 확장자만 입력할 수 있습니다.");
		frm.pp_top.value = '';
		frm.pp_top.focus();
		return false;
	}
	if (front == ""){
		alert("상품정면사진은 필수입력사항입니다.");
		frm.pp_front.focus();
		return false;
	} else if (!(frontchk == "jpg")) {
		alert("상품정면사진은 jpg 확장자만 입력할 수 있습니다.");
		frm.pp_front.value = '';
		return false;
	}
	if (back == ""){
		alert("상품후면사진은 필수입력사항입니다.");
		frm.pp_back.focus();
		return false;
	} else if (!(backchk == "jpg")) {
		alert("상품후면사진은 jpg 확장자만 입력할 수 있습니다.");
		frm.pp_back.value = '';
		frm.pp_back.focus();
		return false;
	}
	if (left == ""){
		alert("상품좌측사진은 필수입력사항입니다.");
		frm.pp_left.focus();
		return false;
	} else if (!(leftchk == "jpg")) {
		alert("상품좌측사진은 jpg 확장자만 입력할 수 있습니다.");
		frm.pp_left.value = '';
		frm.pp_left.focus();
		return false;
	}
	if (right == ""){
		alert("상품우측사진은 필수입력사항입니다.");
		frm.pp_right.focus();
		return false;
	} else if (!(rightchk == "jpg")) {
		alert("상품우측사진은 jpg 확장자만 입력할 수 있습니다.");
		frm.pp_right.value = '';
		frm.pp_right.focus();
		return false;
	}
	if (bottom == ""){
		alert("상품밑창사진은 필수입력사항입니다.");
		frm.pp_bottom.focus();
		return false;
	} else if (!(bottomchk == "jpg")) {
		alert("상품밑창사진은 jpg 확장자만 입력할 수 있습니다.");
		frm.pp_bottom.value = '';
		frm.pp_bottom.focus();
		return false;
	}
	if (!etc1 == ""){
		if (!(etc1chk == "jpg")) {
			alert("사진은 jpg 확장자만 입력할 수 있습니다.");
			frm.pp_etc1.value = '';
			frm.pp_etc1.focus();
			return false;
		}
	}
	if (!etc2 == ""){
		if (!(etc2chk == "jpg")) {
			alert("사진은 jpg 확장자만 입력할 수 있습니다.");
			frm.pp_etc2.value = '';
			frm.pp_etc2.focus();
			return false;
		}
	}
	if (!etc3 == ""){
		if (!(etc3chk == "jpg")) {
			alert("사진은 jpg 확장자만 입력할 수 있습니다.");
			frm.pp_etc3.value = '';
			frm.pp_etc3.focus();
			return false;
		}
	}
	if (!etc4 == ""){
		if (!(etc4chk == "jpg")) {
			alert("사진은 jpg 확장자만 입력할 수 있습니다.");
			frm.pp_etc4.value = '';
			frm.pp_etc4.focus();
			return false;
		}
	}
	if (!etc5 == ""){
		if (!(etc5chk == "jpg")) {
			alert("사진은 jpg 확장자만 입력할 수 있습니다.");
			frm.pp_etc5.value = '';
			frm.pp_etc5.focus();
			return false;
		}
	}
	if (!etc6 == ""){
		if (!(etc6chk == "jpg")) {
			alert("사진은 jpg 확장자만 입력할 수 있습니다.");
			frm.pp_etc6.value = '';
			frm.pp_etc6.focus();
			return false;
		}
	}
	if (desc == ""){
		alert("상품설명은 필수입력사항입니다.");
		frm.pi_desc.focus();
		return false;
	}
	
	/* if (zip == ""){
		alert("반송 받을 주소는 필수 입력사항입니다.");
		frm.schaddr.click();
		return false;
	}
	if (addr1 == ""){
		alert("반송 받을 주소는 필수 입력사항입니다.");
		frm.pi_readdr1.click();
		return false;
	} */
	
	if (chk == false){
		alert("약관에 동의해주세요.");
		return false;
	}
}



/* 
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
        }
    }).open();
}
 */
function onlyNum(obj) {
	if (isNaN(obj.value )) {
		obj.value = "";
	}
}
</script>
</head>
<body>
<form name = "pdtreg" action = "pdt_reg_proc.pdt" method = "get" style = "border : 1px solid black;" onsubmit = "return chkfrm(this);" enctype = "multipart/form-data">
	<input type="hidden" name="mi_id" value="<%=loginMember.getMi_id() %>" />
	<table width = "800" cellpadding = "5" cellspacing = "0" align = "center">
		<tr>
			<th width = "20%">
				기본정보
			</th>
			<th width = "*" align = "right">
				<span style = "color : red;">*</span> 표시는 필수 입력사항입니다.
			</th>
		</tr>
		<tr>
			<th>
				<span style = "color : red;">*</span> 브랜드
			</th>
			<td>
				<select name = "b_id">
					<option value = "" selected disabled hidden>브랜드 선택</option>
					<option value = "<%=brandList.get(4).getB_id() %>"><%=brandList.get(4).getB_name() %></option>
					<option value = "<%=brandList.get(0).getB_id() %>"><%=brandList.get(0).getB_name() %></option>
					<option value = "<%=brandList.get(1).getB_id() %>"><%=brandList.get(1).getB_name() %></option>
					<option value = "<%=brandList.get(6).getB_id() %>"><%=brandList.get(6).getB_name() %></option>
					<option value = "<%=brandList.get(5).getB_id() %>"><%=brandList.get(5).getB_name() %></option>
					<option value = "<%=brandList.get(2).getB_id() %>"><%=brandList.get(2).getB_name() %></option>
					<option value = "<%=brandList.get(3).getB_id() %>"><%=brandList.get(3).getB_name() %></option>
					<!-- 옵션 돌리기 -->
				</select>
			</td>
		</tr>
		<tr>
			<th>
				<span style = "color : red;" maxlength = "50" placeholder = "50자까지 입력 가능합니다.">*</span> 상품이름
			</th>
			<td>
				<input type = "text" name = "pi_name" size = "30" />
			</td>
		</tr>
<!-- 		
		<tr>
			<th>
				상품태그
			</th>
			<td>
				<input type = "text" name = "pi_tag" placeholder = "예)#ex1 #ex2 #ex3 형식으로 #으로 나뉩니다.">
			</td>
		</tr>
-->
		<tr>
			<th>
				<span style = "color : red;">*</span> 사이즈
			</th>
			<td>
				<select name = "pi_size"> 
					<option value="" selected disabled hidden>사이즈 선택</option>
					<%for (int i = 220; i <= 300; i+=5) { %>
					<option value = "<%=i %>"><%=i %></option>
					<% } %>
				</select>
			</td>
		</tr>
		<tr>
			<th>
				<span style = "color : red;">*</span> 입찰시작금액
			</th>
			<td>
				<input type = "text" name = "pi_sprice" onkeyup="onlyNum(this);" maxlength = "8" style = "text-align : right;"/> 원
			</td>
		</tr>
		<tr>
			<th>
				<span style = "color : red;">*</span> 입찰기간
			</th>
			<td>
				<input type = "radio" name = "pi_period" value = "1" id = "period1" checked = "checked"/> <label for = "period1">1일</label>
				<input type = "radio" name = "pi_period" value = "3" id = "period2"/> <label for = "period2">3일</label>
				<input type = "radio" name = "pi_period" value = "7" id = "period3"/> <label for = "period3">7일</label>
				<input type = "radio" name = "pi_period" value = "15" id = "period4"/> <label for = "period4">15일</label>
				<input type = "radio" name = "pi_period" value = "15" id = "period5"/> <label for = "period5">30일</label>
			</td>
		</tr>
		<tr>
			<th>
				상품 이미지
			</th>
			<td>
				※ 사진은 기타를 제외하고 필수로 하나를 첨부해야 합니다.<br />
				사진은 최대 12개까지 첨부가 가능합니다. 사진은 최대 5MB까지 가능합니다.
			</td>
		</tr>
		<tr>
			<th>
				<span style = "color : red;">*</span> 상품 평면
			</th>
			<td>
				<input type = "file" name = "pp_top" />
			</td>
		</tr>
		<tr>
			<th>
				<span style = "color : red;">*</span> 상품 정면
			</th>
			<td>
				<input type = "file" name = "pp_front" />
			</td>
		</tr>
		<tr>
			<th>
				<span style = "color : red;">*</span> 상품 후면
			</th>
			<td>
				<input type = "file" name = "pp_back" />
			</td>
		</tr>
		<tr>
			<th>
				<span style = "color : red;">*</span> 상품 좌측
			</th>
			<td>
				<input type = "file" name = "pp_left" />
			</td>
		</tr>
		<tr>
			<th>
				<span style = "color : red;">*</span> 상품 우측
			</th>
			<td>
				<input type = "file" name = "pp_right" />
			</td>
		</tr>
		<tr>
			<th>
				<span style = "color : red;">*</span> 상품 밑창
			</th>
			<td>
				<input type = "file" name = "pp_bottom" />
			</td>
		</tr>
		<% for (int i = 1; i <= 6; i++){ %>
		<tr>
			<th>
				기타이미지<%=i %>
			</th>
			<td>
				<input type = "file" name = "pp_etc<%=i %>" />
			</td>
		</tr>
		<%} %>
		<tr>
			<th>
				<span style = "color : red;">*</span> 상품 설명
			</th>
			<td>
				<textarea rows="10" cols="80" name = "pi_desc"></textarea>
			</td>
		</tr>
<!-- 		
		<tr>
			<th>
				<span style = "color : red;">*</span> 반송 시 주소 
			</th>
			<td>
				<input type = "text" maxlength = "5" size = "5" name = "pi_rezip" id = "mi_zip" style = "height : 20px;"/>&nbsp;&nbsp;&nbsp;
				<input type = "button" name = "schaddr" value = "우편번호 찾기" onclick = "postcode();" /><br />
				<input type="text" name="pi_readdr1" id = "mi_addr1" size = "70" placeholder="일반주소" maxlength = "50" style = "height : 20px;" /><br />
				<input type="text" name="pi_readdr2" id = "mi_addr2" size = "70" placeholder="상세주소" maxlength = "50" style = "height : 20px;" />
			</td>
		</tr>
 -->	
		<tr>
			<th>
				<span style = "color : red;">*</span> 판매약관
			</th>
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
				<input type = "checkbox" name = "chk" id = "chk" /> <label for = "chk">위 약관에 동의합니다.</label>
			</td>
		</tr>
		<tr>
			<td colspan = "2" align = "right">
				<input type = "submit" value = "판매 등록" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>