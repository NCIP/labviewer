package gov.nih.nci.cagrid.caxchange.listener;


import gov.nih.nci.cagrid.caxchange.service.CaXchangeRequestProcessorImpl;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CaxchangeResponseExceptionListener implements ExceptionListener{
    Logger logger = LogManager.getLogger(CaxchangeResponseExceptionListener.class);
    public CaxchangeResponseExceptionListener() {
    }
   /**
     * Remove the current connection from the registered response listeners so that
     * a new response listener is registered at the next request.
     * 
     * @param jmsException
     */
    public void onException(JMSException jmsException) {
       try {
         logger.error("Error in jms connection.", jmsException);
         logger.info("Stopping the faulty connection.");
         Connection conn=CaXchangeRequestProcessorImpl.responseListeners.get(this);
         CaXchangeRequestProcessorImpl.responseListeners.remove(this);         
         if (conn!=null) {
             conn.stop();
             conn.close();
         }
       }catch(Exception e) {
           logger.error("Error handling jms exception,",jmsException);
           throw new RuntimeException("Error handling jms exception,",jmsException);
       }
    }
}
