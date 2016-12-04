import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceInterface extends Remote{
    void tuwas() throws RemoteException;
    double tuwas(int i, float j, double t)throws RemoteException;
    String tuwas(Integer i, Float j, Double t) throws MyException, RemoteException;
    DataTransferObject transfer(DataTransferObject t)throws RemoteException;
    void forever(int i)throws RemoteException;
	void boundless(int i)throws RemoteException;
}