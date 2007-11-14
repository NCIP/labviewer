package gov.nih.nci.caxchange.jdbc;

public class CaxchangeMessage {
    String messageId;
    String message;
    
    public CaxchangeMessage() {
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
    public String toString() {
        return " messageId:"+messageId+" message:"+message;
    }
}
