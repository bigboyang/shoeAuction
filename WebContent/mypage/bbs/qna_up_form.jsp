<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
PdtPageInfo pdtpageInfo = (PdtPageInfo)request.getAttribute("pdtpageInfo");
CenterInfo article = (CenterInfo)request.getAttribute("article");

int idx = (int)article.getBq_idx();

request.setCharacterEncoding("utf-8");
if (loginMember == null) {
	out.println("<script>");
	out.println("alert('먼저 로그인을 해야 합니다.');");
	out.println("location.href = 'login_form.jsp?url=qna_up_form.etc';");	
	out.println("</script>");
	out.close();
}


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
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   #center { float:right; position:absolute; left:200px;}
   #footer {position:absolute; left:250px; bottom:0px;}
</style>
</head>
</head>
<body>
<%@ include file="../../inc/main_header.jsp" %>
<div id="outer">
<%@ include file="../../inc/center_left.jsp" %>

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
<h3>문의하기</h3>		
<form name="qnaIn" action="qna_up_proc.etc" method="post" enctype="multipart/form-data">
<input type="hidden" name="idx" value="<%=idx%>" />
<table width = "800" cellpadding = "5" cellspacing = "0">
<tr>
<td align="left">분류선택 : 
	<select name="bq_cata">
		<option value="구매"<% if (article.getBq_cata().equals("구매")) {%>selected="selected"<% } %>>구매</option>
		<option value="판매"<% if (article.getBq_cata().equals("판매")) {%>selected="selected"<% } %>>판매</option>
		<option value="검수"<% if (article.getBq_cata().equals("검수")) {%>selected="selected"<% } %>>검수</option>
		<option value="배송"<% if (article.getBq_cata().equals("배송")) {%>selected="selected"<% } %>>배송</option>
		<option value="입찰"<% if (article.getBq_cata().equals("입찰")) {%>selected="selected"<% } %>>입찰</option>
		<option value="기타"<% if (article.getBq_cata().equals("기타")) {%>selected="selected"<% } %>>기타</option>
	</select>
</td>
</tr>
<tr>
<td>문의제목 : <input type="text" size="50" name="bq_title" value="<%=article.getBq_title() %>" />
</td>
</tr>
<tr>
<td>문의내용
<br />
<textarea cols="80" rows="10" name="bq_content"  ><%=article.getBq_content()%>
</textarea></td>
</tr>
<tr>
<td>파일첨부
<br />
<input type="file" name="bq_img1" />
<input type="file" name="bq_img2" />
</td>
</tr>
<tr>
<td>
<input type="checkbox" name="bq_secret" value="y" />비밀글 *비밀글로 작성하시면 본인과 운영자만 열람가능합니다.
</td>
</tr>
<tr>
<td align="center">
<input type="submit" value="문의수정" />
<input type="button" value="취소하기" onclick="history.back();"/>
</tr>
</table>
</form>
</div>
<%@ include file="../../inc/footer.jsp" %>
</div>		
</body>
</html>