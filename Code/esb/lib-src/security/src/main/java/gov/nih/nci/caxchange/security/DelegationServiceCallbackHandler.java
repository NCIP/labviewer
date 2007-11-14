package gov.nih.nci.caxchange.security;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;

import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.TextOutputCallback;

import javax.security.auth.callback.UnsupportedCallbackException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;

import javax.xml.transform.sax.SAXResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;

import javax.xml.xpath.XPathFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.w3c.dom.Document;

public class DelegationServiceCallbackHandler  implements CallbackHandler{
    Logger logger = LogManager.getLogger(DelegationServiceCallbackHandler.class);
    
    String userName;
    String groupName;
    String gridIdentifier;
    
        
    public DelegationServiceCallbackHandler() {
    }

    public void handle(Callback[] callbacks) throws java.io.IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
           Callback callback = callbacks[i];
           if (callback instanceof TextOutputCallback) {
            TextOutputCallback toc = (TextOutputCallback)callbacks[i];
                   switch (toc.getMessageType()) {
                   case TextOutputCallback.INFORMATION:
                       logger.info(toc.getMessage());
                       break;
                   case TextOutputCallback.ERROR:
                       logger.error("ERROR: " + toc.getMessage());
                       throw new IOException("Error authenticating.."+toc.getMessage());
                   case TextOutputCallback.WARNING:
                       logger.warn("WARNING: " + toc.getMessage());
                       break;
                   default:
                       throw new IOException("Unsupported message type: " +
                                           toc.getMessageType());
                   }
           }
           else if (callback instanceof NameCallback) {
               NameCallback nameCallback = (NameCallback)callback;
               nameCallback.setName(getUserName());
           }
           //Handle other callback handled
        }    
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGridIdentifier(String gridIdentifier) {
        this.gridIdentifier = gridIdentifier;
    }

    public String getGridIdentifier() {
        return gridIdentifier;
    }
}
