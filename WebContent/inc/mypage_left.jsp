<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String[] arrUri2 = request.getRequestURI().split("/");
String cFile = arrUri2[(arrUri2.length - 1)];
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
   a:link { color:#000; text-decoration:none; }
   a:visited { color:#000; text-decoration:none; }
   a:hover { color:#000; text-decoration:none;}
   a:focus { color:#000; text-decoration:none;}
   a:active { color:#000; text-decoration:none;}
   #left { float:left; left : 20px; width:150px; }
</style>
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
</head>
<body>
   <div id="left">
      <div class = "container">
         <h4>입찰</h4>
         <div >
            <a href="mp_buyauc.auc"<% if (cFile.equals("mp_buyauc.jsp")) { %> style="font-weight:bold;"<% } %>>구매입찰현황</a><br />
            <a href="mp_sellauc.auc?wtype=s" <% if (cFile.equals("mp_sellauc.jsp")) { %> style="font-weight:bold;"<% } %>>판매입찰현황</a><br />
            <a href="mp_waitauc.auc" <% if (cFile.equals("mp_waitauc.jsp")) { %> style="font-weight:bold;"<% } %>>결제대기중</a><br />
            <a href="mp_failauc.auc?wtype=f" <% if (cFile.equals("mp_failauc.jsp")) { %> style="font-weight:bold;"<% } %>>유찰관리</a><br />
            <a href="mp_notauc.auc" <% if (cFile.equals("mp_paynot.jsp")) { %> style="font-weight:bold;"<% } %>>입찰후미구매</a>
         </div>
      </div><br />
      <div class = "container">
         <h4>거래 내역</h4>
         <div >
            <a href="mp_buyauc.etc" <% if (cFile.equals("mp_buy.jsp")) { %> style="font-weight:bold;"<% } %>>구매내역</a><br />
            <a href="sell_list.etc" <% if (cFile.equals("sell_list.jsp")) { %> style="font-weight:bold;"<% } %>>판매내역</a>
         </div>
      </div><br />
      <div class = "container">
         <h4>고객센터</h4>
         <div >
            <a href="mp_chklist.etc" <% if (cFile.equals("mp_chklist.jsp")) { %> style="font-weight:bold;"<% } %>>검수현황</a><br />
            <a href="myqna_list.etc" <% if (cFile.equals("qna_list.jsp")) { %> style="font-weight:bold;"<% } %>>1:1문의</a>
         </div>
      </div><br />
      <div class = "container">
         <h4>내 정보</h4>
         <div >
            <a href="mypage.mem" <% if (cFile.equals("mypage_form.jsp")) { %> style="font-weight:bold;"<% } %>>회원정보관리</a><br />
            <a href="pwdchg_form.mem" <% if (cFile.equals("mp_pwdchg.jsp")) { %> style="font-weight:bold;"<% } %>>비밀번호 변경</a><br />
            <a href="mp_zzim.mem" <% if (cFile.equals("mp_zzim.jsp")) { %> style="font-weight:bold;"<% } %>>찜목록</a><br />
            <a href="point_form.mem" <% if (cFile.equals("mp_point.jsp")) { %> style="font-weight:bold;"<% } %>>포인트</a>
         </div>
      </div>
   </div>
</body>
</html>