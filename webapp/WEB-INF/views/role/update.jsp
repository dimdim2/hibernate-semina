<%@page import="hibernate.semina.model.FunctionType"%>
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

<script type="text/javascript" src="/resources/js/jquery/jquery-1.5.1.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	$(".removeFunction").click(function() {
		$(this).parent().parent().fadeOut("fast", function(){
			$(this).remove();
		});
	});

	$("#addFunction").click(function() {
		$("#functionTable tbody").append(
			'<tr>'+
			'<td>'+
				'<input name="functionNames">' +
			'</td>'+
			'<td>'+
				'<input name="functionUrls">' +
			'</td>'+
			'<td>'+
				'<select name="functionTypes">'+
					'<option value="<%=FunctionType.ANY%>"><%=FunctionType.ANY%></option>'+
					'<option value="<%=FunctionType.CREATE%>"><%=FunctionType.CREATE%></option>'+
					'<option value="<%=FunctionType.READ%>"><%=FunctionType.READ%></option>'+
					'<option value="<%=FunctionType.UPDATE%>"><%=FunctionType.UPDATE%></option>'+
					'<option value="<%=FunctionType.DELETE%>"><%=FunctionType.DELETE%></option>'+
				'</select>'+
			'</td>'+
			'<td><input type="button" value="삭제" class="removeFunction"/></td>'+
			'</tr>'
		);

		$("#functionTable tbody tr:odd").addClass('even');

		$(".removeFunction").click(function() {
			$(this).parent().parent().fadeOut("fast", function(){
				$(this).remove();
			});
		});
	});
});

function cancel() {
	window.location = "/role/search.htm";
}

</script>
<title></title>

</head>
<body>
<div class="container">
<form method="post" id="vForm" name="vForm" action="/role/update.htm">

	<!-- input start -->
	<div>
		<label for="id">ID</label>
		<input id="id" name="id" value="${role.id}" readonly="readonly">
	</div>

	<div>
		<label for="name">이름</label>
		<input id="name" name="name" value="${role.name}">
	</div>

	<div>
		<label for="description">설명</label>
		<input id="description" name="description" value="${role.description}">
	</div>

	<div>
	<table>
		<tr>
			<td>
				Function
			</td>
			<td>
				<table id="functionTable" class="simple">
				<thead>
					<tr>
						<th align="center" width="100">이름</th>
						<th align="center" width="100">URL</th>
						<th align="center" width="100">타입</th>
						<th align="center" width="100">삭제</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${role.functions}" var="function" varStatus="status">
					<tr ${status.count%2 == 0 ? "class='even'" : "class='odd'" }>
						<td><input name="functionNames" value="${function.name}"></td>
						<td><input name="functionUrls" value="${function.url}"></td>
						<td>
							<select name="functionTypes">
							<%	request.setAttribute("functionTypes", FunctionType.values());	%>
							<c:forEach items="${functionTypes}" var="functionType">
								<option value="${functionType}" ${function.type == functionType ? "selected" : "" }>${functionType}</option>
							</c:forEach>
							</select>
						</td>
						<td><input type="button" value="삭제" class="removeFunction"/></td>
					</tr>
				</c:forEach>
				</tbody>
				</table>
				<input type="button" id="addFunction" value="추가"/>
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