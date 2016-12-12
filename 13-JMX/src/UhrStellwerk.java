/**
 * Aus dieser Klasse heraus wird das Uhrwerk gesteuert.
 */
public class UhrStellwerk {

    private Uhrwerk meinUhrwerk;

    public UhrStellwerk() {
        meinUhrwerk = new Uhrwerk();
    }
    
    public void start() {
        meinUhrwerk.run();

    }

    public void setPause() {
        meinUhrwerk.running = false;
    }

    public void setResume() {
        meinUhrwerk.running = true;
    }

    public void stop() {
        // not implemented yet

    }

}
