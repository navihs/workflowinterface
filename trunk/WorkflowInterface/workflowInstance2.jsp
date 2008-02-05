<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@ page import="java.util.*" %>
<%@ page import="Vues.ModeleTest" %>
<%@ page import="Vues.Box" %>
<%@ page import="org.enhydra.shark.api.client.wfmodel.WfProcess" %>


<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>

	<title>Test</title>

	<script type="text/javascript" src="scripts/prototype.js"> </script>
	<script type="text/javascript" src="scripts/window.js"> </script>
	<script type="text/javascript" src="scripts/debug.js"> </script>
	<script type="text/javascript" src="scripts/effects.js"> </script>
	<script type="text/javascript" src="scripts/wz_jsgraphics.js"> </script>

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
int x1=0;
int y1=0;
int x2=0;
int y2=0;
String s=" ";

WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
Workflow w = wp.getWorkflowById(request.getParameter("workflow"));
List<Box> boxes=ModeleTest.createBox(w);
%>
<%=ModeleTest.displayBoxInstance(boxes,w,request.getParameter("id"))%>


</body>
</html>