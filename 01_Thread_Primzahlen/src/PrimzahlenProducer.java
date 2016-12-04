class PrimzahlenProducer implements Runnable {
    private volatile Thread t;
    private Primzahlen meinePrimzahlen;

    PrimzahlenProducer(Primzahlen meinePrimzahlen){
        this.meinePrimzahlen = meinePrimzahlen;
   }

    void start(){
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
            int n = meinePrimzahlen.getLength()-1;

            // Alle Zahlen von 1 bis n aufschreiben
            for (int j = 1; j <= n; j++)
            {
                meinePrimzahlen.setPrim(j);
                // Jedes Feldelement wird erst einmal auf wahr (= Primzahl) gesetzt
            }

            // 1 ist keine Primzahl
            meinePrimzahlen.setNonPrim(1);
            meinePrimzahlen.setReady(1);
            System.out.println("Primzahlen bis " + 1 + " verfuegbar");

            for (int i = 2; i * i <= n; i++)
            {
                meinePrimzahlen.setReady(i);
                System.out.println("Primzahlen bis " + i + " verfuegbar");

                if (meinePrimzahlen.isPrim(i)) // not durchgestrichen(i)
                {
                    // durchstreichenVielfaches(i)
                    //05
                    int tmpI = i;
                    (new Thread() {
                        public void run() {
                            for (int j = 2* tmpI; j <= n; j = j+ tmpI)
                            {
                                meinePrimzahlen.setNonPrim(j);
                            }
                        }
                    }).start();
                }
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) { }
            }
            meinePrimzahlen.setReady(n);
            System.out.println("Primzahlen bis " + n + " verfuegbar");
    }
}