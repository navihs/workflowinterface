<html>
<head>
<%@ page import="XPDLInterface.*" %>
</head>
<body>
<% 
WorkflowPackage wf = (WorkflowPackage)session.getAttribute("workflowPackage");
Participant p = wf.getParticipantById(request.getParameter("id"));
%>
<table>
<tr>
<td>name</td>
<td><%=p.getName() %></td>
</tr>
</table>
</body>
</html>