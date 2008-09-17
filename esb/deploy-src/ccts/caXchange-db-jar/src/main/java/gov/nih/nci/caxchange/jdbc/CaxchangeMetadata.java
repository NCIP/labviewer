package gov.nih.nci.caxchange.jdbc;
/**
 * This class contains the getters and setters methods for
 * caxchange message
 * @author hmarwaha
 *
 */
public class CaxchangeMetadata {
    String messageType;
    String payloadNamespace;
    /**
     * Default constructor
     */
    public CaxchangeMetadata() {
    }

    /**
     * This method sets the message type
	 * @param messageType
	 * @return
	 * @throws
	 */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * This method sets the message type
	 * @param
	 * @return messageType
	 * @throws
	 */
    public String getMessageType() {
        return messageType;
    }

    /**
     * This method sets the payloadUri
	 * @param payloadUri
	 * @return
	 * @throws
	 */
    public void setPayloadNamespace(String payloadNamespace) {
        this.payloadNamespace = payloadNamespace;
    }

    /**
     * This method get the payloadUri
	 * @param
	 * @return payloadUri
	 * @throws
	 */
    public String getPayloadNamespace() {
        return payloadNamespace;
    }
    /**Returns a string representation of the object.
     *@param
     *@return  a string representation of the object.
     *@throws
     */
    public String toString() {
        return " message type:"+messageType+" payload Namespace:"+payloadNamespace;
    }
}
