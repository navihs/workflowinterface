<%@page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTest;"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>

	<title>Test</title>

	<script type="text/javascript" src="scripts/prototype.js"> </script>
	<script type="text/javascript" src="scripts/window.js"> </script>
	<script type="text/javascript" src="scripts/debug.js"> </script>
	<script type="text/javascript" src="scripts/effects.js"> </script>

	<link href="scripts/themes/default.css" rel="stylesheet" type="text/css" />
	<link href="scripts/themes/alphacube.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style.css" />
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
session.setAttribute("LastObjet",wp);
%>

<%=ModeleTest.workflowPackage(wp) %>

</body>
</html>