<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTest;"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

	<title>Test</title>

	<script type="text/javascript" src="../javascripts/prototype.js"> </script>
	<script type="text/javascript" src="../javascripts/window.js"> </script>
	<script type="text/javascript" src="../javascripts/debug.js"> </script>
	<script type="text/javascript" src="../javascripts/effects.js"> </script>

	<link href="../themes/default.css" rel="stylesheet" type="text/css" >	 </link>
	<link href="../themes/alphacube.css" rel="stylesheet" type="text/css" >	 </link>
	<title>Test</title>
  <style>
  #master_content {
    position:relative;
    overflow:hidden;
  }  
  a {
    color:#333;
    font-size: 16px;
  }
  </style>
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
Workflow w = wp.getWorkflowById(request.getParameter("id"));
%>
<%=ModeleTest.workflow(w) %>
</body>
</html>