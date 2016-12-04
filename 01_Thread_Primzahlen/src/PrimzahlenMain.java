import java.util.Scanner;

public class PrimzahlenMain
{
    private static final int MAXPRIM = 1000000;

    public static void main(String[] args)
    {
        int n;
        Primzahlen meinePrimzahlen = new Primzahlen(MAXPRIM);

        PrimzahlenProducer pp = new PrimzahlenProducer(meinePrimzahlen);
        //01
        pp.start();

        PrimzahlenConsumer pc = new PrimzahlenConsumer(meinePrimzahlen);

        Scanner sc = new Scanner(System.in);
        do{
            // Einlesen einer Zahl
            n = sc.nextInt();
            if(n<MAXPRIM){
                pc.start(n);
            }
        }
        while(n>0);
    }
}