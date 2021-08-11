<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");
ArrayList<BrandInfo> brandList = (ArrayList<BrandInfo>)request.getAttribute("brandList");
int isZzim = (int)request.getAttribute("isZzim");
ArrayList<AuctionInfo> auctionList = (ArrayList<AuctionInfo>)request.getAttribute("auctionList");
if (pdtInfo == null) {
   out.println("<script>");
   out.println("alert('잘못된 경로로 들어오셨습니다.');");
   out.println("history.back();");
   out.println("</script>");
   out.close();
}

request.setCharacterEncoding("utf-8");
String args = "", schargs = "";
String brand = request.getParameter("brand");
if (brand == null) {
   brand = "";
} else schargs += "&brand=" + brand;
String pkeyword = request.getParameter("pkeyword");
if (pkeyword == null) {
   pkeyword = "";
} else schargs += "&pkeyword=" + pkeyword;
String ord = request.getParameter("ord");
if (ord == null) {
   ord = "";
} else  schargs += "&ord=" + ord;
String cpage = request.getParameter("cpage");
args = "?cpage=" + cpage + schargs;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Insert title here</title>
<style>
   a:link { color:#000; text-decoration:none; }
   a:visited { color:#000; text-decoration:none; }
   a:hover { color:#000; text-decoration:none;}
   a:focus { color:#000; text-decoration:none;}
   a:active { color:#000; text-decoration:none;}
   body {

   font-family: "Helvetica Nene", Helvetica, Arial, sans-serif;
   
   font-size: 14px;
   
   line-height: 1.42857143;
   
   color: #000;
   
   background-color: #fff;

}
.fade {
    opacity: 100 !important;
}   
</style>
<script src="jquery-3.6.0.js"></script>
<script>
   function zzim(sort, piid) {
      if (sort == '+') {
         $.ajax({
            type: "post",
            url   : "/shoeAuction/pdt_zzim.pdt",
            data: { "sort" : "+", "piid" : piid },
            success : function(chkRst) {
               if (chkRst == 0) {   
                  alert("찜 추가에 실패했습니다.\n다시 시도해주세요.");
               
               } else {
                  location.reload();
               }
            }
         });
      } else {
         $.ajax({
            type: "post",
            url   : "/shoeAuction/pdt_zzim.pdt",
            data: { "sort" : "-", "piid" : piid },
            success : function(chkRst) {
               if (chkRst == 0) {   
                  alert("찜 삭제에 실패했습니다.\n다시 시도해주세요.");
               
               } else {
                  location.reload();
               }
            }
         });
      }
   }
   
   function log(piid) {
      $.ajax({
         type: "post",
         url   : "/shoeAuction/pdt_log.pdt",
         data: { "piid" : piid },
         success : function(chkRst) {
            if (chkRst == 0) {   
               alert("로그 추가에 실패했습니다.\n다시 시도해주세요.");
            }
         }
      });
   }
</script>
</head>
<body onload="log('<%=pdtInfo.getPi_id() %>');">
<%@ include file="../inc/main_header.jsp" %>
<!-- 상단 목록 영역 시작 -->
<div id="brdList" style="width:900px;" align="left" class = "container">
   <div class="btn_group btn-group-justified">
      <div class = "btn-group">
         <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt';">전체</button>
      </div>
      <div class = "btn-group">
         <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=n01';">nike</button>
      </div>
      <div class = "btn-group">
         <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=a01';">adias</button>
      </div>
      <div class = "btn-group">
         <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=b01';">newbalance</button>
      </div>
      <div class = "btn-group">
         <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=v01';">vans</button>
      </div>
      <div class = "btn-group">
         <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=p01';">puma</button>
      </div>
      <div class = "btn-group">
         <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=d01';">dr.martin</button>
      </div>
      <div class = "btn-group">
         <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=e01';">etc</button>
      </div>
   </div>
</div>
<!-- 상단 목록 영역 종료 -->


<div style = "border : 1px solid lightgray; width : 815px; margin : 0 auto;">
<table width="800" cellpadding="0" align = "center">
<tr align="center">
<td>
   <table width="100%" cellpadding="5">
   <tr align="center">
   <td width="55%">
      <!-- 메인 이미지 영역 시작 -->
      <table width="100%">
      <tr><td align="center" valign="middle">
         <img src="product/shoePic/<%=pdtInfo.getPp_top()%>" width="300" height="400" />
      </td></tr>
      </table>
      <!-- 메인 이미지 영역 종료 -->
   </td>
   <td width="*" valign="top">
      <!-- 상품 정보 영역 시작 -->
      <table id="info" width="100%" cellpadding="3">
      <tr>
      <td colspan="2">
      <%if (pdtInfo.getPi_end() != null && !pdtInfo.getPi_end().equals("")){ %>
         입찰마감시간 : <%=pdtInfo.getPi_end().replace("-", "/") %>
      <%}%>
      </td>
      <td align="right">
      <% 
      if (loginMember == null) { %>
         <a href="javascript:alert('찜하기는 로그인을 먼저 해야 합니다.');" ><img src="img/empty_love.png" height="40" /></a>
      <% 
      } else { %>
         <% if (isZzim == 0) { %>
         <a href="javascript:zzim('+', '<%=pdtInfo.getPi_id()%>');" ><img src="img/empty_love.png" height="40" /></a>
         <% 
         } else if (isZzim > 0) { %>
         <a href="javascript:zzim('-', '<%=pdtInfo.getPi_id()%>');" ><img src="img/full_love.png" height="40" /></a>
         <%   
         } 
      } %>
      </td>
      </tr>
      <tr>
      <td colspan="2"><p>브랜드 : <%=pdtInfo.getB_name()%></p></td>
      </tr>
      <tr><td colspan="2"><p>상품명 : <%=pdtInfo.getPi_name() %></p></td></tr>
      <tr><td colspan="2"><p>상품사이즈 : <%=pdtInfo.getPi_size() %></p></td></tr>
      <tr><td colspan="2"><p>상품등급 : <%=pdtInfo.getPi_quaility() %></p></td></tr>
      </table>
      <!-- 상품 정보 영역 종료 -->
      
      <!-- 입찰 버튼 영역 시작 -->
      
      <div class="card border-dark mb-3" style="max-width: 40rem;">
         <div class="card-header">
            <form name="frmAucBtn" action="pdt_auc_form.pdt<%=args %>" method="post">
            <input type="hidden" name="id" value="<%=pdtInfo.getPi_id() %>" />
            <p>
            <% if (!pdtInfo.getPi_isactive().equals("e") && !pdtInfo.getPi_isactive().equals("f") && !pdtInfo.getPi_isactive().equals("g")) { %>
               <% if (loginMember != null && loginMember.getMi_id().equals(pdtInfo.getMi_id())) { %>
               <button type="button" class="btn btn-default">본인 등록 상품</button>
               <% } else { %>
               <button type="submit" class="btn btn-default">입찰 참여</button>
               <% }
            } else if (pdtInfo.getPi_isactive().equals("e")) { %>
               <input type="button" value="입찰 마감" />   
            <% } else if (pdtInfo.getPi_isactive().equals("f") || pdtInfo.getPi_isactive().equals("g")) { %>
               <input type="button" value="입찰 종료" />
            <% } %>
            </p>
         </div>
         
         <div class="card-body">
            <h4 class="card-title">현재 입찰 내역</h4>
               <table id="aucInfo" width="80%" cellpadding="0" class = "table table-bordered table-hover table-condensed">
                  <% if (auctionList.size() > 0) {
                     if (auctionList.size() > 5) {
                        for(int i = 0 ; i < 5 ; i++) { %>
                           <tr>
                              <td>
                                 <%=auctionList.get(i).getMi_id() %>
                              </td>
                              <td align="right">
                                 <%=auctionList.get(i).getPa_price() %>
                              </td>
                           </tr>
                        <% } %>
                        <tr>
                           <td colspan="2" align="center">
                              . . .
                           </td>
                        </tr>
                     <% } else { 
                        for(int i = 0 ; i < auctionList.size() ; i++) { %>
                           <tr>
                              <td>
                                 <%=auctionList.get(i).getMi_id() %>
                              </td>
                              <td align="right">
                                 <%=auctionList.get(i).getPa_price() %>
                              </td>
                           </tr>
                        <%    } 
                     } %>
                     <tr align = "center">
                        <td width="60%">
                           입찰 시작가
                        </td>
                        <td width="*" align="right">
                           <%=pdtInfo.getPi_sprice() %>
                        </td>
                     </tr>
                  <% } %>
               </table>
         </div>
      </div>
      
   </td>
   </tr>
   </table>
</td>
</tr>
<tr>
<td>
   <table width="800" cellpadding="1" cellspacing="1">
   
   
<br />
   <ul class="nav nav-tabs">
  <li class="nav-item">
    <a class="nav-link active" data-bs-toggle="tab" href="#home">상품소개</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" data-bs-toggle="tab" href="#profile">상품사진</a>
  </li>
</ul>
<div id="myTabContent" class="tab-content" align="center">
  <div class="tab-pane fade show active" id="home">
  <br />
    <p style = "size : 15px;">
       <%=pdtInfo.getPi_desc().replace("\r\n", "<br />") %>
    </p>
  </div>
  
  <div class="tab-pane fade" id="profile" align="center">
     <br />
      <p>
         <img src="product/shoePic/<%=pdtInfo.getPp_top()%>" width="300" height="350" />
      <img src="product/shoePic/<%=pdtInfo.getPp_bottom()%>" width="300" height="350" />
   </p>
   <p>
      <img src="product/shoePic/<%=pdtInfo.getPp_front()%>" width="300" height="350" />
      <img src="product/shoePic/<%=pdtInfo.getPp_back()%>" width="300" height="350" />
   </p>
   <p>
      <img src="product/shoePic/<%=pdtInfo.getPp_right()%>" width="300" height="350" />
      <img src="product/shoePic/<%=pdtInfo.getPp_left()%>" width="300" height="350" />
   </p>
   <!-- 상품 설명 필수 이미지 영역 종료 -->
   
   <!-- 추가 이미지 영역 시작 -->
   <%
   String[] arrPic = {pdtInfo.getPp_etc1(), pdtInfo.getPp_etc2(), pdtInfo.getPp_etc3(), pdtInfo.getPp_etc4(), pdtInfo.getPp_etc5(), pdtInfo.getPp_etc6()};
   ArrayList<String> etcList = new ArrayList<String>();
   for (int i = 0 ; i < arrPic.length ; i++) {
      if (arrPic[i] != null && !arrPic[i].equals(""))   etcList.add(arrPic[i]);
   }
   
   for (int i = 0 ; i < etcList.size(); i++) {
      if (etcList.size() > 0) {
         if (i % 2 == 0) { %><p><% } %>
         <img src="product/shoePic/<%=etcList.get(i) %>" width="300" height="350" />
         <%
         if (i % 2 == 1 || i == etcList.size() - 1) { %></p><% } %>
      <%
      }
   } 
   %>
  </div>
</div>
   <!-- 추가 이미지 영역 종료 -->
   </table>
</td>
</tr>
</table>
</div>
</body>
</html>
