public class Primzahlen
{
    boolean prim[];
    long ready = 0;

    public Primzahlen(int n)
    {
  
        prim = new boolean[n+1];
    }
    
    public void setPrim(int i)
    {
    	prim[i] = true;
    }
    
    public void setNonPrim(int i)
    {
    	prim[i] = false;
    }
    
    
    public void setReady(int i)
    {
    	ready = i;
    }
    
    public int getLength()
    {
    	return prim.length;
    }
    
    public boolean isPrim(int i)
    {
    	return prim[i];
    }
    
}

