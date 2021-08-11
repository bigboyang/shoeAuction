<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String[] arrUri = request.getRequestURI().split("/");
String cFolder = arrUri[(arrUri.length-2)];
//System.out.println(request.getRequestURI());
//System.out.println(cFolder);
%>
<div><a href="a_index_form.jsp"><h2>Logo</h2></a></div>
<div>
<a href="pdt_list.pdta" <% if (cFolder.equals("product")) { %> style="font-weight:bold;"<% } %>>상품</a>
<a href="member_list.mema?ptype=mlist" <% if (cFolder.equals("member")) { %> style="font-weight:bold;"<% } %>>회원</a>
<a href="order_list.orda" <% if (cFolder.equals("order")) { %> style="font-weight:bold;"<% } %>>주문</a>
<a href="admin_list.adma" <% if (cFolder.equals("admin")) { %> style="font-weight:bold;"<% } %>>관리자</a>
<a href="" <% if (cFolder.equals("boarder")) { %> style="font-weight:bold;"<% } %>>게시판</a>
<a href="sale_pdt.stats" <% if (cFolder.equals("stats")) { %> style="font-weight:bold;"<% } %>>통계</a>
</div>