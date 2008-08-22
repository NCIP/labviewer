package gov.nih.nci.caxchange.servicemix.jbi.audit;

import gov.nih.nci.logging.api.user.UserInfoHelper;


import java.security.Principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.MessageExchange;

import javax.jbi.messaging.NormalizedMessage;
import javax.jbi.messaging.MessageExchange.Role;

import javax.security.auth.Subject;
import javax.xml.transform.dom.DOMSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.audit.AbstractAuditor;
import org.apache.servicemix.jbi.container.JBIContainer;
import org.apache.servicemix.jbi.event.ExchangeEvent;
import org.apache.servicemix.jbi.event.ExchangeListener;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.w3c.dom.Node;
/**
 *  Audit exchanges sent from the NMR and the exchanges accepted into the NMR
 *  using Commons Logging API.
 *  
 *  @author Ekagra
 */
public class CommonLoggingListener implements ExchangeListener {

    //Defines the default user to set is subject principal details are not available.
    private String defaultUser;
    private JBIContainer container;
    private boolean logExchangesSent = true;
    private boolean logExchangesAccepted=true;
    private List<String> filters = new ArrayList();
    

    Logger logger = LogManager.getLogger(CommonLoggingListener.class);
    
    public CommonLoggingListener() {
    }
    
    public void doStart() {
        container.addListener(this);
    }
    
    public void doStop() {
        container.removeListener(this);
    }
    /**
     * 
     * @param exchangeEvent
     * @return
     */
    public String getUserFromExchangeEvent(ExchangeEvent exchangeEvent) {
        if (exchangeEvent == null) {
            return defaultUser;
        }
        MessageExchange messageExchange = exchangeEvent.getExchange();
        if (messageExchange == null) {
            return defaultUser;
        }
        try {
          NormalizedMessage message = messageExchange.getMessage("in");
          if (message == null) {
              message = messageExchange.getMessage("out");
          }
          if (message == null) {
              return defaultUser;
          }
          Subject subject =message.getSecuritySubject();
          if (subject == null) {
              return defaultUser;
          }
          Set<Principal> principals =subject.getPrincipals();
          if ((principals == null)||(principals.isEmpty())) {
              return defaultUser;
          }
          return principals.iterator().next().getName();
        }catch (Throwable e)   {
            return defaultUser;
        }
          
    }
    /**
     * Finds out whether an exchange matches a particular pattern.
     * 
     * @param exchangeEvent
     * @return
     */
    public boolean matchFilter(ExchangeEvent exchangeEvent) {
        if ((filters == null)||(filters.size()==0)) {
            return true;
        }
        boolean retVal = false;
        if (exchangeEvent == null) return retVal;
        MessageExchange messageExchange = exchangeEvent.getExchange();
        if (messageExchange==null) return retVal;
        String messageExchangeString = messageExchange.toString();
        for(String filter:filters) {
            if (messageExchangeString.indexOf(filter)>0) {
                return true;
            }
        }
        return retVal;
    }

