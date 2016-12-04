public class Simulation {

    private final static int ANZ_ZUEGE = 3;
    private final static int ANZ_SEGMENTE = 	6;
    final static int ABSTAND_BEOBACHTUNG = 5; 	// Intervall in welchem der "Streckenwachter" updatet
    final static int ANZ_VERSUCHE = 3;			// Versuche des Zuges weiter zu fahren, bevor er "aufgibt"

    public static void main(String[] args)
    {
        Strecke ab = new Strecke(ANZ_SEGMENTE);
        Zugfahrt[] zugfahrten = new Zugfahrt[ANZ_ZUEGE];
        Streckenwachter sw = new Streckenwachter(ab);
        sw.start();

        for(int i=1; i<=ANZ_ZUEGE; i++)
        {
            // Varianten:
            // Züge starten im Osten
            // Zugfahrt eineZugfahrt= new Zugfahrt(ab, i, true);

            // Züge abwechselnd aus Osten oder Westen
            zugfahrten[i-1]= new Zugfahrt(ab, i, (i%2==0));

            while(!ab.reserviere(i, (i%2==0), 0)){
                try{
                    Thread.sleep(100);
                }catch(InterruptedException ignored){

                }
            }

            ab.ankommen(i, (i%2==0));
            zugfahrten[i-1].start();

            try{
                Thread.sleep((int)(Math.random()*20));
            }catch(InterruptedException ignored){

            }
        }

        for(int ii=0;  ii<ANZ_ZUEGE; ii++){
            try {
                zugfahrten[ii].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sw.interrupt();
    }
}