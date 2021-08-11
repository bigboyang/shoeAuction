<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
if (loginMember == null) {
	out.println("<script>");
	out.println("alert('로그인 후 사용하실 수 있습니다.');");
	out.println("location.href='login_form.jsp?url=mypage';");
	out.println("</script>");
	out.close();
}

request.setCharacterEncoding("utf-8");
ArrayList<PdtInfo> logList = (ArrayList<PdtInfo>)request.getAttribute("logList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<style>
   	a:link { color:#000; text-decoration:none; }
	a:visited { color:#000; text-decoration:none; }
	a:hover { color:#000; text-decoration:none;}
	a:focus { color:#000; text-decoration:none;}
	a:active { color:#000; text-decoration:none;}
   
#wrapper {width: 1200px; margin : 0 auto;}
   #header { margin:0 auto; position:relative; left:160px;}
   #outer {width: 1200px; align:center; position:absolute; padding-bottom:150px; padding-left:150px; }
   #left { float:left; left : 20px; width:150px; }
   #center {float:left; width:720px; margin-top:0px;}
   .recent { float:left; }
   .ing { float:left; }
   #center .list-inline li { font-size:15px; padding-left:43px;}
   
   #footer { position:absolute; bottom:0; width:100%; height:150px;}
   
</style>
</head>
<body>
<div id = "wrapper">
<%@ include file="../inc/main_header.jsp" %>
<br />
<div id="outer">
	<div id="left">
		<div class = "container">
			<h4>입찰</h4>
			<div >
				<a href="mp_buyauc.auc">구매입찰현황</a><br />
				<a href="mp_sellauc.auc?wtype=s">판매입찰현황</a><br />
				<a href="mp_waitauc.auc">결제대기중</a><br />
				<a href="mp_failauc.auc?wtype=f">유찰관리</a><br />
				<a href="mp_notauc.auc">입찰후미구매</a>
			</div>
		</div><br />
		<div class = "container">
			<h4>거래 내역</h4>
			<div >
				<a href="mp_buyauc.etc">구매내역</a><br />
				<a href="sell_list.etc">판매내역</a>
			</div>
		</div><br />
		<div class = "container">
			<h4>고객센터</h4>
			<div >
				<a href="mp_chklist.etc">검수현황</a><br />
				<a href="myqna_list.etc">1:1문의</a>
			</div>
		</div><br />
		<div class = "container">
			<h4>내 정보</h4>
			<div >
				<a href="mypage.mem">회원정보관리</a><br />
				<a href="pwdchg_form.mem">비밀번호 변경</a><br />
				<a href="mp_zzim.mem">찜목록</a><br />
				<a href="point_form.mem">포인트</a>
			</div>
		</div>
	</div>
	<div id="center">
		<h3>내 입찰 현황</h3>
		<hr /><%-- 
		<div>
			<div class="ing" align="center">배송중<br /><a href="mp_buyauc.etc"><span style="font-size:150%;"><%=request.getAttribute("buying") %></span></a></div>
			<div class="ing" align="center">판매진행중<br /><a href="mp_sellauc.auc?wtype=s"><span style="font-size:150%;"><%=request.getAttribute("selling") %></span></a></div>
			<div class="ing" align="center">결제대기중<br /><a href="mp_waitauc.auc"><span style="font-size:150%;"><%=request.getAttribute("waiting") %></span></a></div>
			<div class="ing" align="center">유찰관리중<br /><a href="mp_failauc.auc?wtype=f"><span style="font-size:150%;"><%=request.getAttribute("failing") %></span></a></div>
			<div class="ing" align="center">미구매<br /><a href="mp_notauc.auc"><span style="font-size:150%;"><%=request.getAttribute("notpay") %></span></a></div>
		</div> --%>
		<div class = "container">
			<ul class="list-inline">
				<li align = "center">배송중<br /><a href="mp_buyauc.etc"><span style="font-size:150%;"><%=request.getAttribute("buying") %></span></a></li>
				<li align = "center">판매진행중<br /><a href="mp_sellauc.auc?wtype=s"><span style="font-size:150%;"><%=request.getAttribute("selling") %></span></a></li>
				<li align = "center">결제대기중<br /><a href="mp_waitauc.auc"><span style="font-size:150%;"><%=request.getAttribute("waiting") %></span></a></li>
				<li align = "center">유찰관리중<br /><a href="mp_failauc.auc?wtype=f"><span style="font-size:150%;"><%=request.getAttribute("failing") %></span></a></li>
				<li align = "center">미구매<br /><a href="mp_notauc.auc"><span style="font-size:150%;"><%=request.getAttribute("notpay") %></span></a></li>
			</ul>
		</div>
		
		<h3>최근 본 상품</h3>
		<hr />
		<div class = "log">
		<% 
		for(PdtInfo pi : logList) { %>
			<div class="recent" align="center" style="margin:2px 2px;">
				<a href="pdt_view.pdt?id=<%=pi.getPi_id() %>"><img src="product/shoePic/<%=pi.getPp_top() %>" height="300" width="230"></a><br />
				<%=pi.getB_name() %><br />
				<%=pi.getPi_name().length() > 15 ? pi.getPi_name().substring(0, 15)+"..." : pi.getPi_name() %><br />
				<%
				if (pi.getPa_price() == 0) { %>
				입찰 시작가 : <%=pi.getPi_sprice() %> 
				<% } else { %>
				최고 입찰가 : <%=pi.getPa_price() %>
				<% } %>
			</div>
		<% 
		} %>
		</div>
	</div>
</div>
</div>
</body>
</html>