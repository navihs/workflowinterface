<html>
<head>
<%@ page import="XPDLInterface.*" %>
<%@page import="Vues.ModeleTest;"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

	<title>Test</title>

	<script type="text/javascript" src="scripts/prototype.js"> </script>
	<script type="text/javascript" src="scripts/window.js"> </script>
	<script type="text/javascript" src="scripts/debug.js"> </script>
	<script type="text/javascript" src="scripts/effects.js"> </script>
	<script type="text/javascript" src="scripts/wz_jsgraphics.js"> </script>

	<link href="scripts/themes/default.css" rel="stylesheet" type="text/css" >	 </link>
	<link href="scripts/themes/lighting.css" rel="stylesheet" type="text/css" >	 </link>
	<link rel="stylesheet" type="text/css" href="style.css" />	</link>
	<title>Test</title>
  <style>
  #master_content {
    position:relative;
    overflow:hidden;
  }  
  </style>
  
</head>
<body>
<div id="myCanvas" style="position:relative;height:250px;width:100%;"></div>
<div id="anotherCanvas" style="position:relative;height:250px;width:100%;"></div>
<script type="text/javascript">
<!--
function myDrawFunction()
{
  jg_doc.setColor("#00ff00"); // green
  jg_doc.fillEllipse(100, 200, 100, 180); // co-ordinates related to the document
  jg_doc.setColor("maroon");
  jg_doc.drawPolyline(new Array(50, 10, 120), new Array(10, 50, 70));
  jg_doc.paint(); // draws, in this case, directly into the document

  jg.setColor("#ff0000"); // red
  jg.drawLine(10, 113, 220, 55); // co-ordinates related to "myCanvas"
  jg.setColor("#0000ff"); // blue
  jg.fillRect(110, 120, 30, 60);
  jg.paint();

  jg2.setColor("#0000ff"); // blue
  jg2.drawEllipse(10, 50, 30, 100);
  jg2.drawRect(400, 10, 100, 50);
  jg2.paint();
}

var jg_doc = new jsGraphics(); // draw directly into document
var jg = new jsGraphics("myCanvas");
var jg2 = new jsGraphics("anotherCanvas");

myDrawFunction();

//-->
</script>

</body>
</html>