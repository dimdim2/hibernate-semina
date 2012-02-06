<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<script type="text/javascript">

function cancel() {
	window.location = "/user/search.htm";
}

</script>




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

<%@ include file="/WEB-INF/views/include/footer.inc" %>