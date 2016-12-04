import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class Client {
    public static void main(String args[]) {
        String host = "localhost";
        int port = 4711;

        String str1 = "";
        Integer i = 3;
        Float f = 4.2f;
        Double d = 5.7999999;

        System.out.print("security");
        System.setSecurityManager(new RMISecurityManager()); //<--
        System.out.println(" ...done");

        ServiceInterface service = null;
        try {
            System.out.print("lookup");
            service = (ServiceInterface) Naming.lookup("rmi://" + host + ":" + port + "/" + "DataTransferObject");
            System.out.println(" ...done");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //      meinServer.forever(0);
        //		meinServer.boundless(1);

        try {
            service.tuwas();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        tuwas(i, f, d);
        System.out.println("i=" + i + "f=" + f + "d=" + d);
        try {
            str1 = service.tuwas(i, f, d);
        } catch (MyException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(str1);

        DataTransferObject t1 = new DataTransferObject(1);
        DataTransferObject t2 = null;
        try {
            t2 = service.transfer(t1);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("t1=" + t1.value + "   t2=" + t2.value);
    }

	static public String tuwas(Integer i, Float j, Double d){
		System.out.println("tuwas(int float double)");
		i = new Integer(11);
		j = 0.15f;
        d = new Double(23d);
		return "Erfolg";
	}
}