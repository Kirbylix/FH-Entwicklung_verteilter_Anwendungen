package client;

import java.text.DateFormat;
import java.text.*;
import java.util.*;



public class Client 
{
    public final static String PROPERTIES_PFAD = "src/Abschnitt_Titel.txt";
    public final static int PORT = 4712;
    public final static String SERVER = "localhost";

    

    public static void main(String[] args)
    {
	    ClientStub meinStub = new ClientStub();
	    boolean bErfolg;
	    int     anzAbschnitte;
	    int     nrAbschnitt;
	    
	    
	    // Die nachstehenden Methoden haben jeweils die Parameter
	    // Zugnummer, Richtung, Streckenabschnitt
	    bErfolg = meinStub.reserviere(1, false, 11); 
	    bErfolg = meinStub.reserviere(2, false, 21);
	    int i = meinStub.getAbschnitt(true, 9);
	    int j = meinStub.getAbschnitt(false, 9);
		System.out.println(meinStub.wechselnVon(3, true, 31));
		System.out.println(meinStub.wechselnNach(4, true, 41));
		System.out.println(meinStub.freigeben(5, false, 51));
	    
/*
		       
	    // Die nachstehende Methode hat die Parameter
	    // Zugnummer, Richtung 
		bErfolg =  meinStub.verlassen(6, false);

	    // Die nachstehende Methode hat die Parameter
	    // Zugnummer, Richtung 
	    anzAbschnitte  = meinStub.getStreckenLaenge();
		nrAbschnitt    = meinStub.getAbschnitt(false,61);
*/
    }


}
