<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTest;"%>
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
Transition t = wp.getTransitionById(request.getParameter("id"));
%>
<%=ModeleTest.transition(t) %>
</body>
</html>