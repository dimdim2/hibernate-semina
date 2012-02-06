<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="hibernate.semina.model.MenuType"%>
<%@page import="hibernate.semina.model.Menu"%>
<%@page import="hibernate.semina.model.UserGroup"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<script type="text/javascript">
$(document).ready(function() {
    $("#vForm").validate({
		onfocusout: false,
		onkeyup: false,
		rules: {
			name: "required",
		},

		submitHandler: function(form) {
			$(form).ajaxSubmit({
				success: function(data) {
					console.log(JSON.stringify(data));
					var treeStore =  parent.leftFrame.treeStore;
					var parentNode = treeStore.getNodeById('${menu.parentId}');
					parentNode.expand(false);
					parentNode.appendChild({
						id : data.id,
						text : data.name,
						leaf : (data.type == 'LEAF' ? true : false)
					});

					document.location = "/menu/detail.htm?id=" + data.id;
				}
			});
		}
	});
});

function cancel() {
	window.location = "/menu/search.htm";
}
</script>

<div class="container" style="width: 500px;">

<form method="post" id="vForm" name="vForm" action="/menu/create.htm">
	<input type="hidden" name="parentId" value="${menu.parentId}"/>
<fieldset>
	<legend>메뉴 정보</legend>

	<label for="name">이름</label>
	<input id="name" name="name" value="${menu.name}">
	<br/>

	<label for="url">URL</label>
	<input id="url" name="url" value="${menu.url}">
	<br/>

	<label for="type">타입</label>
	<select name="type" id="type">
	<%	request.setAttribute("menuTypes", MenuType.values()); %>
	<c:forEach items="${menuTypes}" var="menuType">
		<option value="${menuType}" ${menu.type == menuType ? "selected" : "" } >${menuType}</option>
	</c:forEach>
	</select>
	<br/>

	<label for="description">설명</label>
	<input id="description" name="description" value="${role.description}">
	<br/>

	<label for="group.id">사용자 그룹</label>
	<select multiple="multiple" name="groupIds">
	<c:forEach items="${groups}" var="group">
	<c:set var="group" value="${group}" scope="request"/>
	<%
		Menu menu = (Menu)request.getAttribute("menu");
		UserGroup userGroup = (UserGroup)request.getAttribute("group");
		boolean isAllowed = menu.isAllowedGroup(userGroup.getId());
	%>
		<option value="${group.id}" <%= isAllowed ? "selected" : "" %>>${group.name }</option>
	</c:forEach>
	</select>
	<br/>

	<button type="submit">저장</button>
	<button type="button" onclick="cancel();">취소</button>

</fieldset>
</form>
</div>

<%@ include file="/WEB-INF/views/include/footer.inc" %>