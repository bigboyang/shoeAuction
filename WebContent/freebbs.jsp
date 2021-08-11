<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "java.util.*" %>
<%
request.setCharacterEncoding("utf-8");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<CenterInfo> BbsList = (ArrayList<CenterInfo>)request.getAttribute("BbsList");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");


int rcnt = pageInfo.getRcnt();	
int pcnt = pageInfo.getPcnt();		int cpage = pageInfo.getCpage();	
int spage = pageInfo.getSpage();	int epage = pageInfo.getEpage();	
int psize = pageInfo.getPsize();	int bsize = pageInfo.getBsize();
String ord = pageInfo.getOrd();

String schtype = pageInfo.getSchtype();	String keyword = pageInfo.getKeyword();
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

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" href="/css/bootstrap.css">
<script type="text/javascript" src="/js/bootstrap.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
 
<title>Insert title here</title>
<style>
	#wrapper { position:relative; min-height:100%; }
	html, body { height : 100%;}
	
   #header { margin:0 auto; position:relative; left:160px;}
   #outer {width: 1200px; align:center; position:absolute; left:50%; margin-left:-500px; }
   #center {float:left; width:730px; position:relative; left:10%;}
   #left { float:left; width:150px; height:400px;}
   
   
   .page ul { text-align : center;}
   .page li { display : inline-block;}
   
   	a:link { color:#000; text-decoration:none; }
	a:visited { color:#000; text-decoration:none; }
	a:hover { color:#000; text-decoration:none;}
	a:focus { color:#000; text-decoration:none;}
	a:active { color:#000; text-decoration:none;}
	
	.page { text-align : center !important;}
	/* 주석다는법  */
</style>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 
</head>
<body>
<div id = "wrapper">
<%@ include file = "inc/main_header.jsp" %>
<div id = "outer">
<div id = "center">
<h3>자유게시판</h3>
<form action = ""  method="get">
<p style="width:700px;" align="right">
      <select name = "sort" id = "sort" onchange = "location.href=this.value" class = "form-control">
      <option value="freebbs.bbs?ord="<%=args %> <% if (ord.equals("idxd")) {%> selected = "selected" <%} %>>최신순</option>
      <option value="freebbs.bbs?ord=reply"<%=args %> <% if (ord.equals("replyd")) {%> selected = "selected" <%} %>>댓글순</option>
   </select>
</p>
<table style="width:700px;" cellpadding="5" class = "table table-bordered table-hover table-condensed">
<% if (BbsList != null && BbsList.size() > 0) { %>
	 <tr>
	<td>제목</td><td>작성자</td><td>등록일</td><td>조회수</td><td>댓글수</td>
	</tr>
	 <% for (int i = 0 ; i < BbsList.size() ; i++) {
	      CenterInfo c = BbsList.get(i);
	      String lnk = "<a href='freebbsview.bbs?idx="+c.getBf_idx()+args+"'>";
	%>
	<tr>
		<td><%=lnk%><%=c.getBf_title() %></a></td>
		<td> <%=c.getMi_id() %> </td>
		<td> <%=c.getBf_date() %> </td>
		<td> <%=c.getBf_readcnt()%> </td>
		<td> <%=c.getBf_reply()%> </td>
	</tr>
	<% } %>
<%} else { %>
 	<tr><th>검색 결과가 없습니다.</th></tr>
<%} %>
</table>
<table width="700">
<tr>
<td align="center">
	<select name="schtype">
		<option value="bf_title" <%if (schtype.equals("title")) {%> selected="selected"<% } %>>제목</option>
		<option value="br_content" <%if (schtype.equals("content")) {%> selected="selected"<% } %>>내용</option>
		<option value="tc" <%if (schtype.equals("tc")) {%> selected="selected"<% } %>>제목+내용</option>
	</select>
	<input type="text" name="keyword" value="<%=keyword%>" />
	<input type="submit" value="검색" />
</td>
</tr>
</table>
</form>
<form name="frmin" action="bbsin.bbs" method="post">
<table width="700">
<tr>
<td align="right"><input type="submit" value="글 등록"/>
</td>
</tr>
</table>
</form>
	<div class = "page">
<%
if (BbsList != null && BbsList.size() > 0) {	
	args = "&ord=" + ord + schargs;
	// out.println("<p style=\"width:800px;\" align=\"center\" class = \"pagination\">");
	out.println("<ul style=\"width:800px;\" class = \"pagination\">");
	if (cpage == 1) {	
		out.println("<li><a>[&lt;&lt;]</a><a>[&lt;]</a></li>");
	} else {
		out.print("<li><a href='freebbs.bbs?cpage=1" + args + "'>");
		out.println("[&lt;&lt;]</a></li>");
		out.print("<li><a href='freebbs.bbs?cpage=" + (cpage - 1) + args + "'>");
		out.println("[&lt;]</a></li>");
	}	
	
	for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
		if (cpage == k) {
			out.print("<li><a><strong>" + k + "</strong></a></li>");
		} else {
			out.print("<li><a href='freebbs.bbs?cpage=" + k + args + "'>");
			out.print(k + "</a></li>");
		}
	}
	
	if (cpage == pcnt) {
		out.println("<li><a>[&gt;]</a><a>[&gt;&gt]</a></li>");
	} else {
		out.println("<li><a href='freebbs.bbs?cpage=" + (cpage + 1) + args + "'>[&gt;]</a></li>");
		out.print("<li><a href='freebbs.bbs?cpage=" + pcnt + args + "'>");
		out.println("[&gt;][&gt;]</a></li>");
	}

	// out.println("</p>");
	out.println("</ul>");
}
%>
</div>




</div>
</div>
</div>
</body>
</html>