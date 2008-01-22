<%@page import="Vues.testAfficheur"%>
<HTML>
<HEAD>
<TITLE>Page d'affichage</TITLE>
</HEAD>
<BODY>
<% 
testAfficheur aff= (testAfficheur) session.getAttribute("testAfficheur"); 
%>
<%=aff.getB() %>
</BODY>
</HTML>