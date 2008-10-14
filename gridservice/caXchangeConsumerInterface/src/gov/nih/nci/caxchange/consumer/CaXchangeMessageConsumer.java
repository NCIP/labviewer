package gov.nih.nci.caxchange.consumer;

import org.w3c.dom.Node;

public interface CaXchangeMessageConsumer {
	
	public Node process(Node payload) throws CaXchangeConsumerException;
	public void rollback(Node payload) throws CaXchangeConsumerException;
	public void commit(Node payload) throws CaXchangeConsumerException;	

}
