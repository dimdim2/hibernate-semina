<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
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
	Ext.onReady( function() {
		Ext.QuickTips.init();

		var login = new Ext.FormPanel({
			labelWidth :80,
			url :'/home/login.json',
			frame :true,
			title :'Login',
			defaultType :'textfield',
			monitorValid :true,
			items : [
			new Ext.Panel({
				//html: '<img src="/images/LG_movisk_login.jpg"/><br/><br/>',
				border: false
			}),{
				fieldLabel :'User ID',
				name :'userId',
				value : '',
				anchor: '100%',
				allowBlank :false
			}, {
				fieldLabel :'Password',
				name :'password',
				value : '',
				anchor: '100%',
				inputType :'password',
				allowBlank :false
			}],

			buttons : [{
				text :'Login',
				formBind :true,
				handler : function() {
					login.getForm().submit({
						method :'POST',
						waitTitle :'Connecting',
						waitMsg :'Sending data...',
						success : function() {
							var redirect = '/home/home.htm';
							window.location = redirect;
						},

						failure : function(form, action) {
							if (action.failureType == 'server') {
								obj = Ext.util.JSON.decode(action.response.responseText);
								Ext.Msg.alert('Login Failed!', obj.errors.reason);
							} else {
								Ext.Msg.alert('Warning!', 'Authentication server is unreachable : ' + action.response.responseText);
							}
							login.getForm().reset();
						}
					});
				}
			}]
		});

		var win = new Ext.Window( {
			layout :'fit',
			width :585,
			height :365,
			closable :false,
			resizable :false,
			plain :true,
			border :false,
			items : [ login ]
		});
		win.show();
	});
</script>

<title></title>
</head>
<!-- <body bgcolor="#deefbd"> -->
<body bgcolor="#BFCCEF">

</body>
</html>