package server;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MethodResponse extends Response {
	private String valueType;      // Boolean
	private String value;         // z.B 17
	
	public MethodResponse(){}

    public MethodResponse(String valueType, String value){
        this.valueType = valueType;
        this.value = value;
    }

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return this.value;
	}
	
	public void setValueType(String valueType){
		this.valueType = valueType;
	}

	public String getValueType(){
		return this.valueType;
	}
}
