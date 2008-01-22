package XPDLInterface;

import java.util.List;

public class Transition {

	private Activity from;
	public Activity getFrom(){return this.from;}
	public void setFrom(Activity tFrom){this.from = tFrom;}
	
	private Activity to;
	public Activity getTo(){return this.to;}
	public void setTo(Activity tTo){this.to = tTo;}
	
	private List<ExtendedAttribute> extendedAttributes;
	public List<ExtendedAttribute> getExtendedAttributes(){return this.extendedAttributes;}
	public void setExtendedAttributes(List<ExtendedAttribute> tExtendedAttributes){this.extendedAttributes = tExtendedAttributes;}
	
	private String id;
	public String getId(){return this.id;}
	
	private String conditionType;
	public String getConditionType(){return this.conditionType;}
	
	private String condition;
	public String getCondition(){return this.condition;}
	
	public Transition(String tId, String tConditionType, String tCondition)
	{
		this.id = tId;
		this.conditionType = tConditionType;
		this.condition = tCondition;
	}
}
