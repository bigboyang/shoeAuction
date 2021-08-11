<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<BoardInfo> replyList = (ArrayList<BoardInfo>)request.getAttribute("replyList");
BoardInfo article = (BoardInfo)request.getAttribute("article");
PdtPageInfo pdtpageInfo = (PdtPageInfo)request.getAttribute("pdtpageInfo");

int cpage = pdtpageInfo.getCpage();   // 현재 페이지 번호.
int pcnt = pdtpageInfo.getPcnt();      // 전체 페이지 개수.
int rcnt = pdtpageInfo.getRcnt();      // 전체 게시글 개수.
int spage = pdtpageInfo.getSpage();   // 블록 시작 페이지 번호.
int epage = pdtpageInfo.getEpage();   // 블록 종료 페이지 번호.
int psize = pdtpageInfo.getPsize();   // 페이지크기.
int bsize = pdtpageInfo.getBsize();   // 블록크기.

String ord = pdtpageInfo.getOrd();
String args = "";

args = "&cpage=" + cpage;

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
   #like { position:relative; left:370px; bottom:0px;}
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
<br />
<table width="800">
<tr>
<th width="10%">제목 : </th><td width="60%"><%=article.getBf_title()%></td> <td>게시판 : 자유게시판</td>
</tr>
<tr>
<th>작성자</th><td><%=article.getMi_id()%></td> <td>작성일 : <%=article.getBf_date()%></td>
</tr>
</table>
<br /><br />
<table width="800">
<tr>
<th width="10%">내용</th><td width="*"><%=article.getBf_content()%></td>
</tr>
</table>
<br /><br />
<table>
<tr><td><% if (article.getBf_img1() == null) {%> 첨부파일이 없습니다. <% } else { %><%=article.getBf_img1() %> <% } %></td></tr>
<tr><td><% if (article.getBf_img2() == null) {%> 첨부파일이 없습니다. <% } else { %> <%=article.getBf_img1() %> <% } %></td></tr>
</table>
<br /><br />
<div id="like">
<a href="">좋아요</a>
</div>
<table width="800">
<tr>
<td width="40%">조회수 : <%=article.getBf_readcnt()%> 좋아요 수 : <%=article.getBf_like() %> 댓글수 : <%=article.getBf_reply() %></td>
<td align="center"><input type="button" value="목록" onclick="history.back();"/></td>
<td align="right"><input type="submit" value="수정">&nbsp;&nbsp;<input type="button" value="삭제" /></td>
</tr>
</table>
<br /><br />
<form name="reply" action="" method="get">
<input type="hidden" name="idx" value="<%=article.getBf_idx()%>" />
<table width="800px" >
<%
if (replyList.size() > 0 && rcnt > 0) {
	int num = rcnt - (psize * (cpage - 1));
	for (int i = 0; i < replyList.size(); i++) {
		BoardInfo fi = replyList.get(i);	// noticeList에 들어있는 NoticeInfo인스턴스를 ni에 저장
%>
<tr align="center">
<td><%=fi.getBfr_idx() %></td>
<td><%=fi.getMi_id() %></td>
<td><%=fi.getBfr_content()%></td>
<td><%=fi.getBfr_date().substring(2, 11).replace('-', '.')%></td>
</tr>
<% 
		num--;
	}
} else {
	out.println("<tr><td colspan='4' align='center'>댓글이 없습니다.</td></tr>");
}
%>
</table>
</form>
<br />
<form name="replyIn" action="replyIn.bbs" method="post" />
<input type="hidden" name="idx" value="<%=article.getBf_idx()%>" />
<table>
<tr>
<td>
	<textarea cols="100" rows="5" name="bfr_content" placeholder="회원만 입력 가능합니다."></textarea>
</td>
<td>
	<input type="submit" value="등록" />
</td>
</tr>
</table>
</form>
</div>
</div>
</div>
</body>
</html>