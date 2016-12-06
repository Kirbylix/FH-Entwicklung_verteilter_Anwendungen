package server;

import java.io.*;
import java.net.*;
import java.util.*;

import common.MessageDTO;




public class LauflichtGeneratorServer
{

    long anzWiederholungen = 1000;  // Laufzeit des Lauflichtes
    long anzLEDs = 30;
	long sequential = 0;
	
    public static void main(String[] args) throws IOException 
    {

        // Datagram-Socket

        LauflichtGeneratorServer myServer = new LauflichtGeneratorServer();
        
        int offset = 0;
        
        for(int i=0; i<myServer.anzWiederholungen;i++) // Anzahl Durchläufe
        {
        	// Nach jedem Durchlauf wird etwas gewartet.
        	try 
			{
				Thread.sleep(5);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			for(int led=0; led < myServer.anzLEDs; led++)
        	{
        		try 
        		{
					Thread.sleep(common.Properties.LED_TIME_OFFSET);
				} 
        		catch (InterruptedException e)
        		{
					e.printStackTrace();
				}
        		
        		myServer.sendMsg(led, led%3==offset);
        		}
        offset=(offset+1)%3;
        System.out.println(" " + offset); 
    	}
    }
    
    public void sendMsg(int led, boolean state)
    {
    	// Generator für Zufallszahlen
       	Random r = new Random();
       	DatagramSocket socket = null;
        InetAddress address = null;
        
		try
		{
			socket = new DatagramSocket();
			address = InetAddress.getByName("localhost");
		} 
		catch (SocketException e1) 
		{
			e1.printStackTrace();
		} 
        catch (UnknownHostException e1) 
		{
			e1.printStackTrace();
		}

        byte[] buf = new byte[256];  // Buffer für den Sende-Request

    	/**
         *    Nachricht erzeugen 
         */
        	
        MessageDTO myMessage = new MessageDTO();
        myMessage.nanoTime = System.nanoTime();   // aktuelle Zeit
        myMessage.sequential = sequential++;      // Sequenznummer
        myMessage.i = led;                        // LED
        myMessage.b = state;                      // on|off
        	
        /**
         *    Nachricht für den Versand serialisieren 
         */
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();; 
        ObjectOutput objectOut;
		try 
		{
			objectOut = new ObjectOutputStream(byteOut);
			objectOut.writeObject(myMessage);
			objectOut.flush();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}

            
            
        buf = byteOut.toByteArray();

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        
        try
        {
            socket.send(packet);
		} 
        catch (IOException e1)
        {
		    e1.printStackTrace();
		} 


        /**
         *     De-Serialisierung
         */
        
        ByteArrayInputStream byteIn = new ByteArrayInputStream(buf);
        ObjectInput objectIn = null;
		try 
		{
			objectIn = new ObjectInputStream(byteIn);
		} 
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
          

//        System.out.println("Deserialized Objects:");
            
        Object o = null;
		try
		{
		    o = objectIn.readObject();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		MessageDTO msg = (MessageDTO) o;
		System.out.println("Send:" +msg.nanoTime + " " + msg.sequential + " " + msg.i + " " +msg.b);
				    
    }
    // Schließen der Socket-Verbindung
    //        socket.close();
            
  } 