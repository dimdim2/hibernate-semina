<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="hibernate.semina.model.FunctionType"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<script type="text/javascript">

$(document).ready(function() {
	$("#addFunction").click(function() {
		$("#functionTable tbody").append(
			'<tr>'+
			'<td>'+
				'<input name="functionNames">' +
			'</td>'+
			'<td>'+
				'<input name="functionUrls">' +
			'</td>'+
			'<td>'+
				'<select name="functionTypes">'+
					'<option value="<%=FunctionType.ANY%>"><%=FunctionType.ANY%></option>'+
					'<option value="<%=FunctionType.CREATE%>"><%=FunctionType.CREATE%></option>'+
					'<option value="<%=FunctionType.READ%>"><%=FunctionType.READ%></option>'+
					'<option value="<%=FunctionType.UPDATE%>"><%=FunctionType.UPDATE%></option>'+
					'<option value="<%=FunctionType.DELETE%>"><%=FunctionType.DELETE%></option>'+
				'</select>'+
			'</td>'+
			'<td><input type="button" value="삭제" class="removeFunction"/></td>'+
			'</tr>'
		);

		$("#functionTable tbody tr:odd").addClass('even');

		$(".removeFunction").click(function() {
			$(this).parent().parent().fadeOut("fast", function(){
				$(this).remove();
			});
		});
	});
});

function cancel() {
	window.location = "/role/search.htm";
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
<form method="post" id="vForm" name="vForm" action="/role/create.htm">
<fieldset>
	<legend>Role 정보</legend>

	<label for="name">이름</label>
	<input id="name" name="name" value="${role.name}">
	<br/>

	<label for="description">설명</label>
	<input id="description" name="description" value="${role.description}">
	<br/>

	<table>
		<tr>
			<td>
				Function
			</td>
			<td>
				<table id="functionTable" class="simple">
				<thead>
					<tr>
						<th align="center" width="100">이름</th>
						<th align="center" width="100">URL</th>
						<th align="center" width="100">타입</th>
						<th align="center" width="100">삭제</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
				</table>
				<input type="button" id="addFunction" value="추가"/>
			</td>
		</tr>
	</table>

	<button type="submit">저장</button>
	<button type="button" onclick="cancel();">취소</button>

</fieldset>
</form>
</div>

<%@ include file="/WEB-INF/views/include/footer.inc" %>