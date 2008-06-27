package gov.nih.nci.caXchange.outbound.operations;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridInvocationStrategy;
import gov.nih.nci.caXchange.outbound.GridMessage;

public class TestInvocationStrategy extends GridInvocationStrategy {

	private static Logger logger = LogManager.getLogger(TestInvocationStrategy.class); 

	@Override
	public GridInvocationResult invokeGridService(DeliveryChannel channel,
			MessageExchange exchange, GridMessage message)
			throws GridInvocationException {
	try {
		final Document resp = new SourceTransformer()
		  .toDOMDocument(new StringSource("<result>success</result>"));
        return new GridInvocationResult() {
	                   public Node getResult() {
		                   return resp.getDocumentElement();
	                    }

	                    public boolean isFault() {
		                          return false;
	                    }

           };
	  }catch(Exception e){
		  logger.error("Error in testing component.",e);
		  throw new GridInvocationException("Error in testing component.",e);
	  }
	}

}
