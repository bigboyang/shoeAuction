<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
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
   
   #outer {width: 1200px; aligh:center; position:absolute;}
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   #center { float:right; position:absolute; left:200px;}
   
   .ft { border : 1px solid black; }
   .fontBlue { color:blue; font-weight:bold; }
	.fontRed { color:red; font-weight:bold; }
</style>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function chkfrm(frm){
	var id = frm.ai_id.value;
	var pwd = frm.ai_pwd.value;
	var name = frm.ai_name.value;
	var pms = frm.ai_pms.checked;
	var vali = frm.idChk.value;
	
	if (id == ""){
		alert("아이디를 입력해주세요.");
		frm.ai_id.focus();
		return false;
	}
	if (vali == "n"){
		alert("아이디를 확인해주세요.");
		frm.ai_id.focus();
		return false;
	}
	if (pwd == ""){
		alert("비밀번호를 입력해주세요.");
		frm.ai_pwd.focus();
		return false;
	}
	if (name == ""){
		alert("이름을 입력해주세요.");
		frm.ai_name.focus();
		return false;
	}
	if (pms == false){
		alert("권한을 선택해주세요.");
		return false;
	}
}

function chkDupID(aiid) {
	$.ajax({
		type : "POST", 				// 데이터 전송방법	
		url : "/shoeAuction/admin/dupID",
		data : {"aiid":aiid}, 		// 컨트롤러로 보낼 매개변수
		success : function(chkRst) {	// 데이터를 보내어 실행한 결과를 chkRst로 받아옴
			var msg = "";
			if (chkRst == 0) {			// uid와 동일한 회원정보가 없으면
				msg = "<span class='fontBlue'>사용하실 수 있는 ID입니다.</span>";
				$("#idChk").val("y");
			} else {					// uid와 동일한 회원정보가 있으면(중복된 ID이면)
				msg = "<span class='fontRed'>이미 사용중인 ID입니다.</span>";
				$("#idChk").val("n");
			}
			document.getElementById("id_validate").innerHTML = msg;
		}
	});
}
</script>
</head>
<body>
<%@ include file="../../inc/admin_header.jsp" %>
<div id = "outer">
	<%@ include file="../../inc/admin_admin_left.jsp" %>
	<div id = "center">
	<h2>관리자 등록</h2>
		<form name = "afrm" action = "admin_proc.adma" method = "post" onsubmit = "return chkfrm(this)">
		<input type="hidden" name="idChk" id="idChk" value="n" />
			<table width = "500" cellpadding = "5" cellspacing = "0" class = "ft">
				<tr>
					<th width = "20%">
						아이디
					</th>
					<td width = "*">
						<input type = "text" name = "ai_id" maxlength = "20" size = "20" onkeyup="chkDupID(this.value);" />
						<span id = "id_validate"></span>
					</td>
				</tr>
				<tr>
					<th>
						비밀번호
					</th>
					<td>
						<input type = "password" name = "ai_pwd" maxlength = "20" size = "20" />
					</td>
				</tr>
				<tr>
					<th>
						이름
					</th>
					<td>
						<input type = "text" name = "ai_name" maxlength = "20" size = "20" />
					</td>
				</tr>
				<tr>
					<th>
						권한
					</th>
					<td>
						<input type = "checkbox" name = "ai_pms" value = "a" id = "ca"><label for = "ca">모든권한</label>
					</td>
				</tr>
				<tr>
					<td colspan = "2" align = "center">
						<input type = "submit" value = "등록" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
</body>
</html>