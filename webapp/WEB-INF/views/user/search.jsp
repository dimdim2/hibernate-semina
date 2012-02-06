<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<script language="javascript">

function insert(){
	document.location = "/user/create.htm";
}

function detail(id){
	document.location = "/user/detail.htm?id=" + id;
}

function update(id){
	document.location = "/user/update.htm?id=" + id;
}

function goDelete(id) {
	if(confirm('정말로 삭제하시겠습니까?')) {
		$.ajax({
			url: '/user/delete.json',
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
<form method="post" name="vForm" action="/user/search.htm">

	<div class="searchBox">
		<input type="text" id="name" name="name" value="${name}"/>
		<input type="button" value="검색" onclick="search();"/>
	</div>

	<display:table name="users" id="user" class="table table-striped table-bordered table-condensed"
			requestURI="/user/search.htm" pagesize="10">

		<display:column title="ID" property="id"/>
		<display:column title="이름" property="name"/>
		<display:column title="생성일">
			<fmt:formatDate value="${user.createTime}" pattern="yyyy/MM/dd" />
		</display:column>

		<display:column title="명령" style="text-align:center;" media="html">
			<button type="button" onclick="detail('${user.id}')">조회</button>
		<c:if test="${authority.update}">
			<button type="button" onclick="update('${user.id}')">수정</button>
		</c:if>
		<c:if test="${authority.delete}">
			<button type="button" onclick="goDelete('${user.id}')">삭제</button>
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

<%@ include file="/WEB-INF/views/include/footer.inc" %>