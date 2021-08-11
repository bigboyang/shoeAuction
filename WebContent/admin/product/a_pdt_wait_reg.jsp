<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "vo.*" %>
<%
AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");
ArrayList<BrandInfo> brandList = (ArrayList<BrandInfo>)request.getAttribute("brandList");
request.setCharacterEncoding("utf-8");
if (adminMember == null) {
	out.println("<script>");
	out.println("alert('먼저 로그인을 해야 합니다.');");
	out.println("location.href = 'a_login_form.jsp';");	
	out.println("</script>");
	out.close();
}


request.setCharacterEncoding("utf-8");
String brand = request.getParameter("brand");
if (brand == null) brand = "";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
  #outer {width: 1200px; aligh:center; position:absolute;}
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   #center { float:right; position:absolute; left:200px;}
</style>
<script>
function isImg(str) {
	var arrImg = ["jpg"];
	var ext = str.substring(str.lastIndexOf(".") + 1);
	for (var i = 0 ; i < arrImg.length ; i++) {
		if (ext == arrImg[i])	return true;
	}
	return false;
}
/*
function chkfrm(frm){
	var name = frm.pi_name.value;
	var img1 = frm.pp_top.value;
	var img2 = frm.pp_front.value;
	var img3 = frm.pp_back.value;
	var img4 = frm.pp_left.value;
	var img5 = frm.pp_right.value;
	var img6 = frm.pp_bottom.value;

	if (name == "") {
		alert("상품이름은 필수입력사항입니다.") 
		frm.name.focus;
		return false;
	}
	
	if (img1 == "") {
		alert("상품 평면이미지는 필수입력사항입니다.")
		return false;
	} else if (!isImg(img1)) {
		alert("jsp파일만 등록 가능합니다.");	frm.pp_top.focus();		return false;
	}
	
	if (img2 == "") {
		alert("상품 정면이미지는 필수입력사항입니다.")
		return false;
	} else if (!isImg(img2)) {
		alert("jsp파일만 등록 가능합니다.");	frm.pp_front.focus();		return false;
	}
	
	if (img3 == "") {
		alert("상품 후면이미지는 필수입력사항입니다.")
		return false;
	} else if (!isImg(img3)) {
		alert("jsp파일만 등록 가능합니다.");	frm.pp_back.focus();		return false;
	}
	
	if (img4 == "") {
		alert("상품 좌측이미지는 필수입력사항입니다.")
		return false;
	} else if (!isImg(img4)) {
		alert("jsp파일만 등록 가능합니다.");	frm.pp_left.focus();		return false;
	}
	
	if (img5 == "") {
		alert("상품 우측이미지는 필수입력사항입니다.")
		return false;
	} else if (!isImg(img5)) {
		alert("jsp파일만 등록 가능합니다.");	frm.pp_right.focus();		return false;
	}
	
	if (img6 == "") {
		alert("상품 밑창이미지는 필수입력사항입니다.")
		return false;
	} else if (!isImg(img6)) {
		alert("jsp파일만 등록 가능합니다.");	frm.pp_bottom.focus();		return false;
	}
	
	if (!isImg(etc1)) {
		alert("jsp파일만 등록 가능합니다.");	frm.pp_etc1.focus();		return false;
	}
	if (!isImg(etc2)) {
		alert("jsp파일만 등록 가능합니다.");	frm.pp_etc2.focus();		return false;
	}
	if (!isImg(etc3)) {
		alert("jsp파일만 등록 가능합니다.");	frm.pp_etc3.focus();		return false;
	}
	if (!isImg(etc4)) {
		alert("jsp파일만 등록 가능합니다.");	frm.pp_etc4.focus();		return false;
	}
	if (!isImg(etc5)) {
		alert("jsp파일만 등록 가능합니다.");	frm.pp_etc5.focus();		return false;
	}
	if (!isImg(etc6)) {
		alert("jsp파일만 등록 가능합니다.");	frm.pp_etc6.focus();		return false;
	}
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
<div id="outer">
<%@ include file="../../inc/admin_header.jsp" %>
<div id="left">
<%@ include file="../../inc/admin_product_left.jsp" %>
<div id="center">
<form name="pdtreg" action="pdt_wait_reg_proc.pdta" method="post" style="border:1px solid black;" onsubmit="return chkfrm(this);" enctype="multipart/form-data">
<input type="hidden" name="pi_id" value="<%=pdtInfo.getPi_id() %>" />
<input type="hidden" name="oldImg1" value="<%=pdtInfo.getPp_top() %>" />
<input type="hidden" name="oldImg2" value="<%=pdtInfo.getPp_front() %>" />
<input type="hidden" name="oldImg3" value="<%=pdtInfo.getPp_back() %>" />
<input type="hidden" name="oldImg4" value="<%=pdtInfo.getPp_left() %>" />
<input type="hidden" name="oldImg5" value="<%=pdtInfo.getPp_right() %>" />
<input type="hidden" name="oldImg6" value="<%=pdtInfo.getPp_bottom() %>" />
<input type="hidden" name="oldDesc" value="<%=pdtInfo.getPi_desc() %>" />
	
	<table width = "800" cellpadding = "5" cellspacing = "0" align = "center">
		<tr>
		<th width = "20%">관리대기 : <% if (pdtInfo.getPi_isactive().equals("a")) {%>미수령
			<% } else if (pdtInfo.getPi_isactive().equals("b")) {%>검수중
			<% } else if (pdtInfo.getPi_isactive().equals("c")) {%>검수완료
			<% } else if (pdtInfo.getPi_isactive().equals("h")) {%>상품거절
			<% } %></td> 
		</th>
		<th width = "*" align = "right">
			<span style = "color : red;">*</span> 표시는 필수 입력사항입니다.
		</th>
		</tr>
		<tr>
		<th>등록번호</th>
		<td><%=pdtInfo.getPi_id() %>&nbsp;&nbsp;
		상품등급 : <select name = "pi_quaility">
				<option value = "x" <% if (pdtInfo.getPi_quaility().equals("x")) {%>selected="selected" <% } %>>x</option>		
				<option value = "s" <% if (pdtInfo.getPi_quaility().equals("s")) {%>selected="selected" <% } %>>s</option>
				<option value = "a" <% if (pdtInfo.getPi_quaility().equals("a")) {%>selected="selected" <% } %>>a</option>
				<option value = "b" <% if (pdtInfo.getPi_quaility().equals("b")) {%>selected="selected" <% } %>>b</option>		
		</td>
		</tr>
		<tr>
		<th><span style = "color : red;">*</span> 브랜드</th>
		<td>
			<select name = "b_id">
				<option value = "" selected disabled hidden>브랜드 선택</option>
				<option value = "<%=brandList.get(4).getB_id() %>" <% if (pdtInfo.getB_name().equals("nike")) {%>selected="selected" <% } %>><%=brandList.get(4).getB_name() %></option>
				<option value = "<%=brandList.get(0).getB_id() %>" <% if (pdtInfo.getB_name().equals("adidas")) {%>selected="selected" <% } %>><%=brandList.get(0).getB_name() %></option>
				<option value = "<%=brandList.get(1).getB_id() %>" <% if (pdtInfo.getB_name().equals("newbalance")) {%>selected="selected" <% } %>><%=brandList.get(1).getB_name() %></option>
				<option value = "<%=brandList.get(6).getB_id() %>" <% if (pdtInfo.getB_name().equals("vans")) {%>selected="selected" <% } %>><%=brandList.get(6).getB_name() %></option>
				<option value = "<%=brandList.get(5).getB_id() %>" <% if (pdtInfo.getB_name().equals("puma")) {%>selected="selected" <% } %>><%=brandList.get(5).getB_name() %></option>
				<option value = "<%=brandList.get(2).getB_id() %>" <% if (pdtInfo.getB_name().equals("dr.martin")) {%>selected="selected" <% } %>><%=brandList.get(2).getB_name() %></option>
				<option value = "<%=brandList.get(3).getB_id() %>" <% if (pdtInfo.getB_name().equals("etc")) {%>selected="selected" <% } %>><%=brandList.get(3).getB_name() %></option>
				<!-- 옵션 돌리기 -->
			</select>
		</td>
		</tr>
		<tr>
		<th>
			<span style = "color : red;" maxlength = "50" placeholder = "50자까지 입력 가능합니다.">*</span> 상품이름
		</th>
		<td><input type = "text" name = "pi_name" size = "30" value="<%=pdtInfo.getPi_name() %>" /></td>
		</tr>
<!-- 		
		<tr>
		<th>상품태그</th>
		<td>
			<input type = "text" name = "pi_tag" placeholder = "예)#ex1 #ex2 #ex3 형식으로 #으로 나뉩니다.">
		</td>
		</tr>
-->
		<tr>
		<th><span style = "color : red;">*</span> 사이즈</th>
		<td>
			<select name = "pi_size"> 
				<option value="" selected disabled hidden>사이즈 선택</option>
		<option value="" >전체</option>
		<option value="200"<% if (pdtInfo.getPi_size().equals("200")) { %> selected="selected"<% } %>>200</option>
		<option value="205"<% if (pdtInfo.getPi_size().equals("205")) { %> selected="selected"<% } %>>205</option>
		<option value="210"<% if (pdtInfo.getPi_size().equals("210")) { %> selected="selected"<% } %>>210</option>
		<option value="215"<% if (pdtInfo.getPi_size().equals("215")) { %> selected="selected"<% } %>>215</option>
		<option value="220"<% if (pdtInfo.getPi_size().equals("220")) { %> selected="selected"<% } %>>220</option>
		<option value="225"<% if (pdtInfo.getPi_size().equals("225")) { %> selected="selected"<% } %>>225</option>
		<option value="230"<% if (pdtInfo.getPi_size().equals("230")) { %> selected="selected"<% } %>>230</option>
		<option value="235"<% if (pdtInfo.getPi_size().equals("235")) { %> selected="selected"<% } %>>235</option>
		<option value="240"<% if (pdtInfo.getPi_size().equals("240")) { %> selected="selected"<% } %>>240</option>
		<option value="245"<% if (pdtInfo.getPi_size().equals("245")) { %> selected="selected"<% } %>>245</option>
		<option value="250"<% if (pdtInfo.getPi_size().equals("250")) { %> selected="selected"<% } %>>250</option>
		<option value="255"<% if (pdtInfo.getPi_size().equals("255")) { %> selected="selected"<% } %>>255</option>
		<option value="260"<% if (pdtInfo.getPi_size().equals("260")) { %> selected="selected"<% } %>>260</option>
		<option value="265"<% if (pdtInfo.getPi_size().equals("265")) { %> selected="selected"<% } %>>265</option>
		<option value="270"<% if (pdtInfo.getPi_size().equals("270")) { %> selected="selected"<% } %>>270</option>
		<option value="275"<% if (pdtInfo.getPi_size().equals("275")) { %> selected="selected"<% } %>>275</option>
		<option value="280"<% if (pdtInfo.getPi_size().equals("280")) { %> selected="selected"<% } %>>280</option>
		<option value="285"<% if (pdtInfo.getPi_size().equals("285")) { %> selected="selected"<% } %>>285</option>
		<option value="290"<% if (pdtInfo.getPi_size().equals("290")) { %> selected="selected"<% } %>>290</option>
		<option value="295"<% if (pdtInfo.getPi_size().equals("295")) { %> selected="selected"<% } %>>295</option>
		<option value="300"<% if (pdtInfo.getPi_size().equals("300")) { %> selected="selected"<% } %>>300</option>
			</select>
		</td>
		</tr>
		<tr>
		<th><span style = "color : red;">*</span> 입찰시작금액</th>
		<td>
			<input type = "text" name = "pi_sprice" onkeyup="onlyNum(this);" maxlength = "8" style = "text-align : right;" value="<%=pdtInfo.getPi_sprice()%>"/> 원
		</td>
		</tr>
		<tr>
		<th><span style = "color : red;">*</span> 입찰기간	</th>
		<td>
			<input type = "radio" name = "pi_period" value = "1" id = "period1" checked = "checked"/> <label for = "period1">1일</label>
			<input type = "radio" name = "pi_period" value = "3" id = "period2"/> <label for = "period2">3일</label>
			<input type = "radio" name = "pi_period" value = "7" id = "period3"/> <label for = "period3">7일</label>
			<input type = "radio" name = "pi_period" value = "15" id = "period4"/> <label for = "period4">15일</label>
			<input type = "radio" name = "pi_period" value = "15" id = "period5"/> <label for = "period5">30일</label>
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
		<th><span style = "color : red;">*</span> 상품 평면</th>
		<td>
			<input type = "file" name = "pp_top" accept="image/*" />
			<% if (pdtInfo.getPp_top() == null || pdtInfo.getPp_top().equals("")) { %>
				기존 이미지가 없습니다.	
			<% } else { %>
			<img src="../product/shoePic/<%=pdtInfo.getPp_top()%>" width="50" height="60" />	
			<% } %>
		<tr>
		<th><span style = "color : red;">*</span> 상품 정면</th>
		<td><input type = "file" name = "pp_front" accept="image/*" />
			<% if (pdtInfo.getPp_front() == null || pdtInfo.getPp_front().equals("")) { %>
				기존 이미지가 없습니다.	
			<% } else { %>
			<img src="../product/shoePic/<%=pdtInfo.getPp_front()%>" width="50" height="60" />	
			<% } %>
		</td>
		</tr>
		<tr>
		<th><span style = "color : red;">*</span> 상품 후면</th>
		<td><input type = "file" name = "pp_back" accept="image/*" />
			<% if (pdtInfo.getPp_back() == null || pdtInfo.getPp_back().equals("")) { %>
				기존 이미지가 없습니다.	
			<% } else { %>
			<img src="../product/shoePic/<%=pdtInfo.getPp_back()%>" width="50" height="60" />	
			<% } %>		
		</td>
		</tr>
		<tr>
		<th><span style = "color : red;">*</span> 상품 좌측</th>
		<td><input type = "file" name = "pp_left" accept="image/*" />
			<% if (pdtInfo.getPp_left() == null || pdtInfo.getPp_left().equals("")) { %>
				기존 이미지가 없습니다.	
			<% } else { %>
			<img src="../product/shoePic/<%=pdtInfo.getPp_left()%>" width="50" height="60" />	
			<% } %>			
		</td>
		</tr>
		<tr>
		<th><span style = "color : red;">*</span> 상품 우측</th>
		<td><input type = "file" name = "pp_right" accept="image/*" />
			<% if (pdtInfo.getPp_right() == null || pdtInfo.getPp_right().equals("")) { %>
				기존 이미지가 없습니다.	
			<% } else { %>
			<img src="../product/shoePic/<%=pdtInfo.getPp_right()%>" width="50" height="60" />	
			<% } %>			
		</td>
		</tr>
		<tr>
		<th><span style = "color : red;">*</span> 상품 밑창</th>
		<td><input type = "file" name = "pp_bottom" accept="image/*" />
			<% if (pdtInfo.getPp_bottom() == null || pdtInfo.getPp_bottom().equals("")) { %>
				기존 이미지가 없습니다.	
			<% } else { %>
			<img src="../product/shoePic/<%=pdtInfo.getPp_bottom()%>" width="50" height="60" />	
			<% } %>				
		</td>
		</tr>
		<% for (int i = 1; i <= 6; i++){ %>
		<tr>
		<th>기타이미지<%=i %></th>
		<td><input type = "file" name = "pp_etc<%=i %>" accept="image/*" />

		</td>
		</tr>
		<%} %>
		<tr>
		<th><span style = "color : red;">*</span> 상품 설명</th>
		<td><textarea rows="10" cols="80" name = "pi_desc" ><%=pdtInfo.getPi_desc()%></textarea></td>
		</tr>	
		<tr>
		<td>
		</td>
		</tr>
		<tr><td colspan="2" align="right">
			<input type = "button" value = "목록" onclick="history.back();" />
			<input type = "submit" value = "판매등록" />
			<input type = "button" value = "삭제" onclick="" />			
		</td></tr>
	</table>
</form>
</div>
</div>
</div>
</body>
</html>