<%@page import="Vues.testAfficheur"%>
<HTML>
<HEAD>
<TITLE>Page de d�marrage</TITLE>
</HEAD>
<BODY>
<% 
Session session = 
testAfficheur aff= (testAfficheur) session.getAttribute("testAfficheur"); 
%>
<%=aff.getB() %>
</BODY>
</HTML>