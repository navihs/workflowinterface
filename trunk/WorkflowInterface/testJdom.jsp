<HTML>
<BODY>
<%@ page import="XPDLInterface.*" %>
<jsp:useBean id="activity" class="Activity" scope="session" />
<% 
testJDOM parser = new testJDOM(); 
parser.parsePackage();
activity = new Activity("1","2");
%>
<%=parser.aff() %>
</BODY>
</HTML>