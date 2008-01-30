<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTexte;"%>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
Transition t = wp.getTransitionById(request.getParameter("id"));
%>
<%=ModeleTexte.transition(t) %>
</body>
</html>