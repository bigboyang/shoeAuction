<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String[] arrUri2 = request.getRequestURI().split("/");
String cFile = arrUri2[(arrUri2.length - 1)];
System.out.println("arrUri2 =" + arrUri2);
System.out.println("cfile = " +cFile);
%>

<div id = "left">
<a>고객센터 홈 </a>
<br />
<a href="faq.bbs" <%if (cFile.equals("faq.jsp")) { %> style="font-weight:bold;"  <%} %>> FAQ </a>
<br />
<a href="qna_list.etc" <%if (cFile.equals("qna_list.jsp")) { %> style="font-weight:bold;"  <%} %>>QNA</a>
<br />
<a href="notice_list.ntc" <%if (cFile.equals("notice_list.jsp")) { %> style="font-weight:bold;"  <%} %>>공지사항</a>
<br />
</div>