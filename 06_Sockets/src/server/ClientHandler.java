package server;

import client.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 *
 */
public class ClientHandler  extends Thread {
    private Socket socket;

    public ClientHandler(Socket aSocket) {
        this.socket = aSocket;
        this.start();
    }

    public void run() {
        try {
            //TCP:
            //Message Lesen vom Client
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String dieNachricht = (String) ois.readObject();
            System.out.println("TCP Message Erhalten");

            StreckeStub meinStub = new StreckeStub();

            //Client Antworten
            String dieAntwort = meinStub.sendReceiveMsg(dieNachricht);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(dieAntwort);
            System.out.println("TCP Message gesendet");

            //UDP
            //Nachricht an UDPListener für die Ausgabe senden
            InetAddress group = InetAddress.getByName("228.6.7.8");
            int port = 4713;
            //Nachricht verpacken
            byte[] buffer = dieAntwort.getBytes();
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length, group, port);
            //UDP Verbindung aufbauen & senden
            MulticastSocket ms = new MulticastSocket(port);
            ms.joinGroup(group);
            ms.send(dp);
            System.out.println("UDP Message gesendet");

            ois.close();
            oos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

