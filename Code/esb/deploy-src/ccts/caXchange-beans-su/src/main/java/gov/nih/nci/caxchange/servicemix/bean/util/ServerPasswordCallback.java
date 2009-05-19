package gov.nih.nci.caxchange.servicemix.bean.util;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.ws.security.WSPasswordCallback;

/**
 * This is a callback class for handling the WS Security
 * actions set in the WSS4JInInterceptor.
 * 
 * @author Ekagra Software Technologies
 * 
 */
public class ServerPasswordCallback implements CallbackHandler {

	static Logger logger = LogManager.getLogger(ServerPasswordCallback.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.security.auth.callback.CallbackHandler#handle(javax.security.auth
	 * .callback.Callback[])
	 */
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		logger
				.debug("**********SERVER PASSWORD VALIDATOR BEAN CALLED**********");

		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		logger.debug("User Identifier: " + pc.getIdentifer());

		if (pc.getIdentifer().equals("wsClient")) {
			// for password digest, set the password on the callback.
			// This will be compared to the password which was sent from the
			// client.
			// pc.setPassword("password");
			if (!pc.getPassword().equals("password")) {
				throw new IOException("wrong password");
			}
		}
	}

}
