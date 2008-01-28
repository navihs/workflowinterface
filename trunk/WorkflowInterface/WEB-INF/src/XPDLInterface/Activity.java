package XPDLInterface;
import java.util.*;

/***
 * <!ELEMENT Activity>
 */
public class Activity{

	/***
	 * Permet de connaitre le workflow parent de l'activity (l'identifiant de l'activity n'étant pas unique)
	 */
	private Workflow workflowParent;
	public Workflow getWorkflowParent(){return this.workflowParent;}
		
	/***
	 * <!ELEMENT Performer (#PCDATA)>
	 */
	private Participant performer;
	public void setPerformer(Participant activityPerformer){this.performer=activityPerformer;}
	public Participant getPerformer(){return this.performer;}
	
	/***
	 * <!ELEMENT SubFlow (ActualParameters?)>
	 */
	private Workflow subflow;
	public void setSubflow(Workflow activitySubflow)
	{
		System.out.println(this.workflowParent.getName()+" / "+this.id+" / ISSubflow");
		System.out.println(activitySubflow.getId());
		this.subflow=activitySubflow;
	}
	public Workflow getSubflow(){return this.subflow;}
	
	/***
	 * <!ELEMENT TransitionRefs (TransitionRef*)>
	 */
	private List<Transition> transitions;
	public void setTranstions(List<Transition> activityTranstions){this.transitions=activityTranstions;}
	public List<Transition> getTranstions(){return this.transitions;}
	
	/***
	 * <!ELEMENT ExtendedAttributes (ExtendedAttribute*)>
	 */
	private List<ExtendedAttribute> extendedAttributes;
	public void setExtendedAttributes(List<ExtendedAttribute> activityExtendedAttributes){this.extendedAttributes=activityExtendedAttributes;}
	public List<ExtendedAttribute> getExtendedAttributes(){return this.extendedAttributes;}
	
	/***
	 * <!ELEMENT Description (#PCDATA)>
	 */
	private String description;
	public void setDescription(String activityDescription){this.description=activityDescription;}
	public String getDescription(){return this.description;}
	
	/***
	 * <!ELEMENT Limit (#PCDATA)>
	 */
	private String limit;
	public void setLimit(String activityLimit){this.limit=activityLimit;}
	public String getLimit(){return this.limit;}
	
	/***
	 * <!ELEMENT Route EMPTY>
	 */
	private String route;
	public void setRoute(String activityRoute){this.route=activityRoute;}
	public String getRoute(){return this.route;}
	
	/***
	 * <!ELEMENT Implementation (No | Tool+ | SubFlow | Loop)>
	 */
	private boolean implementation;
	//public void setImplementation(String activityImplementation){this.implementation=activityImplementation;}
	//public String getImplementation(){return this.implementation;}
	public void setImplementation(boolean activityImplementation){this.implementation=activityImplementation;}
	public Boolean getImplementation(){return this.implementation;}
	
		
	/***
	 * <!ATTLIST Activity Id NMTOKEN #REQUIRED
	 */
	private String id;
	public String getId(){return this.id;}
	
	/***
	 * <!ATTLIST Activity Name CDATA #IMPLIED
	 */
	private String name;
	public String getName(){return this.name;}
	//private String TransitionRestriction
	//private String Join	//XOR ou AND
	//private String Split 	//XOR ou AND
	//TransitionRefs ? 
	
	private String transitionRestrictionJoin;
	public void setTransitionRestrictionJoin(String activityTransitionRestrictionJoin){this.transitionRestrictionJoin=activityTransitionRestrictionJoin;}
	public String getTransitionRestrictionJoin(){return this.transitionRestrictionJoin;}
	
	private String transitionRestrictionSplit;
	public void setTransitionRestrictionSplit(String activityTransitionRestrictionSplit){this.transitionRestrictionSplit=activityTransitionRestrictionSplit;}
	public String getTransitionRestrictionSplit(){return this.transitionRestrictionSplit;}
	
	public Activity(Workflow parent, String activityId, String activityName, Boolean implementation)
	{
		this.id = activityId;
		this.name = activityName;
		this.implementation = implementation;
		this.workflowParent = parent;
	}
	
	public Activity(Workflow parent, String activityId, Boolean implementation)
	{
		this.id = activityId;
		this.name = "";
		this.implementation = implementation;
		this.workflowParent = parent;
	}
	
	public boolean isSubflow()
	{
		return implementation;
	}
	
	public ExtendedAttribute getExtendedAttributeByName(String name)
	{
		Iterator<ExtendedAttribute> it = extendedAttributes.iterator();
		while(it.hasNext())
		{
			ExtendedAttribute ea = it.next();
			if(ea.getName().equals(name))
				return ea;
		}
		return null;
	}
}
