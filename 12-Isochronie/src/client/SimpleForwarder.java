package client;

import common.MessageDTO;

public class SimpleForwarder extends Forwarder 
{
	
	
	public SimpleForwarder(LauflichtGUI gui, LauflichtModell model)
	{
		super(gui, model);
	}
	
	
	public void executeMsg(MessageDTO msg)
	{
         model.setLauflicht(msg.i,msg.b);
//     	 ((LauflichtGUI)gui).refresh();
         LauflichtGUI lf = (LauflichtGUI)this.gui;
     	 lf.refresh();
	}
}

