<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css" media="all">
	@import url("/css/common.css");
</style>

<script type="text/javascript">
	CurrentOn = 8;
</script>

<script>
	alert('세션이 만료 되었습니다. 다시 로그인 해주세요');

	top.location.href='/home/login.htm';
</script>
</head>

</html>