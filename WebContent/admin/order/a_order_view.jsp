<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "java.util.*" %>
<%
request.setCharacterEncoding("utf-8");

OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

int price = orderInfo.getOi_price();
int usepnt = orderInfo.getOi_usepnt();

int savepnt = (price - usepnt) / 100;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<style>
   a:link { color:#4f4f4f; text-decoration:none; }
   a:visited { color:#8a2e91; text-decoration:none; }
   a:hover { color:#000; text-decoration:none; font-weight:bold; }
   a:focus { color:#000; text-decoration:none; font-weight:bold;}
   a:active { color:#000; text-decoration:none; font-weight:bold;}
   #outer {width: 1200px; aligh:center; position:absolute;}
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   #center { float:right; position:absolute; left:200px;}
.tb {
	border : 1px solid black;
}
</style>
<body>
<%@ include file="../../inc/admin_header.jsp" %>
<div id = "outer">
	<%@ include file="../../inc/admin_order_left.jsp" %>
	<div id = "center">
<table width = "800" align = "center" class = "tb">
	<tr>
		<th colspan = "2">
			주문상품
		</th>
	</tr>
	<tr>
		<td width = "20%">
			 <img src="../product/shoePic/<%=orderInfo.getOi_pdtimg() %>" width="180" height="200" />
		</td>
		<td width = "*">
			상품이름 : <%=orderInfo.getOi_pdtname() %><br />
			브랜드 : <%=orderInfo.getB_name() %><br />
			사이즈 : <%=orderInfo.getPi_size() %><br />
			상태 : <%=orderInfo.getPi_quaility() %>
		</td>
	</tr>
	<tr>
		<th>
			이름
		</th>
		<td width = "*">
			<%=orderInfo.getMi_name() %>
		</td>
	</tr>
	<tr>
		<th>
			연락처
		</th>
		<td>
			<%=orderInfo.getOi_phone() %>
		</td>
	</tr>
	<tr>
		<th>
			이메일
		</th>
		<td>
			<%=orderInfo.getMi_email() %>
		</td>
	</tr>
	<tr><td colspan = "2"></td></tr>
	<tr>
		<th colspan = "2">
			배송정보
		</th>
	</tr>
	<tr>
		<th>
			받으시는 분
		</th>
		<td>
			<%=orderInfo.getOi_name() %>
		</td>
	</tr>
	<tr>
		<th>
			연락처
		</th>
		<td>
			<%=orderInfo.getOi_phone() %>
		</td>
	</tr>
	<tr>
		<th>
			배송주소
		</th>
		<td>
			<%=orderInfo.getOi_addr1() %>
			&nbsp;&nbsp;
			<%=orderInfo.getOi_addr2() %>
		</td>
	</tr>
	<tr>
		<th>
			배송메모
		</th>
		<td>
			<%if (!orderInfo.equals(null)) { %>
				<%=orderInfo.getOi_comment() %>
			<%} %>
		</td>
	</tr>
	<tr>
		<th>
			상태
		</th>
		<td>
			<%if (orderInfo.getOi_status().equals("a")){ %>
				배송 준비중
			<%} else if (orderInfo.getOi_status().equals("b")){%>
				배송 중
			<%} else if (orderInfo.getOi_status().equals("c")){%>
				배송 완료
			<%} %>
		</td>
	</tr>
	<tr>
		<th>
			송장번호
		</th>
		<td>
			<%=orderInfo.getOi_invoice() %>
		</td>
	</tr>
	<tr><td colspan = "2"></td></tr>
	<tr>
		<th colspan = "2">
			적립 포인트
		</th>
	</tr>
	<tr>
		<th>
			적립금
		</th>
		<td colspan = "2" align = "left">
			<%=savepnt %> 포인트 적립
		</td>
	</tr>
	<tr><td colspan = "2"></td></tr>
	<tr>
		<th colspan = "2">
			총 결제 금액
		</th>
	</tr>
	<tr>
		<th>	
			상품 금액
		</th>
		<td>	
			<%=orderInfo.getOi_price() %> 원
		</td>
	</tr>
	<tr>
		<th>
			배송비
		</th>
		<td>
			+ 3000 원
		</td>
	</tr>
	<tr>
		<th>
			사용 포인트
		</th>
		<td>
			- <%=orderInfo.getOi_usepnt() %> point
		</td>
	</tr>
	<tr>
		<th>
			합계 금액
		</th>
		<td>
			<%=orderInfo.getOi_pay() %> 원
		</td>
	</tr>
	<tr><td colspan = "2"></td></tr>
	<tr>
		<th colspan = "2">
			결제 상세 정보
		</th>
	</tr>
	<tr>
		<th>
			결제방법
		</th>
		<td>
			<%if (orderInfo.getOi_payment().equals("a")) {%>
				카드결제
			<%} else if (orderInfo.getOi_payment().equals("b")){%>
				핸드폰결제
			<%} else if (orderInfo.getOi_payment().equals("c")){%>
				카카오페이
			<%} %>
		</td>
	</tr>
	<tr>
		<th>
			결제 금액
		</th>
		<td>
			<%=orderInfo.getOi_pay() %>	원
		</td>
	</tr>
	<tr><td colspan = "2"></td></tr>
	<tr>	
		<th colspan = "2">
			<input type = "button" value = "뒤로" onclick = "history.back();" />
		</th>
	</tr>
</table>
	</div>
</div>
</body>
</html>