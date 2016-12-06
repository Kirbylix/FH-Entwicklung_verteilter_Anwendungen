package client;
/**
 * @(#)LauflichtModell.java
 *
 *
 * @author
 * @version 1.00 2010/12/3
 */


public class LauflichtModell
{

    boolean lauflicht[];

    /**
     * Das Lauflicht wir angelegt und initialisert.
     * @param len L�nge des Lauflichtes
     */
    public LauflichtModell(int len)
    {
    	lauflicht = new boolean[len];
        for (int i = 0; i < len/3; i++)
        {
            lauflicht[3*i] = true;
        }
    }
    
    
    public void setLauflicht(int i, boolean b)
    {
        lauflicht[i] = b;
        zeigeLauflicht();
    }

    public void zeigeLauflicht()
    {
    	for(int lfd=0; lfd<lauflicht.length; lfd++)
    	{
    		if(lauflicht[lfd]==true)
    		{
    			System.out.print("*");

    		}
    		else
    		{
    			System.out.print(" ");
    		}
    	}
        System.out.println("");
    }

    public boolean getLauflicht(int i)
    {
        return lauflicht[i];
    }

    public int getLauflichtLength()
    {
        return lauflicht.length;
    }

    public void toggleLauflicht(int i)
    {
    	if(lauflicht[i] == true)
    	{
    		lauflicht[i] = false;
    	}
    	else
    	{
    		lauflicht[i] = true;
    	}
    }


}