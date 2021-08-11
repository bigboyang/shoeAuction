<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");
ArrayList<BrandInfo> brandList = (ArrayList<BrandInfo>)request.getAttribute("brandList");
ArrayList<AuctionInfo> auctionList = (ArrayList<AuctionInfo>)request.getAttribute("auctionList");
if (loginMember == null) {
   out.println("<script>");
   out.println("alert('먼저 로그인을 해야 합니다.');");
   out.println("location.href = 'login_form.jsp?url=pdt_auc_form.pdt?id=" + pdtInfo.getPi_id() + "';");
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

request.setCharacterEncoding("utf-8");

// 상단 브랜드 카테고리를 위해.
String args = "", schargs = "";
String brand = request.getParameter("brand");
if (brand == null) {
   brand = "";
} else schargs += "&brand=" + brand;
String pkeyword = request.getParameter("pkeyword");
if (pkeyword == null) {
   pkeyword = "";
} else schargs += "&pkeyword=" + pkeyword;
String ord = request.getParameter("ord");
if (ord == null) {
   ord = "";
} else  schargs += "&ord=" + ord;
String cpage = request.getParameter("cpage");
args = "?cpage=" + cpage + schargs;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
   table { border:0px black solid; }
   a:link { color:#4f4f4f; text-decoration:none; }
   a:visited { color:#8a2e91; text-decoration:none; }
   a:hover { color:#000; text-decoration:none; font-weight:bold; }
   a:focus { color:#000; text-decoration:none; font-weight:bold; }
   a:active { color:#000; text-decoration:none; font-weight:bold; }
</style>
<script>
   function chkValue(frm) {
      var price = frm.price.value, agree = frm.agree.value;
      
      if (price == "") {
         alert("입찰금액을 입력하세요.");
         frm.price.focus();   return false;
      } else if (price <= <%=auctionList.size() > 0 ? auctionList.get(0).getPa_price() : pdtInfo.getPi_sprice() %>) {
         alert("<%=auctionList.size() > 0 ? "최고 입찰가를 초과 입력하세요." : "입찰 시작가를 초과 입력하세요." %>");
         frm.price.value = "";   frm.price.focus();   
         return false;
      }

      if (agree == "n" || agree == "") {
         alert("유의사항에 동의해야 입찰 가능합니다.");
         return false;
      }
      
      return true;
   }
function onlyNum(obj) {
	if (isNaN(obj.value )) {
		obj.value = "";
	}
}
</script>
</head>
<body>
<%@ include file="../inc/main_header.jsp" %>
<!-- 상단 목록 영역 시작 -->
<div id="brdList" style="width:900px;" align="left" class = "container">
	<div class="btn_group btn-group-justified">
		<div class = "btn-group">
			<button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt';">전체</button>
		</div>
		<div class = "btn-group">
			<button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=n01';">nike</button>
		</div>
		<div class = "btn-group">
			<button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=a01';">adias</button>
		</div>
		<div class = "btn-group">
			<button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=b01';">newbalance</button>
		</div>
		<div class = "btn-group">
			<button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=v01';">vans</button>
		</div>
		<div class = "btn-group">
			<button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=p01';">puma</button>
		</div>
		<div class = "btn-group">
			<button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=d01';">dr.martin</button>
		</div>
		<div class = "btn-group">
			<button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=e01';">etc</button>
		</div>
	</div>
</div>
<!-- 상단 목록 영역 종료 -->

<div style = "border : 1px solid lightgray; width : 815px; margin : 0 auto;">
<table width="800" cellpadding="0" align = "center">
<tr align="center">
<td>
	<table width="100%" cellpadding="5">
	<tr align="center">
	<td width="55%">
		<!-- 메인 이미지 영역 시작 -->
		<table width="100%">
		<tr><td align="center" valign="middle">
			<img src="product/shoePic/<%=pdtInfo.getPp_top()%>" width="300" height="400" />
		</td></tr>
		</table>
		<!-- 메인 이미지 영역 종료 -->
	</td>
	<td width="*" valign="top">
		<!-- 상품 정보 영역 시작 -->
		<table id="info" width="100%" cellpadding="3">
		<br />
		<tr>
		<td colspan="2">
		<p>
			<%if (pdtInfo.getPi_end() != null && !pdtInfo.getPi_end().equals("")){ %>
				입찰마감시간 : <%=pdtInfo.getPi_end().replace("-", "/") %>
			<%}%>
		</p>
		</td>
		<td align="right">
		</td>
		</tr>
		<tr>
		<td colspan="2"><p>브랜드 : <%=pdtInfo.getB_name()%></p></td>
		</tr>
		<tr><td colspan="2"><p>상품명 : <%=pdtInfo.getPi_name() %></p></td></tr>
		<tr><td colspan="2"><p>상품사이즈 : <%=pdtInfo.getPi_size() %></p></td></tr>
		<tr><td colspan="2"><p>상품등급 : <%=pdtInfo.getPi_quaility() %></p></td></tr>
		</table>

		<form name="frmAucBtn" action="pdt_auc_proc.pdt" method="post" onsubmit="return chkValue(this);">
			<input type="hidden" name="id" value="<%=pdtInfo.getPi_id() %>" />
			<input type="hidden" name="uid" value="<%=loginMember.getMi_id() %>" />
			<table id="btn" border="1" width="80%" cellpadding="3" style = "margin : 0;">
		      	
		      	<div class = "form-group">
			      	<div class = "col-md-4" style = "top : 5px;">
						입찰 금액 : 
					</div>
					<div class = "col-md-6">
						<input type = "text" class="form-control" name = "price" onkeyup="onlyNum(this);" />
					</div>
					<div class = "col-md-2" style = "top : 5px;">
						원
					</div>
				</div>
		</table>

		<div>
			<p><%=(auctionList.size() > 0) ? "최고 입찰가 : " + auctionList.get(0).getPa_price() + "원" : "입찰 시작가 : " + pdtInfo.getPi_sprice() + "원" %></p>
		</div>
</td>
   </tr>
   </table>
</td>
</tr>
<tr>
<td>
<br />
	<!-- 입찰 설명 영역 시작 -->
	<table align="center" width = "100%">
	<tr><td>
    <div style = "border : 1px solid lightgray;" width = "100%">
    	<br />
	    <p>최고 입찰가 혹은 입찰 시작가 이상의 금액만 입찰할 수 있습니다.</p>
	    <p>입찰이 되지 않을 경우 따로 연락이 가지 않습니다.</p>
	    <p>입찰에 성공할 경우, 입찰 종료로부터 48시간 이내에 결제해야 합니다.</p>
	    <p>입찰 성공 후 48시간 이내에 결제하지 않을 경우 결제기회가 사라집니다.</p>
	    <p>다음 차순위 입찰자가 결제 기회를 갖게 됩니다.</p>
	    <p>입찰기회를 잃은 입찰자는 차순위 입찰자일지라도 결제 기회를 가질 수 없습니다.</p>
	    <p>입찰에 성공한 후 상품미결제 횟수가 3회 이상이면 회원탈퇴 처리됩니다.</p>
	    <p>결제 후에는 주문 취소가 불가능합니다.</p>
	    <br />
    </div>
    
    <div class="form-check">
		<label class="form-check-label">
		<input type="radio" class="form-check-input" name="agree" id="optionsRadios1" value="y">
			동의
		</label>
	</div>
      
	<div class="form-check">
		<label class="form-check-label">
		<input type="radio" class="form-check-input" name="agree" id="optionsRadios2" value="n">
			동의 안 함
		</label>
	</div>
	
   </td></tr>
   <tr><td align="center">
      <div class="form-group" align = "center">
	    <button type="submit" class="btn btn-secondary">입찰등록</button>
	    <button type="button" class="btn btn-secondary" onclick="history.back();">입찰취소</button>
    </div>
      </td></tr>
   </table>
   </form>
   <!-- 입찰 설명 영역 종료 -->
</td>
</tr>
</table>
</div>
</body>
</html>
