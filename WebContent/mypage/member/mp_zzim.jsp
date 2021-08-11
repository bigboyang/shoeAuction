<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
ArrayList<ZzimInfo> zzimList = (ArrayList<ZzimInfo>)request.getAttribute("zzimList");
ZzimPageInfo zzimInfo = (ZzimPageInfo)request.getAttribute("zzimInfo");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
if (loginMember == null) {
   out.println("<script>");
   out.println("alert('로그인 후 사용하실 수 있습니다.');");
   out.println("location.href='login_form.jsp?url=mypage';");
   out.println("</script>");
   out.close();
}

request.setCharacterEncoding("utf-8");
String args = "", schargs = "";
//검색관련 쿼리스트링 제작
if (zzimInfo.getSchtype() == null)   schargs += "&schtype=";
else      schargs += "&schtype=" + zzimInfo.getSchtype();
if (zzimInfo.getKeyword() == null)   schargs += "&keyword=";
else      schargs += "&keyword=" + zzimInfo.getKeyword();
if (zzimInfo.getStatus() == null)   schargs += "&status=";
else      schargs += "&status=" + zzimInfo.getStatus(); 

args = "?cpage=" + zzimInfo.getCpage() + schargs;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
      #outer {width: 1200px; align:center; position:absolute; padding-bottom:150px; padding-left:150px; }
   #center {float:left; width:700px; margin-top:0px;}
   #footer {position:absolute; left:250px; bottom:-500px;}
   .page ul { text-align : center;}
   .page li { display : inline-block;}
   #schBttn { text-align:center; width:60px; }
   #schschsch { width:300px; }
   #delBtn {height:30px; width:45px; font-size:15px; font-weight:bold;}
   #schss1 { width:100px;  }
   #schss2 { width:100px;  }
