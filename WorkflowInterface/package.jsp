<%@page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTexte;"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
%>
<%=ModeleTexte.workflowPackage(wp) %>
</body>
</html>