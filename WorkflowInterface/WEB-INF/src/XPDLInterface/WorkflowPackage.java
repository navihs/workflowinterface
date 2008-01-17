package XPDLInterface;
import java.util.*;

/***
 * Classe défissant <!ELEMENT Package 
 * @author Laurent
 *
 */
public class WorkflowPackage {

	/* Attributs Objets */
	private ArrayList participants;
	private ArrayList workflows;
	private ArrayList extendedAttributes;
	
	/* Attributs String*/
	/***
	 * <!ELEMENT Created
	 */
	private Date created;
	/***
	 * <!ATTLIST Package id
	 */
	private String id;
	/***
	 * <!ATTLIST Package name
	 */
	private String name;
	
	public WorkflowPackage()
	{
		
	}
}
