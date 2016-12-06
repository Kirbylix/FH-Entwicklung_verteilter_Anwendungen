package client;

import javax.swing.*;


public class LauflichtGUIStart
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LauflichtModell model;
   

    public static void main(String[] args)
    {
    	final LauflichtGUIStart meinStarter = new LauflichtGUIStart();
    	meinStarter.model = new LauflichtModell(30);

    	LauflichtGUI gui = new LauflichtGUI(meinStarter.model);
    	gui.setVisible(true);
        SwingUtilities.invokeLater(gui);
    	MsgListener listener = new MsgListener(meinStarter.model, (LauflichtGUI)gui);
    	listener.start();

    }
}
       

