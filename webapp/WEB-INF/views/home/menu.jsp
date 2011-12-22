<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css" media="all">
	@import url("/resources/css/common.css");
	@import url("/resources/css/displaytag.css");
	@import url("/resources/js/extjs/resources/css/ext-all.css");
</style>

<script type="text/javascript" src="/resources/js/jquery/jquery-1.5.1.js"></script>
<script src="/resources/js/extjs/adapter/ext/ext-base.js" type="text/javascript"></script>
<script src="/resources/js/extjs/ext-all.js" type="text/javascript"></script>

<script type="text/javascript">
	var tree;

	function displayMenu(rootNode){
	    var treeLoader = new Ext.tree.TreeLoader({
		    dataUrl: '/home/menuRender.json',
		    listeners:{
	    		'loadexception' : function(loader, node, response) {
	    			parent.window.location="/home/logout.htm";
	    		}
			}
	    });

		tree = new Ext.tree.TreePanel({
			el:'tree-div',
			useArrows:true,
			autoScroll:true,
			animate:true,
			enableDD:false,
			containerScroll:true,
			loader: treeLoader,
			root: rootNode,
			rootVisible: false,
			singleExpand: true,
		 	useArrows : true,
		 	border: false
		});

		tree.render();

		tree.on('click', onClick);

		function onClick(node, e) {
			var nodeAttr = node.attributes;
			if(nodeAttr.url) {
				parent.mainFrame.location = nodeAttr.url;
			}
			if(!nodeAttr.leaf) {
				node.toggle();
			}
	   	}
	}

	Ext.onReady(function() {
		var rootNode = new Ext.tree.AsyncTreeNode({
			id: '0',
	        text: 'HOME',
	      	draggable: false,
	        allowDrag: false,
	        allowDrop: false
	    });

		displayMenu(rootNode);
	});

</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="tree-div" style="overflow:auto; border:none; height:400px; width:240px;"></div>

</body>
</html>