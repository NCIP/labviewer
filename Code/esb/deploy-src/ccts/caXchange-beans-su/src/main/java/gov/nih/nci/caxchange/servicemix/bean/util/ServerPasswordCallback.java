package gov.nih.nci.caxchange.servicemix.bean.util;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class ServerPasswordCallback implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		System.out
				.println("**********SERVER PASSWORD VALIDATOR BEAN CALLED**********");

		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		System.out.println("User Identifier: " + pc.getIdentifer());

		if (pc.getIdentifer().equals("wsClient")) {
			System.out.println("USERNAME PASSWORD SET BLOCK");
			System.out.println("PASSWORD: " + pc.getPassword());
			// set the password on the callback. This will be compared to the
			// password which was sent from the client.
			// pc.setPassword("password");
			if (!pc.getPassword().equals("password")) {
				throw new IOException("wrong password");
			}
		}
	}

}
