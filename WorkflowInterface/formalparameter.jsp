<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTexte;"%>
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
FormalParameter fp = wp.get(request.getParameter("id"));
%>
<%=ModeleTexte.formalParameter(fp) %>
</body>
</html>