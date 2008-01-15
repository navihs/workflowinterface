package XPDLInterface;
import java.util.*;

public class Activity{

	private Participant performer;
	private Workflow subflow;
	private ArrayList transitions;
	
	public Activity()
	{
		
	}
	
	public boolean isSubflow()
	{
		return (subflow!=null);
	}
}
