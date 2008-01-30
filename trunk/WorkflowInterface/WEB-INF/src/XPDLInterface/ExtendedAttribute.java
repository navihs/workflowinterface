package XPDLInterface;

/**
 * <!ELEMENT ExtendedAttribute>
 * @author Laurent
 *
 */
public class ExtendedAttribute {

	/**
	 * <!ATTLIST ExtendedAttribute Name
	 */
	private String name;
	public String getName(){return this.name;}
	
	/**
	 * <!ATTLIST ExtendedAttribute Value
	 */
	private String value;
	public String getValue(){return this.value;}
	
	/**
	 * Constructeur de l'objet ExtendedAttribute
	 * @param eaName <!ATTLIST ExtendedAttribute Name
	 * @param eaValue <!ATTLIST ExtendedAttribute Value
	 */
	public ExtendedAttribute(String eaName,String eaValue)
	{
		this.name = eaName;
		this.value = eaValue;
	}
}
