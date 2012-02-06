<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<link type="text/css" rel="stylesheet" href="/resources/extjs/resources/css/ext-all.css" media="all" />
<style type="text/css" media="all">
	main {
		margin-top : 16px;
		margin-bottom : 16px;
	}
</style>

<script type="text/javascript" src="/resources/extjs/ext-all.js"></script>

<script type="text/javascript">
Ext.require([
	'Ext.tree.*',
	'Ext.data.*',
	'Ext.tip.*'
]);

var tree;

var ROOT_NODE_ID = '0';

var treeStore = Ext.create('Ext.data.TreeStore', {
	proxy: {
		type: 'ajax',
		url: '/menu/treeRender.json'
	},
	root: {
		text: 'HOME',
		id: ROOT_NODE_ID,
		draggable:false,
		terminal: 'directory'
	}
});

function displayTree(){
	Ext.QuickTips.init();

	tree = new Ext.create('Ext.tree.Panel', {
		store: treeStore,
		title: "Admin Tool Menu Hierarchy Management",
		renderTo:'tree-div',
		height: 450,
        width: 250,
		useArrows:true,
		autoScroll:true,
		animate:true,
		autoScroll: true,
		resizable: true,

	<c:if test="${authority.update}">
		viewConfig: {
            plugins: {
                ptype: 'treeviewdragdrop'
            }
        },
	</c:if>

		bbar: ['->',{
			text: 'Reload',
			handler : function() { reloadTree(); }
		}]
	});

	tree.getRootNode().expand(false);

	tree.on('itemclick', onClick);
<c:if test="${authority.update}">
	tree.on('itemcontextmenu', onContextMenu);
	tree.on('itemmove', onMove);
</c:if>

	function onClick(view, node, item, index, event) {
		parent.rightFrame.location = "/menu/detail.htm?id=" + node.get('id');
   	}

	function onContextMenu(view, node, item, index, event){
		event.stopEvent();

		var menu = new Ext.menu.Menu();
		var nodeId = node.get('id');

		var menuIdx = 1;
		if(!node.isLeaf()) {
			menu.add({
				op		: menuIdx++,
				text	: '메뉴추가',
				handler	: function (){
					parent.rightFrame.location = "/menu/create.htm?parentId=" + nodeId;
				},
				scope	: this
			});
		}

		if(nodeId != ROOT_NODE_ID) {
			menu.add({
				op		: menuIdx++,
				text	: '메뉴수정',
				handler	: function (){
					parent.rightFrame.location = "/menu/update.htm?id=" + nodeId;
				},
				scope	: this
			});

			menu.add({
				op		: menuIdx++,
				text	: '메뉴삭제',
				handler	: function (){
					if(confirm('메뉴를 삭제하시겠습니까?')) {
						$.ajax({
							url: '/menu/delete.json',
							type : "POST",
							data: {
								id : nodeId
							},
							dataType: 'json',
							success: function(data) {
								alert(data.resultMsg);
								if(data.isSuccess) {
									node.remove();
								}
							}
						});
					}
				},
				scope : this
			});
		}

		menu.showAt(event.getXY());
	}
}

function onMove(node, oldParent, newParent, index, tree) {
	index++;
	var nodeId = node.get('id');

	$.ajax({
		url:'/menu/move.json',
		type : "POST",
		data: {
			id: nodeId,
			oldParentMenuId: oldParent.get('id'),
			newParentMenuId: newParent.get('id'),
			index: index
		},
		dataType: 'json'
	});
}

function reloadTree(){
	treeStore.load();
}

Ext.onReady(function() {
	displayTree();
});

</script>

<BR>
<!--  EXT 라이브러리의 Ajax기능을 쓰기 위한 element -->
<div id="move" style="visibility:hidden"></div>
<div id="tree-div" style="overflow:auto;width:280px;border:0px solid #c3daf9;"></div>

<%@ include file="/WEB-INF/views/include/footer.inc" %>