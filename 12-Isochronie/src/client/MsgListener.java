package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.TreeMap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;


import common.MessageDTO;

public class MsgListener extends Thread
{

	private LauflichtModell model;
	private Runnable gui;

	private Forwarder forwarder;
	
	
    protected DatagramSocket socket = null;

	TreeMap meineMap = new TreeMap();

	/**
	 * 
	 * @param model
	 * @param gui2
	 */
	
	public MsgListener(LauflichtModell model, LauflichtGUI gui)
	{
		this.gui = gui;
		this.model = model;
		//this. forwarder = new SimpleForwarder(gui, model);
		this. forwarder = new JitterBuffer(gui, model);
	}


	public void run()
	{
	    System.out.println("Listener gestartet!");
		
	    try
        {
		    socket = new DatagramSocket(common.Properties.PORT_GUI);
	    } 
        catch (SocketException e)
	    {
		    e.printStackTrace();
	    }
        
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



            /**
             *   Deserialisierung der Nachricht
             */
            //

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


            Object o = null;
			try
			{
				o = objectIn.readObject();
			} 
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			MessageDTO msg = (MessageDTO) o;


			System.out.println("recv Id:" + msg.sequential + " TS:" +  msg.nanoTime + 
					
					            "delta:" + (System.nanoTime()- msg.nanoTime) +"/"+  msg.i + " " +msg.b);

			System.out.println("recv Id:" + msg.sequential + " TS:" +  msg.nanoTime ); 
			System.out.println("network delay:" + (System.nanoTime()- msg.nanoTime)/1000000 +"[ms]");
 //           System.out.println("stepTime:" + (msg.nanoTime - TSlastRecv));
 //           TSlastRecv = msg.nanoTime;

	
			forwarder.executeMsg(msg);
		    
        }
    }    
}


	/**
	public void run()
	{
        

        	
        	while(true)
            {
        		long sleepTime = 0;
        		System.out.println("MapSize: "+meineMap.size());
        		if(!meineMap.isEmpty())
        		{
        		
        		    MessageDTO myEntry = (MessageDTO) meineMap.firstEntry().getValue();
        		    meineMap.remove(myEntry.nanoTime);
        		    long delta = (System.nanoTime()- myEntry.nanoTime)/1000000;;
        		    System.out.println("msg" +myEntry.sequential + ", d="+delta);
        		    System.out.println(common.Properties.DELAY_BUFFER);
        		   sleepTime = delta + common.Properties.DELAY_BUFFER;
        		   System.out.println("sleeptime " +sleepTime);
     	           
     	          if(sleepTime>0)
     	           {
     	        	  try 
     	              {
					       Thread.sleep(sleepTime);
				      } 
     	              catch (InterruptedException e) 
     	               {
                           e.printStackTrace();
				    }
     	   
     	            meinLauflichtModell.setLauflicht(myEntry.i,myEntry.b);
     	            
     	       	    myPanel.repaint(0,0,1400,1400);
     	        }
        		}
     	        try 
                {
                  //  Thread.sleep(10);
                } 
     	        catch (Exception e) 
     	        {
                    e.printStackTrace();
                }
            }

        }
    }; // Ende Thread
    }
    **/