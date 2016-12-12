package uhrwerk;

import java.text.DecimalFormat;

public class Uhrwerk implements Runnable {

    public boolean shutdown = false;
    public boolean running = false;
    public long timeStart;
    public long timeAct;


    public void run() {
        System.out.println("Uhr gestartet");
        running = true;
        timeStart = System.nanoTime();
        while (!shutdown) {
            timeAct = System.nanoTime();
            long timeDelta = (timeAct - timeStart) / 1000000;
            if (running) {
                String str = buildTimeString(timeDelta);
                System.out.println(str);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public String buildTimeString(long l) {
        long rest = 0;

        long std = l / 3600000;
        rest = l - std * 3600000;

        long min = rest / 60000;
        rest = rest - min * 60000;

        long sec = rest / 1000;
        rest = rest - sec * 1000;
        long milli = rest;

        DecimalFormat df2 = new DecimalFormat("00");
        String sekunden = df2.format(sec);
        String minuten = df2.format(min);
        String stunden = df2.format(std);

        DecimalFormat df3 = new DecimalFormat("000");
        String millis = df3.format(milli);

        String str = stunden + ":" + minuten + ":" + sekunden + ":" + millis;

        return str;
    }
}