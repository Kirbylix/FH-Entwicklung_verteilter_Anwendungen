package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * Client Connection
 */
class ClientConnection {

	private Socket socket;

	/**
	 * Konstruktor
	 * einstellung des TCP-Sockets über dem die Nachricht verschickt werden soll
	 * @param seqNr
	 */
	ClientConnection(int seqNr) {
		try {
			//Verbindung aufbauen
			socket = new Socket(Client.SERVER, Client.PORT);
			System.out.println("Verbindung zu " + socket.getInetAddress());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Nachricht senden TCP
	 * @param dieNachricht XML Nachricht die verschickt werden soll
	 * @return Antwort vom Server
	 */
    String sendMsg(String dieNachricht)
    {	String response = "";
		try {
			//TCP:
			//Nachricht senden an Server
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(dieNachricht);
			System.out.println("TCP Message gesendet");

			//Antwort auslesen vom Server
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			response = (String) ois.readObject();
			System.out.println("TCP Message erhalten");

			//UDP:
			//XML Nachricht an UDPListener für die Ausgabe senden
			InetAddress group = InetAddress.getByName("228.6.7.8");
			int portUDP = 4713;
			//Nachricht verpacken
			byte[] buffer = dieNachricht.getBytes();
			DatagramPacket dp = new DatagramPacket(buffer, buffer.length, group, portUDP);
			//UDP Verbindung aufbauen & senden
			MulticastSocket ms = new MulticastSocket(portUDP);
			ms.joinGroup(group);
			ms.send(dp);
			System.out.println("UDP Message gesendet");

		}catch (Exception e){
			e.printStackTrace();
		}
	    return response;
    }
}