</style>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 
<script>
function zzimdel(piid) {
   if (confirm("찜목록에서 삭제하시겠습니까?")) {
      location.href = "mp_zzimdel.mem?pi_id=" + piid;
   }
}
</script>
</head>
<body>
<div id="wrapper">
<%@ include file="../../inc/main_header.jsp" %>
<div id="outer">
   <%@ include file="../../inc/mypage_left.jsp" %>
   <div id="center">
      <form name="frm" action="" method="get">
      <input type="hidden" name="ord" value="<%=zzimInfo.getOrd() %>" />   
      <table width="700" class = "table table-bordered table-hover table-condensed"  >
      <tr>
      <td>
         <select name="status" class = "form-control" id="schss1">
            <option value="a" <% if (zzimInfo.getStatus().equals("a")) { %> selected="selected"<% } %>>전체</option>
             <option value="b" <% if (zzimInfo.getStatus().equals("b")) { %> selected="selected"<% } %>>입찰중</option>
            <option value="c" <% if (zzimInfo.getStatus().equals("c")) { %> selected="selected"<% } %>>입찰마감</option>
         </select>
      </td>
      <td>
         <select name="schtype" class = "form-control" id="schss2">
            <option value="pi_name" <% if (zzimInfo.getSchtype().equals("pi_name")) { %> selected="selected"<% } %>>상품명</option>
            <option value="b_name" <% if (zzimInfo.getSchtype().equals("b_name")) { %> selected="selected"<% } %>>브랜드</option>
            <option value="pi_quaility" <% if (zzimInfo.getSchtype().equals("pi_quaility")) { %> selected="selected"<% } %>>상태</option>
            <option value="pi_size" <% if (zzimInfo.getSchtype().equals("pi_size")) { %> selected="selected"<% } %>>사이즈</option>      
         </select>
      </td>
      <td>
      <input type="text" width="200" name="keyword" value="<%=zzimInfo.getKeyword() %>" id ="schschsch"/>
      </td> 
      <td>   
      <input type="submit" value="검색" id="schBttn" class = "btn btn-default btn-block"/>
      </td>
      </tr>
      </table>
      
      <br />
      <table width="700" height="*" class = "table table-bordered table-hover table-condensed" >      
      <tr>
      <td align="right" colspan="3">
            <select name = "sort" id = "sort" class = "form-control" onchange ="location.href=this.value">
            <option value="mp_zzim.mem<%=args %>&ord=dated" <% if (zzimInfo.getOrd().equals("dated")) {%> selected = "selected" <%} %>> 최신순 </option>
            <option value="mp_zzim.mem<%=args %>&ord=datea" <% if (zzimInfo.getOrd().equals("datea")) {%> selected = "selected" <%} %>> 오래된순</option>
      </select>
      </td>
      </tr>
      <% 
      if (zzimList != null && zzimList.size() > 0) {
      // 상품 검색결과가 있으면
         for (int i = 0 ; i < zzimList.size() ; i++) {
            ZzimInfo pi = zzimList.get(i);
            String lnk = "<a href=\"pdt_view.pdt" + args +
                  "&id=" + pi.getPi_id() +  "\">";
      %>   
      <tr>
      <td><%=lnk%><img src="product/shoePic/<%=pi.getPp_top() %>" width="150" height="130" /></td>
      <td>
      <input type="hidden" name="pi_id" value="<%=pi.getPi_id()%>" />
      상품이름 :<%=pi.getPi_name() %> <br />
      브랜드 : <%=pi.getB_name() %><br />
      사이즈 : <%=pi.getPi_size() %> <br />
       상태: <%=pi.getPi_quaility() %></td>
      <td align="right">
      <% if(pi.getPi_isactive().equals("d")) {%>입찰중
      <% } else { %>
      입찰마감
      <% } %>
       <br />
      (입찰 마감날짜)<br /><% if (pi.getPi_end() == null) { %> <% } else { %><%=pi.getPi_end()%><% } %><br />
      최고입찰가 : <%=pi.getPi_eprice() %><br />
      <input type="button" value="삭제" onclick="zzimdel('<%=pi.getPi_id()%>');" id= "delBtn" class="btn btn-outline-secondary" /></td>
      </tr>
      <%   
         }
      } else {
         out.println("<tr><th colspan='3'>검색결과가 없습니다.</th></tr>");
      }
      %>
      </table>
      <div class = "page"></div>
      <%
      if (zzimList != null && zzimList.size() > 0) {
         args = "?ord=" + zzimInfo.getOrd() + schargs;
      
         out.println("<ul class = \"pagination\"  style=\"width:800px;\"  align=\"center\">");
         if (zzimInfo.getCpage() == 1) {   // 현재 페이지 번호가 1이면
            out.println("<li><a>[&lt;&lt;]</a><a>[&lt;]</a></li>");
         } else {
            out.print("<li><a href='mp_zzim.mem" + args + "&cpage=1" + "'>");
            out.println("[&lt;&lt;]</a></li>");
            out.print("<li><a href='mp_zzim.mem" + args + 
               "&cpage=" + (zzimInfo.getCpage() - 1) + "'>");
            out.println("[&lt;]</a></li>");
         } // 첫 페이지와 이전 페이지 링크
      
         for (int i = 1, k = zzimInfo.getSpage() ; i <= zzimInfo.getBsize() && k <= zzimInfo.getEpage() ; i++, k++) {
         // i : 루프돌릴 횟수를 검사하는 용도의 변수, k : 페이지 번호 출력용 변수
         // 조건 : bsize만큼 루프를 도는데 페이지가 마지막 페이지일 경우 bsize보다 작아도 멈춤
            if (zzimInfo.getCpage() == k) {   // 현재 페이지 번호일 경우 링크없이 강조만 함
               out.print("<li><a><strong>" + k + "</strong></a></li>");
            } else {
               out.print("<li><a href='mp_zzim.mem" + args + "&cpage=" + k + "'>");
               out.print(k + "</a></li>");
            }
         }
      
         if (zzimInfo.getCpage() == zzimInfo.getPcnt()) {   // 현재 페이지번호가 마지막 페이지 번호이면
            out.println("<li><a>[&gt;]</a><a>[&gt;&gt;]</a>");
         } else {
            out.println("<li><a href='mp_zzim.mem" + args + "&cpage=" + 
               (zzimInfo.getCpage() + 1) + "'>[&gt;]</a></li>");
            out.print("<li><a href='mp_zzim.mem" + args + 
               "&cpage=" + zzimInfo.getPcnt() + "'>");
            out.println("[&gt;&gt;]</a></li>");
         }
      
         //out.println("</p>");
         out.println("</ul>");
      }
      %>
      </div>
      </form>
   </div>
</div>
</div>
</body>
</html>