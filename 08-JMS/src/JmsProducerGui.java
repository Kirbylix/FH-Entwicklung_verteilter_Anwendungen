import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Place JavaDoc information for this class here.
 */
public class JmsProducerGui extends JFrame implements ActionListener 
{


	// Attribute
	private JLabel jLabelUrl;
	private JTextField jTextFieldUrl;
	private ButtonGroup jButtonGroupSendMethode;
	private JRadioButton jRadioButtonPubSub;
	private JRadioButton jRadioButtonP2p;
	private JLabel jLabelSubject;
	private JTextField jTextFieldSubject;
	private JRadioButton jRadioButtonDurable;
	private JLabel jLabelTimeToLive;
	private JTextField jTextFieldTimeToLive;
	private JRadioButton jRadioButtonTransacted;
	public JTextField jTextFieldMessage;
	
	private JButton jButtonRun;
	private JButton jButtonSubmit;
	private JButton jButtonStop;

	private JmsProducer tool;
	
	private ArrayList<JComponent> components;

	/**
	 * Default Constructor. Sets frame preferences and initializes the GUI.
	 */
	public JmsProducerGui() {
		// init contentPane and components
		this.initGUI();
        this.setTitle("JMS Producer");

		// frame properties
		this.setVisible(true);
	}

	/**
	 * Create graphical components and display them.
	 */
	private void initGUI() {
		// components

		components = new ArrayList<JComponent>();
		
		jLabelUrl = new JLabel("Provider-URL:");
		components.add(jLabelUrl);
		jTextFieldUrl = new JTextField("tcp://localhost:61616");
		components.add(jTextFieldUrl);
		jRadioButtonPubSub = new JRadioButton("Publish/Subscribe", true);
		components.add(jRadioButtonPubSub);
		jRadioButtonP2p = new JRadioButton("Peer-to-Peer", false);
		components.add(jRadioButtonP2p);
		
		jButtonGroupSendMethode = new ButtonGroup();
		jButtonGroupSendMethode.add(jRadioButtonPubSub);
		jButtonGroupSendMethode.add(jRadioButtonP2p);
		
		jLabelSubject = new JLabel("Subject:");
		components.add(jLabelSubject);
		jTextFieldSubject = new JTextField("default");
		components.add(jTextFieldSubject);
		jRadioButtonDurable = new JRadioButton("Durable", false);
		components.add(jRadioButtonDurable);
		jLabelTimeToLive = new JLabel("TimeToLive:");
		components.add(jLabelTimeToLive);
		jTextFieldTimeToLive = new JTextField("0");
		components.add(jTextFieldTimeToLive);
		jRadioButtonTransacted = new JRadioButton("Transacted", false);
		components.add(jRadioButtonTransacted);
		jTextFieldMessage = new JTextField("Bond James 007 m false");

		jButtonRun = new JButton("Starten");
		jButtonSubmit = new JButton("Senden");
		jButtonStop = new JButton("Stoppen");
		

		// Properties and methods
		this.setSize(new Dimension(600, 240));

		this.getContentPane().add(jLabelUrl);
		this.getContentPane().add(jTextFieldUrl);
		this.getContentPane().add(jRadioButtonPubSub);
		this.getContentPane().add(jRadioButtonP2p);
		this.getContentPane().add(jLabelSubject);
		this.getContentPane().add(jTextFieldSubject);
		this.getContentPane().add(jRadioButtonDurable);
		this.getContentPane().add(jLabelTimeToLive);
		this.getContentPane().add(jTextFieldTimeToLive);
		this.getContentPane().add(jRadioButtonTransacted);
		this.getContentPane().add(jTextFieldMessage);

		this.getContentPane().add(jButtonRun);
		this.getContentPane().add(jButtonSubmit);
		this.getContentPane().add(jButtonStop);
		
		this.getContentPane().setLayout(null);
		this.getContentPane().setName(null);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(7, 27));
		this.setName("JmsConsumer");
		this.setPreferredSize(new Dimension(600, 240));
		this.setResizable(false);
		
		jLabelUrl.setBounds(new Rectangle(20, 20, 80, 18));
		jTextFieldUrl.setBounds(new Rectangle(110, 20, 180, 18));
		
		jRadioButtonPubSub.setBounds(new Rectangle(20, 40, 200, 20));
		jRadioButtonPubSub.setActionCommand("publish");
		
		jRadioButtonP2p.setBounds(new Rectangle(230, 40, 120, 20));
		jRadioButtonP2p.setActionCommand("p2p");
		
		jLabelSubject.setBounds(new Rectangle(20, 60, 80, 18));
		jTextFieldSubject.setBounds(new Rectangle(110, 60, 140, 18));
		jRadioButtonDurable.setBounds(new Rectangle(20, 80, 200, 20));
		
