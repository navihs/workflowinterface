<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTexte;"%>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
String id = request.getParameter("id");

DataField df=null;

/*Cas dans Workflow*/
if(request.getParameter("workflow")!=null)
{
	String wId = request.getParameter("workflow");
	df = wp.getWorkflowById(wId).getDataFieldById(id);
}
else
/*Cas dans WorkflowPackage */
	df = wp.getDataFieldById(id);

%>
<%=ModeleTexte.dataField(df) %>
</body>
</html>