    /**
     * Log messages sent out of  the NMR.
     * 
     * @param exchangeEvent
     */
    public void exchangeSent(ExchangeEvent exchangeEvent) {
       if ((logExchangesSent)&&(matchFilter(exchangeEvent))) {
         MessageExchange messageExchange = exchangeEvent.getExchange();    
         boolean error = messageExchange.getStatus().equals(ExchangeStatus.ERROR);
         UserInfoHelper.setUserInfo(getUserFromExchangeEvent(exchangeEvent), messageExchange.getExchangeId());
         try {
        	 String messageExchangeAsString = convertMessageExchangeToString(messageExchange);
        	 //Escape any single quotes :
        	 messageExchangeAsString = messageExchangeAsString.replace("'", "''");        	 
         if (error) {
             logger.error(messageExchangeAsString);
         }else {
             logger.info(messageExchangeAsString);
         }
         }catch(Exception e){
        	 //Log but ignore the exception as this is a logging error .
        	  logger.error("Error logging message exchange.", e);
          }
       }
         
    }
    /**
     * Log messages sent to the NMR
     * @param exchangeEvent
     */
    public void exchangeAccepted(ExchangeEvent exchangeEvent) {
        if ((logExchangesAccepted)&&(matchFilter(exchangeEvent))) {
           MessageExchange messageExchange = exchangeEvent.getExchange();    
           boolean error = messageExchange.getStatus().equals(ExchangeStatus.ERROR);
           UserInfoHelper.setUserInfo(getUserFromExchangeEvent(exchangeEvent), messageExchange.getExchangeId());
           try{
        	 String messageExchangeAsString = convertMessageExchangeToString(messageExchange);
        	 //Escape any single quotes :
        	 messageExchangeAsString = messageExchangeAsString.replace("'", "''");
             if (error) {
                logger.error(messageExchangeAsString);
             }else {
                logger.info(messageExchangeAsString);
             }
           }catch(Exception e) {
        	 //Log but ignore the exception as this is a logging error .
        	   logger.error("Error logging message exchange.", e);
           }
        }
    }
    
    public String convertMessageExchangeToString(MessageExchange me) {
        try {
            StringBuffer sb = new StringBuffer();
            String name = me.getClass().getName();
            name = name.substring(name.lastIndexOf('.') + 1, name.length() - 4);
            sb.append(name);
            sb.append("[\n");
            sb.append("  id: ").append(me.getExchangeId()).append('\n');
            sb.append("  status: ").append(me.getStatus()).append('\n');
            sb.append("  role: ").append(me.getRole() == Role.CONSUMER ? "consumer" : "provider").append('\n');
            if (me.getInterfaceName() != null) {
                sb.append("  interface: ").append(me.getInterfaceName()).append('\n');
            }
            if (me.getService() != null) {
                sb.append("  service: ").append(me.getService()).append('\n');
            }
            if (me.getEndpoint() != null) {
                sb.append("  endpoint: ").append(me.getEndpoint().getEndpointName()).append('\n');
            }
            if (me.getOperation() != null) {
                sb.append("  operation: ").append(me.getOperation()).append('\n');
            }
            SourceTransformer st = new SourceTransformer(); 
            display(me,"in", sb, st);
            display(me,"out", sb, st);
            display(me,"fault", sb, st);
            if (me.getError() != null) {
                sb.append("  error: ");
                sb.append(me.getError());
                sb.append('\n');
            }
            sb.append("]");
            return sb.toString();
        } catch (Exception e) {
            logger.error("Error caught in converting message exchange to string.", e);
            return "Error caught in converting message exchange to string."+e.getMessage();
        }
    }    
    
    private void display(MessageExchange me, String msg, StringBuffer sb, SourceTransformer st) {
        if (me.getMessage(msg) != null) {
            sb.append("  ").append(msg).append(": ");
            try {
                if (me.getMessage(msg).getContent() != null) {
                    Node node = st.toDOMNode(me.getMessage(msg).getContent());
                    me.getMessage(msg).setContent(new DOMSource(node));
                    String str = st.toString(node);
                    sb.append(str);                    
                } else {
                    sb.append("null");
                }
            } catch (Exception e) {
                sb.append("Unable to convert to string: ").append(e);
            }
            sb.append('\n');
        }
    }

    public void setDefaultUser(String defaultUser) {
        this.defaultUser = defaultUser;
    }

    public String getDefaultUser() {
        return defaultUser;
    }

    public void setContainer(JBIContainer container) {
        this.container = container;
    }

    public JBIContainer getContainer() {
        return container;
    }

    public void setLogExchangesSent(boolean logExchangesSent) {
        this.logExchangesSent = logExchangesSent;
    }

    public boolean isLogExchangesSent() {
        return logExchangesSent;
    }

    public void setLogExchangesAccepted(boolean logExchangesAccepted) {
        this.logExchangesAccepted = logExchangesAccepted;
    }

    public boolean isLogExchangesAccepted() {
        return logExchangesAccepted;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public List<String> getFilters() {
        return filters;
    }
}
