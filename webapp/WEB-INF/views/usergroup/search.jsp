<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

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

<div class="container">

	<form method="post" class="well form-search" name="vForm" action="/usergroup/search.htm">
		<input class="input-medium search-query" id="name" name="name" value="${name}"/>
		<button type="button" class="btn" onclick="search();">검색</button>
	</form>

	<display:table name="userGroups" id="userGroup" class="table table-striped table-bordered table-condensed"
			requestURI="/usergroup/search.htm" pagesize="10">

		<display:column title="ID" property="id"/>
		<display:column title="이름" property="name"/>
		<display:column title="생성일">
			<fmt:formatDate value="${userGroup.createTime}" pattern="yyyy/MM/dd" />
		</display:column>

		<display:column title="명령" style="text-align:center;" media="html">
			<button type="button" class="btn btn-info" onclick="detail('${userGroup.id}')">조회</button>
		<c:if test="${authority.update}">
			<button type="button" class="btn btn-info" onclick="update('${userGroup.id}')">수정</button>
		</c:if>
		<c:if test="${authority.delete}">
			<button type="button" class="btn btn-info" onclick="goDelete('${userGroup.id}')">삭제</button>
		</c:if>
		</display:column>
	</display:table>

	<!-- add button start -->
<c:if test="${authority.create}">

	<button type="button" class="btn btn-primary" onclick="insert()">생성</button>

</c:if>
	<!-- add button end -->

</div>

<%@ include file="/WEB-INF/views/include/footer.inc" %>