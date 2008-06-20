package gov.nih.nci.caxchange.eip;

import gov.nih.nci.caxchange.servicemix.ConfigurationException;

/**
 * An exception thrown reading and verifying the EIP service unit configuration
 * file.
 * 
 * @author Harsh Marwaha
 *
 */
public class EipConfigurationException extends ConfigurationException {

	public EipConfigurationException() {
		// TODO Auto-generated constructor stub
	}

	public EipConfigurationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EipConfigurationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public EipConfigurationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
