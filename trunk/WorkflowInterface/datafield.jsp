<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTexte;"%>
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
DataField df = wp.getDataFieldById(request.getParameter("id"));
%>
<%=ModeleTexte.dataField(df) %>
</body>
</html>