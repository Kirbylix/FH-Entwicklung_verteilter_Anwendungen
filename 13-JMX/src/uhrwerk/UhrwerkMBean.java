package uhrwerk;

public interface UhrwerkMBean {
    boolean isRunning();
    boolean isExist();
    void start();
    void stop();
    void pause();
    void resume();
}