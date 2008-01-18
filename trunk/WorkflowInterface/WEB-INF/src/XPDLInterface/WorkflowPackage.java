package XPDLInterface;

import java.util.*;

/***
 * Classe défissant <!ELEMENT Package 
 * @author Laurent
 *
 */
public class WorkflowPackage {

	private List participants;
	public List getParticipants(){return this.participants;}
	public void setParticipants(List participants){this.participants = participants;}
	
	private List workflows;
	public List getWorkflows(){return this.workflows;}
	public void setWorkflows(List workflows){this.workflows = workflows;}
	
	private List extendedAttributes;
	public List getExtendedAttributes(){return this.extendedAttributes;}
	public void setExtendedAttributes(List extendedAttributes){this.extendedAttributes = extendedAttributes;}
	
	private List dataFields;
	public List getDataFields(){return this.dataFields;}
	public void setDataFields(List dataFields){this.dataFields = dataFields;}
	
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
	}
	
	public void addParticipants(List newParticipants){this.participants.addAll(newParticipants);}
	
	public Workflow workflowExist(String id)
	{
		Iterator it = workflows.iterator();
		while(it.hasNext())
		{
			Workflow wf = (Workflow)it.next();
			if(wf.getId().equals(id))
				return wf;
		}
		return null;
	}
	
}
