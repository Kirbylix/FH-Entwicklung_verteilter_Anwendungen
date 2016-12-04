
import javax.jms.*;
import java.util.Date;


public class JmsProducer extends JmsUtility

{
    // Anwendungsspezifische Gr��en

    protected int messageSize = 255;
    protected long sleepTime = 0L;
    protected boolean verbose = true;
    protected long messageID = 0;


    // Attribute aus dem Producer-Interface

    long timeToLive = 0;


    // JMS-Objekte 
    private Connection connection;
    private Session session;
    private MessageProducer producer;


    // Referenz auf die Oberfl�che, damit dort etwas eingetragen werden kann

    private JmsProducerGui dieGui;


    public JmsProducer(JmsProducerGui dieGui) {
        this.dieGui = dieGui;
    }


    protected void runTool(JmsProducer tool) {
 
 /*   	
        // Die Attribute sind in ToolSupport definiert.

//    	tool.clientID = null;
        this.url="tcp://172.22.144.157:61616";
        this.url="tcp://localhost:61616";
        this.durable = true;
        this.topic = true;  // true=Pub/Sub, false=P2P
        this.subject = "default";
//      tool.messageCount = 10;
//      tool.messageSize = 255;
//      tool.clientID = args[6];
//      tool.timeToLive = 0L;
//      tool.sleepTime = 10L;
//      tool.transacted = true";
*/
        tool.run();
    }

    public void run() {
        try {
            connection = createConnection();
            session = createSession(connection);
            producer = createProducer(session);

            System.out.println("Bereit");
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public void doClose() {
        try {
            close(connection, session);
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    protected MessageProducer createProducer(Session session) throws JMSException {
        MessageProducer producer = session.createProducer(destination);
        if (durable) {
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        } else {
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        }
        if (timeToLive != 0)
            producer.setTimeToLive(timeToLive);
        return producer;
    }

    protected void sendMessage(String messageContent) {
        String[] parts = messageContent.split(" ");

        MessageObject msg = new MessageObject();
        msg.setName(parts[0]);
        msg.setVorname(parts[1]);
        msg.setMatrikelNr(Integer.parseInt(parts[2]));
        msg.setGeschlecht(parts[3].charAt(0));
        if("true".equals(parts[4])){
            msg.setEingeschrieben(true);
        }else if("false".equals(parts[4])){
            msg.setEingeschrieben(false);
        }
        try {
            ObjectMessage objectMessage = session.createObjectMessage(msg);
            objectMessage.setJMSReplyTo(producer.getDestination());
            objectMessage.setJMSType("ObjectMessage");
            objectMessage.setJMSCorrelationID(messageID + "");
            producer.send(objectMessage);
            messageID++;
            if (transacted) {
                session.commit();
                Thread.sleep(sleepTime);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
/*
        String strMessage;
        StringBuffer buffer = new StringBuffer(messageSize);
        String messageHeader = "\n\nNT: " + messageID + " dt=" + new Date() + "\n";
        buffer.append(messageHeader);
        messageID++;

        if (messageContent.equals("")
                || messageContent.compareToIgnoreCase("quit") == 0
                || messageContent.compareToIgnoreCase("exit") == 0) {
        } else

            buffer.append(messageContent);

        if (buffer.length() > messageSize) {
            strMessage = buffer.substring(0, messageSize);
        }
        for (int i = buffer.length(); i < messageSize; i++) {
            buffer.append(' ');
        }
        strMessage = buffer.toString();


        if (strMessage != null) {
            try {
                TextMessage message = session.createTextMessage(strMessage);
                producer.send(message);
                if (transacted) {
                    session.commit();
                    Thread.sleep(sleepTime);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }*/
    }


}
