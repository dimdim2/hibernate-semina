<%@page import="hibernate.semina.model.GroupAuth"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css" media="all">
	@import url("/resources/css/common.css");
	@import url("/resources/css/displaytag.css");
</style>

<script type="text/javascript">

function cancel() {
	window.location = "/usergroup/search.htm";
}

function allAuthModify() {
	var form = document.vForm;
	var allAuth = form.allAuth;
	var selElems = form.elements;

	for(var i = 0; i < selElems.length; i++) {
		var selElem = selElems[i];
		if(selElem.options) {
			selElem.value = allAuth.value;
		}
	}
}

</script>
<title></title>

</head>
<body>
<div class="container">
<form method="post" id="vForm" name="vForm" action="/usergroup/update.htm">

	<!-- input start -->
	<div>
		<label for="id">ID</label>
		<input id="id" name="id" value="${userGroup.id}" readonly="readonly">
	</div>

	<div>
		<label for="name">이름</label>
		<input id="name" name="name" value="${userGroup.name}">
	</div>

	<div>
		<label for="description">설명</label>
		<input id="description" name="description" value="${userGroup.description}">
	</div>

	<div>
	<table>
		<tr>
			<td>권한</td>
			<td>
				<table id="functionTable" class="simple">
				<thead>
					<tr>
						<th align="center" width="100">Name</th>
						<th align="center" width="100">권한범위</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${userGroup.authorities}" var="auth" varStatus="status">
					<tr ${status.count%2 == 0 ? "class='even'" : "class='odd'" }>
						<td>${auth.role.name}</td>
						<td>
							<select name="${auth.role.id}">
								<option value="N">None</option>
								<option value="R" ${auth.authority=="R" ? "selected" : ""}>Read</option>
								<option value="RC" ${auth.authority=="RC" ? "selected" : ""}>Read|Creat</option>
								<option value="RCU" ${auth.authority=="RCU" ? "selected" : ""}>Read|Create|Update</option>
								<option value="RCUD" ${auth.authority=="RCUD" ? "selected" : ""}>Read|Create|Update|Delete</option>
							</select><br/>
						</td>
					</tr>
				</c:forEach>
				</tbody>
				</table>
				<div style="margin: 5px;">
					<select name="allAuth">
						<option value="N">권한 없음</option>
						<option value="R">조회</option>
						<option value="RC">조회|추가</option>
						<option value="RCU">조회|추가|수정</option>
						<option value="RCUD">조회|추가|수정|삭제</option>
					</select>&nbsp;&nbsp;
					<input type="button" value="일괄변경" style="width: 80px;" onclick="allAuthModify()">
				</div>
			</td>
		</tr>
	</table>
	</div>

	<div>
		<input type="submit" value="저장"/>
		<input type="button" value="취소" onclick="cancel();"/>
	</div>

</form>
</div>
</body>
</html>