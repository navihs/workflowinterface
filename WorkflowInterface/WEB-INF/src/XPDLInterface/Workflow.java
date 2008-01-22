package XPDLInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.*;

import org.jdom.Element;

public class Workflow{

	private List<Activity> activities;
	public List<Activity> getActivities(){return this.activities;}
	public void setActivities(List<Activity> wfActivities){this.activities = wfActivities;}

	private List<DataField> dataFields;
	public List<DataField> getDataFields(){return this.dataFields;}
	public void setDataFields(List<DataField> wfDataFields){this.dataFields = wfDataFields;}

	private List<ExtendedAttribute> extendedAttributes;
	public List<ExtendedAttribute> getExtendedAttributes(){return this.extendedAttributes;}
	public void setExtendedAttributes(List<ExtendedAttribute> wfExtendedAttributes){this.extendedAttributes = wfExtendedAttributes;}
	
	private List<FormalParameter> formalParameters;
	public List<FormalParameter> getFormalParameters(){return this.formalParameters;}
	public void setFormalParameters(List<FormalParameter> wfFormalParameters){this.formalParameters = wfFormalParameters;}
	
	private List<Transition> transitions;
	public List<Transition> getTransitions(){return this.transitions;}
	public void setTransitions(List<Transition> wfTransitions){this.transitions = wfTransitions;}
	
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
	
	public Activity getActivityById(String id)
	{
		Iterator<Activity> it = activities.iterator();
		while(it.hasNext())
		{
			Activity act = it.next();
			if(act.getId().equals(id))
				return act;
		}
		return null;
	}
		
}
