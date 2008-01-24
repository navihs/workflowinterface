<%@page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTexte;"%>
<html>
<head>
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
session.setAttribute("LastObjet",wp);
%>
<%=ModeleTexte.workflowPackage(wp) %>
</body>
</html>