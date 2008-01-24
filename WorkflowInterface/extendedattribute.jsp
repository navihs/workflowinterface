<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTexte;"%>
</head>
<body>
<% 
WorkflowPackage wp = (WorkflowPackage)session.getAttribute("workflowPackage");
String eaName = request.getParameter("name");
/*Cas dans Activity*/
if(request.getAttribute("activity")!=null)
{
	String wId = request.getParameter("workflow");
	String aId = request.getParameter("activity");
	ExtendedAttribute ea = wp.getWorkflowById(wId).getActivityById(aId).getExtendedAttributeByName(eaName);
}
else
/*Cas dans Workflow*/
if(request.getAttribute("activity")==null && request.getAttribute("workflow")!=null)
{
	String wId = request.getParameter("workflow");
	ExtendedAttribute ea = wp.getWorkflowById(wId).getExtendedAttributeByName(eaName);
}
else
/*Cas dans Transition*/
if(request.getAttribute("transition")!=null)
{
	
}
else
	/*Cas dans WorkflowPackage*/
	ExtendedAttribute ea = wp.getExtendedAttributeByName(eaName);
%>
<%=ModeleTexte.extendedAttribute(ea) %>
</body>
</html>