package XPDLInterface;

import java.util.*;

/***
 * Classe défissant <!ELEMENT Package 
 * @author Laurent
 *
 */
public class WorkflowPackage {

	/* Attributs Objets */
	private List participants;
	private List workflows;
	private List extendedAttributes;
	private List dataFields;
	
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
	
	public WorkflowPackage(String id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public void setCreated(Date d)
	{
		this.created = d;
	}
	
	public void setDataFields(List dataFields)
	{
		this.dataFields = dataFields;
	}
	
	public void setExtendedAttributes(List extendedAttributes)
	{
		this.extendedAttributes = extendedAttributes;
	}
	
	public void setWorkflows()
	{}
	
}
