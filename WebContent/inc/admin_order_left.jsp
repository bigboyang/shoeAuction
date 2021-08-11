<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String[] arrUri2 = request.getRequestURI().split("/");
String cFile = arrUri2[(arrUri2.length - 1)];
//System.out.println(cFile);
%>
<div id="left">
<br /><br />
<a href="order_list.orda" style="font-weight:bold;">결제</a><br />
</div>