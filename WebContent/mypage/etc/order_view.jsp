<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "java.util.*" %>
<%
request.setCharacterEncoding("utf-8");

MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

if (orderInfo == null) {
   out.println("<script>");
   out.println("alert('잘못된 경로로 들어오셨습니다.');");
   out.println("history.back();");
   out.println("</script>");
   out.close();
}

int price = orderInfo.getOi_price();
int usepnt = orderInfo.getOi_usepnt();

int savepnt = (price - usepnt) / 100;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
</head>
<style>
#wrapper {width: 1200px; margin : 0 auto;}
.tb {
	border : 1px solid black;
}
.btw{
	align : center;
}
</style>
<body>
<div id="wrapper">
<%@ include file = "../../inc/main_header.jsp" %>
<div class="container">
<div align = "center">
<h2>구매해주셔서 감사합니다.</h2>
</div>
<table width = "800" align = "center" class = "tb table table-bordered table-hover table-condensed">
	<tr>
		<th colspan = "2">
			주문상품
		</th>
	</tr>
	<tr>
		<td width = "20%">
			 <img src="product/shoePic/<%=orderInfo.getOi_pdtimg() %>" width="180" height="200" />
		</td>
		<td width = "*">
			상품이름 : <%=orderInfo.getOi_pdtname() %><br />
			상품브랜드 : <%=orderInfo.getB_name() %><br />
			상품등급 : <%=orderInfo.getPi_quaility() %><br />
			상품사이즈 : <%=orderInfo.getPi_size() %>
		</td>
	</tr>
	<tr>
		<th>
			이름
		</th>
		<td width = "*">
			<%=loginMember.getMi_name() %>
		</td>
	</tr>
	<tr>
		<th>
			연락처
		</th>
		<td>
			<%=loginMember.getMi_phone() %>
		</td>
	</tr>
	<tr>
		<th>
			이메일
		</th>
		<td>
			<%=loginMember.getMi_email() %>
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
	<tr align = "center">	
		<th colspan = "2" align="center">
			<input type = "button" value = "메인으로" onclick = "location.href='index.jsp'" class = "btn btn-primary"/>
		</th>
	</tr>
</table>
</div>
</div>
</body>
</html>