package client;

import java.awt.*;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.TreeMap;

import javax.swing.*;

import common.MessageDTO;


public class LauflichtGUI extends JFrame implements Runnable
{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
    private Thread th;
    private LauflichtModell model;
    
    
    protected int dx = 30;
    protected int dy = 30;
    private final MyPanel myPanel;

    private long anzEmpfangen = 0;





    public LauflichtGUI(LauflichtModell model)
    {
        super("Lauflicht");

        this.model = model;
        
        setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);
        myPanel = new MyPanel(model);
        add(myPanel);
        startAnimation();


    }

    public void startAnimation() {
        th = new Thread(this);
        th.start();
    }

    public void refresh()
    {
    	System.out.println("Refresh");
   	     myPanel.repaint(0,0,1400,1400);
    }

    public void run()
    {

    	long TSlastRecv = 0;
    	

 

        //myPanel.repaint(20,20,500,500);

        
    }    


class MyPanel extends JPanel
{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final LauflichtModell meinLauflichtmodell;

    MyPanel(LauflichtModell meinLauflichtmodell)
    {
        this.meinLauflichtmodell = meinLauflichtmodell;

    }

    @Override
    protected void paintComponent(final Graphics g)
    {
        super.paintComponent(g);

        g.setColor(Color.black);
        for(int i=0; i<meinLauflichtmodell.getLauflichtLength(); i++)
        {
        	boolean b = meinLauflichtmodell.getLauflicht(i);

        	if(b==true)
        	{
                 g.fillRect(30+30*i, 30, 30, 30);
        	}
        }
    }

}}


/*
 * 

class LauflichtUpdateThread extends Thread
{

	public void run()
	{
    	if(meineMap.size() > 50)
    	{
    	    MessageDTO myEntry = (MessageDTO) meineMap.firstEntry().getValue();
    	    
    	meineMap.remove(myEntry.nanoTime);
            meinLauflichtModell.setLauflicht(myEntry.i,myEntry.b);
 //         meinLauflichtModell.setLauflicht(msg.i,msg.b);
    	 
         	myPanel.repaint(0,0,1400,1400);
    }}
	}
}




};

th.start();

 */
