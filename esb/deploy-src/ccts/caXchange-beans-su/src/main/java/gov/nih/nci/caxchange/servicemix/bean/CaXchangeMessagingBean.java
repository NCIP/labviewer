package gov.nih.nci.caxchange.servicemix.bean;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;


import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.CaxchangeErrors;
import gov.nih.nci.caxchange.servicemix.bean.util.CaXchangeDataUtil;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.Fault;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.MessageExchangeListener;
import org.apache.servicemix.jbi.util.MessageUtil;
/**
 * An abstract servicemix bean component which can be implemented to add new beans to 
 * the caXchange servicemix bean unit. This class expects an IN-OUT or a ROBUST IN-ONLY
 * message exchange pattern, as it returns a Fault in case of an error. 
 * 
 * 
 * @author Ekagra software technologies
 *
 */
public abstract class CaXchangeMessagingBean implements MessageExchangeListener {

	@javax.annotation.Resource
    protected DeliveryChannel channel;
	protected boolean copyProperties=true;
	protected boolean copyAttachments=true;
	protected boolean copySubject=true;
	
	protected Set<String> eligibleServiceTypes = new HashSet<String>();
	protected CaXchangeDataUtil caXchangeDataUtil = new CaXchangeDataUtil();

	static private Logger logger = LogManager.getLogger(CaXchangeMessagingBean.class);

	
	public void onMessageExchange(MessageExchange exchange)
                                       	throws MessagingException {
           try {
        	   if (exchange.getStatus().equals(ExchangeStatus.DONE)) {
        		   return;
        	   }
        	   if (exchange.getStatus().equals(ExchangeStatus.ERROR)) {
        		   logger.error("Error exchange recieved."+exchange);
        		   //throw new MessagingException("Error exchange received.");
        	   }
        	   logger.debug("In bean:"+this.getClass().getName()+":"+new java.util.Date().getTime());
        	   if (exchange.getStatus().equals(ExchangeStatus.ACTIVE)) { 
        		  caXchangeDataUtil.setIn(exchange.getMessage("in"));
        		  caXchangeDataUtil.initialize();
        		  //Process the exchange if the all service types are eligible or the message type is eligible.
        		  if ((eligibleServiceTypes.isEmpty())||(eligibleServiceTypes.contains(caXchangeDataUtil.getMessageType()))) {
                     processMessageExchange(exchange);
        		  }else {
        		    NormalizedMessage in = exchange.getMessage("in");
        	        NormalizedMessage out = exchange.createMessage();
        	        MessageUtil.transfer(in,out);   
        	        copyPropertiesAndAttachments(in,out);
        	        exchange.setMessage(out, "out");
        	        channel.send(exchange);		
        	      }
        	   }
        	   logger.debug("Out bean:"+this.getClass().getName()+":"+new java.util.Date().getTime());
               return;
           }catch(Exception e){
              logger.error("Error occurred processing exchange."+e.getMessage(), e);
   			  Fault fault = getFault(CaxchangeErrors.UNKNOWN,"Error occurred processing exchange.: "+e.getMessage(), exchange);
   			  exchange.setFault(fault);
   			  channel.send(exchange);
   			  return;
           }
	}
    public abstract void processMessageExchange(MessageExchange exchange)	throws Exception;
    /**
     * Returns a Fault based on the error code and error message provided. The error code and message
     * are set as properties. The in content is set as the content of the message.
     * 
     * @param errorCode
     * @param errorMessage
     * @param exchange
     * @return
     * @throws javax.jbi.messaging.MessagingException
     */
	public Fault getFault(String errorCode, String errorMessage, javax.jbi.messaging.MessageExchange exchange) throws javax.jbi.messaging.MessagingException {
		javax.jbi.messaging.NormalizedMessage in = exchange.getMessage("in");
		Fault fault = exchange.createFault();
		MessageUtil.transfer(in, fault);
		fault.setProperty(CaxchangeConstants.ERROR_CODE,
		errorCode);
		fault.setProperty(CaxchangeConstants.ERROR_MESSAGE,
		errorMessage);
        return fault;
	}
	/**
	 * Sets the eligible caXchange service types for this bean. It initializes the Set of eligible caXchange
	 * service types. The bean will process exchanges only if the input service type is among the eligible
	 * service types.
	 * 
	 * @param eligibleServiceTypes A comma separated list of caXchange service types
	 */
	public void setEligibleServiceTypes(String eligibleServiceTypes) {
		StringTokenizer stringTokenizer = new StringTokenizer(eligibleServiceTypes,",");
		while (stringTokenizer.hasMoreTokens()) {
			this.eligibleServiceTypes.add(stringTokenizer.nextToken());
		}
	}
	
	
	protected void copyPropertiesAndAttachments(NormalizedMessage in,
			NormalizedMessage out) throws Exception {
		if (isCopyProperties()) {
			for (Iterator it = in.getPropertyNames().iterator(); it.hasNext();) {
				String name = (String) it.next();
				out.setProperty(name, in.getProperty(name));
			}
		}
		if (isCopyAttachments()) {
			for (Iterator it = in.getAttachmentNames().iterator(); it.hasNext();) {
				String name = (String) it.next();
				out.addAttachment(name, in.getAttachment(name));
			}
		}
		if (isCopySubject()) {
			out.setSecuritySubject(in.getSecuritySubject());
		}
	}
	public boolean isCopyProperties() {
		return copyProperties;
	}
	public void setCopyProperties(boolean copyProperties) {
		this.copyProperties = copyProperties;
	}
	public boolean isCopyAttachments() {
		return copyAttachments;
	}
	public void setCopyAttachments(boolean copyAttachments) {
		this.copyAttachments = copyAttachments;
	}
	public boolean isCopySubject() {
		return copySubject;
	}
	public void setCopySubject(boolean copySubject) {
		this.copySubject = copySubject;
	}

}
