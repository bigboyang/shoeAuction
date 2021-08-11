<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
PdtPageInfo pdtpageInfo = (PdtPageInfo)request.getAttribute("pdtpageInfo");
CenterInfo article = (CenterInfo)request.getAttribute("article");
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
	#footer { position:relative; left:300px; bottom:0px;}
</style>
</head>
</head>
<body>
<%@ include file="../../inc/main_header.jsp" %>
<div id="outer">
	<div id="center">
		<br /><br />
		<div id="brdList" style="width:800px;" align="center">
		<a href="pdt_list.pdt">전체</a>&nbsp;&nbsp;&nbsp;
		<a href="pdt_list.pdt?brand=n01">nike</a>&nbsp;&nbsp;
		<a href="pdt_list.pdt?brand=a01">adias</a>&nbsp;&nbsp;
		<a href="pdt_list.pdt?brand=b01">newbalance</a>&nbsp;&nbsp;
		<a href="pdt_list.pdt?brand=v01">vans</a>&nbsp;&nbsp;
		<a href="pdt_list.pdt?brand=p01">puma</a>&nbsp;&nbsp;
		<a href="pdt_list.pdt?brand=d01">dr.martin</a>&nbsp;&nbsp;
		<a href="pdt_list.pdt?brand=e01">etc</a>&nbsp;&nbsp;
		</div>
<h2>1:1 문의</h2>
<form name="frm" action="qna_form.etc" method="post">
<table width="800" border=1>
<tr>
<th>분류</th><td><%=article.getBq_cata()%></td>
<th>처리상태</th><td><% if (article.getBq_status().equals("a")) { %>처리중<% } else { %>답변완료<% } %></td>
</tr>
<tr>
<th>제목</th><td><% if (article.getBq_title().length() > 20) { %>
<%=article.getBq_title().substring(0,20) + "..."%>
<% } else { %><%=article.getBq_title()%><% } %></td>
<th>문의일</th><td><%=article.getBq_qdate()%></td>
</tr>
<tr>
<th>아이디</th><td><%=article.getMi_id()%></td>
<th>답변일</th><td><%if (article.getBq_adate() == null) {%> - <% } else {%>
<%=article.getBq_adate()%><% } %></td>
</tr>
</table>
<br />
<h3>문의내용</h3>
<table width="800" height="200" border="1">
<tr>
<td>
<%=article.getBq_content()%>
</td>
</tr>
</table>
<br />
<h3>답변</h3>
<table width="800" height="200" border="1">
<tr>
<td>
<% if (article.getBq_answer() == null) { %>아직 처리중입니다.<% } else { %>
<%=article.getBq_answer()%><% } %>
</td>
</tr>
</table>
<br />
<table width="800">
<tr>
<td>
<input type="submit" value="문의수정" />&nbsp;&nbsp;
<input type="button" value="문의삭제" onclick=""/>
</td>
<td align="right">
<input type="button" value="목록으로" onclick="location.href='qna_list.etc'" />
</td>
</tr>
</table>
</form>
</div>
<%@ include file="../../inc/footer.jsp" %>
</div>
</body>
</html>