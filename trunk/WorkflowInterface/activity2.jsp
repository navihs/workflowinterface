<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTest;"%>
<link rel="stylesheet" href="style.css" />
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
Workflow w = wp.getWorkflowById(request.getParameter("workflow"));
Activity a = w.getActivityById(request.getParameter("id"));
%> 
<%=ModeleTest.activity(a,1,1) %>
</body>
</html>