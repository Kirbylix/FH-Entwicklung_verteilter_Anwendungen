package server;

import java.io.Serializable;

class Abschnitt implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean Reservierung;
	public int     Belegung;
	private String Titel;

    public Abschnitt()
    {
    	Reservierung = false;    // der Abschnitt ist nicht reserviert
    	Belegung = 0;            // auf dem Abschnitt f�hrt zur Zeit kein Zug
    	this.Titel = "kein Titel vergeben";
    }
    
    public Abschnitt(String Titel){
    	Reservierung = false;
    	Belegung = 0; 
    	this.Titel = Titel;    	
    }
    
    public String getTitel(){
    	return this.Titel;
    }
}