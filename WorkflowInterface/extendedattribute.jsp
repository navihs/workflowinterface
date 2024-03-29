<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTexte;"%>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
String eaName = request.getParameter("name");
ExtendedAttribute ea=null;
/*Cas dans Activity*/
if(request.getParameter("activity")!=null)
{
	String wId = request.getParameter("workflow");
	String aId = request.getParameter("activity");
	ea = wp.getWorkflowById(wId).getActivityById(aId).getExtendedAttributeByName(eaName);
}
else
/*Cas dans Workflow*/
if(request.getParameter("activity")==null && request.getParameter("workflow")!=null)
{
	String wId = request.getParameter("workflow");
	ea = wp.getWorkflowById(wId).getExtendedAttributeByName(eaName);
}
else
/*Cas dans Transition*/
if(request.getParameter("transition")!=null)
{
	String pId = request.getParameter("transition");
	ea = wp.getTransitionById(pId).getExtendedAttributeByName(eaName);
}
else
{	/*Cas dans WorkflowPackage*/
	ea = wp.getExtendedAttributeByName(eaName);
}
%>
<%=ModeleTexte.extendedAttribute(ea) %>
</body>
</html>