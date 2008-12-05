package gov.nih.nci.caxchange.security;

import java.security.Principal;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class DelegationServiceLoginModule implements LoginModule {
   private Subject subject;
   private CallbackHandler callbackHandler;
   String userName;
   
    public DelegationServiceLoginModule() {
    }

    public void initialize(Subject subject, CallbackHandler callbackHandler, 
                           Map<String, ?> sharedState, 
                           Map<String, ?> options) {
    }

    public boolean login() throws LoginException {
      //Replace with the actual implementation to call the delegation Service
      try {
       Callback callbacks[] = new Callback[1];
           callbacks[0] = new NameCallback("UserName?");
           callbackHandler.handle(callbacks);
           userName=((NameCallback)callbacks[0]).getName();
        return true;
      }
      catch(Exception e) {
          throw new LoginException("Error login"+e.getMessage());
      }
    }

    public boolean commit() {
         CaXchangePrincipal principal = new CaXchangePrincipal();
         principal.setName(userName);
         subject.getPrincipals().add(principal);
        return true;
    }

    public boolean abort() {
        return false;
    }

    public boolean logout() {
        return true;
    }




}
