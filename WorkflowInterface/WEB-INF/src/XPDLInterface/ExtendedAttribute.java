package XPDLInterface;

/***
 * <!ELEMENT ExtendedAttribute>
 * @author Laurent
 *
 */
public class ExtendedAttribute {

	private String name;
	public String getName(){return this.name;}
	
	private String value;
	public String getValue(){return this.value;}
	
	public ExtendedAttribute(String eaName,String eaValue)
	{
		this.name = eaName;
		this.value = eaValue;
	}
}
