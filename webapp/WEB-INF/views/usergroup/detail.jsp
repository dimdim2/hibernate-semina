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
	<!-- input start -->
	<div>
		<label>ID</label>
		${userGroup.id}
	</div>

	<div>
		<label>이름</label>
		${userGroup.name}
	</div>

	<div>
		<label>설명</label>
		${userGroup.description}
	</div>

	<div>
		<table>
			<tr>
				<td>권한</td>
				<td>
				<c:forEach items="${userGroup.authorities}" var="auth">
					<label>${auth.role.name}</label>
					<c:set var="auth" value="${auth}" scope="request"/>
					<%
						GroupAuth auth = (GroupAuth)request.getAttribute("auth");
						out.println(auth.getAuthString());
					%>
					<br/>
				</c:forEach>
				</td>
			</tr>
		</table>
	</div>

	<div>
		<label>생성일</label>
		<fmt:formatDate value="${userGroup.createTime}" pattern="yyyy/MM/dd" />
	</div>

	<div>
		<label>수정일</label>
		<fmt:formatDate value="${userGroup.updateTime}" pattern="yyyy/MM/dd" />
	</div>

	<div>
		<input type="button" value="목록" onclick="goSearch();"/>
		<input type="button" value="수정" onclick="update();"/>
	</div>
</div>
</body>
</html>