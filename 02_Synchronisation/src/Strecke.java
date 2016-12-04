import message.InfoType;
import message.Message;
import message.MessageType;

import java.util.Date;

public class Strecke {// Zur Synchronisation werden drei Objekte angelegt, um für drei Aspekte
    // getrennt zu synchronisieren.

    private Abschnitt dieAbschnitte[];
    private static int counterId = 0;
    private final int id;

    public Strecke(int anzAbschnitte)
    {
        dieAbschnitte = new Abschnitt[anzAbschnitte];
        for(int i=0; i<anzAbschnitte; i++)
        {
            dieAbschnitte[i] = new Abschnitt();
        }
        id = counterId++;
    }

    public synchronized boolean reserviere(int zugnummer, boolean bRichtung, int n)
    {
        boolean reservierung;

        // Fallunterscheidung, ob Ost oder West
        if(bRichtung == false){
            n = dieAbschnitte.length-1-n;   // Abschnitte von hinten nach vorne
        }
        System.out.println("Versuche Abschnitt " + n + " fuer Zug " + zugnummer + " zu reservieren");
        reservierung = dieAbschnitte[n].Reservierung;  // ermittle die Reservierung

        // kein Zug auf dem Abschnitt
        if(reservierung == false){
            dieAbschnitte[n].Reservierung = true;
        }
        // kein Zug auf dem Abschnitt
        if(reservierung == false){
            System.out.println("Abschnitt " + n + " wird fuer Zug " + zugnummer + " reserviert");
            zeigeStreckenabschnitt();
            return true;
        }else
            System.out.println("Abschnitt "+ n + " für Zug " + zugnummer + " besetzt");
        return false;
    }

    // in dieser Methode werden die Züge koordiniert, die nach Westen fahren
    // diese Methode wird von den Zügen aufgerufen, die diesen Streckenabschnitt verlassen
    public void leaveAB(int zugnummer) {
        System.out.println(zugnummer + " verlaeßt Streckenabschnitt");
    }

    public synchronized int getAbschnitt(boolean bRichtung,int n) {
        if(bRichtung == true) {
            return n;
        }else{
            return dieAbschnitte.length-1-n;
        }
    }

    public synchronized int getAnzahlZuege() {
        int counter = 0;

        for(int i=0; i<dieAbschnitte.length; i++){
            if (dieAbschnitte[i].Belegung != 0) counter += 1;
        }

        return counter;
    }

    public void zeigeStreckenabschnitt(){
        for (Abschnitt aDieAbschnitte1 : dieAbschnitte) {
            if (aDieAbschnitte1.Reservierung == true) {
                System.out.print("r|");
            } else {
                System.out.print(" |");
            }
        }
        System.out.println("");
        for (Abschnitt aDieAbschnitte : dieAbschnitte) {
            System.out.print(aDieAbschnitte.Belegung + "|");
        }

        System.out.println("");

    }

// ----------------------------------------------------------------------------------------------

    public int getStreckenLaenge()
    {
        return dieAbschnitte.length;
    }

// ----------------------------------------------------------------------------------------------

    public synchronized void freigeben(int zugnummer, boolean bRichtung, int n)
    {
        if(bRichtung == false)
        {
            n = dieAbschnitte.length-1-n;   // Abschnitte von hinten nach vorne
        }

        dieAbschnitte[n].Reservierung = false;

        System.out.println("Abschnitt " + n + " wird freigegeben");

        zeigeStreckenabschnitt();
    }


// ----------------------------------------------------------------------------------------------

    /**
     * Aus Gründen der Synchronisation wurde
     *         public void wechselnVon(int zugnummer, boolean bRichtung, int from)
     *         public void wechselnNach(int zugnummer, boolean bRichtung, int to)
     * ersetzt durch
     *         public void wechseln(int zugnummer, boolean bRichtung, int from, int to)
     */
    public void wechselnVon(int zugnummer, boolean bRichtung, int from)
    {
        if(bRichtung == false)
        {
            from = dieAbschnitte.length-1-from;   // Abschnitte von hinten nach vorne
        }

        dieAbschnitte[from].Belegung = 0;
        System.out.println("Zug " + zugnummer + " verlaesst Abschnitt " + from);
        // zeigeStreckenabschnitt();
    }

    public void wechselnTo(int zugnummer, boolean bRichtung, int to)
    {
        if(bRichtung == false){
            to   = dieAbschnitte.length-1-to;     // Abschnitte von hinten nach vorne
        }

        dieAbschnitte[to].Belegung = zugnummer;

        System.out.println("Zug wechselt befaehrt jetzt " + to);
        zeigeStreckenabschnitt();
    }

    public synchronized void ankommen(int zugnummer, boolean bRichtung){
        int first = 0;
        if(bRichtung == false){
            first = dieAbschnitte.length-1-first;   // Abschnitte von hinten nach vorne
        }

        dieAbschnitte[first].Belegung = zugnummer;

        System.out.println("Zug " + zugnummer + " kommt nach " + first);
        zeigeStreckenabschnitt();
    }

    public synchronized void verlassen(int zugnummer, boolean bRichtung){
        int last = 0;
        if(bRichtung == true){
            last = dieAbschnitte.length-1-last;   // Abschnitte von hinten nach vorne
        }
        dieAbschnitte[last].Belegung = 0;

        System.out.println("Zug verläßt " + last);
        zeigeStreckenabschnitt();
    }

    public int getId() {
        return id;
    }
}