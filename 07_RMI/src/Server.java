import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

public class Server extends UnicastRemoteObject implements ServiceInterface {

    public Server()throws RemoteException {
        super();
    }

	public static void main (String[] args /* args[0]: port */) throws RemoteException
	{
		int port = 4711;

		Server obj = new Server();
		String objName = "DataTransferObject";

		if (System.getSecurityManager() == null) {
			System.setSecurityManager (new RMISecurityManager());
		}

		Registry reg = LocateRegistry.getRegistry (port);
		boolean bound = false;
		for (int i = 0; ! bound && i < 2; i++) {
			try {
				reg.rebind (objName, obj);
				bound = true;
				System.out.println (objName+" bound to registry, port " + port + ".");
			}catch (RemoteException e) {
				System.out.println ("Rebinding " + objName + " failed, retrying ...");
				reg = LocateRegistry.createRegistry (port);
				System.out.println ("Registry started on port " + port + ".");
			}
		}
		System.out.println ("Server ready.");
	}

    
	public void tuwas()throws RemoteException {
		System.out.println("call: tuwas()");
		System.out.println("tuwas()");
	}

	/**
	 * Dies ist eine Funktion mit einer Endlosschleife 
	 */
	public void forever(int i)throws RemoteException
	{
		System.out.println("call: forever");
		while(i!=1 && i!=2)
		{
			System.out.println("," + i);
		}
		
	}

	/**
	 *  Diese Methode liefert einen Laufzeitfehler
	 */
	
	public double tuwas(int i, float j, double t)throws RemoteException
	{
		System.out.println("call: tuwas(int, float, double)");
		int k = 1/0;
		
		return i+j+t;
	}

	/**
	 * Diese Methode liefert eine eigene Ausnahme
	 */
	
	public String tuwas(Integer i, Float j, Double t) throws MyException, RemoteException
	{
		System.out.println("call: tuwas(Integer, Float, Double)");
//		System.exit(0);
		if(1<0)
		{
		throw (new MyException("Interne Fehler in tuwas")); 
		}
		
		i = new Integer(4711);
		j = new Float(0.815f);
		return "Erfolg";
	}

	/**
	 * Diese Methode verbraucht recht schnell den gesamten Speicher
	 * 
	 */
	
	public void boundless(int i)throws RemoteException
	{
	  System.out.println("call: boundless");	
	  while(true)
	  {
		  System.out.println("i="+i);
		  int[] a = new int[i];
		  boundless(2*i);
		  
	  }
	}

	/**
	 * Ein Beispiel um Daten zu �bertragen - hier nur einen Integer
	 */
	
    public DataTransferObject transfer(DataTransferObject t) throws RemoteException
    {
    	System.out.println("call: transfer(DataTransferObject)");
    	t.setValue(t.getValue()+1);
    	return t;
    }
}