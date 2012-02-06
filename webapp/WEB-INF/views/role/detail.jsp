<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<script type="text/javascript">

function goSearch() {
	location.href = "/role/search.htm";
}

function update() {
	location.href = "/role/update.htm?id=${role.id}";
}

</script>

<div class="container">
<fieldset>
	<legend>Role 정보</legend>

	<label>ID</label>
	${role.id}
	<br/>

	<label>이름</label>
	${role.name}
	<br/>

	<label>설명</label>
	${role.description}
	<br/>

	<table>
		<tr>
			<td>
				<label>Function</label>
			</td>
			<td>
				<table id="functionTable" class="simple">
				<thead>
					<tr>
						<th align="center" width="100">이름</th>
						<th align="center" width="100">URL</th>
						<th align="center" width="100">타입</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${role.functions}" var="function" varStatus="status">
					<tr ${status.count%2 == 0 ? "class='even'" : "class='odd'" }>
						<td>${function.name}</td>
						<td>${function.url}</td>
						<td>${function.type}</td>
					</tr>
				</c:forEach>
				</tbody>
				</table>
			</td>
		</tr>
	</table>

	<label>생성일</label>
	<fmt:formatDate value="${role.createTime}" pattern="yyyy/MM/dd" />
	<br/>

	<label>수정일</label>
	<fmt:formatDate value="${role.updateTime}" pattern="yyyy/MM/dd" />
	<br/>

	<button onclick="goSearch();">목록</button>
	<button onclick="update();">수정</button>

</fieldset>
</div>

<%@ include file="/WEB-INF/views/include/footer.inc" %>