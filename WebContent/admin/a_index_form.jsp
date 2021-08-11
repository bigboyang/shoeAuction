<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
if (adminMember == null) {
	out.println("<script>");
	out.println("alert('로그인 후 사용하실 수 있습니다.');");
	out.println("location.href = 'a_login_form.jsp?';");
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
   
   div { padding:20px; }
  	#outer {width:1000px; aligh:center; position:relative; margin: 0 auto;}
	.cata { float:left; }
</style>
</head>
<body>
<div id="outer">
<h2>관리자 페이지</h2>
<input type="button" value="마스터 버튼" onclick="location.href='../masterButton';" />
	<div class="cata">
		<h4>상품</h4>
			<a href="pdt_list.pdta"><span>상품관리</span></a><br />
			<a href="pdt_wait_list.pdta"><span>대기상품</span></a><br />
			<a href="pdt_failauc_list.pdta"><span>유찰관리</span></a><br />
			<a href="pdt_waiting_list.pdta"><span>결제대기</span></a>
	</div>
	<div class="cata">
		<h4>회원</h4>
			<a href="member_list.mema?ptype=mlist"><span>회원목록</span></a><br />
			<a href="member_list.mema?ptype=plist"><span>포인트관리</span></a><br />
			<a href="member_mail.mema"><span>메일발송</span></a>
	</div>
	<div class="cata">
		<h4>주문</h4>
			<a href="order_list.orda"><span>결제</span></a>
	</div>
	<div class="cata">
		<h4>관리자</h4>
			<a href="admin_list.adma"><span>관리자목록</span></a>
	</div>
	<div class="cata">
		<h4>게시판</h4>
			<a href=""><span>자유게시판</span></a><br />
			<a href=""><span>가짜는가라</span></a><br />
			<a href=""><span>공지사항</span></a><br />
			<a href=""><span>FAQ</span></a><br />
			<a href=""><span>Q&A</span></a>	
	</div>
	<div class="cata">
		<h4>통계</h4>
			<a href="sale_pdt.stats"><span>매출통계</span></a><br />
			<a href="register_pdt.stats"><span>등록통계</span></a><br />
			<a href="date_pdt.stats"><span>기간별 판매 통계</span></a>
	</div>
</div>
</body>
</html>