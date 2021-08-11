<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String[] arrUri2 = request.getRequestURI().split("/");
String cFile = arrUri2[(arrUri2.length - 1)];
//System.out.println(cFile);
%>
<div id="left">
<br /><br />
<a href="member_list.mema?ptype=mlist" <% if (cFile.equals("a_member_list.jsp")) { %> style="font-weight:bold;"<% } %>>회원목록</a><br />
<a href="member_list.mema?ptype=plist" <% if (cFile.equals("a_member_point.jsp") || cFile.equals("a_member_point_detail.jsp")) { %> style="font-weight:bold;"<% } %>>포인트관리</a><br />
<a href="member_mail.mema" <% if (cFile.equals("a_member_mail.jsp")) { %> style="font-weight:bold;"<% } %>>메일발송</a><br />
</div>