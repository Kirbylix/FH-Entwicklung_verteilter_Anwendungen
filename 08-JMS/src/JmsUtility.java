

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.util.IndentPrinter;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Abstrakte Basisklasse
 *
 * @version $Revision: 0.9 $
 */

public class JmsUtility {


	// Diese Werte hier nicht ver�ndern. Sie dienen nur als Default-Werte
	
    protected Destination destination;
    protected String subject = "default";
    protected boolean topic = false;
    protected String user = ActiveMQConnection.DEFAULT_USER;
    protected String pwd = ActiveMQConnection.DEFAULT_PASSWORD;
    protected String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    protected boolean transacted = false;
    protected boolean durable = false;
    protected String clientID;
    protected int ackMode = Session.AUTO_ACKNOWLEDGE;
    protected String consumerName = "TOOL.USER";


    protected Session createSession(Connection connection) throws Exception
    {
        Session session = connection.createSession(transacted, ackMode);
        if (topic)
        {
            destination = session.createTopic(subject);
        }
        else {
            destination = session.createQueue(subject);
        }
        return session;
    }

    protected Connection createConnection() throws JMSException, Exception 
    {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, pwd, url);
        Connection connection = connectionFactory.createConnection();
        if (durable && clientID!=null)
        {
            connection.setClientID(clientID);
        }
        connection.start();
        return connection;
    }

    protected void close(Connection connection, Session session) throws JMSException {
        // lets dump the stats
        dumpStats(connection);

        if (session != null) 
        {
            session.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    protected void dumpStats(Connection connection) 
    {
        ActiveMQConnection c = (ActiveMQConnection) connection;
        c.getConnectionStats().dump(new IndentPrinter());
    }
}
