<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
CenterInfo bbsInfo = (CenterInfo)request.getAttribute("bbsInfo");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");

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
</head>
<body>
<%@ include file="../inc/main_header.jsp" %>
<div id="outer">
	<div id="center">
		<h2>자유게시판 글 등록</h2>
		<form action="bbsin_proc.bbs" method="post" enctype="multipart/form-data">
		<table width = "700" cellpadding = "5" cellspacing = "0" align = "center" class = "table table-bordered table-hover table-condensed" >
			<tr style = "font-size : 15px;">
				<th width = "*" align = "left" colspan = "2">
					<input type="text" name="title" style="width:720px;" placeholder="제목입력">
				</th>
			</tr>
			<tr>
				<th width = "*" align = "left">
				</th>
				<th width = "20%">
				</th>
			</tr>
			<tr valign = "top" height = "300">
				<td colspan = "2">
					<textarea rows="24" cols="100" placeholder="내용입력" name = "content" text-align="left">
					</textarea>
				</td>
			</tr>
			<tr>
			<td><input type="file" name="bf_img1"/><input type="file" name="bf_img2"/></td>
			<td></td> 
			</tr>
		</table>
		<p style = "width:700px; text-align :right">
			<input type="checkbox" id="chk" name="chk"
         value="y" checked="checked" > <label for="chk">게시여부</label>
			<input type = "button" value = "목록으로" onclick = "location.href='freebbs.bbs?cpage=1'"/>
			<input type = "submit" value = "등록" />
		</p>
		</form>
	</div>
</div>
</body>
</html>