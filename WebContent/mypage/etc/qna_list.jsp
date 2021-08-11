<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<CenterInfo> qnaList = (ArrayList<CenterInfo>)request.getAttribute("qnaList");
PdtPageInfo pdtpageInfo = (PdtPageInfo)request.getAttribute("pdtpageInfo");
//목록화면에서 사용할 페이징과 검색관련 정보들을 담은 pageInfo 인스턴스 생성

int rcnt = pdtpageInfo.getRcnt();	
int pcnt = pdtpageInfo.getPcnt();		int cpage = pdtpageInfo.getCpage();	
int spage = pdtpageInfo.getSpage();	int epage = pdtpageInfo.getEpage();	
int psize = pdtpageInfo.getPsize();	int bsize = pdtpageInfo.getBsize();

String schtype = pdtpageInfo.getSchtype();	String keyword = pdtpageInfo.getKeyword();
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
#wrapper {width: 1200px; margin : 0 auto;}
</style>
</head>
<body>
<div id="wrapper">
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
<form name="frmqna" action="" method="get">
<table width="800" border="1">
<tr>
<th><a href="">전체</a></th>
<th><a href="">구매</a></th>
<th><a href="">판매</a></th>
<th><a href="">배송</a></th>
<th><a href="">반품/교환</a></th>
<th><a href="">기타</a></th>
</tr>
</table>
<br />
<table width="800">
<tr>
<th>분류</th><th>아이디</th><th>문의제목</th><th>문의일</th><th>답변일</th><th>처리상태</th>
</tr>
<%
if (qnaList.size() > 0 && rcnt > 0) {
	int num = rcnt - (psize * (cpage - 1));
	String title = "", lnk = "";
	for (int i = 0; i < qnaList.size(); i++) {
		CenterInfo qi = qnaList.get(i);	// noticeList에 들어있는 NoticeInfo인스턴스를 ni에 저장
		title = qi.getBq_title();
		if (title.length() > 30) 	title = title.substring(0, 28) + "...";
		lnk = "<a href='qna_view.etc?nidx=" + qi.getBq_idx() + args + "'>";
%>
<tr align="center">
<td><%=qi.getBq_cata() %></td>
<td><%=qi.getMi_id() %></td>
<td align="center"><%=lnk + title + "</a>"%></td>
<td><%=qi.getBq_qdate().substring(2, 11).replace('-', '.')%></td>
<td><%=qi.getBq_adate().substring(2, 11).replace('-', '.')%></td>
<td><% if (qi.getBq_status().equals("a")) {%>처리중<%} else { %>답변완료<% } %></td>
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
		if (qnaList != null && qnaList.size() > 0) {   
		   out.println("<p style=\"width:800px;\" align=\"center\">");
		   
		   if (cpage == 1) {   
		      out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
		   } else {
		      out.print("<a href='qna_list.etc?cpage=1" + args + "'>");
		      out.println("[&lt;&lt;]</a>&nbsp;");
		      out.print("<a href='qna_list.etc?cpage=" + (cpage - 1) + args + "'>");
		      out.println("[&lt;]</a>&nbsp;");
		   }   
		   
		   for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
		      if (cpage == k) {
		         out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
		      } else {
		         out.print("&nbsp;<a href='qna_list.etc?cpage=" + k + args + "'>");
		         out.print(k + "</a>&nbsp;");
		      }
		   }
		   
		   if (cpage == pcnt) {
		      out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
		   } else {
		      out.println("&nbsp;<a href='qna_list.etc?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
		      out.print("&nbsp;<a href='qna_list.etc?cpage=" + pcnt + args + "'>");
		      out.println("[&gt;][&gt;]</a>");
		   }
		
		   out.println("</p>");
		   
		} else {   
		   out.println("<p style=\"width:800px;\" align=\"center\">검색 결과가 없습니다.</p>");
		}
		%>
</td>
</tr>
</table>
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
<form name="frmin" action="qna_form.etc" method="post">
<table width="800">
<tr>
<td align="right"><input type="submit" value="글 등록" />
</td>
</tr>
</table>
</form>
</div>
</div>
</div>
</body>
</html>