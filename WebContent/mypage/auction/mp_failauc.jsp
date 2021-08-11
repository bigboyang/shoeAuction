<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
request.setCharacterEncoding("utf-8");
if (loginMember == null) {
   out.println("<script>");
   out.println("alert('먼저 로그인을 해야 합니다.');");
   out.println("location.href = 'login_form.jsp';");   // 나중에 링크 붙이기
   out.println("</script>");
   out.close();
}

int cpage = pageInfo.getCpage();   
int pcnt = pageInfo.getPcnt();      
int rcnt = pageInfo.getRcnt();      
int spage = pageInfo.getSpage();   
int epage = pageInfo.getEpage();   
int psize = pageInfo.getPsize();   
int bsize = pageInfo.getBsize();

String ord = pageInfo.getOrd();

String sdate = pageInfo.getSdate();         // 등록기간 중 시작일.
String edate = pageInfo.getEdate();         // 등록기간 중 종료일.
String schtype = pageInfo.getSchtype();      // 대분류.
String keyword = pageInfo.getKeyword();      // 검색어.
String isactive = pageInfo.getIsactive();   // 게시 여부.

String args = "", schargs = "";
if (pageInfo.getKeyword() == null) schargs += "&keyword=";
else schargs += "&keyword=" + pageInfo.getKeyword();
if (pageInfo.getSchtype() == null) schargs += "&schtype=";
else schargs += "&schtype=" + pageInfo.getSchtype();
if (pageInfo.getSdate() == null) schargs += "&sdate=";
else schargs += "&sdate=" + pageInfo.getSdate();
if (pageInfo.getEdate() == null) schargs += "&edate=";
else schargs += "&edate=" + pageInfo.getEdate();
if (isactive == null) {
   schargs += "&isactive=";   isactive = "";
} else schargs += "&isactive=" + isactive;

args = "?cpage=" + cpage + schargs;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>

#wrapper {width: 1200px; margin : 0 auto;}
   #header { margin:0 auto; position:relative; left:160px;}
   #outer {width: 1200px; align:center; position:absolute; padding-bottom:150px; padding-left:150px; }
   #center {float:left; width:700px; margin-top:0px;}
   #footer {position:absolute; left:250px; bottom:-500px;}
   .page ul { text-align : center;}
   .page li { display : inline-block;}
