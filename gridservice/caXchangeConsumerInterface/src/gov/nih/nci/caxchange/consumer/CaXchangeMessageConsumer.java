package gov.nih.nci.caxchange.consumer;

import org.w3c.dom.Node;
/**
 * The caXchange message consumers should implement this interface. The implementations 
 * of this interface are invoked from the caXchange consumer service.
 * 
 * 
 * @author Ekagra Software Technologies
 *
 */
public interface CaXchangeMessageConsumer {
	/**
	 * Processes the caXchange payload. The implementation should perform the business logic 
	 * for the target service for the given payload.
	 * 
	 * @param payload
	 * @return
	 * @throws CaXchangeConsumerException
	 */
	public Node process(Node payload) throws CaXchangeConsumerException;
	/**
	 * Performs the rollback logic for the target service for the given payload.
	 *  
	 * @param payload
	 * @throws CaXchangeConsumerException
	 */
	public void rollback(Node payload) throws CaXchangeConsumerException;
	
	
	public void commit(Node payload) throws CaXchangeConsumerException;	

}
