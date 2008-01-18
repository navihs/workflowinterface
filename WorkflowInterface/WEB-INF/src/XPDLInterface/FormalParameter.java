package XPDLInterface;

/***
 * <!ELEMENT FormalParameter
 */
public class FormalParameter {
	
	/***
	 * <!ATTLIST FormalParameter
	 * Id NMTOKEN #REQUIRED
	 */
	private String id;
	public String getId(){return this.id;}
	
	/***
	 * <!ATTLIST FormalParameter
	 * Mode (IN | OUT | INOUT) "IN"
	 */
	private String mode;
	public String getMode(){return this.mode;}
	
	/***
	 * <!ATTLIST FormalParameter
	 * <!ELEMENT Description (#PCDATA)>
	 */
	private String description;
	public String getDescription(){return this.description;}
	
	/***
	 * <!ATTLIST FormalParameter
	 * <!ELEMENT DataType (%Type;)> 
	 */
	private String dataType;
	public String getDataType(){return this.dataType;}
	
	public FormalParameter(String fpId, String fpMode,String fpDataType, String fpDescription)
	{
		this.id =fpId;
		this.mode = fpMode;
		this.dataType = fpDataType;
		this.description = fpDescription;
	}
		

}
