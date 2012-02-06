<%@page import="hibernate.semina.model.GroupAuth"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<script type="text/javascript">

function goSearch() {
	location.href = "/usergroup/search.htm";
}

function update() {
	location.href = "/usergroup/update.htm?id=${userGroup.id}";
}
</script>

<div class="container">

<fieldset class="form-horizontal">
	<legend>그룹 정보</legend>

	<div class="control-group">
		<label>ID</label>
		<div class="controls">
			<input readonly="readonly" value="${userGroup.id}">
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">이름</label>
		<div class="controls">
			<input readonly="readonly" value="${userGroup.name}">
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">설명</label>
		<div class="controls">
			<textarea readonly="readonly">${userGroup.description}</textarea>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">권한</label>
		<div class="controls">
			<table class="table table-striped table-condensed" style="margin-bottom: 0px;">
			<thead>
				<tr>
					<th align="center" width="100">Name</th>
					<th align="center" width="100">권한범위</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${userGroup.authorities}" var="auth" varStatus="status">
				<tr ${status.count%2 == 0 ? "class='even'" : "class='odd'" }>
					<td>${auth.role.name}</td>
					<td>
					<c:set var="auth" value="${auth}" scope="request"/>
				<%
					GroupAuth auth = (GroupAuth)request.getAttribute("auth");
					out.println(auth.getAuthString());
				%>
					</td>
				</tr>
			</c:forEach>
			</tbody>
			</table>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">생성일</label>
		<div class="controls">
			<input readonly="readonly" value='<fmt:formatDate value="${userGroup.createTime}" pattern="yyyy/MM/dd" />'>
		</div>
	</div>

	<div class="control-group">
		<label class="control-label">수정일</label>
		<div class="controls">
			<input readonly="readonly" value='<fmt:formatDate value="${userGroup.updateTime}" pattern="yyyy/MM/dd" />'>
		</div>
	</div>

	<div class="form-actions">
		<button type="button" class="btn btn-primary" onclick="update();">수정</button>
		<button type="button" class="btn" onclick="goSearch();">목록</button>
	</div>

</fieldset>

</div>

<%@ include file="/WEB-INF/views/include/footer.inc" %>