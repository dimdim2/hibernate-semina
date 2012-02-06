<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<script type="text/javascript">
	function home() {
		parent.location.href='/home/home.htm';
	}

	function logout() {
		parent.location.href='/home/logout.htm';
	}
</script>

<div class="container" align="right">
	<div>
		로그인정보 [사용자:${loginUser.id}, 그룹:${loginGroup.name}]
	</div>

	<div>
		<button type="button" class="btn" onclick="home();">HOME</button>
		<button type="button" class="btn" onclick="logout();">로그아웃</button>
	</div>
</div>

<%@ include file="/WEB-INF/views/include/footer.inc" %>