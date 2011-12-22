<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css" media="all">
	@import url("/resources/css/common.css");
	@import url("/resources/css/displaytag.css");
	@import url("/resources/js/extjs/resources/css/ext-all.css");
</style>

<script type="text/javascript">

function home() {
	parent.location.href='/home/home.htm';
}

function logout() {
	parent.location.href='/home/logout.htm';
}

</script>
<title></title>
</head>
<body>
<div class="container" align="right">
	<div>
		로그인정보 [사용자:${loginUser.id}, 그룹:${loginGroup.name}]
	</div>

	<div>
		<input type="button" value="HOME" onclick="home();"/>
		<input type="button" value="로그아웃" onclick="logout();"/>
	</div>
</div>
</body>
</html>