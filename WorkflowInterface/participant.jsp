<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTexte;"%>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
Participant p = wp.getParticipantById(request.getParameter("id"));
%>
<%=ModeleTexte.participant(p) %>
</body>
</html>