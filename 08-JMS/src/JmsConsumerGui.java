import java.awt.*;
import javax.swing.*;


import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Place JavaDoc information for this class here.
 */
public class JmsConsumerGui extends JFrame implements ActionListener 
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
	private JLabel jLabelClientId;
	private JTextField jTextFieldClientId;
	private JRadioButton jRadioButtonTransacted;
	
	public JScrollPane jScrollPaneMessage;
	public JTextArea jTextAreaMessage;
	
	private JButton jButtonRun;
	private JButton jButtonStop;
	
	private ArrayList<JComponent> components;
	
	private JmsConsumer tool;

	

	/**
	 * Default Constructor. Sets frame preferences and initializes the GUI.
	 */
	public JmsConsumerGui() {
		// init contentPane and components
		this.initGUI();
        this.setTitle("JMS Consumer");
		// frame properties
		this.setVisible(true);
	}

	/**
	 * Create graphical components and display them.
	 */
	private void initGUI() {
		// components

		components = new ArrayList<JComponent>();
		
		jLabelUrl = new JLabel("URL");
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
		
		jLabelSubject = new JLabel("Subject");
		components.add(jLabelSubject);
		jTextFieldSubject = new JTextField("default");
		components.add(jTextFieldSubject);
		jRadioButtonDurable = new JRadioButton("Durable", false);
		components.add(jRadioButtonDurable);
		jLabelClientId = new JLabel("ClientId");
		components.add(jLabelClientId);
		jTextFieldClientId = new JTextField("");
		components.add(jTextFieldClientId);
		jRadioButtonTransacted = new JRadioButton("Transacted", false);
		components.add(jRadioButtonTransacted);
		
		jTextAreaMessage = new JTextArea(4,20);
		//components.add(jTextAreaMessage);

		jTextAreaMessage.setTabSize(4);
		jTextAreaMessage.setLineWrap(true);
		jTextAreaMessage.setWrapStyleWord(true);
		jScrollPaneMessage = new JScrollPane(jTextAreaMessage);


		jButtonRun = new JButton("Starten");
		components.add(jButtonRun);
		jButtonStop = new JButton("Stoppen");
		jButtonStop.setEnabled(false);
		

		// Properties and methods
		this.setSize(new Dimension(600, 240));

		this.getContentPane().add(jLabelUrl);
		this.getContentPane().add(jTextFieldUrl);
		this.getContentPane().add(jRadioButtonPubSub);
		this.getContentPane().add(jRadioButtonP2p);
		this.getContentPane().add(jLabelSubject);
		this.getContentPane().add(jTextFieldSubject);
		this.getContentPane().add(jRadioButtonDurable);
		this.getContentPane().add(jLabelClientId);
		this.getContentPane().add(jTextFieldClientId);
		this.getContentPane().add(jRadioButtonTransacted);
		//this.getContentPane().add(jTextAreaMessage);
		this.getContentPane().add(jScrollPaneMessage);

		this.getContentPane().add(jButtonRun);
		this.getContentPane().add(jButtonStop);

		this.getContentPane().setLayout(null);
		this.getContentPane().setName(null);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(7, 27));
		this.setName("JmsConsumer");
		this.setPreferredSize(new Dimension(600, 240));
		this.setResizable(false);
		
		jLabelUrl.setBounds(new Rectangle(20, 20, 40, 18));
		jTextFieldUrl.setBounds(new Rectangle(80, 20, 180, 18));
		//jRadioButtonPubSub.setBounds(new Rectangle(20, 40, 200, 20));
		
		jRadioButtonPubSub.setBounds(new Rectangle(20, 40, 200, 20));
		jRadioButtonPubSub.setActionCommand("publish");
		
		jRadioButtonP2p.setBounds(new Rectangle(230, 40, 120, 20));
		jRadioButtonP2p.setActionCommand("p2p");
		
		jLabelSubject.setBounds(new Rectangle(20, 60, 80, 18));
		jTextFieldSubject.setBounds(new Rectangle(80, 60, 140, 18));
		jRadioButtonDurable.setBounds(new Rectangle(20, 80, 200, 20));
		jLabelClientId.setBounds(new Rectangle(20, 100, 80, 18));
		jTextFieldClientId.setBounds(new Rectangle(80, 100, 140, 18));
		jRadioButtonTransacted.setBounds(new Rectangle(20, 120, 200, 20));
//		jTextAreaMessage.setBounds(new Rectangle(20, 140, 500, 60));
		jScrollPaneMessage.setBounds(new Rectangle(20, 140, 500, 60));
		
		jButtonRun.setBounds(new Rectangle(350, 40, 90, 20));
		jButtonStop.setBounds(new Rectangle(350, 80, 90, 20));
/*
		jCheckBox7.setBounds(new Rectangle(140, 130, 20, 20));
		jCheckBox7.setBorder(etchedBorder9);
		jLabel12.setBounds(new Rectangle(20, 50, 120, 10));
		jLabel14.setFont(new Font("Tahoma", 0, 14));
		jLabel14.setBounds(new Rectangle(20, 20, 110, 10));
*/
		// ActionListener
		
		jButtonRun.addActionListener(this);
		jButtonStop.addActionListener(this);

	}

	/**
	 * The entry point of this class when starting the class directly.
	 *
	 * @param arguments String[] command line arguments
	 */
	public static void main(String[] arguments) 
	{
		new JmsConsumerGui();

	}

	
		
	/**
	 * This operation is called when an ActionEvent occurs.
	 *
	 * @param event ActionEvent
	 */
	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == this.jButtonRun){
			this.tool = new JmsConsumer(this);
	
			        // Abweichungen von Defaults
			        
	//		        tool.url="tcp://172.22.144.157:61616";
	
			        
			          tool.topic = true;  // true=Pub/Sub, false=P2P
			          tool.subject = "default";
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
				System.out.println("Verbindung zu URL: " + tool.url);
				
				tool.topic = jRadioButtonPubSub.isSelected();
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
	
	            tool.clientID = jTextFieldClientId.getText();
	
	            tool.transacted = jRadioButtonTransacted.isSelected();
	
				tool.run();
				
				this.setTitle("JMS Consumer RUNNING");
				this.jButtonStop.setEnabled(true);
				Iterator<JComponent> iterator = components.iterator();
				while(iterator.hasNext()){
					iterator.next().setEnabled(false);
				}
				
		}
		else if (event.getSource() == this.jButtonStop){
			System.out.println("Stoppe.");
			tool.close();
			this.setTitle("JMS Consumer");
			jButtonStop.setEnabled(false);
			Iterator<JComponent> iterator = components.iterator();
			while(iterator.hasNext()){
				iterator.next().setEnabled(true);
			}
		}

	
	}
		
}
