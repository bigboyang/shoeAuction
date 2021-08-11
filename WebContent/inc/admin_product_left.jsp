<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String[] arrUri2 = request.getRequestURI().split("/");
String cFile = arrUri2[(arrUri2.length - 1)];
//System.out.println(cFile);
%>
    <style>
    	a:link { color:#4f4f4f; text-decoration:none; }
	a:visited { color:#8a2e91; text-decoration:none; }
	a:hover { color:#000; text-decoration:none; font-weight:bold; }
	a:focus { color:#000; text-decoration:none; font-weight:bold; }
	a:active { color:#00; text-decoration:none; font-weight:bold; }
    
    </style>
	<div id="left">
	<br /><br />
		<a href="pdt_list.pdta" <% if (cFile.equals("a_pdt_list.jsp") || cFile.equals("a_pdt_view.jsp") || cFile.equals("a_pdt_reg.jsp")) { %> style="font-weight:bold;"<% } %>>상품관리</a><br />
		<a href="pdt_wait_list.pdta" <% if (cFile.equals("a_pdt_wait_list.jsp") || cFile.equals("a_pdt_wait_reg.jsp")) { %> style="font-weight:bold;"<% } %>>대기상품</a><br />
		<a href="pdt_failauc_list.pdta" <% if (cFile.equals("a_failauc_list.jsp") || cFile.equals("a_failauc_view.jsp")) { %> style="font-weight:bold;"<% } %>>유찰관리</a><br />
		<a href="pdt_waiting_list.pdta" <% if (cFile.equals("a_waiting_list.jsp")) { %> style="font-weight:bold;"<% } %>>구매대기</a><br />
	</div>