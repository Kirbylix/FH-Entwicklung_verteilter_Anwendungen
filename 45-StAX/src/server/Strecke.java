package server;
import java.io.*;
import java.util.Properties;

import Client.Client;
import common.StreckeI;

public class Strecke implements StreckeI
{
	// Zur Synchronisation werden drei Objekte angelegt, um f�r drei Aspekte
	// getrennt zu synchronisieren.
	
    private SyncObjekt reservation = new SyncObjekt();
    private SyncObjekt booking     = new SyncObjekt();
    private SyncObjekt reporting    = new SyncObjekt();
    
    public final String rechnerkennung = "die Strecke";


    Abschnitt dieAbschnitte[];
    

	public Strecke()
	{
		int anzAbschnitte = 61;
		Properties properties = new Properties();
		
		try {
			properties.load(new FileInputStream(new File(Client.PROPERTIES_PFAD)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
         dieAbschnitte = new Abschnitt[anzAbschnitte];
         for(int i=0; i<anzAbschnitte; i++)
         {
       		dieAbschnitte[i] = new Abschnitt(properties.getProperty(""+i%properties.size()));
         }
	}


// ----------------------------------------------------------------------------------------------

    public Boolean reserviere(Integer zugnummer, Boolean bRichtung, Integer n)
    {

    	boolean reservierung;
    	
        // Fallunterscheidung, ob Ost oder West
        if(bRichtung == false)
        {
        	n = dieAbschnitte.length-1-n;   // Abschnitte von hinten nach vorne
        }

        synchronized(reporting)
        {
            System.out.println("Versuche " + dieAbschnitte[n].getTitel() + " fuer Zug " + zugnummer + " zu reservieren");
        }

        synchronized(reservation)
        {
            reservierung = dieAbschnitte[n].Reservierung;  // ermittle die Reservierung
        
            if(reservierung == false)  // kein Zug auf dem Abschnitt
            {
        	    dieAbschnitte[n].Reservierung = true;
            }
        }
        synchronized(reporting)
        {
            if(reservierung == false)  // kein Zug auf dem Abschnitt
            {
        	    System.out.println(dieAbschnitte[n].getTitel() + " wird fuer Zug " + zugnummer + " reserviert");
       	        zeigeStreckenabschnitt();
       	        return true;
            }
            else
        
        	System.out.println(dieAbschnitte[n].getTitel() + " fuer Zug " + zugnummer + " besetzt");
        	return false;
        }        	
    }

    // in dieser Methode werden die Z�ge koordiniert, die nach Westen fahren
        
    // diese Methode wird von den Z�gen aufgerufen, die diesen Streckenabschnitt verlassen

    public void leaveAB(Integer zugnummer)
    {
		System.out.println(zugnummer + " verl�sst Streckenabschnitt");
    	
    }
    
//    public synchronized void zeigeStreckenabschnitt()

// ----------------------------------------------------------------------------------------------


    public Integer getAbschnitt(Boolean bRichtung,Integer n)
    {
        if(bRichtung == true)
        {
        	return n;   
        }
        else
        {
        	return dieAbschnitte.length-1-n;   
        }
    	
    }

// ----------------------------------------------------------------------------------------------


    public Integer getAnzahlZuege()
    {
        int counter = 0;
    	synchronized(booking)
    	{    		
            for(int i=0; i<dieAbschnitte.length; i++)
            {
            	if (dieAbschnitte[i].Belegung != 0) counter += 1;   
            }
    	}
        
        return counter;
    }

// ----------------------------------------------------------------------------------------------

    public void zeigeStreckenabschnitt()
    {

    	synchronized(reporting)
    	{
        	synchronized(reservation)
        	{
            	synchronized(booking)
            	{
    		    	for(int i=0; i< dieAbschnitte.length; i++)
    	            {
    		            if(dieAbschnitte[i].Reservierung == true)
    		            {
    		                System.out.print("r|");
    		            }
    		            else
    		            {
    		                System.out.print(" |");
    		            }    		
    	            }    	
		            System.out.println("");
    	            for(int i=0; i< dieAbschnitte.length; i++)
    	            {
    		            System.out.print(dieAbschnitte[i].Belegung + "|");
    		        }
    	
		            System.out.println("");
        
            	}
            	
        	}
    	}
    }
    
// ----------------------------------------------------------------------------------------------
    
    public Integer getStreckenLaenge()
    {
    	return dieAbschnitte.length;
    }
    
// ----------------------------------------------------------------------------------------------
   
    public Boolean freigeben(Integer zugnummer, Boolean bRichtung, Integer n)
    {
        if(bRichtung == false)
        {
        	n = dieAbschnitte.length-1-n;   // Abschnitte von hinten nach vorne
        }

    	synchronized(reservation)
    	{
       	    dieAbschnitte[n].Reservierung = false;
    	}
    	zeigeStreckenabschnitt();
    	return true;
    }


// ----------------------------------------------------------------------------------------------

    public String wechselnVon(Integer zugnummer, Boolean bRichtung, Integer from)
    {
        
        synchronized(booking)
        {
            if(bRichtung == false)
            {
            	System.out.println(""+ dieAbschnitte.length + " " + from);
        	    from = dieAbschnitte.length-1-from;   // Abschnitte von hinten nach vorne
            }

    	    dieAbschnitte[from].Belegung = 0;
        }
        
        return "Zug verl�sst " + dieAbschnitte[from].getTitel();
        
    };


// ----------------------------------------------------------------------------------------------
   
    public String wechselnNach(Integer zugnummer, Boolean bRichtung, Integer to)
    {
        synchronized(booking)
        {
            if(bRichtung == false)
            {   
        	    to = dieAbschnitte.length-1-to;     // Abschnitte von hinten nach vorne
            }

    	    dieAbschnitte[to].Belegung = zugnummer;
        }
       	    zeigeStreckenabschnitt();
       	    return "Zug wechselt befaehrt jetzt " + dieAbschnitte[to].getTitel();
       	
    };


// ----------------------------------------------------------------------------------------------
    
   
    public String ankommen(Integer zugnummer, Boolean bRichtung)
    {
        int first = 0;
        if(bRichtung == false)
        {
        	first = dieAbschnitte.length-1-first;   // Abschnitte von hinten nach vorne
        }
        
        synchronized(booking)
        {
    	    dieAbschnitte[first].Belegung = zugnummer;
    	
            System.out.println("Zug kommt nach " + dieAbschnitte[first].getTitel());
        }
    	zeigeStreckenabschnitt();
    	return dieAbschnitte[first].getTitel();
    };

// ----------------------------------------------------------------------------------------------
   
    public String verlassen(Integer zugnummer, Boolean bRichtung)
    {
        int last = 0;
        if(bRichtung == true)
        {
        	last = dieAbschnitte.length-1-last;   // Abschnitte von hinten nach vorne
        }

        synchronized(booking)
        {
        	
    	     dieAbschnitte[last].Belegung = 0;
    	
             
        }
        zeigeStreckenabschnitt();
        return "Zug verl�sst " + dieAbschnitte[last].getTitel();
    }


}

    /**
     * Dies Klasse dient dazu, zwei Objekte zur Synchronisation zu definieren 
     *
     */

    class SyncObjekt
    {
    	// Dies Klasse ben�tigt weder Attribute noch Methoden
    }


