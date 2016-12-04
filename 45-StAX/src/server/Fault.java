package server;

public class Fault extends Response
{
	private String faultCode;
	private String reason;
	
	public Fault(){}

	public Fault(String faultCode, String reason){
		this.faultCode = faultCode;
		this.reason = reason;
	}

	public void setFaultCode(String faultCode){
		this.faultCode = faultCode;
	}
	
	public String getFaultCode(){
		return this.faultCode;
	}

	public void setReason(String theReason){
		this.reason = theReason;
	}
	
	public String getReason(){
		return this.reason;
	}
}