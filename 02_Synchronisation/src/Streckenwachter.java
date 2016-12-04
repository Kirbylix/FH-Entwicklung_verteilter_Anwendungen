class Streckenwachter extends Thread{
     Strecke dieStrecke;
    private static int counterId = 0;
    private final int id;

    Streckenwachter(Strecke eineStrecke)
    {
        this.dieStrecke = eineStrecke;
        id=counterId++;
    }

    public synchronized void run()
    {
        while(!this.isInterrupted())
        {
                System.out.println("Anzahl Zuege: " + dieStrecke.getAnzahlZuege());
                warten(Simulation.ABSTAND_BEOBACHTUNG);
        }
    }

    private void warten(int n)
    {
        try
        {
            Thread.sleep(n);
        }
        catch(InterruptedException e)
        {
            this.interrupt();
        }
    }
}
