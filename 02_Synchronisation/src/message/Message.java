package message;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Message implements Serializable{

    private static int id = 0;
    private int refID;
    private MessageType messageType;
    private InfoType infoType;
    private Date timestamp;
    private int senderId;
    private int receiverId;
    private Map<InfoType, Object> infos;

    public Message(MessageType messageType,InfoType infoType, Date timestamp, int senderId, int receiverId){
        id++;
        this.messageType = messageType;
        this.infoType = infoType;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public static int getId() {
        return id;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public InfoType getInfoType() {
        return infoType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void addInfo(InfoType infoType, Object info){
        this.infos.put(infoType, info);
    }

    public Object getInfo(InfoType infoType){
        return this.infos.get(infoType);
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", InfoType='" + infoType + '\'' +
                ", timestamp=" + timestamp +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                '}';
    }
}