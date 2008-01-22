<HTML>
<HEAD>
<%@ page import="XPDLInterface.*" %>
<% WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage"); %>
<TITLE>Page de démarrage</TITLE>
</HEAD>
<BODY>
Bonjour <%=wp.getName() %><br>
id : <%=wp.getId() %>

</BODY>
</HTML>