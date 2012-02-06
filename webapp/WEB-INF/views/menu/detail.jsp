<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="hibernate.semina.model.UserGroup"%>
<%@page import="hibernate.semina.model.Menu"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<script type="text/javascript">

function update() {
	location.href = "/menu/update.htm?id=${menu.id}";
}

</script>




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

<%@ include file="/WEB-INF/views/include/footer.inc" %>