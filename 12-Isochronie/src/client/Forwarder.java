package client;


import common.MessageDTO;

public abstract class Forwarder 
{

	protected  LauflichtGUI gui = null;
	protected  LauflichtModell model = null;
	
	
	public Forwarder(LauflichtGUI gui, LauflichtModell model)
	{
		this.gui =gui;
	    this.model = model;
	}
	

	/**
	 * 
         model.setLauflicht(msg.i,msg.b);
         LauflichtGUI lf = (LauflichtGUI)this.gui;
     	 lf.refresh();

	 */
	public abstract void executeMsg(MessageDTO msg);
}

