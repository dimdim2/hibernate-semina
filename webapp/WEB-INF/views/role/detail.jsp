<%@page import="hibernate.semina.model.GroupAuth"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css" media="all">
	@import url("/resources/css/common.css");
	@import url("/resources/css/displaytag.css");
</style>

<script type="text/javascript" src="/resources/js/jquery/jquery-1.5.1.js"></script>
<script type="text/javascript" src="/resources/js/jquery/validator/jquery.validate.js" ></script>

<script type="text/javascript">

function goSearch() {
	location.href = "/role/search.htm";
}

function update() {
	location.href = "/role/update.htm?id=${role.id}";
}

</script>
<title></title>

</head>
<body>
<div class="container">
<fieldset>
	<legend>Role 정보</legend>

	<label>ID</label>
	${role.id}
	<br/>

	<label>이름</label>
	${role.name}
	<br/>

	<label>설명</label>
	${role.description}
	<br/>

	<table>
		<tr>
			<td>
				<label>Function</label>
			</td>
			<td>
				<table id="functionTable" class="simple">
				<thead>
					<tr>
						<th align="center" width="100">이름</th>
						<th align="center" width="100">URL</th>
						<th align="center" width="100">타입</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${role.functions}" var="function" varStatus="status">
					<tr ${status.count%2 == 0 ? "class='even'" : "class='odd'" }>
						<td>${function.name}</td>
						<td>${function.url}</td>
						<td>${function.type}</td>
					</tr>
				</c:forEach>
				</tbody>
				</table>
			</td>
		</tr>
	</table>

	<label>생성일</label>
	<fmt:formatDate value="${role.createTime}" pattern="yyyy/MM/dd" />
	<br/>

	<label>수정일</label>
	<fmt:formatDate value="${role.updateTime}" pattern="yyyy/MM/dd" />
	<br/>

	<button onclick="goSearch();">목록</button>
	<button onclick="update();">수정</button>

</fieldset>
</div>
</body>
</html>