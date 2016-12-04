package listener;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * UDP Listener der die Nachrichten auf der Konsole ausgiebt
 */
public class UDPListener{
    public static void main(String[] args) {
        System.out.println("Listener gestartet");
        while(true){
            try {
                //Socket konfigurieren
                MulticastSocket socket = new MulticastSocket(4713);
                socket.joinGroup(InetAddress.getByName("228.6.7.8"));

                byte[] buffer = new byte[1000];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                //Nachrichten empfangen
                socket.receive(packet);
                //Nachricht umwandeln
                String message = new String(packet.getData());
                //Konsole ausgabe
                System.out.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}