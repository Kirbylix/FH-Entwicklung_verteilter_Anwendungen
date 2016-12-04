import message.InfoType;
import message.Message;
import message.MessageManagement;
import message.MessageType;

import java.util.Date;

class Zugfahrt extends Thread{
    private static int counterId = 0;
    private final int id;
    private Strecke dieStrecke;
    private boolean bRichtung;
    private int zugnummer;

    Zugfahrt(Strecke eineStrecke, int zugnummer, boolean bRichtung)
    {
        this.dieStrecke = eineStrecke;       // einspurige Strecke
        this.bRichtung = bRichtung;         // Richtung
        this.zugnummer = zugnummer;
        this.id = counterId++;
    }

    private void fahre(boolean bRichtung){
        if(bRichtung){
            System.out.println("Zug " + zugnummer + " startet im Westen");
        }else{
            System.out.println("Zug " + zugnummer + " startet im Osten");
        }

        // die Anzahl der Streckenabschnitte wird ermittelt
        //Kommunikation an Strecke
        MessageManagement.send(MessageType.REQUEST, InfoType.STRECKENLEANGE, new Date(), this.id, this.dieStrecke.getId());
        int len = (Integer) MessageManagement.receive().getInfo(InfoType.STRECKENLEANGE);
        //len = dieStrecke.getStreckenLaenge();

        boolean weiterfahrt = false;
        int i;  // Position des Zuges (abhängig von der Richtung)

        for(i=1; i<len; i++){
            weiterfahrt = false;
            int anzTrials = Simulation.ANZ_VERSUCHE;
            do
            {
                //Kommunikation an Strecke
                MessageManagement.send(MessageType.REQUEST, InfoType.RESERVIEREN, new Date(), this.id, this.dieStrecke.getId());
                //Antwort von Strecke
                weiterfahrt = (Boolean) MessageManagement.receive().getInfo(InfoType.RESERVIEREN);
                weiterfahrt = dieStrecke.reserviere(zugnummer, bRichtung, i);

                if(weiterfahrt==true)
                {   // Zug darf weiterfahren
                    try
                    {
                        // Fahren auf dem alten Abschnittes
                        Thread.sleep((int)(Math.random()*50));
                    }
                    catch(InterruptedException e)
                    {
                    }
                    synchronized(dieStrecke) {
                        // alten Abschnitt verlassen und freigeben
                        System.out.println("Zug " + zugnummer + " wechselt jetzt auf " + dieStrecke.getAbschnitt(bRichtung, i));
                        //Kommunikation an Strecke
                        Message message1 = new Message(MessageType.REQUEST, InfoType.WECHSELNTO, new Date(), this.id, this.dieStrecke.getId());
                        message1.addInfo(InfoType.RICHTUNG, bRichtung);
                        message1.addInfo(InfoType.ABSCHNITT, i-1);
                        MessageManagement.send(message1);
                        //dieStrecke.wechselnVon(zugnummer, bRichtung, i - 1);

                        wartenUnterschiedlich(50);

                        //Kommunikation an Strecke
                        Message message2 = new Message(MessageType.REQUEST, InfoType.WECHSELNTO, new Date(), this.id, this.dieStrecke.getId());
                        message2.addInfo(InfoType.RICHTUNG, bRichtung);
                        message2.addInfo(InfoType.ABSCHNITT, i);
                        MessageManagement.send(message1);
                        //dieStrecke.wechselnTo(zugnummer, bRichtung, i);
                    }
                        // Fahren auf dem neuen Abschnitt
                        wartenUnterschiedlich(5);
                        dieStrecke.freigeben(zugnummer, bRichtung, i - 1);
                }
                else
                {   // Zug darf noch nicht weiterfahren
                    anzTrials--;
                    System.out.println("Zug " + zugnummer + " wartet");
                    warten(100);   // Zugführer schaut nach einer Zeit wieder auf das Signal
                }
            } while (weiterfahrt == false && anzTrials >0);

            if(anzTrials == 0) break;
        }

        if(weiterfahrt==true)
        {
            System.out.println("Zug " + zugnummer + " verläßt " + dieStrecke.getAbschnitt(bRichtung,len-1));
            dieStrecke.verlassen(zugnummer, bRichtung);
            dieStrecke.freigeben(zugnummer, bRichtung, len-1); // alten Abschnitt freigeben
        }
        else
        {
            System.out.println("Zug " + zugnummer + " gibt auf in Abschnitt"
                    + dieStrecke.getAbschnitt(bRichtung,i-1));
        }
    }

    public void run()
    {
        fahre(bRichtung);
    }

    private void wartenUnterschiedlich(int n){
        try{
            Thread.sleep((int)(Math.random()*n));
        }catch(InterruptedException e){
        }
    }

    // vorgegeben Zeit warten (in msec)
    public void warten(int n){
        try{
            Thread.sleep(n);
        }catch(InterruptedException e) {
        }
    }
}