package XPDLInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.*;

import org.jdom.Element;

public class Workflow{

	private List activities;
	public List getActivities(){return this.activities;}
	public void setActivities(List wfActivities){this.activities = wfActivities;}

	private List dataFields;
	public List getDataFields(){return this.dataFields;}
	public void setDataFields(List wfDataFields){this.dataFields = wfDataFields;}

	private List extendedAttributes;
	public List getExtendedAttributes(){return this.extendedAttributes;}
	public void setExtendedAttributes(List wfExtendedAttributes){this.extendedAttributes = wfExtendedAttributes;}
	
	private List formalParameters;
	public List getFormalParameters(){return this.formalParameters;}
	public void setFormalParameters(List wfFormalParameters){this.formalParameters = wfFormalParameters;}
	
	//private ArrayList participants;
	
	private String id;
	public String getId(){return this.id;}

	private String name;
	public String getName(){return this.name;}

	private Date created;
	public Date getCreated(){return this.created;}

	public Workflow(String wfId, String wfName, String wfCreated)
	{
		this.id = wfId;
		this.name = wfName;
		
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		try{
			this.created = dateFormat.parse(wfCreated);
		}catch(Exception err)
		{
			this.created = null;
		}
		
	}
	
}
