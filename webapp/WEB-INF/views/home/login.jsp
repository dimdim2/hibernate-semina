<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<link type="text/css" rel="stylesheet" href="/resources/extjs/resources/css/ext-all.css" media="all" />

<script type="text/javascript" src="/resources/extjs/ext-all.js"></script>
<script type="text/javascript">
Ext.require([
	'Ext.window.Window',
	'Ext.form.*',
	'Ext.layout.container.Column',
	'Ext.tab.Panel'
]);

Ext.onReady(function() {

	$("body").css("backgroundColor", "#BFCCEF");

	Ext.QuickTips.init();

	var login = Ext.create('Ext.form.Panel', {
		url :'/home/login.json',
		frame :true,
		title :'Login',
		defaultType :'textfield',
		fieldDefaults: {
            msgTarget: 'side',
            labelWidth: 80
        },
		monitorValid :true,
		items : [
			Ext.create('Ext.panel.Panel', {
				//html: '<img src="/resources/images/login.gif"/><br/><br/>',
				border: false,
				height : 210,
				width: 565,
				margin : '0, 0, 15, 0'
			}),{
				fieldLabel :'User ID',
				name :'userId',
				anchor: '95%',
				allowBlank :true
			}, {
				fieldLabel :'Password',
				name :'password',
				anchor: '95%',
				inputType :'password',
				allowBlank :true
			}
		],
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
							obj = Ext.JSON.decode(action.response.responseText);
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

	Ext.create('Ext.Window', {
		plain: true,
		layout: 'fit',
		closable :false,
		resizable :true,
		plain :true,
		border :false,
		items : [ login ]
	}).show();
});

</script>

<%@ include file="/WEB-INF/views/include/footer.inc" %>