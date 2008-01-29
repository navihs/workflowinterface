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
	private List<Transition> transitions=new ArrayList<Transition>();
	public void setTranstions(List<Transition> activityTranstions){this.transitions=activityTranstions;}
	public void addTranstion(Transition activityTranstion){this.transitions.add(activityTranstion);}
	public List<Transition> getTranstions(){return this.transitions;}
	
	/***
	 * <!ELEMENT ExtendedAttributes (ExtendedAttribute*)>
	 */
	private List<ExtendedAttribute> extendedAttributes=new ArrayList<ExtendedAttribute>();
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
	 * Permet de savoir si il y a une implémentation ou pas
	 */
	private boolean implementation;
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
	
	/***
	 * <!ELEMENT TransitionRestriction
	 * <!ELEMENT Join Type=
	 */
	private String transitionRestrictionJoin;
	public void setTransitionRestrictionJoin(String activityTransitionRestrictionJoin){this.transitionRestrictionJoin=activityTransitionRestrictionJoin;}
	public String getTransitionRestrictionJoin(){return this.transitionRestrictionJoin;}
	
	/***
	 * <!ELEMENT TransitionRestriction
	 * <!ELEMENT Split Type=
	 */
	private String transitionRestrictionSplit;
	public void setTransitionRestrictionSplit(String activityTransitionRestrictionSplit){this.transitionRestrictionSplit=activityTransitionRestrictionSplit;}
	public String getTransitionRestrictionSplit(){return this.transitionRestrictionSplit;}
	
	/***
	 * Constructeur quand l'attribut Name existe
	 * @param parent workflow qui détient l'activité
	 * @param activityId <!ATTLIST Activity Id
	 * @param activityName <!ATTLIST Activity Name
	 * @param implementation <!ELEMENT Implementation
	 */
	public Activity(Workflow parent, String activityId, String activityName, Boolean implementation)
	{
		this.id = activityId;
		this.name = activityName;
		this.implementation = implementation;
		this.workflowParent = parent;
	}
	
	/***
	 * Constructeur quand il n'y a pas d'attribut name
	 * @param parent workflow qui détient l'activité
	 * @param activityId <!ATTLIST Activity Id
	 * @param implementation <!ELEMENT Implementation
	 */
	public Activity(Workflow parent, String activityId, Boolean implementation)
	{
		this.id = activityId;
		this.name = this.id;
		this.implementation = implementation;
		this.workflowParent = parent;
	}
	
	/***
	 * Retourne si l'activity est liée à un workflow
	 * @return
	 */
	public boolean isSubflow()
	{
		return implementation;
	}
	
	/***
	 * Retourne l'ExtendedAttribute recherché dans l'activité
	 * @param name attribut name de l'ExtendedAttribute recherché
	 * @return
	 */
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
