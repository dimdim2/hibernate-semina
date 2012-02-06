<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<script type="text/javascript">

function goSearch() {
	location.href = "/user/search.htm";
}

function update() {
	location.href = "/user/update.htm?id=${user.id}";
}

</script>




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

<%@ include file="/WEB-INF/views/include/footer.inc" %>