import java.io.Serializable;


public class DataTransferObject implements Serializable
{

    Integer value;

	public DataTransferObject(int i)
	{
		value = new Integer(i);
	}
    
	public void setValue(Integer i)
	{
		
//		this.value = new Integer(i); //outboxing anders
		this.value = i; //outboxng anders
	}

	public Integer getValue()
	{
	
		return value;
	}
}


