<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<script type="text/javascript">

function cancel() {
	window.location = "/usergroup/search.htm";
}

function allAuthModify() {
	var form = document.vForm;
	var allAuth = form.allAuth;
	var selElems = form.elements;

	for(var i = 0; i < selElems.length; i++) {
		var selElem = selElems[i];
		if(selElem.options) {
			selElem.value = allAuth.value;
		}
	}
}

</script>

<div class="container">
<form method="post" id="vForm" name="vForm" class="form-horizontal" action="/usergroup/update.htm">
<fieldset>
	<legend>그룹 정보</legend>

	<div class="control-group">
		<label for="id" class="control-label">ID</label>
		<div class="controls">
			<input id="id" name="id" value="${userGroup.id}" readonly="readonly">
		</div>
	</div>

	<div class="control-group">
		<label for="name" class="control-label">이름</label>
		<div class="controls">
			<input id="name" name="name" value="${userGroup.name}">
		</div>
	</div>

	<div class="control-group">
		<label for="description" class="control-label">설명</label>
		<div class="controls">
			<textarea id="description" name="description">${userGroup.description}</textarea>
		</div>
	</div>

	<div class="control-group">
		<label for="description" class="control-label">권한</label>
		<div class="controls">
			<table class="table table-striped table-condensed">
				<thead>
					<tr>
						<th align="center">Name</th>
						<th align="center">권한범위</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${userGroup.authorities}" var="auth" varStatus="status">
					<tr ${status.count%2 == 0 ? "class='even'" : "class='odd'" }>
						<td>${auth.role.name}</td>
						<td>
							<select name="${auth.role.id}">
								<option value="N">None</option>
								<option value="R" ${auth.authority=="R" ? "selected" : ""}>Read</option>
								<option value="RC" ${auth.authority=="RC" ? "selected" : ""}>Read|Creat</option>
								<option value="RCU" ${auth.authority=="RCU" ? "selected" : ""}>Read|Create|Update</option>
								<option value="RCUD" ${auth.authority=="RCUD" ? "selected" : ""}>Read|Create|Update|Delete</option>
							</select><br/>
						</td>
					</tr>
				</c:forEach>
				</tbody>
				</table>

				<select name="allAuth">
					<option value="N">권한 없음</option>
					<option value="R">조회</option>
					<option value="RC">조회|추가</option>
					<option value="RCU">조회|추가|수정</option>
					<option value="RCUD">조회|추가|수정|삭제</option>
				</select>&nbsp;&nbsp;
				<button type="button" class="btn btn-success" onclick="allAuthModify()">일괄변경</button>
		</div>
	</div>

	<div class="form-actions">
		<button type="submit" class="btn btn-primary">저장</button>
		<button type="button" class="btn" onclick="cancel();">취소</button>
	</div>

</fieldset>
</form>
</div>

<%@ include file="/WEB-INF/views/include/footer.inc" %>