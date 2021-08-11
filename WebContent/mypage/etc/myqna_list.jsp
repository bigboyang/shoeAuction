<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
ArrayList<QnaInfo> MyQnaList = (ArrayList<QnaInfo>)request.getAttribute("MyQnaList");

request.setCharacterEncoding("utf-8");
if (loginMember == null) {
   out.println("<script>");
   out.println("alert('먼저 로그인을 해야 합니다.');");
   out.println("location.href = 'login_form.jsp';");   // 나중에 링크 붙이기
   out.println("</script>");
   out.close();
}

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

String args = "", schargs = "";

if (pageInfo.getKeyword() == null) schargs += "&keyword=";
else schargs += "&keyword=" + pageInfo.getKeyword();

if (schtype == null || keyword == null) {   
   schtype = "";   keyword = "";
} else if (!keyword.equals("")) {   
   schargs = "&schtype=" + schtype + "&keyword=" + keyword;
}

if (pageInfo.getStatus() == null) schargs += "&status=";
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
   
#wrapper {width: 1200px; margin : 0 auto;}
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
    #outer {width: 1200px; align:center; position:absolute; padding-bottom:150px; padding-left:150px; }
   #center {float:left; width:700px; margin-top:0px;}
   #footer {position:absolute; left:250px; bottom:-500px;}
</style> 
</head>
<body>
<div id="wrapper">
<%@ include file="../../inc/main_header.jsp" %>
<div id="outer">
	<%@ include file="../../inc/mypage_left.jsp" %>
	<div id="center">
		<form name="frmSch" method="get">
		<input type="hidden" name="ord" value="<%=ord %>" />
		<h2>1대1 문의</h2>
		<table  width="800" cellpadding="5"  class = "table table-bordered table-hover table-condensed" >
		<tr>
		<td align="center">
		   <select name="schtype" class = "form-control">
		         <option value="bq_title" <% if (schtype.equals("bq_title")) { %>selected="selected"<% } %>>제목</option>
		         <option value="bq_content" <% if (schtype.equals("bq_content")) { %>selected="selected"<% } %>>내용</option>
		         <option value="tc" <% if (schtype.equals("tc")) { %>selected="selected"<% } %>>제목+내용</option>
		   </select>
		</td>
		<td>
		<td width="40%"><input type="text" name="keyword" value="<%=pageInfo.getKeyword()%>" /></td>
		</td>
		<td align="center">
		   <select name="status" class = "form-control">
		         <option value="c" <% if (status.equals("c")) { %>selected="selected"<% } %>>전체</option>
		         <option value="a" <% if (status.equals("a")) { %>selected="selected"<% } %>>답변대기</option>
		         <option value="b" <% if (status.equals("b")) { %>selected="selected"<% } %>>답변완료</option>
		   </select>
		</td>
		<td><input type="submit" value="검색" /></td>
		</tr>
		</table>
		<br />
		<table width="800"  class = "table table-bordered table-hover table-condensed">
		<tr>
		<th width="10%">문의분류</th><th width="20%">문의일자</th>
		<th width="50%">제목</th><th width="20%">답변상태</th>
		</tr>
		<tr>
		<% 
		if (MyQnaList != null && MyQnaList.size() > 0) {   // 상품 검색 결과가 있으면
		   for (int i = 0 ; i < MyQnaList.size() ; i++) {
			   String title = "";
		      QnaInfo mq = MyQnaList.get(i);
				title = mq.getBq_title();
				if (title.length() > 30) 	title = title.substring(0, 20) + "...";

		         String lnk = "<a href=\"qna_view.etc" + args +
		                 "&idx=" + mq.getBq_idx() +  "\">";
		
		%>
		</tr>
		<tr>
		<td align="center"><%=mq.getBq_cata() %></td>
		<td><%=mq.getBq_qdate() %></td>
		<td align="center"><%=lnk %><%=mq.getBq_title()%></a></td>
		<td align="center"><% if (mq.getBq_status().equals("a")) {%>답변대기
		<% } else { %>
		답변완료
		<% } %>
		</td>
		<%
		   }
		} else {
		   out.println("<td colspan='4' align='center'> 검색 결과가 없습니다.</td>");
		}
		%>
		</tr>
		</table>
		<br />
		<%
		if (MyQnaList != null && MyQnaList.size() > 0) {   
		   args = "&ord=" + ord + schargs;
		   out.println("<p style=\"width:800px;\" align=\"center\">");
		   
		   if (cpage == 1) {   
		      out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
		   } else {
		      out.print("<a href='myqna_list.etc?cpage=1" + args + "'>");
		      out.println("[&lt;&lt;]</a>&nbsp;");
		      out.print("<a href='myqna_list.etc?cpage=" + (cpage - 1) + args + "'>");
		      out.println("[&lt;]</a>&nbsp;");
		   }   
		   
		   for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
		      if (cpage == k) {
		         out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
		      } else {
		         out.print("&nbsp;<a href='myqna_list.etc?cpage=" + k + args + "'>");
		         out.print(k + "</a>&nbsp;");
		      }
		   }
		   
		   if (cpage == pcnt) {
		      out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
		   } else {
		      out.println("&nbsp;<a href='myqna_list.etc?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
		      out.print("&nbsp;<a href='myqna_list.etc?cpage=" + pcnt + args + "'>");
		      out.println("[&gt;][&gt;]</a>");
		   }
		
		   out.println("</p>");
		   
		} else {   
		   out.println("<p style=\"width:800px;\" align=\"center\" >검색 결과가 없습니다.</p>");
		}
		%>
		</form>
	</div>
	<%@ include file="../../inc/footer.jsp" %>
</div>
</div>
</body>
</html>