		jLabelTimeToLive.setBounds(new Rectangle(20, 100, 80, 18));
		jTextFieldTimeToLive.setBounds(new Rectangle(110, 100, 140, 18));
		jRadioButtonTransacted.setBounds(new Rectangle(20, 120, 200, 20));
//		jTextAreaMessage.setBounds(new Rectangle(20, 140, 500, 60));
		jTextFieldMessage.setBounds(new Rectangle(20, 160, 500, 20));
		
		jButtonRun.setBounds(new Rectangle(350, 40, 90, 20));
		jButtonRun.setEnabled(true);
		jButtonStop.setBounds(new Rectangle(350, 80, 90, 20));
		jButtonStop.setEnabled(false);
		jButtonSubmit.setBounds(new Rectangle(350, 120, 90, 20));
		jButtonSubmit.setEnabled(false);
		
/*
		jCheckBox7.setBounds(new Rectangle(140, 130, 20, 20));
		jCheckBox7.setBorder(etchedBorder9);
		jLabel12.setBounds(new Rectangle(20, 50, 120, 10));
		jLabel14.setFont(new Font("Tahoma", 0, 14));
		jLabel14.setBounds(new Rectangle(20, 20, 110, 10));
*/
		// ActionListener
		
		jButtonRun.addActionListener(this);
		jButtonSubmit.addActionListener(this);
		jButtonStop.addActionListener(this);

	}

	/**
	 * The entry point of this class when starting the class directly.
	 *
	 * @param arguments String[] command line arguments
	 */
	public static void main(String[] arguments) 
	{
		new JmsProducerGui();

	}

	
		
	/**
	 * This operation is called when an ActionEvent occurs.
	 *
	 * @param event ActionEvent
	 */
	public void actionPerformed(ActionEvent event) 
	{

		if(event.getSource()==jButtonSubmit)
		{
			tool.sendMessage(jTextFieldMessage.getText());
		}
		else if(event.getSource()==jButtonStop){
			tool.doClose();
			this.setTitle("JMS Producer");
			Iterator<JComponent> iterator = components.iterator();
			while(iterator.hasNext()){
				iterator.next().setEnabled(true);
			}
			jButtonStop.setEnabled(false);
			jButtonRun.setEnabled(true);
			jButtonSubmit.setEnabled(false);
		}
		else if(event.getSource()==jButtonRun)
		{
		    tool = new JmsProducer(this);

		        // Abweichungen von Defaults
		        

		        
//		        tool.durable = true;
//		        tool.maxiumMessages = 10;
//		        tool.clientID = args[5];
//		        tool.transacted = false;
//		        tool.sleepTime = 0L;
//		        tool.receiveTimeOut = 0L;
		        

			tool.url =  jTextFieldUrl.getText();		
		
			if(tool.url.equals(""))
			{
				// Wenn keine URL eingegeben wird, wird ein Default gesetzt
	            tool.url="tcp://localhost:61616";
			}
			System.out.println("Verbindung mit Provider: " + tool.url);
			
			//tool.topic = jRadioButtonPubSub.isSelected();
			tool.topic = jButtonGroupSendMethode.
							getSelection().
							getActionCommand().
							equals("publish") ? true : false;
			
			if(tool.topic == true)
				System.out.println("Kommunikation: Publish/Subscribe");
			else
				System.out.println("Kommunikation: Peer-to-Peer");
				
            tool.subject = jTextFieldSubject.getText();
			System.out.println("Subject: "+tool.subject);
			
            tool.durable = jRadioButtonDurable.isSelected();
            System.out.println("Verwende " + (tool.durable ? "durable" : "non-durable") + " publishing");

            System.out.println("TimeToLive >>>" + jTextFieldTimeToLive.getText() + "<<<");
            try
            {
                tool.timeToLive = Integer.parseInt(jTextFieldTimeToLive.getText());
            }
            catch (Exception e)
            {
            	System.out.println(e);
            	tool.timeToLive = 0;
            }
                if( tool.timeToLive!=0 ) {
                System.out.println("Messages time to live "+tool.timeToLive+" ms");                

            tool.transacted = jRadioButtonTransacted.isSelected();
/*
            System.out.println("Versenden einer Nachricht der Gr��e " + tool.messageSize + " to " 
            		           + (tool.topic ? "topic" : "queue") + ": " + tool.subject);
            System.out.println("Sleeping between publish "+sleepTime+" ms");                
*/
            }
           
	        tool.runTool(tool);
	        this.setTitle("JMS Producer RUNNING");
	        jButtonRun.setEnabled(false);
	        jButtonSubmit.setEnabled(true);
	        jButtonStop.setEnabled(true);
	        Iterator<JComponent> iterator = components.iterator();
			while(iterator.hasNext()){
				iterator.next().setEnabled(false);
			}

		}
	}
		
}
