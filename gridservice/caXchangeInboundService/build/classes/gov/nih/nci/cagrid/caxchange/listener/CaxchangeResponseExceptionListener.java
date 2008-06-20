package gov.nih.nci.cagrid.caxchange.listener;


import gov.nih.nci.cagrid.caxchange.service.CaXchangeRequestProcessorImpl;

import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CaxchangeResponseExceptionListener implements ExceptionListener{
    Logger logger = LogManager.getLogger(CaxchangeResponseExceptionListener.class);
    
    public CaxchangeResponseExceptionListener() {
    	logger.setLevel((Level)Level.WARN);
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

         if(CaXchangeRequestProcessorImpl.consumer!=null){
        	 CaXchangeRequestProcessorImpl.consumer.close();
         }
         
         if(CaXchangeRequestProcessorImpl.session!=null){
        	 CaXchangeRequestProcessorImpl.session.close();
         }
         
         if (CaXchangeRequestProcessorImpl.connection!=null) {
        	 CaXchangeRequestProcessorImpl.connection.stop();
        	 CaXchangeRequestProcessorImpl.connection.close();
         }
         
       }catch(Exception e) {
           logger.error("Error handling jms exception,",jmsException);
           throw new RuntimeException("Error handling jms exception,",jmsException);
       }finally{
    	   CaXchangeRequestProcessorImpl.connection=null;
    	   CaXchangeRequestProcessorImpl.session=null;
    	   CaXchangeRequestProcessorImpl.consumer=null;
    	   CaXchangeRequestProcessorImpl.listener=null;
       }
       
       // register a new listener
       try{
      	 logger.debug("ActiveMQ Connection prob: creating new listener");
      	 CaXchangeRequestProcessorImpl requestProcessor = new CaXchangeRequestProcessorImpl();
           requestProcessor.registerResponseListeners();
       }catch(Exception e){
      	 logger.fatal("Error creating ActiveMQ Connection", e);
       }
       
    }
}
