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

function cancel() {
	window.location = "/user/search.htm";
}

</script>
<title></title>

</head>
<body>
<div class="container">
<form method="post" id="vForm" name="vForm" action="/user/update.htm">
<fieldset>
	<legend>사용자 정보</legend>

	<!-- input start -->
	<label for="id">ID</label>
	<input id="id" name="id" value="${user.id}">
	<br/>

	<label for="name">이름</label>
	<input id="name" name="name" value="${user.name}">
	<br/>

	<label for="password">패스워드</label>
	<input type="password" id="password" name="password" value="${user.password}">
	<br/>

	<label for="group.id">사용자 그룹</label>
	<select id="group.id" name="group.id">
	<c:forEach items="${groups}" var="group">
		<option value="${group.id}" ${group.id == user.group.id ? "selected" : "" }>${group.name}</option>
	</c:forEach>
	</select>
	<br/>

	<button type="submit">저장</button>
	<button type="button" onclick="cancel();">취소</button>

</fieldset>
</form>
</div>
</body>
</html>