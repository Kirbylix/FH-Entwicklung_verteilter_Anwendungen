package message;

import java.util.Date;

public class MessageManagement {

    public static void send(MessageType messageType, InfoType infoType, Date timestamp, int senderId, int receiverId) {
        Message messageObj = new Message(messageType, infoType, timestamp, senderId, receiverId);
        System.out.println("Sende: \n" + messageObj.toString());
    }

    public static void send(Message message){
        System.out.println("Sende: \n" + message.toString());
    }

    public static Message receive() {
        Message message = null; //JMS-message empfangen
        return message;
    }
}
