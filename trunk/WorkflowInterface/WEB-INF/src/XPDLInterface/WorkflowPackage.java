package XPDLInterface;

import java.util.*;

/***
 * Classe défissant <!ELEMENT Package 
 * @author Laurent
 *
 */
public class WorkflowPackage {

	private List<Participant> participants;
	public List<Participant> getParticipants(){return this.participants;}
	public void setParticipants(List<Participant> participants){this.participants = participants;}
	
	private List<Workflow> workflows;
	public List<Workflow> getWorkflows(){return this.workflows;}
	public void setWorkflows(List<Workflow> workflows){this.workflows = workflows;}
	
	private List<ExtendedAttribute> extendedAttributes;
	public List<ExtendedAttribute> getExtendedAttributes(){return this.extendedAttributes;}
	public void setExtendedAttributes(List<ExtendedAttribute> extendedAttributes){this.extendedAttributes = extendedAttributes;}
	
	private List<DataField> dataFields;
	public List<DataField> getDataFields(){return this.dataFields;}
	public void setDataFields(List<DataField> dataFields){this.dataFields = dataFields;}
	
	/***
	 * <!ELEMENT Created
	 */
	private Date created;
	public Date getCreated(){return this.created;}
	public void setCreated(Date d){this.created = d;}
	
	/***
	 * <!ATTLIST Package id
	 */
	private String id;
	public String getId(){return this.id;}
	
	/***
	 * <!ATTLIST Package name
	 */
	private String name;
	public String getName(){return this.name;}
	
	
	public WorkflowPackage(String id, String name)
	{
		this.id = id;
		this.name = name;
		this.created = null;
		this.participants = new ArrayList<Participant>();
		this.workflows = new ArrayList<Workflow>();
		this.extendedAttributes = new ArrayList<ExtendedAttribute>();
		this.dataFields = new ArrayList<DataField>();	
	}
	
	public Workflow workflowExist(String id)
	{
		Iterator<Workflow> it = workflows.iterator();
		while(it.hasNext())
		{
			Workflow wf = it.next();
			if(wf.getId().equals(id))
				return wf;
		}
		return null;
	}
	
	public Participant getParticipantById(String id)
	{
		Iterator<Participant> it = participants.iterator();
		while(it.hasNext())
		{
			Participant p = it.next();
			if(p.getId().equals(id))
				return p;
		}
		return null;
	}
	
	public ExtendedAttribute getExtendedAttributeByName(String name)
	{
		Iterator<ExtendedAttribute> it = extendedAttributes.iterator();
		while(it.hasNext())
		{
			ExtendedAttribute eA = it.next();
			if(eA.getName().equals(name))
				return eA;
		}
		return null;
	}
	
	public DataField getDataFieldById(String id)
	{
		Iterator<DataField> it = dataFields.iterator();
		while(it.hasNext())
		{
			DataField df = it.next();
			if(df.getId().equals(id))
				return df;
		}
		return null;
	}
	
	public Workflow getWorkflowById(String id)
	{
		Iterator<Workflow> it = workflows.iterator();
		while(it.hasNext())
		{
			Workflow wf = it.next();
			if(wf.getId().equals(id))
				return wf;
		}
		return null;
	}
	
	public Transition getTransitionById(String id)
	{
		
		// Parcours des Workflows
		Iterator<Workflow> itWf = workflows.iterator();
		while(itWf.hasNext())
		{
			Workflow wf = itWf.next();
			
			//Parcours des activities
			Iterator<Activity> itA = wf.getActivities().iterator();
			while(itA.hasNext())
			{
				Activity a = itA.next();
				
				//Parcours des transitions
				Iterator<Transition> itT = a.getTransitions().iterator();
				while(itT.hasNext())
				{
					Transition t = itT.next();
					if(t.getId().equals(id))
						return t;
				}
					
			}
				
		}
		return null;
	}

}
