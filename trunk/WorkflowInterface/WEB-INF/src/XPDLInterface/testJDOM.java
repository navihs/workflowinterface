package XPDLInterface;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.filter.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

public class testJDOM {

	private org.jdom.Element racine;
	private org.jdom.Document document;

	public testJDOM()
	{
		initParser();
	}
	
	private void initParser()
	{
		 SAXBuilder sxb = new SAXBuilder();
	     try
	     {
	    	 document = sxb.build(new File("c:\\DAI3.1.xpdl"));
	     }
	     catch(Exception e){}

	     racine = document.getRootElement();
	}
	
	public String parsePackage()
	{
		List elements = racine.getChildren("PackageHeader");
		Element el1 = (Element)elements.get(0);
		return el1.getName();
	    //Element packageHeader = racine.getChildren();
	   // return String.valueOf(racine.getChildren().size());
	   // return String.valueOf(packageHeader.getChildren().size());
/*	    Element created = packageHeader.getChild("Created");
	    return created.getText();*/
	    
	    /* Attributs String*/
		/***
		 * <!ELEMENT Created
		 * <!ATTLIST Package id
		 * <!ATTLIST Package name
		 */
	}
}
