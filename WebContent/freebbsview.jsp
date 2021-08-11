<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
int islike = (int)request.getAttribute("islike");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
CenterInfo bbsInfo = (CenterInfo)request.getAttribute("bbsInfo");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
PdtPageInfo replyPage = (PdtPageInfo)request.getAttribute("replyPage");
ArrayList<CenterInfo> replyList = (ArrayList<CenterInfo>)request.getAttribute("replyList");

String cpage = request.getParameter("cpage");
String schtype = request.getParameter("schtype");
String keyword = request.getParameter("keyword");
int idx = bbsInfo.getBf_idx();

int cpage2 = replyPage.getCpage();   // 현재 페이지 번호.
int pcnt = replyPage.getPcnt();      // 전체 페이지 개수.
int rcnt = replyPage.getRcnt();      // 전체 게시글 개수.
int spage = replyPage.getSpage();   // 블록 시작 페이지 번호.
int epage = replyPage.getEpage();   // 블록 종료 페이지 번호.
int psize = replyPage.getPsize();   // 페이지크기.
int bsize = replyPage.getBsize();   // 블록크기.


String args = "", schargs = "";
if (schtype == null) schargs += "&schtype=";
else schargs += "&schtype=" + schtype;
if (keyword == null) schargs += "&keyword=";
else schargs += "&keyword=" + keyword;

