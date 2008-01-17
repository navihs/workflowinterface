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
	public void parsePackage()
	{
		/*on lit le Header,
		 * on créer un objet WorkflowPackage
		 * on récupère l'élément <Package Id="dossier_accueil_individualise" Name="Dossier d'accueil individualisé" 
		 * que l'on met dans WorkflowPackage.id et WorkflowPackage.name
		 * on récupère l'élément <Created>2006-07-24 13:57:13</Created> que l'on met dans WorkflowPackage.date
		 * on lance parseParticipant("Participants")
		 * on récupère une liste de participant que l'on ajoute à WorkflowPackage.participants
		 * on lance parseDatafields("DataFields")
		 * on récupère une liste de dataFields que l'on ajoute à WorkflowPackage.dataFields
		 * on lance parseWorkflowProcesses("WorkflowProcesses")
		 * on récupère une liste de Worflow que l'on ajoute à WorkflowPackage.workflows
		 * on lance parseExtendedAttributes("ExtendedAttributes")
		 * on récupère une liste d'ExtendedAttribute que l'on ajoute à WorkflowPackage.extendedAttributes
		 */
		WorkflowPackage workflowPackage;
		Element packageHeader = racine.getChild("PackageHeader");
		workflowPackage = new WorkflowPackage(packageHeader.getAttribute("Id").getValue(),packageHeader.getAttribute("Name").getValue());
		
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		Element created = packageHeader.getChild("Created");
		try{
			workflowPackage.setCreated(dateFormat.parse(created.getValue()));
		}catch(Exception err){}
		
		
	}

}
