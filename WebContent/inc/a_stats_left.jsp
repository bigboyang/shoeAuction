<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String[] arrUri2 = request.getRequestURI().split("/");
String cFile = arrUri2[(arrUri2.length - 1)];
//System.out.println(cFile);
%>
<div id="left">
<br /><br />
<a href="sale_pdt.stats" <% if (cFile.equals("a_sale_option.jsp")) { %> style="font-weight:bold;"<% } %>>판매 통계</a><br />
<a href="register_pdt.stats" <% if (cFile.equals("a_register_option.jsp")) { %> style="font-weight:bold;"<% } %>>등록 통계</a><br />
<a href="date_pdt.stats" <% if (cFile.equals("a_sale_date.jsp")) { %> style="font-weight:bold;"<% } %>>기간별 판매 통계</a><br />

<br />