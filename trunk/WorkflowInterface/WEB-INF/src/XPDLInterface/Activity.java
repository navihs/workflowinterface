package XPDLInterface;
import java.util.*;

/***
 * <!ELEMENT Activity>
 */
public class Activity{

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
	public void setSubflow(Workflow activitySubflow){this.subflow=activitySubflow;}
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
	private String implementation;
	public void setImplementation(String activityImplementation){this.implementation=activityImplementation;}
	public String getImplementation(){return this.implementation;}
	
	/***
	 * <!ELEMENT StartMode (%Mode;)>
	 */
	private String startMode;
	public void setStartMode(String activityStartMode){this.startMode=activityStartMode;}
	public String getStartMode(){return this.startMode;}
	
	/***
	 * <!ELEMENT FinishMode (%Mode;)>
	 */
	private String finishMode;
	public void setFinishMode(String activityFinishMode){this.finishMode=activityFinishMode;}
	public String getFinishMode(){return this.finishMode;}
		
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
	
	public Activity(String activityId, String activityName)
	{
		this.id = id;
		this.name = name;
	}
	
	public boolean isSubflow()
	{
		return (subflow!=null);
	}
	
}
