<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>LGW MOVISK</title>
<style type="text/css" media="all">
	@import url("/resources/css/common.css");
	@import url("/resources/css/displaytag.css");
	@import url("/resources/css/alternative.css");
	@import url("/resources/js/extjs/resources/css/ext-all.css");
</style>

<script src="/resources/js/extjs/adapter/ext/ext-base.js" type="text/javascript"></script>
<script src="/resources/js/extjs/ext-all.js" type="text/javascript"></script>

<script language="javascript">

<c:if test="${message != null}">
	alert('${message}');
</c:if>

	var tree = parent.leftFrame.tree;
	var reloadNode = tree.getNodeById("${reloadNodeId}");
	reloadNode.reload();
	reloadNode.expand(1);

<c:if test="${redirectUrl != null}">
	document.location.href="${redirectUrl}";
</c:if>

</script>
</head>
<body>
</body>
</html>
