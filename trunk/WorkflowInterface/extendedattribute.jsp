<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTexte;"%>
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
ExtendedAttribute ea = wp.getExtendedAttributeByName(request.getParameter("name"));
%>
<%=ModeleTexte.extendedAttribute(ea) %>
</body>
</html>