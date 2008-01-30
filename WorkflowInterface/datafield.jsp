<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTexte;"%>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
DataField df;
if(session.getAttribute("LastObject").getClass().toString()=="WorkflowPackage")
{
	df = wp.getDataFieldById(request.getParameter("id"));
}else
	if(session.getAttribute("LastObject").getClass().toString()=="Workflow");
	{
		Workflow wf = (Workflow)session.getAttribute("LastObject");
		df = wf.getDataFieldById(request.getParameter("id"));
	}
%>
<%=ModeleTexte.dataField(df) %>
</body>
</html>