<%@page import="hibernate.semina.model.UserGroup"%>
<%@page import="hibernate.semina.model.Menu"%>
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

function update() {
	location.href = "/menu/update.htm?id=${menu.id}";
}

</script>
<title></title>

</head>
<body>
<div class="container" style="width: 500px;">
<fieldset>
	<legend>메뉴 정보</legend>

	<label>ID</label>
	${menu.id}
	<br/>

	<label>이름</label>
	${menu.name}
	<br/>

	<label>타입</label>
	${menu.type}
	<br/>

	<label>설명</label>
	${menu.description}
	<br/>

	<label>사용자 그룹</label>
	<c:forEach items="${menu.allowGroups}" var="group">
		${group.name}&nbsp;
	</c:forEach>
	<br/>

	<label>생성일</label>
	<fmt:formatDate value="${menu.createTime}" pattern="yyyy/MM/dd" />
	<br/>

	<label>수정일</label>
	<fmt:formatDate value="${menu.updateTime}" pattern="yyyy/MM/dd" /><br/>
	<br/>

	<button onclick="update();">수정</button>

</fieldset>
</div>
</body>
</html>