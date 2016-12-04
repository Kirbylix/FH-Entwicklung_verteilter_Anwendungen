class PrimzahlenConsumer implements Runnable
{
    private volatile Thread t;
    private Primzahlen meinePrimzahlen;
    private volatile int n;
    public volatile boolean isprim=false;

    PrimzahlenConsumer(Primzahlen meinePrimzahlen)
    {
        this.meinePrimzahlen = meinePrimzahlen;	
    }
    
    
    boolean istPrimzahl(int l)
    {
    	return meinePrimzahlen.isPrim(l);
    }
        

    public void printPrimzahlen()
    {
        int n = meinePrimzahlen.getLength();
        
        for (int j = 1; j <= n; j++)
        {
            if (meinePrimzahlen.isPrim(j))
            {
                System.out.print(j + " ");
            }
        }
    }

    void start(int n){
        t = new Thread(this);
        t.start();
        this.n = n;
    }

    @Override
    public void run() {
        //04
        while (!Thread.interrupted()) {
            if (meinePrimzahlen.ready >= n) {
                //03
                if (istPrimzahl(n)) {
                    System.out.println(n + " ist eine Primzahl");
                } else {
                    System.out.println(n + " ist keine Primzahl");
                }
                t.interrupt();
            } else {
                if (!istPrimzahl(n)) {
                    System.out.println(n + " ist keine Primzahl");
                    t.interrupt();
                } else {
                    try {
                        System.out.println("PC sleep");
                        Thread.sleep(2000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }
}