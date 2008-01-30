<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTest;"%>
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
FormalParameter fp = wp.get(request.getParameter("id"));
%>
<%=ModeleTest.formalParameter(fp) %>
</body>
</html>