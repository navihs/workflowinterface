<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTexte;"%>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
FormalParameter fp = wp.g(request.getParameter("id"));
%>
<%=ModeleTexte.formalParameter(fp) %>
</body>
</html>