package common;
import java.io.Serializable;


public class MessageDTO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	public long nanoTime;
    public long sequential;
    public int  i;
    public boolean b;
}
