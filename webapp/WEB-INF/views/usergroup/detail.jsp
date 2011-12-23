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
	location.href = "/usergroup/search.htm";
}

function update() {
	location.href = "/usergroup/update.htm?id=${userGroup.id}";
}
</script>
<title></title>

</head>
<body>
<div class="container">
<fieldset>
	<legend>그룹 정보</legend>

	<label>ID</label>
	${userGroup.id}
	<br/>

	<label>이름</label>
	${userGroup.name}
	<br/>

	<label>설명</label>
	${userGroup.description}
	<br/>

	<table>
		<tr>
			<td><label>권한</label></td>
			<td>
				<table class="simple">
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
						<c:set var="auth" value="${auth}" scope="request"/>
					<%
						GroupAuth auth = (GroupAuth)request.getAttribute("auth");
						out.println(auth.getAuthString());
					%>
						</td>
					</tr>
				</c:forEach>
				</tbody>
				</table>
			</td>
		</tr>
	</table>

	<label>생성일</label>
	<fmt:formatDate value="${userGroup.createTime}" pattern="yyyy/MM/dd" />
	<br/>

	<label>수정일</label>
	<fmt:formatDate value="${userGroup.updateTime}" pattern="yyyy/MM/dd" /><br/>
	<br/>

	<button onclick="goSearch();">목록</button>
	<button onclick="update();">수정</button>

</fieldset>
</div>
</body>
</html>