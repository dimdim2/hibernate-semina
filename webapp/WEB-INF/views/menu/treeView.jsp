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
<title>LGW MOVISK</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css" media="all">
	@import url("/resources/css/common.css");
	@import url("/resources/css/displaytag.css");
	@import url("/resources/css/alternative.css");
	@import url("/resources/js/extjs/resources/css/ext-all.css");
</style>

<script type="text/javascript" src="/resources/js/jquery/jquery-1.5.1.js"></script>
<script src="/resources/js/extjs/adapter/ext/ext-base.js" type="text/javascript"></script>
<script src="/resources/js/extjs/ext-all.js" type="text/javascript"></script>

<script type="text/javascript">
	var tree;

	function displayTree(rootNode){

		tree = new Ext.tree.TreePanel({
			title: "Admin Tool Menu Hierarchy Management",
			el:'tree-div',
			useArrows:true,
			autoScroll:true,
			animate:true,
		<c:if test="${authority.update}">
			enableDD:true,
		</c:if>
			containerScroll:true,
			dataUrl: '/menu/treeRender.json',
			root: rootNode,

			bbar: ['->',{
				text: 'Reload',
				handler : function() { reloadTree(); }
			}]
		});

		// render the tree
		tree.render();
		rootNode.expand(1);

		tree.on('click', onClick);
	<c:if test="${authority.update}">
		tree.on('contextmenu', onContextMenu);
		tree.on('movenode', moveEvent);
	</c:if>

		function onClick(node, e) {
			parent.rightFrame.location = "/menu/detail.htm?id=" + node.id;
	   	}

		function onContextMenu(node, event){
			var menu = new Ext.menu.Menu();

			var menuIdx = 1;
			if(!node.isLeaf()) {
				menu.add({
					op		: menuIdx++,
					text	: '메뉴 생성',
					handler	: function (){
						parent.rightFrame.location = "/menu/create.htm?parentId=" + node.id;
					},
					scope	: this
				});
			}
			if(node.id != tree.root.id) {
				menu.add({
					op		: menuIdx++,
					text	: '메뉴 수정',
					handler	: function (){
						parent.rightFrame.location = "/menu/update.htm?id=" + node.id;
					},
					scope	: this
				});

				menu.add({
					op		: menuIdx++,
					text	: '메뉴 삭제',
					handler	: function (){
						if(confirm('정말 삭제하시겠습니까?')) {
							$.ajax({
								url: '/menu/delete.json',
								type : "POST",
								data: {
									id : node.id
								},
								dataType: 'json',
								success: function(data) {
									alert(data.resultMsg);
									if(data.isSuccess) {
										reloadTree();
									}
								}
							});
						}
					},
					scope	: this
				});
			}

			menu.showAt(event.getXY());
		}
	}

	function moveEvent(tree, node, oldParent, newParent, index){
		index++;
		var move = Ext.get('move');
		move.load({
			url:'/menu/move.json',
			params:{
				id: node.id,
				oldParentMenuId: oldParent.id,
				newParentMenuId: newParent.id,
				index: index
			},
			timeout: 10,
			text: "Loading...",
			nocashe: true,
			callback: function (el, success, xmlHttpReq) {
				if(success){
					//alert("Succ - " + xmlHttpReq.responseText);
				} else {
					alert("Fail - " + xmlHttpReq.responseText);
				}
			}
   		});
   	}

	function reloadTree(){
		// Clear tree-div Inner HTML
		var treeElem = document.getElementById("tree-div");
		treeElem.innerHTML = "";

		var rootNode = new Ext.tree.AsyncTreeNode({
	        text: 'HOME',
	        draggable:false,
	        id: '0',
			terminal: 'directory'
	    });

		displayTree(rootNode);
	}

	Ext.onReady(function() {
		var rootNode = new Ext.tree.AsyncTreeNode({
	        text: 'HOME',
	        draggable:false,
	        id: '0',
			terminal: 'directory'
	    });

		displayTree(rootNode);
	});

</script>
</head>
<body topmargin="16" marginheight="16">
<BR>
<!--  EXT 라이브러리의 Ajax기능을 쓰기 위한 element -->
<div id="move" style="visibility:hidden"></div>
<div id="tree-div" style="overflow:auto;width:280px;border:0px solid #c3daf9;"></div>
</body>
</html>