<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTexte;"%>
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
FormalParameter fp = wp.getParticipantById(request.getParameter("id"));
%>
<%=ModeleTexte.participant(fp) %>
</body>
</html>