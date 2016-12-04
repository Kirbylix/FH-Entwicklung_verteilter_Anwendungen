import javax.jms.*;

import java.io.IOException;



public class JmsConsumer extends JmsUtility implements MessageListener, ExceptionListener 
{
    protected int count = 0;
    protected int dumpCount = 10;
    protected boolean verbose = true;
    protected int maxiumMessages = 0;
    private boolean pauseBeforeShutdown;
    private boolean bRunning;
    private Session session;
    private Connection connection;
    private MessageConsumer consumer;
    private long sleepTime=0;
    private long receiveTimeOut=0;

    private JmsConsumerGui dieGui;
    
    public JmsConsumer(JmsConsumerGui dieGui)
    {
    	this.dieGui = dieGui;
    }
    public void run() 
    {
        try 
        {
            bRunning = true;
            
            System.out.println("Verbindung zu URL: " + url);
            System.out.println("Kommunikation " + (topic ? "PubSub" : "P2P") + ": " + subject);
            System.out.println("Verwende " + (durable ? "dauerhafter" : "nicht-dauerhafter") + " Bezug");

            connection = createConnection();
            connection.setExceptionListener(this);
            session = createSession(connection);
            //MessageConsumer consumer = null;
            
            if (durable && topic) 
            {
                consumer = session.createDurableSubscriber((Topic) destination, consumerName);
            }
            else {
                consumer = session.createConsumer(destination);
            }
            if ( maxiumMessages > 0 ) {
                consumeMessagesAndClose(connection, session, consumer);
            } else  {
                if(receiveTimeOut==0) {
                    consumer.setMessageListener(this);
                } else {
                    consumeMessagesAndClose(connection, session, consumer, receiveTimeOut);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public void close(){
    	try {
			consumeMessagesAndClose(connection, session, consumer);
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) message;
                if (verbose) {

                    String msg = txtMsg.getText();
                    if (msg.length() > 250) {
                        msg = msg.substring(0, 250) + "...";
                    }

                    System.out.println("Received: " + msg);

                    dieGui.jTextAreaMessage.append(msg);
                    dieGui.jTextAreaMessage.setCaretPosition(dieGui.jTextAreaMessage.getDocument().getLength());
                    dieGui.repaint();
                }
            }else if(message instanceof ObjectMessage){
                ObjectMessage nachricht = (ObjectMessage) message;
                MessageObject msg = (MessageObject) nachricht.getObject();
                if (verbose) {
                    readProperties(message);
                    System.out.println("Received: " + msg.toString());

                    dieGui.jTextAreaMessage.append(msg.toString() + "\n");
                    dieGui.jTextAreaMessage.setCaretPosition(dieGui.jTextAreaMessage.getDocument().getLength());
                    dieGui.repaint();
                }
            }
            else {
                if (verbose) {
                    System.out.println("Received: " + message);
                }
            }
            if(transacted) {
                session.commit();
            }
            /*
            if (++count % dumpCount == 0) {
                dumpStats(connection);
            }
            */
        }
        catch (JMSException e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        } finally {
            if( sleepTime> 0 ) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    synchronized public void onException(JMSException ex) 
    {
        System.out.println("JMS Exception occured.  Shutting down client.");
        bRunning = false;
    }

    synchronized boolean isRunning() 
    {
        return bRunning;
    }
    
    protected void consumeMessagesAndClose(Connection connection, Session session, MessageConsumer consumer) throws JMSException, IOException {
        System.out.println("We are about to wait until we consume: " + maxiumMessages + " message(s) then we will shutdown");

        for (int i = 0; i < maxiumMessages && isRunning(); ) {
            Message message = consumer.receive(1000);
            if( message!=null ) {
                i++;
                onMessage(message);
            }
        }
        System.out.println("Closing connection");
        consumer.close();
        session.close();
        connection.close();
        if (pauseBeforeShutdown) {
            System.out.println("Press return to shut down");
            System.in.read();
        }
    }
    
    protected void consumeMessagesAndClose(Connection connection, Session session, MessageConsumer consumer, long timeout) throws JMSException, IOException {
        System.out.println("We will consume messages while they continue to" +
        		           " be delivered within: " + timeout + " ms, and then we will shutdown");

        Message message;
        while ( (message = consumer.receive(timeout)) != null ) {
            onMessage(message);
        }
        
        System.out.println("Closing connection");
        consumer.close();
        session.close();
        connection.close();
        if (pauseBeforeShutdown) {
            System.out.println("Press return to shut down");
            System.in.read();
        }
    }

    public void readProperties(Message message){
        try {
            System.out.println("JMSDestination: " + message.getJMSDestination());
            System.out.println("JMSReplyTo: " + message.getJMSReplyTo());
            System.out.println("JMSType: " + message.getJMSType());
            System.out.println("JMSDeliveryMode: " + message.getJMSDeliveryMode());
            System.out.println("JMSPriority: " + message.getJMSPriority());
            System.out.println("JMSMessageID: " + message.getJMSMessageID());
            System.out.println("JMSTimestamp: " + message.getJMSTimestamp());
            System.out.println("JMSCorrelationID: " + message.getJMSCorrelationID());
            System.out.println("JMSExpiration: " + message.getJMSExpiration());
            System.out.println("JMSRedelivered: " + message.getJMSRedelivered());

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}

