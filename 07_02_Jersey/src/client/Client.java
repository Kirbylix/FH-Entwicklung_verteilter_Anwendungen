package client;

import java.text.DateFormat;
import java.text.*;
import java.util.*;

public class Client 
{
    public final static String PROPERTIES_PFAD = "src/Abschnitt_Titel.txt";
    public final static int PORT = 4712;
    public final static String SERVER = "localhost";

    public static void main(String[] args) {
	    ClientStub meinStub = new ClientStub();
	    boolean bErfolg;
	    int     anzAbschnitte;
	    int     nrAbschnitt;

	    // Die nachstehenden Methoden haben jeweils die Parameter
	    // Zugnummer, Richtung, Streckenabschnitt
	    System.out.println(bErfolg = meinStub.reserviere(1, false, 11)); 
	    System.out.println(bErfolg = meinStub.reserviere(2, false, 21));
	    System.out.println(meinStub.getAbschnitt(true, 9));
	    System.out.println(meinStub.getAbschnitt(false, 9));
		System.out.println(meinStub.wechselnVon(3, true, 31));
		System.out.println(meinStub.wechselnNach(4, true, 41));
		System.out.println(meinStub.freigeben(5, false, 51));
    }
}