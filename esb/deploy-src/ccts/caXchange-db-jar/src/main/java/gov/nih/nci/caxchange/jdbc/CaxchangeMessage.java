package gov.nih.nci.caxchange.jdbc;
/**
 * This class contains the getters and setters methods for 
 * caxchange message
 * @author hmarwaha
 *
 */
public class CaxchangeMessage {
    String messageId;
    String message;
    /**
     * Default constructor
     */
    public CaxchangeMessage() {
    }
    
    /**
     * This method sets the message id
	 * @param messageId
	 * @return
	 * @throws
	 */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    /**
     * This method sets the message id
	 * @param 
	 * @return messageId
	 * @throws
	 */
    public String getMessageId() {
        return messageId;
    }

    /**
     * This method sets the message
	 * @param message
	 * @return
	 * @throws
	 */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * This method get the message
	 * @param 
	 * @return message
	 * @throws
	 */
    public String getMessage() {
        return message;
    }
    /**Returns a string representation of the object.
     *@param
     *@return  a string representation of the object.
     *@throws
     */
    public String toString() {
        return " messageId:"+messageId+" message:"+message;
    }
}
