<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
ArrayList<BrandInfo> brandList = (ArrayList<BrandInfo>)request.getAttribute("brandList");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
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
      #footer { position:relative; top:400px; left:100px; }
</style>
</head>
<body>
<%@ include file="inc/main_header.jsp" %>
<h2>고객센터 메인</h2>
<a href="faq.bbs">자주 묻는 질문</a>
<a href="qna_list.etc">1:1문의</a>
<a href="notice_list.ntc">공지사항</a>
<%@ include file="inc/footer.jsp" %>
</body>
</html>