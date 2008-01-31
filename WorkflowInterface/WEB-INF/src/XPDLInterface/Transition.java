package XPDLInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * <!ELEMENT Transition>
 * @author Laurent
 *
 */
public class Transition {

	/**
	 * <!ATTLIST Transition From
	 */
	private Activity from;
	public Activity getFrom(){return this.from;}
	public void setFrom(Activity tFrom){this.from = tFrom;}
	
	/**
	 * <!ATTLIST Transition To
	 */
	private Activity to;
	public Activity getTo(){return this.to;}
	public void setTo(Activity tTo){this.to = tTo;}
	
	/**
	 * <!ELEMENT ExtendedAttributes>
	 */
	private List<ExtendedAttribute> extendedAttributes=new ArrayList<ExtendedAttribute>();;
	public List<ExtendedAttribute> getExtendedAttributes(){return this.extendedAttributes;}
	public void setExtendedAttributes(List<ExtendedAttribute> tExtendedAttributes){this.extendedAttributes = tExtendedAttributes;}
	
	/**
	 * <!ATTLIST Transition Id
	 */
	private String id;
	public String getId(){return this.id;}
	
	/**
	 * <!ELEMENT Condition
	 * <!ATTLIST Condition Type
	 */
	private String conditionType;
	public String getConditionType(){return this.conditionType;}
	
	/**
	 * <!ELEMENT Condition
	 */
	private String condition;
	public String getCondition(){return this.condition;}
	
	/**
	 * Construction d'une transition
	 * @param tId <!ATTLIST Transition Id
	 * @param tConditionType <!ATTLIST Condition Type
	 * @param tCondition <!ELEMENT Condition
	 * @param activityFrom <!ATTLIST Transition From
	 * @param activityTo <!ATTLIST Transition To
	 * On attache cette transition à la liste des transitions des activités from et to
	 */
	public Transition(String tId, String tConditionType, String tCondition,Activity activityFrom, Activity activityTo)
	{
		this.id = tId;
		this.conditionType = tConditionType;
		this.condition = tCondition;
		this.from = activityFrom;
		this.to = activityTo;
		activityFrom.addTransition(this);
		activityTo.addTransition(this);
	}
}
