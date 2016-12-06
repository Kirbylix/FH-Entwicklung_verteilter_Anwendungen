package disturber;

import java.net.DatagramSocket;
import java.net.DatagramPacket;

import java.util.Random;
import java.net.SocketException;

import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.IOException;

import common.MessageDTO;


/*
import java.awt.*;

import java.io.ByteArrayOutputStream;

import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;

*/

public class DisturberServer
{

    /**
	 * Konfiguration
	 */

	private static final long serialVersionUID = 1L;
 

    public static void main(String[] args)
    {


    	Random r = new Random();

        DisturberServer derDisturber = new DisturberServer();
        DatagramSocket socket = null;
        

        try
        {
			socket = new DatagramSocket(4445);
		} 
        catch (SocketException e)
		{
			e.printStackTrace();
		}

        try
        {
			socket.setReceiveBufferSize(2096304);
		} 
        catch (SocketException e2) 
		{
			e2.printStackTrace();
		}
        
        System.out.println("Disturber empfangsbereit");
    	while (true)
    	{
            byte[] buf = new byte[256];

            // receive request
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            try 
            {
					socket.receive(packet);
			}
            catch (Exception e)
			{
				e.printStackTrace();
			}


            /**
              *   Deserialisierung der Nachricht
             */
             
                ByteArrayInputStream byteIn = new ByteArrayInputStream(buf);
                ObjectInput objectIn = null;
				try {
					objectIn = new ObjectInputStream(byteIn);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

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



    			// Jetzt kommt der Disturber, ein Thread für jede Nachricht!
    			DisturbWorker myWorker = new DisturbWorker(msg);
    			myWorker.start();
    			
            }
    }
} 


