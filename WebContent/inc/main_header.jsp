<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
PdtPageInfo pageInfo2 = (PdtPageInfo)request.getAttribute("pageInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 
</head>
<style>
.navbar { align : center; width : 800px; margin : 0 auto;}
</style>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
  	
    <a class="navbar-brand" href="index.jsp">logo</a>
    
    
     <form class="d-flex" name = "schForm" action = "pdt_list.pdt" />
       <input class="form-control me-sm-2" type="text" placeholder="Search" name="pkeyword" value="<%=pageInfo2 == null ? "" : pageInfo2.getPkeyword() %>" />
       <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
       
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor03" aria-controls="navbarColor03" aria-expanded="true" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
      </button>
     </form>
    <div class="navbar-collapse collapse show" id="navbarColor03" style="">
      <ul class="navbar-nav me-auto">
        <li class="nav-item">
          <a class="nav-link" href="pdt_list.pdt">????????????
            <span class="visually-hidden">(current)</span>
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="pdt_reg_form.pdt">????????????</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="freebbs.bbs">?????????</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="centerform.jsp">????????????</a>
        </li>
        <% if (loginMember == null ) {%>
        <li class="nav-item">
          <a class="nav-link" href="login_form.jsp">?????????</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="join_form.jsp">????????????</a>
        </li>
        <% } else { %>
        <li class="nav-item">
          <a class="nav-link" href="mypage">???????????????</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="logout.jsp">????????????</a>
        </li>
        <% } %>
        <li class="nav-item dropdown">
        	<div class="dropdown-divider"></div>
        </li>
      </ul>
    
    </div>
  </div>
</nav>
<%-- 
<span style="font-weight:bold"><a href="index.jsp">LOGO</a></span>
<input type="text" name="pkeyword" value="<%=pageInfo2 == null ? "" : pageInfo2.getPkeyword() %>" />
<input type="submit" value="??????" />
<div class="btn-group">
<button type = "button" class = "btn btn-default" onclick = "location.href='pdt_list.pdt';">????????????</button>
<button type = "button" class = "btn btn-default" onclick = "location.href='pdt_reg_form.pdt';">????????????</button>
<button type = "button" class = "btn btn-default" onclick = "location.href='#';">?????????</button>
<button type = "button" class = "btn btn-default" onclick = "location.href='centerform.jsp';">????????????</button>
<!-- <a href="pdt_list.pdt" class = "btn btn-primary">????????????</a>
<a href="pdt_reg_form.pdt" id="sch" class = "btn btn-primary">????????????</a>
<a href="" class = "btn btn-primary">?????????</a> 
<a href="centerform.jsp" class = "btn btn-primary">????????????</a> -->
</div>
<div class="btn-group">
&nbsp;&nbsp;&nbsp;&nbsp;
<% if (loginMember == null ) {%>
<!-- <a href="login_form.jsp" class = "btn btn-default">?????????</a>&nbsp; -->
<button type = "button" class = "btn btn-default" onclick = "location.href='login_form.jsp';">?????????</button>
<!-- <a href="join_form.jsp" class = "btn btn-default">????????????</a> -->
<button type = "button" class = "btn btn-default" onclick = "location.href='join_form.jsp';">????????????</button>
<% } else { %>&nbsp;&nbsp;&nbsp;&nbsp;
<!-- <a href="mypage" class = "btn btn-default">???????????????</a>&nbsp;&nbsp; -->
<button type = "button" class = "btn btn-default" onclick = "location.href='mypage';">???????????????</button>
<!-- <a href="logout.jsp" class = "btn btn-default">????????????</a> -->
<button type = "button" class = "btn btn-default" onclick = "location.href='logout.jsp';">????????????</button>
<% } %>
</div>
</div>
</form>
 --%>
</body>
</html>