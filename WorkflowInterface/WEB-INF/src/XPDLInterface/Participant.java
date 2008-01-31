package XPDLInterface;

/**
 * <!ELEMENT Participant>
 * @author Laurent
 *
 */
public class Participant {

	/**
	 * <!ATTLIST Partcipant Id
	 */
	private String id;
	public String getId(){return this.id;}
	
	/**
	 * <!ATTLIST Partcipant Name
	 */
	private String name;
	public String getName(){return this.name;}

	/**
	 * <!ELEMENT Participant
	 * <!ELEMENT ParticipantType
	 */
	private String type;
	public String getType(){return this.type;}
	
	/**
	 * <!ELEMENT Participant
	 * <!ELEMENT Description
	 */
	private String description;
	public String getDescription(){return this.description;}

	/**
	 * Constructeur de l'élément participant
	 * @param pId <!ATTLIST Partcipant Id
	 * @param pName <!ATTLIST Partcipant Name
	 * @param pType <!ELEMENT ParticipantType
	 * @param pDescription <!ELEMENT Description
	 */
	public Participant(String pId, String pName, String pType, String pDescription)
	{
		this.id = pId;
		this.name = pName;
		this.type = pType;
		this.description = pDescription;
	}
}
