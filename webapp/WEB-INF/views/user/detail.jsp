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
	location.href = "/user/search.htm";
}

function update() {
	location.href = "/user/update.htm?id=${user.id}";
}

</script>
<title></title>

</head>
<body>
<div class="container">
<fieldset>
	<legend>사용자 정보</legend>

	<label>ID</label>
	${user.id}
	<br/>

	<label>이름</label>
	${user.name}
	<br/>

	<label>사용자 그룹</label>
	${user.group.name}
	<br/>

	<label>생성일</label>
	<fmt:formatDate value="${user.createTime}" pattern="yyyy/MM/dd" />
	<br/>

	<label>수정일</label>
	<fmt:formatDate value="${user.updateTime}" pattern="yyyy/MM/dd" /><br/>
	<br/>

	<button onclick="goSearch();">목록</button>
	<button onclick="update();">수정</button>

</fieldset>
</div>
</body>
</html>