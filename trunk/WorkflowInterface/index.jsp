<%@page import="Vues.testAfficheur"%>
<HTML>
<HEAD>
<TITLE>Page de démarrage</TITLE>
</HEAD>
<BODY>
<% 
Session session = 
testAfficheur aff= (testAfficheur) session.getAttribute("testAfficheur"); 
%>
<%=aff.getB() %>
</BODY>
</HTML>