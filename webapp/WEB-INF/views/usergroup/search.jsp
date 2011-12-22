<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<style type="text/css" media="all">
	@import url("/resources/css/displaytag.css");
	@import url("/resources/css/common.css");
</style>

<script type="text/javascript" src="/resources/js/jquery/jquery-1.5.1.js"></script>

<script language="javascript">

function insert(){
	document.location = "/usergroup/create.htm";
}

function detail(id){
	document.location = "/usergroup/detail.htm?id=" + id;
}

function update(id){
	document.location = "/usergroup/update.htm?id=" + id;
}

function goDelete(id) {
	if(confirm('정말로 삭제하시겠습니까?')) {
		$.ajax({
			url: '/usergroup/delete.json',
			type : "POST",
			data: {
				'id' : id
			},
			dataType: 'json',
			success: function(data) {
				alert(data.resultMsg);
				if(data.isSuccess) {
					window.location.reload(true);
				}
			}
		});
	}
}

function search() {
	document.vForm.submit();
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<div class="container">
<form method="post" name="vForm" action="/usergroup/search.htm">
	<div class="searchBox">
		<input type="text" id="name" name="name" value="${name}"/>
		<input type="button" value="검색" onclick="search();"/>
	</div>
	<!-- search _ end -->

	<display:table name="userGroups" id="userGroup" class="grid" requestURI="/usergroup/search.htm" pagesize="10">
		<display:column title="ID" property="id"/>
		<display:column title="이름" property="name"/>
		<display:column title="생성일">
			<fmt:formatDate value="${userGroup.createTime}" pattern="yyyy/MM/dd" />
		</display:column>

		<display:column title="명령" style="text-align:center;" media="html">
			<input type="button" value="조회" onclick="detail('${userGroup.id}')"/>
		<c:if test="${authority.update}">
			<input type="button" value="수정" onclick="update('${userGroup.id}')"/>
		</c:if>
		<c:if test="${authority.delete}">
			<input type="button" value="삭제" onclick="goDelete('${userGroup.id}')"/>
		</c:if>
		</display:column>
	</display:table>

	<!-- add button start -->
<c:if test="${authority.create}">
	<div>
		<input type="button" value="생성" onclick="insert()">
	</div>
</c:if>
	<!-- add button end -->

</form>
</div>
</body>
</html>