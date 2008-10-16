package gov.nih.nci.caxchange.consumer;

import java.rmi.RemoteException;
/**
 * An Exception condition consuming a caXchange request.
 * 
 * 
 * @author Ekagra Software Technologies
 *
 */
public class CaXchangeConsumerException extends RemoteException {

	public CaXchangeConsumerException() {
		// TODO Auto-generated constructor stub
	}

	public CaXchangeConsumerException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	public CaXchangeConsumerException(String s, Throwable cause) {
		super(s, cause);
		// TODO Auto-generated constructor stub
	}

}
