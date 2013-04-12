/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.servicemix;

/**
 * An exception thrown reading verifying servicemix configuration files.
 * 
 * @author Harsh Marwaha
 *
 */
public class ConfigurationException extends Exception {

	public ConfigurationException() {
		// TODO Auto-generated constructor stub
	}

	public ConfigurationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ConfigurationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
