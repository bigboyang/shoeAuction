<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String[] arrUri2 = request.getRequestURI().split("/");
String cFile = arrUri2[(arrUri2.length - 1)];
//System.out.println(cFile);
%>
<div id="left">
<br /><br />
<a href="admin_list.adma" <% if (cFile.equals("a_admin_list.jsp")) { %> style="font-weight:bold;"<% } %>>관리자목록</a><br />
<a href="admin_form.adma" <% if (cFile.equals("a_admin_form.jsp")) { %> style="font-weight:bold;"<% } %>>관리자등록</a><br />
</div>