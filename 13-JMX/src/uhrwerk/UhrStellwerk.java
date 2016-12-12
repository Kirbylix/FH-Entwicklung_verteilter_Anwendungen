package uhrwerk;

/**
 * Aus dieser Klasse heraus wird das uhrwerk.Uhrwerk gesteuert.
 */
public class UhrStellwerk implements UhrwerkMBean {

    private Uhrwerk meinUhrwerk;
    private long diff;

    public UhrStellwerk() {
        meinUhrwerk = new Uhrwerk();
    }

    @Override
    public void start() {
        System.out.println("START");
        meinUhrwerk.shutdown = true;
        meinUhrwerk.run();
    }

    @Override
    public void stop() {
        System.out.println("STOP");
        meinUhrwerk.shutdown = true;
    }

    /**
     * Fragen ob Uhr läuft
     */
    @Override
    public boolean isRunning(){
        return meinUhrwerk.running;
    }

    /**
     * Uhr vorhanden aber noch nicht gestartet
     */
    @Override
    public boolean isExist(){
        if(this.meinUhrwerk == null){
            return true;
        }
        return false;
    }

    @Override
    public void pause() {
        System.out.println("PAUSE");
        meinUhrwerk.running = false;
        diff = meinUhrwerk.timeAct - meinUhrwerk.timeStart;
    }

    @Override
    public void resume() {
        System.out.println("RESUME");
        meinUhrwerk.running = true;
        meinUhrwerk.timeStart = System.nanoTime()  - diff;
    }
}