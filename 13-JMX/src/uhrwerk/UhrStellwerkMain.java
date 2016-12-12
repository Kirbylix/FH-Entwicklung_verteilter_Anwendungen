package uhrwerk;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class UhrStellwerkMain {
    public static void main(String[] args) throws Exception {

        // Get the Platform MBean Server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        UhrStellwerk meinStellwerk = new UhrStellwerk();
        StandardMBean mbean = new StandardMBean(meinStellwerk, UhrwerkMBean.class);

        // Construct the ObjectName for the MBean we will register
        ObjectName name = new ObjectName("uhrwerk:type=UhrStellwerk");

        // Register the Hello World MBean
        mbs.registerMBean(mbean, name);

        // Wait forever
        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }
}