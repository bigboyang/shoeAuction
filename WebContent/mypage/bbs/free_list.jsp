<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<BoardInfo> freeList = (ArrayList<BoardInfo>)request.getAttribute("freeList");
PdtPageInfo pdtpageInfo = (PdtPageInfo)request.getAttribute("pdtpageInfo");

int cpage = pdtpageInfo.getCpage();   // 현재 페이지 번호.
int pcnt = pdtpageInfo.getPcnt();      // 전체 페이지 개수.
int rcnt = pdtpageInfo.getRcnt();      // 전체 게시글 개수.
int spage = pdtpageInfo.getSpage();   // 블록 시작 페이지 번호.
int epage = pdtpageInfo.getEpage();   // 블록 종료 페이지 번호.
int psize = pdtpageInfo.getPsize();   // 페이지크기.
int bsize = pdtpageInfo.getBsize();   // 블록크기.

String ord = pdtpageInfo.getOrd();

String schtype = pdtpageInfo.getSchtype();      // 대분류.
String keyword = pdtpageInfo.getKeyword();      // 검색어.

String args = "", schargs = "";	// 전체 쿼리스트링과 검색용 쿼리스트링
if (schtype == null || keyword == null) {
	schtype = "";	keyword = "";
} else if (!keyword.equals("")) {
	schargs = "&schtype=" + schtype + "&keyword=" + keyword;
}

args = "&cpage=" + cpage + schargs;

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
	   #outer {width: 1200px; aligh:center; position:absolute;}
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   #center { float:right; position:absolute; left:200px;}
</style>
</head>
<body>
<div id="outer">
<%@ include file="../../inc/main_header.jsp" %>
	<div id = "left">
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
<h2>자유게시판</h2>
<form name="freeList" action="free_form.bbs" method="post" >
<table width="800">
<tr>
<td align="right">
	<select name="ord" onchange ="location.href=this.value">
		<option value="" selected disabled hidden>정렬</option>
		<option value="brd_free.bbs<%=args %>&ord=dated" <% if (pdtpageInfo.getOrd().equals("dated")) {%> selected = "selected" <%} %>>최신순</option>
		<option value="brd_free.bbs<%=args %>&ord=replyd" <% if (pdtpageInfo.getOrd().equals("datea")) {%> selected = "selected" <%} %>>댓글순</option>
	</select>
</td>
</tr>
</table>
<br />
<table width="800">
<tr>
<th>글번호</th><th>제목</th><th>작성자</th><th>등록일</th><th>조회수</th>
</tr>
<%
if (freeList.size() > 0 && rcnt > 0) {
	int num = rcnt - (psize * (cpage - 1));
	String title = "", lnk = "";
	for (int i = 0; i < freeList.size(); i++) {
		BoardInfo fi = freeList.get(i);	// noticeList에 들어있는 NoticeInfo인스턴스를 ni에 저장
		title = fi.getBf_title();
		if (title.length() > 20) 	title = title.substring(0, 20) + "...";
		lnk = "<a href='free_view.bbs?idx=" + fi.getBf_idx() + args + "'>";
%>
<tr align="center">
<td><%=fi.getBf_idx() %></td>
<td align="center"><%=lnk + title + "</a>"%>(<%=fi.getBf_reply()%>)</td>
<td><%=fi.getMi_id() %></td>
<td><%=fi.getBf_date().substring(2, 11).replace('-', '.')%></td>
<td><%=fi.getBf_readcnt()%></td>
</tr>
<% 
		num--;
	}
} else {
	out.println("<tr><td colspan='4' align='center'>검색 결과가 없습니다.</td></tr>");
}
%>
</table>
<table width="800">
<tr>
<td>
		<%
		if (freeList != null && freeList.size() > 0) {   
		   out.println("<p style=\"width:800px;\" align=\"center\">");
		   
		   if (cpage == 1) {   
		      out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
		   } else {
		      out.print("<a href='brd_free.bbs?cpage=1" + args + "'>");
		      out.println("[&lt;&lt;]</a>&nbsp;");
		      out.print("<a href='brd_free.bbs?cpage=" + (cpage - 1) + args + "'>");
		      out.println("[&lt;]</a>&nbsp;");
		   }   
		   
		   for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
		      if (cpage == k) {
		         out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
		      } else {
		         out.print("&nbsp;<a href='brd_free.bbs?cpage=" + k + args + "'>");
		         out.print(k + "</a>&nbsp;");
		      }
		   }
		   
		   if (cpage == pcnt) {
		      out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
		   } else {
		      out.println("&nbsp;<a href='brd_free.bbs?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
		      out.print("&nbsp;<a href='brd_free.bbs?cpage=" + pcnt + args + "'>");
		      out.println("[&gt;][&gt;]</a>");
		   }
		
		   out.println("</p>");
		   
		} else {   
		   out.println("<p style=\"width:800px;\" align=\"center\">검색 결과가 없습니다.</p>");
		}
		%>
</td>
<td>
<input type="submit" value="등록">
</td>
</tr>
</table>
</form>
<br /><br />
<form name="" action="" method="get">
<table width="800">
<tr>
<td align="center">
	<select name="schtype">
		<option value="title" <%if (schtype.equals("title")) {%> selected="selected"<% } %>>제목</option>
		<option value="content" <%if (schtype.equals("content")) {%> selected="selected"<% } %>>내용</option>
		<option value="tc" <%if (schtype.equals("tc")) {%> selected="selected"<% } %>>제목+내용</option>
	</select>
	<input type="text" name="keyword" value="<%=keyword%>" />
	<input type="submit" value="검색" />
</td>
</tr>
</table>
</form>
</div>
</div>
</div>
</body>
</html>