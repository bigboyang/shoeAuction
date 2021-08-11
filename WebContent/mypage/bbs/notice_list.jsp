<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
ArrayList<CenterInfo> noticeList = (ArrayList<CenterInfo>)request.getAttribute("noticeList");

int cpage = pageInfo.getCpage();   // 현재 페이지 번호.
int pcnt = pageInfo.getPcnt();      // 전체 페이지 개수.
int rcnt = pageInfo.getRcnt();      // 전체 게시글 개수.
int spage = pageInfo.getSpage();   // 블록 시작 페이지 번호.
int epage = pageInfo.getEpage();   // 블록 종료 페이지 번호.
int psize = pageInfo.getPsize();   // 페이지크기.
int bsize = pageInfo.getBsize();   // 블록크기.

String ord = pageInfo.getOrd();

String schtype = pageInfo.getSchtype();      // 대분류.
String keyword = pageInfo.getKeyword();      // 검색어.

String args = "", schargs = "";
if (pageInfo.getSchtype() == null) schargs += "&schtype=";
else schargs += "&schtype=" + pageInfo.getSchtype();
if (pageInfo.getKeyword() == null) schargs += "&keyword=";
else schargs += "&keyword=" + pageInfo.getKeyword();

args = "?cpage=" + cpage + schargs;
String sargs = "&" + args.substring(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<style>
   a:link { color:#4f4f4f; text-decoration:none; }
   a:visited { color:#8a2e91; text-decoration:none; }
   a:hover { color:#000; text-decoration:none; font-weight:bold; }
   a:focus { color:#000; text-decoration:none; font-weight:bold;}
   a:active { color:#000; text-decoration:none; font-weight:bold;}
   
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   #footer {position:absolute; left:350px; bottom:-800px;}
   .nt{ border : 1px solid black; border-collapse: collapse }
   .nt tr { border-bottom : 1px solid black;}
   .isnotice { background : #ebecf0;}
</style>
</head>
<div id="outer">
<%@ include file="../../inc/main_header.jsp" %>
	<div id = "left">
	<%@ include file="../../inc/center_left.jsp" %>
	<div id="center">
	</div>
	<div id="center">
		<h2>NOTICE</h2>
		<table width = "800" cellpadding = "5" cellspacing = "0" class = "nt">
			<tr>
				<th width = "*">	
					제목
				</th>
				<th width = "15%">
					등록일
				</th>
				<th width = "15%">
					조회수
				</th>
			</tr>
			<% 
			if (noticeList != null && noticeList.size() > 0) {
				for (int i = 0 ; i < noticeList.size() ; i++) {
					CenterInfo ci = noticeList.get(i);
					String title = ci.getBn_title();
					if (title.length() > 50) {
						title = ci.getBn_title().substring(0, 47) + "...";
					}
					String lnk = "<a href='notice_view.ntc?id=" + ci.getBn_idx() + sargs + "'>";
			%>
			<tr <%if(ci.getBn_isnotice().equals("y")){ %> class = "isnotice" <%} %>>
				<td>
					<%=lnk + title %></a>
				</td>
				<td align = "center">
					<%=ci.getBn_date().substring(0, 10) %>
				</td>
				<td align = "center">
					<%=ci.getBn_read() %>
				</td>
			</tr>
			<%
				}
			} else {
				out.println("<tr><th>검색 결과가 없습니다.</th></tr>");
			}
			%>
		</table>
		
		<%
		if (noticeList != null && noticeList.size() > 0) {   
			args = schargs;
			out.println("<p style=\"width:800px;\" align=\"center\">");
         
			if (cpage == 1) {   
				out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
			} else {
				out.print("<a href='notice_list.ntc?cpage=1" + args + "'>");
				out.println("[&lt;&lt;]</a>&nbsp;");
				out.print("<a href='notice_list.ntc?cpage=" + (cpage - 1) + args + "'>");
				out.println("[&lt;]</a>&nbsp;");
			}   
	         
			for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
				if (cpage == k) {
					out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
				} else {
					out.print("&nbsp;<a href='notice_list.ntc?cpage=" + k + args + "'>");
					out.print(k + "</a>&nbsp;");
				}
			}
	         
			if (cpage == pcnt) {
				out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
			} else {
				out.println("&nbsp;<a href='notice_list.ntc?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
				out.print("&nbsp;<a href='notice_list.ntc?cpage=" + pcnt + args + "'>");
				out.println("[&gt;][&gt;]</a>");
			}

			out.println("</p>");   
		}
		%>
		
		<form name="frmSch" method="get">
			<table width="800" cellpadding="5">
			<tr><td colspan = "3"></td></tr>
				<tr>
					<td align="center" colspan = "3">
						<select name="schtype">
							<option value="title" <% if (schtype.equals("title")) { %>selected="selected"<% } %>>제목</option>
							<option value="content" <% if (schtype.equals("content")) { %>selected="selected"<% } %>>내용</option>
							<option value="tc" <% if (schtype.equals("tc")) { %>selected="selected"<% } %>>제목 + 내용</option>
						</select>
						<input type="text" name="keyword" value="<%=pageInfo.getKeyword() %>" />
						<input type="submit" value="검색"/>
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