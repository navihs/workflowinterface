package XPDLInterface;

public class Participant {

	private String id;
	public String getId(){return this.id;}
	
	private String name;
	public String getName(){return this.name;}

	private String type;
	public String getType(){return this.type;}
	
	private String description;
	public String getDescription(){return this.description;}

	public Participant(String pId, String pName, String pType, String pDescription)
	{
		this.id = pId;
		this.name = pName;
		this.type = pType;
		this.description = pDescription;
	}
}
