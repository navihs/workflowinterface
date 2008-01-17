package XPDLInterface;
import java.util.*;

public class Activity{

	/* Attributs Objets */
	private Participant performer;
	private Workflow subflow;
	private ArrayList transitions;
	private ArrayList extendedAttributes;

	/* Attributs String */
	private String description;
	private String limit;
	private String route;
	private String implementation;
	private String mode;
	//private String TransitionRestriction
	//private String Join	//XOR ou AND
	//private String Split 	//XOR ou AND
	//TransitionRefs ? 
	
	public Activity()
	{
		
	}
	
	public boolean isSubflow()
	{
		return (subflow!=null);
	}
	
}
