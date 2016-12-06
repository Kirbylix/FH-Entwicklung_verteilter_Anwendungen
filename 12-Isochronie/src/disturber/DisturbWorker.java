package disturber;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

import common.MessageDTO;
import common.Properties;




 

public class DisturbWorker extends Thread
{



	    
	MessageDTO theMsg = null;
	
	
	public DisturbWorker(MessageDTO msg)
	{

		theMsg = msg;
		
	}
	
	

	public void run()
	{
		
        
	// Her kommt der Disturber
	
	// Generator für Zufallszahlen
        System.out.println(theMsg.sequential + " angenomen");

        Random r = new Random();

   	/*
   	 *  Paketverlust
   	 */
   	
   	int i = r.nextInt(100);
   	
   	if(i <= common.Properties.NETZWERK_PAKETVERLUSTRATE) 
   	{
 
   		System.out.println(theMsg.sequential + " verloren");
   		return; //Diese Nachrichten gehen verloren
   	
   	}
   	/**
   	 *  Delay und Jitter
   	 */
   	
   	int jitter = r.nextInt(common.Properties.NETZWERK_JITTER);

   	try 
   	{
		Thread.sleep(jitter+common.Properties.NETZWERK_DELAY);
	} 
   	catch (InterruptedException e)
   	{
		e.printStackTrace();
	}
   	
   	
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
     *    Nachricht für den Versand serialisieren 
     */

  
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();; 
    ObjectOutput objectOut;
	try 
	{
		objectOut = new ObjectOutputStream(byteOut);
		objectOut.writeObject(theMsg);
		objectOut.flush();
	} 
	catch (IOException e1) 
	{
		e1.printStackTrace();
	}

        
        
    buf = byteOut.toByteArray();

    DatagramPacket packet = new DatagramPacket(buf, buf.length, address, common.Properties.PORT_GUI);
    
    try
    {
        socket.send(packet);
        System.out.println(theMsg.sequential + " weitergeleitet");
	} 
    catch (IOException e1)
    {
	    e1.printStackTrace();
	} 

    socket.close();
    
    	}
}
