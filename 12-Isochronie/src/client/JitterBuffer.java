package client;

import common.MessageDTO;

public class JitterBuffer extends Forwarder{
    private static final int DELAY = 250;

    public JitterBuffer(LauflichtGUI gui, LauflichtModell model) {
        super(gui, model);
    }

    public void executeMsg(MessageDTO msg) {
        long now = System.nanoTime();
        long msgDelay = (now-msg.nanoTime) / 1000 / 1000;
        long delay = DELAY-msgDelay;

        try {
            Thread.sleep(delay);

            model.setLauflicht(msg.i,msg.b);
            LauflichtGUI lf = (LauflichtGUI)this.gui;
            lf.refresh();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}