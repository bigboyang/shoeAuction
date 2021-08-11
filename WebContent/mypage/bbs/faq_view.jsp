<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
CenterInfo faqInfo = (CenterInfo)request.getAttribute("faqInfo");

request.setCharacterEncoding("utf-8");

int cpage = pageInfo.getCpage();   // 현재 페이지 번호.
int pcnt = pageInfo.getPcnt();      // 전체 페이지 개수.
int rcnt = pageInfo.getRcnt();      // 전체 게시글 개수.
int spage = pageInfo.getSpage();   // 블록 시작 페이지 번호.
int epage = pageInfo.getEpage();   // 블록 종료 페이지 번호.
int psize = pageInfo.getPsize();   // 페이지크기.
int bsize = pageInfo.getBsize();   // 블록크기.


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
   #footer {position:absolute; left:350px; bottom:-800px;}
   .TaHeader{
   	height : 30px;
   }
   
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
</style> 
</head>
<body>
<div id="outer">
<%@ include file="../../inc/main_header.jsp" %>
	<div id = "left">
	<%@ include file="../../inc/center_left.jsp" %>
	<div id="center">
<h2>FAQ</h2>
<form name="faqViewfrm" method="get" action = "">
<table cellpadding = "0" cellspacing = "0" id = "Ta">
		<tr>
			<td align="center" class = "TaHeader"> <a href = "faq.bbs?status=&schtype=<%=schtype%>&keyword=<%=keyword%>">전체</a></td>
			<td align="center" class = "TaHeader"> <a href = "faq.bbs?status=a&schtype=<%=schtype%>&keyword=<%=keyword%> ">구매 </a></td>
			<td align="center" class = "TaHeader"> <a href = "faq.bbs?status=b&schtype=<%=schtype%>&keyword=<%=keyword%>">입찰</a></td>
			<td align="center" class = "TaHeader"> <a href = "faq.bbs?status=c&schtype=<%=schtype%>&keyword=<%=keyword%>">배송</a></td>
			<td align="center" class = "TaHeader"> <a href = "faq.bbs?status=d&schtype=<%=schtype%>&keyword=<%=keyword%>">기타</a></td>
		</tr>
			<tr>
				<td colspan = "5" align = "left" height = "50px;">
					<%=faqInfo.getBf_title() %>
				</td>			
			</tr>
			<tr>
				<td colspan = "5" align ="left" v-align="top">
					<%=faqInfo.getBf_content() %>
				</td>			
			</tr>
			<tr>
				<td colspan = "5" align = "center">
				<input type = "button" value ="목록으로" onclick = "location.href ='faq.bbs<%=args%>'"/>
				</td>
			</tr>
</table>
</form>
</div>
</div>
<%@ include file="../../inc/footer.jsp" %>
</div>
</body>
</html>