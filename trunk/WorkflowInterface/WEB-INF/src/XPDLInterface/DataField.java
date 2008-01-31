package XPDLInterface;

public class DataField 
{
	
	private String initialValue;
	public String getInitialValue(){return this.initialValue;}
	public void setInitialValue(String dataFieldInitialValue){this.initialValue = dataFieldInitialValue;}

	private String length;
	public String getLength(){return this.length;}
	public void setLength(String dataFieldLength){this.length = dataFieldLength;}

	private String id;
	public String getId(){return this.id;}
	public void setId(String dataFieldId){this.id = dataFieldId;}

	private String name;
	public String getName(){return this.name;}
	public void setName(String dataFieldName){this.name = dataFieldName;}

	private boolean isArray;
	public boolean getIsArray(){return this.isArray;}
	public void setIsArray(boolean dataFieldIsArray){this.isArray = dataFieldIsArray;}

	private String dataType;
	public String getDataType(){return this.dataType;}
	public void setDataType(String dataFieldDataType){this.dataType = dataFieldDataType;}

	private String description;
	public String getDescription(){return this.description;}
	public void setDescription(String dataFieldDescription){this.description = dataFieldDescription;}

	
	public DataField(String dataFieldId, String dataFieldName, String dataFieldIsArray)
	{
		this.id = dataFieldId;
		this.name = dataFieldName;
		this.isArray = dataFieldIsArray.equalsIgnoreCase("TRUE");
	}
}
