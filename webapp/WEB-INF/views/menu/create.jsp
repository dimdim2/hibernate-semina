<%@page import="hibernate.semina.model.MenuType"%>
<%@page import="hibernate.semina.model.Menu"%>
<%@page import="hibernate.semina.model.UserGroup"%>
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
	window.location = "/menu/search.htm";
}

</script>
<title></title>

</head>
<body>
<div class="container" style="width: 500px;">
<form method="post" id="vForm" name="vForm" action="/menu/create.htm">
	<input type="hidden" name="parentId" value="${menu.parentId}"/>

	<!-- input start -->
	<div>
		<label for="name">이름</label>
		<input id="name" name="name" value="${menu.name}">
	</div>

	<div>
		<label for="url">URL</label>
		<input id="url" name="url" value="${menu.url}">
	</div>

	<div>
		<label for="type">타입</label>
		<select name="type" id="type">
		<%	request.setAttribute("menuTypes", MenuType.values()); %>
		<c:forEach items="${menuTypes}" var="menuType">
			<option value="${menuType}" ${menu.type == menuType ? "selected" : "" } >${menuType}</option>
		</c:forEach>
		</select>
	</div>

	<div>
		<label for="description">설명</label>
		<input id="description" name="description" value="${role.description}">
	</div>

	<div>
		<label for="group.id">사용자 그룹</label>
		<c:forEach items="${groups}" var="group">
		<c:set var="group" value="${group}" scope="request"/>
		<%
			Menu menu = (Menu)request.getAttribute("menu");
			UserGroup userGroup = (UserGroup)request.getAttribute("group");
			boolean isAllowed = menu.isAllowedGroup(userGroup.getId());
		%>
			<label for="${group.id}">${group.name}</label>
			<input type="checkbox" id="${group.id}" name="groupIds" value="${group.id}" <%= isAllowed ? "checked" : "" %>>
		</c:forEach>
	</div>

	<div>
		<input type="submit" value="저장"/>
		<input type="button" value="취소" onclick="cancel();"/>
	</div>

</form>
</div>
</body>
</html>