args = "?cpage="+ cpage + schargs;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
  #wrapper { position:relative; min-height:100%; }
	html, body { height : 100%;}
	
   #header { margin:0 auto; position:relative; left:160px;}
  #outer {width: 1200px; align:center; position:absolute; left:50%; margin-left:-500px; }
   #center {float:left; width:730px;  position:relative; left:10%;}
   #left { float:left; width:150px; height:400px;}
   
   	a:link { color:#000; text-decoration:none; }
	a:visited { color:#000; text-decoration:none; }
	a:hover { color:#000; text-decoration:none;}
	a:focus { color:#000; text-decoration:none;}
	a:active { color:#000; text-decoration:none;}
</style>
<script src="jquery-3.6.0.js"></script>
<script>
	function delReply(bfridx,replier,bf_idx) {
		alert("댓글을 삭제했습니다");
			$.ajax({
				type: "post",
				url	: "/shoeAution/del_reply.bbs",
				data: { "bfridx" : bfridx, "replier" : replier, "bf_idx":bf_idx},
				success : function(chkRst) {
					if (chkRst == 0) {	
						alert("댓글삭제에 실패했습니다.\n다시 시도해주세요.");
					
					} else {
						location.reload();
					}
				}
			});
	}

function youcant(){
	alert("권한이 없습니다.");
}
	
function replyfn(){
	var reply = document.getElementById("reply").value;
	location.href = "bbsreply.bbs?reply="+ reply + "&idx=" +<%=bbsInfo.getBf_idx()%>+"";
}
function like(sort,bf_idx, login_id){
	alert("좋아요 반영되었습니다.");
		$.ajax({
			type:"post",
			url :"/shoeAution/like.bbs",
			data : {"sort":sort,"bf_idx":bf_idx, "login_id":login_id},
			success : function(chkRst){
				if(chkRst == 0){
					alert("반영 실패");
				}else {
					location.reload();
				}
			}
		});
}
</script>
</head>
<body>
<%@ include file="../inc/main_header.jsp" %>
<div id="outer">
	<div id="center">
		<h2>자유게시판</h2>
		<table width = "700" cellpadding = "5" cellspacing = "0" align = "center" class = "table table-bordered table-hover table-condensed" >
			<tr style = "font-size : 15px;">
				<th width = "*" align = "left" colspan = "2">
					<%=bbsInfo.getBf_title() %>
				</th>
			</tr>
			<tr>
				<th width = "*" align = "left">
					작성일 : <%=bbsInfo.getBf_date().substring(0, 16) %>
				</th>
				<th width = "20%">
					조회수 : <%=bbsInfo.getBf_readcnt()%>
				</th>
			</tr>
			<tr valign = "top" style="height:300px;">
				<td colspan="2">
					<%=bbsInfo.getBf_content().replace("\r\n", "<br />") %>
				</td>
			</tr>
			<%if(!bbsInfo.getBf_img1().equals("null")) { %>
			<table  width = "700" cellpadding = "5" cellspacing = "0" align = "center" class = "table table-bordered table-hover table-condensed">
			<tr>
				<td> <img src ="product/shoeEx/<%=bbsInfo.getBf_img1()%>" width="700px" height="500px" alt="등록 이미지 없음"> </td>
			</tr>
			</table>
			<%} %>
			<% if(!bbsInfo.getBf_img2().equals("null"))  { %>
			<table width = "700" cellpadding = "5" cellspacing = "0" align = "center" class = "table table-bordered table-hover table-condensed">
			<tr>
				<td> <img src ="product/shoeEx/<%=bbsInfo.getBf_img2()%>" width="700px" height="500px" alt="등록 이미지 없음"> </td>
			</tr>
			</table>
			<%} %>
		</table>
		<p width = "700" align="center">
			<% if(loginMember == null) { %>
			<span style="cursor:pointer" onclick="youcant()">추천</span> 
			<%} else { 
			if (islike == 0) { %>
			<span style="cursor:pointer" onclick="like('+','<%=bbsInfo.getBf_idx()%>','<%=loginMember.getMi_id()%>');">추천</span>
			<%} else if (islike != 0) { %>
			<span style="cursor:pointer" onclick="like('-','<%=bbsInfo.getBf_idx()%>','<%=loginMember.getMi_id()%>');">추천한 게시글입니다</span>
			<%} %>
			<%} %>
			<br />
			추천수 <%=bbsInfo.getBf_like() %>
		</p>
		
		<table width = "700" cellpadding = "5" cellspacing = "0" align = "center" class = "table table-bordered table-hover table-condensed" >
		<% if(replyList != null && replyList.size() > 0) { 
			for(int i =0; i < replyList.size(); i++ ) {
				CenterInfo reply = replyList.get(i);
		%>
				<tr>
					<td>
					<%=reply.getBrf_content() %>
					</td>
					<td>
					<%=reply.getMi_id()%> 
					<% if(loginMember == null || !loginMember.getMi_id().equals(reply.getMi_id())) { %>
					<span style="cursor:pointer"; onclick="youcant()">삭제</span>
					<% } else {%>
					<span style="cursor:pointer"; onclick="delReply('<%=reply.getBfr_idx()%>','<%=reply.getMi_id()%>','<%=reply.getBf_idx()%>');">삭제</span>
					<%} %>
					</td>
				</tr>
			<%} %>
			<tr>
				<td colspan="2">
					<div class = "page">
<%
if (replyList != null && replyList.size() > 0) {	
	args = "&idx="+idx;
	// out.println("<p style=\"width:800px;\" align=\"center\" class = \"pagination\">");
	out.println("<ul style=\"width:700px;\" class = \"pagination\">");
	if (cpage2 == 1) {	
		out.println("<li><a>[&lt;&lt;]</a><a>[&lt;]</a></li>");
	} else {
		out.print("<li><a href='freebbsview.bbs?cpage2=1" + args + "'>");
		out.println("[&lt;&lt;]</a></li>");
		out.print("<li><a href='freebbsview.bbs?cpage2=" + (cpage2 - 1) + args + "'>");
		out.println("[&lt;]</a></li>");
	}	
	
	for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
		if (cpage2 == k) {
			out.print("<li><a><strong>" + k + "</strong></a></li>");
		} else {
			out.print("<li><a href='freebbsview.bbs?cpage2=" + k + args + "'>");
			out.print(k + "</a></li>");
		}
	}
	
	if (cpage2 == pcnt) {
		out.println("<li><a>[&gt;]</a><a>[&gt;&gt]</a></li>");
	} else {
		out.println("<li><a href='freebbsview.bbs?cpage2=" + (cpage2 + 1) + args + "'>[&gt;]</a></li>");
		out.print("<li><a href='freebbsview.bbs?cpage2=" + pcnt + args + "'>");
		out.println("[&gt;][&gt;]</a></li>");
	}

	// out.println("</p>");
	out.println("</ul>");
}
%>
</div>
				</td>		
			</tr>
		<% } %>
		<tr>
		
			<td><input type="text" placeholder="댓글입력" name="reply" id="reply" style="width:630px;"/></td>
			<td><input type="button" value="댓글등록" onclick="replyfn()"/></td>
		</tr>
		</table>
		<p style = "width:700px; text-align:right;">
			<input type = "button" value = "목록으로" onclick = "location.href='freebbs.bbs<%=args%>'" />
		</p>
	</div>
</div>
</body>
</html>