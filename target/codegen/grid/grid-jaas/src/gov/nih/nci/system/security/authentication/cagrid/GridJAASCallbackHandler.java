package gov.nih.nci.system.security.authentication.cagrid;
import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class GridJAASCallbackHandler implements CallbackHandler 
{
	private String _userID;
	private String _password;
	private String _authenticationServiceURL;
	private String _dorianServiceURL;

	public GridJAASCallbackHandler(String userID, String password) {
		super();
		_userID = userID;
		_password = password;
	}

	public GridJAASCallbackHandler(String userID, String password, String authenticationServiceURL, String dorianServiceURL) {
		super();
		_userID = userID;
		_password = password;
		_authenticationServiceURL = authenticationServiceURL;
		_dorianServiceURL = dorianServiceURL;
	}
	
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {

		if(callbacks.length >=2)
		{
			NameCallback nameCallback = (NameCallback) callbacks[0];
			nameCallback.setName(_userID);
			PasswordCallback passwordCallback = (PasswordCallback) callbacks[1];
			passwordCallback.setPassword(_password.toCharArray());
		}

		if(callbacks.length ==4)
		{
			TextInputCallback textCallback = (TextInputCallback) callbacks[2];
			textCallback.setText(_authenticationServiceURL);
			TextInputCallback textCallback2 = (TextInputCallback) callbacks[3];
			textCallback2.setText(_dorianServiceURL);
		}
	}
}