</style>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 
<script>
$(function() {
   $.datepicker.regional['ko'] = {
      closeText: '닫기',
      prevText: '이전달',
      nextText: '다음달',
      currentText: '오늘',
      monthNames: ['1월','2월','3월','4월','5월','6월',
      '7월','8월','9월','10월','11월','12월'],
      monthNamesShort: ['1월','2월','3월','4월','5월','6월',
      '7월','8월','9월','10월','11월','12월'],
      dayNames: ['일','월','화','수','목','금','토'],
      dayNamesShort: ['일','월','화','수','목','금','토'],
      dayNamesMin: ['일','월','화','수','목','금','토'],
      //buttonImage: "/images/kr/create/btn_calendar.gif",
      buttonImageOnly: true,
      // showOn :"both",
      weekHeader: 'Wk',
      dateFormat: 'yy-mm-dd',
      firstDay: 0,
      isRTL: false,
      duration:200,
      showAnim:'show',
      showMonthAfterYear: false
      // yearSuffix: '년'
   };
   $.datepicker.setDefaults($.datepicker.regional['ko']);

   $( "#sdate" ).datepicker({
      //defaultDate: "+1w",
      changeMonth: true,
      //numberOfMonths: 3,
      dateFormat:"yy-mm-dd",
      onClose: function( selectedDate ) {
         //$( "#eday" ).datepicker( "option", "minDate", selectedDate );
      }
   });
   $( "#edate" ).datepicker({
      //defaultDate: "+1w",
      changeMonth: true,
      //numberOfMonths: 3,
      dateFormat:"yy-mm-dd",
      onClose: function( selectedDate ) {
         //$( "#sday" ).datepicker( "option", "maxDate", selectedDate );
      }
   });
});

   function choice(choice, piid) {
      if (choice == 'n') {
         if (confirm("정말 취소하시겠습니까?")) {
            location.href = "mp_fail_cancel.auc?piid=" + piid;
         }
      } else {
         if (confirm("재입찰하시겠습니까?")) {
            location.href = "mp_rereg_form.auc?piid=" + piid;
         }
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
      <h2>유찰 관리 현황</h2>
      <!-- 검색 영역 시작 -->
      <form name="frmSch" method="get">
      <input type="hidden" name="wtype" value="f" />
      <input type="hidden" name="ord" value="<%=ord %>" />
      <table  class = "table table-bordered table-hover table-condensed" align = "center" width="800" cellpadding="5">
      <tr>
      <th width="15%">기간</th>
      <td colspan="3">
         <input type="text" name="sdate" id="sdate" value="<%=pageInfo.getSdate() %>" size="8" class="ipt" /> ~
         <input type="text" name="edate" id="edate" value="<%=pageInfo.getEdate() %>" size="8" class="ipt" />
      </td>
      <td rowspan="2"><input type="submit"  class = "btn btn-default btn-block" value="검색" /></td>
      </tr>
      <tr>
      <td align="center">
         <select name="schtype" class = "form-control">
               <option value="name" <% if (schtype.equals("name")) { %>selected="selected"<% } %>>상품명</option>
               <option value="brand" <% if (schtype.equals("brand")) { %>selected="selected"<% } %>>브랜드</option>
               <option value="quaility" <% if (schtype.equals("quaility")) { %>selected="selected"<% } %>>상태</option>
               <option value="size" <% if (schtype.equals("size")) { %>selected="selected"<% } %>>사이즈</option>
         </select>
      </td>
      <td width="40%"><input type="text" name="keyword" value="<%=pageInfo.getKeyword() %>" /></td>
      <th width="15%">판매 상태</th>
      <td>
         <select name="isactive" class = "form-control">
            <option value="" <% if (isactive.equals(""))    { %>selected="selected"<% } %>>전체</option>
            <option value="a" <% if (isactive.equals("a"))   { %>selected="selected"<% } %>>대기 중</option>
            <option value="b" <% if (isactive.equals("b"))   { %>selected="selected"<% } %>>재입찰 등록</option>
            <option value="c" <% if (isactive.equals("c"))   { %>selected="selected"<% } %>>판매 취소</option>
         </select>
      </td>
      </tr>
      </table>
      </form>
      <!-- 검색 영역 종료 -->
      
      <!-- 정렬 영역 시작 -->
      <p style="width:700px;" align="right">정렬조건  
            <select name = "sort" id = "sort" onchange = "location.href=this.value"  class = "form-control">
            <option value="mp_sellauc.auc<%=args %>&ord=faildated&wtype=f" <% if (ord.equals("faildated")) {%> selected = "selected" <%} %>>최신순</option>
            <option value="mp_sellauc.auc<%=args %>&ord=faildatea&wtype=f" <% if (ord.equals("faildatea")) {%> selected = "selected" <%} %>>오래된순</option>
         </select>
      </p>
      <!-- 정렬 영역 종료 -->
      
      <!-- 상품 리스트 영역 시작 -->
      선택 대기 중 : <%=request.getAttribute("ingCnt") %>개 &nbsp;&nbsp;<span style="font-size:90%; color:blue;">* 유찰 후 2일이 지나면 자동 판매취소됩니다.</span>
      <table width="700" cellpadding="5" class = "table table-bordered table-hover table-condensed">
      <% 
      if (pdtList != null && pdtList.size() > 0) {
      // 상품 검색 결과가 있으면
         for (int i = 0 ; i < pdtList.size() ; i++) {
            PdtInfo pi = pdtList.get(i);
            String lnk = "<a href='pdt_view.pdt?id=" + pi.getPi_id() + "'>"; %>
      <tr>
      <td width="150" align="center"><%=lnk %><img src="product/shoePic/<%=pi.getPp_top() %>" width="80" height="90" /></a></td>
      <td width="*">
         상품이름 : <%=lnk + pi.getPi_name() %></a><br />
         브랜드 : <%=pi.getB_name() %><br />
         사이즈 : <%=pi.getPi_size() %><br />
         상태 : <%=pi.getPi_quaility() %>
      </td>
      <td width="200" align="center">
      <% 
      String pfStt = pi.getPf_status();
      if (pfStt == null)   pfStt = "";
      
      if (pfStt.equals("a"))   out.println("대기 중<br /> 유찰날짜 : " + pi.getPf_faildate());
      else if (pfStt.equals("b"))   out.println("재입찰 등록<br /> 등록일 : " + pi.getPf_chgdate());
      else if (pfStt.equals("c"))   out.println("판매취소(사용자)<br /> 취소일 : " + pi.getPf_chgdate());
      else if (pfStt.equals("d"))   out.println("판매취소(자동)<br /> 취소일 : " + pi.getPf_chgdate());
      %>
      <br />
      <% if (pfStt.equals("a")) { %>
      <input type="button" value="재입찰" onclick="choice('y', '<%=pi.getPi_id() %>');" />
      <input type="button" value="판매취소" onclick="choice('n', '<%=pi.getPi_id() %>');" />
      <% } %>
      </td>
      </tr>
      <%
         }
      } else {
         out.println("<tr><th colspan = '3'>검색 결과가 없습니다.</th></tr>");
      }
      %>
      </table>
      <!-- 상품 리스트 영역 종료 -->
      
      <br />
      
      <!-- 페이징 영역 시작 -->
      <%
      if (pdtList != null && pdtList.size() > 0) {   
         args = "&ord=" + ord + schargs + "&wtype=f";
         out.println("<p style=\"width:800px;\" align=\"center\">");
         
         if (cpage == 1) {   
            out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
         } else {
            out.print("<a href='mp_failauc.auc?cpage=1" + args + "'>");
            out.println("[&lt;&lt;]</a>&nbsp;");
            out.print("<a href='mp_failauc.auc?cpage=" + (cpage - 1) + args + "'>");
            out.println("[&lt;]</a>&nbsp;");
         }   
         
         for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
            if (cpage == k) {
               out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
            } else {
               out.print("&nbsp;<a href='mp_failauc.auc?cpage=" + k + args + "'>");
               out.print(k + "</a>&nbsp;");
            }
         }
         
         if (cpage == pcnt) {
            out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
         } else {
            out.println("&nbsp;<a href='mp_failauc.auc?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
            out.print("&nbsp;<a href='mp_failauc.auc?cpage=" + pcnt + args + "'>");
            out.println("[&gt;][&gt;]</a>");
         }
      
         out.println("</p>");
         
      } else {   
         out.println("<p style=\"width:800px;\" align=\"center\"></p>");
      }
      %>
      <!-- 페이징 영역 종료 -->
   </div>
</div>
</div>
</body>
</html>