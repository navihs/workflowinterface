package XPDLInterface;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.filter.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.text.*;

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
		Element packageHeader = racine.getChild("PackageHeader");
		Element created = packageHeader.getChild("Created");
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		Date d=null;
		try{
			d = dateFormat.parse(created.getValue());
		}catch(Exception err){}
		
		return "err";
	    /* Attributs String*/
		/***
		 * <!ELEMENT Created
		 * <!ATTLIST Package id
		 * <!ATTLIST Package name
		 */
	}
}
