<HTML>
<BODY>
<%@ page import="XPDLInterface.*" %>
<% 
testJDOM parser = new testJDOM(); 
parser.parsePackage();
%>
<%=parser.aff() %>
</BODY>
</HTML>