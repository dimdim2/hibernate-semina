<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<link type="text/css" rel="stylesheet" href="/resources/extjs/resources/css/ext-all.css" media="all" />
<style type="text/css" media="all">
	a {
		color : black;
		text-decoration: none;
	}
</style>

<script type="text/javascript" src="/resources/extjs/ext-all.js"></script>

<script type="text/javascript">

Ext.onReady(function() {
	var ROOT_NODE_ID = '0';

	var treeStore = Ext.create('Ext.data.TreeStore', {
		proxy: {
			type: 'ajax',
			reader: 'json',
			url: '/home/menuRender.json'
		},
		root: {
			text: 'HOME',
			id: ROOT_NODE_ID,
			draggable:false,
			terminal: 'directory'
		},
		listeners: {
	        load: {
				element: 'el', //bind to the underlying el property on the panel
				fn: function(store, models, successful, eOpts){
					if(!successful) {
						parent.window.location="/home/logout.htm";
					}
				}
	        }
		}
	});

	var tree = new Ext.create('Ext.tree.Panel', {
		store: treeStore,
		height: 450,
        width: 240,
		renderTo:'tree-div',
		useArrows:true,
		autoScroll:true,
		animate:true,
		containerScroll:true,
		rootVisible: false,
		singleExpand: true,
	 	useArrows : true,
	 	border: false,
	 	bodyBorder: false
	});

	tree.render();

	tree.on('itemclick', onClick);

	function onClick(view, node, item, index, event) {
		event.stopEvent();

		var url = node.get('href');
		if(url) {
			parent.mainFrame.location = url;
		}

		if(!node.isLeaf()) {
			if(node.isExpanded()) {
				node.collapse();
			} else {
				node.expand(false);
			}
		}
   	}
});

</script>

<div id="tree-div" style="overflow:auto; border:none; height:500px; width:240px;"></div>

<%@ include file="/WEB-INF/views/include/footer.inc" %>