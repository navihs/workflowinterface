<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTest;"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

	<title>Test</title>

	<script type="text/javascript" src="scripts/prototype.js"> </script>
	<script type="text/javascript" src="scripts/window.js"> </script>
	<script type="text/javascript" src="scripts/debug.js"> </script>
	<script type="text/javascript" src="scripts/effects.js"> </script>

	<link href="scripts/themes/default.css" rel="stylesheet" type="text/css" >	 </link>
	<link href="scripts/themes/lighting.css" rel="stylesheet" type="text/css" >	 </link>
	<link rel="stylesheet" type="text/css" href="style.css" />	</link>
	<title>Test</title>
  <style>
  #master_content {
    position:relative;
    overflow:hidden;
  }  
  </style>
  
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
Workflow w = wp.getWorkflowById(request.getParameter("id"));
//List boxes=new ArrayList<Box>();
//Box boite=new Box(a.getName(),x,y);
//boxes.add(boite);
%>
<%=ModeleTest.workflow(w) %>
</body>
</html>