<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
ArrayList<CenterInfo> faqList = (ArrayList<CenterInfo>)request.getAttribute("faqList");

request.setCharacterEncoding("utf-8");

int cpage = pageInfo.getCpage();   // 현재 페이지 번호.
int pcnt = pageInfo.getPcnt();      // 전체 페이지 개수.
int rcnt = pageInfo.getRcnt();      // 전체 게시글 개수.
int spage = pageInfo.getSpage();   // 블록 시작 페이지 번호.
int epage = pageInfo.getEpage();   // 블록 종료 페이지 번호.
int psize = pageInfo.getPsize();   // 페이지크기.
int bsize = pageInfo.getBsize();   // 블록크기.

String ord = pageInfo.getOrd();

String keyword = pageInfo.getKeyword();      // 검색어.
String schtype = pageInfo.getSchtype();      // 검색조건
String status = pageInfo.getStatus();      // 구분
//구매a, 입찰b, 배송c, 기타d
String args = "", schargs = "";




if (pageInfo.getKeyword() == null) schargs += "&keyword=";
else schargs += "&keyword=" + pageInfo.getKeyword();
if (schtype == null || keyword == null) {   
   schtype = "";   keyword = "";
} else if (!keyword.equals("")) {   
   schargs = "&schtype=" + schtype + "&keyword=" + keyword;
}
if (pageInfo.getStatus() == null || pageInfo.getStatus().equals("")) schargs += "&status=";
else schargs += "&status=" + pageInfo.getStatus();

args = "?cpage=" + cpage + schargs;
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
   a:focus { color:#000; text-decoration:none; font-weight:bold;}
   a:active { color:#000; text-decoration:none; font-weight:bold;}
   
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
    #center { float:right; position:absolute; left:200px;}
   
   #Ta {
   	width : 600px; height : 500px; 
    border-top: 1px solid #444444;
    border-collapse: collapse;
   }
    th, td {
    border-top: 1px solid #444444;
    padding: 10px;
  }
   .TaHeader{
   	height : 30px;
   }
   #page {
   	position:relative;
   	right:110px;
   }
   #footer { position:absolute; left:250px; bottom:0px;}
</style> 
</head>
<body>
<%@ include file="../inc/main_header.jsp" %>
<div id ="outer"/>
<%@ include file = "../../inc/center_left.jsp" %>
<div id = "center">
<h2>FAQ</h2>
<table cellpadding = "0" cellspacing = "0"  id = "Ta">
<form name="faqfrm" method="get" action = "" >
		<input type="hidden" name="ord" value="<%=ord %>" />
		
		<tr class = "TaHeader">
			<td align="center"> <a href = "faq.bbs?status=&schtype=<%=schtype%>&keyword=<%=keyword%>">전체</a></td>
			<td align="center"> <a href = "faq.bbs?status=a&schtype=<%=schtype%>&keyword=<%=keyword%> ">구매 </a></td>
			<td align="center"> <a href = "faq.bbs?status=b&schtype=<%=schtype%>&keyword=<%=keyword%>">입찰</a></td>
			<td align="center"> <a href = "faq.bbs?status=c&schtype=<%=schtype%>&keyword=<%=keyword%>">배송</a></td>
			<td align="center"> <a href = "faq.bbs?status=d&schtype=<%=schtype%>&keyword=<%=keyword%>">기타</a></td>
		</tr>
			<%if (faqList != null && faqList.size() > 0) {
				for (int i = 0; i < faqList.size(); i++) {
				 CenterInfo nt = faqList.get(i);
			     String lnk = "<a href=\"faq_view.bbs" + args +
			                 "&idx=" + nt.getBf_idx() +  "\">";
			%>
			<tr height = "80px;" align = "left">
				<td colspan = "5"> <%=lnk %><%=nt.getBf_title()%></a></td>
			</tr>
				<%} %>
			<tr heigth = "50px;">
				<td colspan = "5" align = "center">
					<select name = "schtype">
						<option value = "bf_title" <%if(schtype.equals("bf_title")) {%> selected = "selected" <%} %> >제목</option>
						<option value = "tc" <%if(schtype.equals("tc")) {%> selected = "selected" <%} %> >제목 + 내용</option>
					</select>
					<input type="text" name = "keyword" value = "<%=pageInfo.getKeyword() %>"/>
					<input type = "submit" value ="검색"/>
				</td>
			</tr>
			<%} else {  out.println("<td colspan='5' align='center'>검색결과가 없습니다.</td>");  } %>
			
</table>
<%
if (faqList != null && faqList.size() > 0) {	
	args = "&ord=" + ord + schargs;
	out.println("<p id=\"page\" style=\"width:800px;\" align=\"center\">");
	
	if (cpage == 1) {	
		out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
	} else {
		out.print("<a href='faq.bbs?cpage=1" + args + "'>");
		out.println("[&lt;&lt;]</a>&nbsp;");
		out.print("<a href='faq.bbs?cpage=" + (cpage - 1) + args + "'>");
		out.println("[&lt;]</a>&nbsp;");
	}	
	
	for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
		if (cpage == k) {
			out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
		} else {
			out.print("&nbsp;<a href='faq.bbs?cpage=" + k + args + "'>");
			out.print(k + "</a>&nbsp;");
		}
	}
	
	if (cpage == pcnt) {
		out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
	} else {
		out.println("&nbsp;<a href='faq.bbs?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
		out.print("&nbsp;<a href='faq.bbs?cpage=" + pcnt + args + "'>");
		out.println("[&gt;][&gt;]</a>");
	}

	out.println("</p>");
	
} 
%>
</div>
<%@ include file="../inc/footer.jsp" %>
</div>
</body>
</html>