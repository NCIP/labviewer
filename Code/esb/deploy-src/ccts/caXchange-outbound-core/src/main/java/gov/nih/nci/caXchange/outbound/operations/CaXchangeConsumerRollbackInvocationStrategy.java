package gov.nih.nci.caXchange.outbound.operations;

import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridInvocationStrategy;
import gov.nih.nci.caXchange.outbound.GridMessage;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.client.CaXchangeConsumerServiceClient;

import java.io.InputStream;
import java.io.StringReader;
import java.util.Set;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.NormalizedMessage;

import org.apache.axis.AxisFault;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.globus.gsi.GlobusCredential;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
/**
 * Implements an invocation strategy for invoking the rollback operation on the caXchange
 * consumer service.
 * 
 * @author Ekagra Software Technologies
 *
 */
public class CaXchangeConsumerRollbackInvocationStrategy extends
		GridInvocationStrategy {
    private static Logger logger = LogManager.getLogger(CaXchangeConsumerInvocationStrategy.class);

	@Override
	public GridInvocationResult invokeGridService(DeliveryChannel channel,
			MessageExchange exchange, GridMessage message)
			throws GridInvocationException {
		try {
			GlobusCredential cred=null;
			Set <GlobusCredential> s = exchange.getMessage("in").getSecuritySubject().getPrivateCredentials(GlobusCredential.class);

			if(s.size()>0){
				cred=s.iterator().next();
			}else{
				throw new GridInvocationException("no credentials found");
			}
			
			String url=serviceUrl;
			CaXchangeConsumerServiceClient client = new CaXchangeConsumerServiceClient(
					url, cred);

			SourceTransformer transformer = new SourceTransformer();
			InputStream deseralizeStream = client.getClass().getResourceAsStream(
							"client-config.wsdd");
			NormalizedMessage in = exchange.getMessage("in");
			Document input = new SourceTransformer().toDOMDocument(in);
			StringReader reader = new StringReader(transformer.toString(input));
			Message caXchangeRequestMessage = (Message) Utils.deserializeObject(
					reader, Message.class, deseralizeStream);

			client.rollback(caXchangeRequestMessage);
			return new GridInvocationResult() {

				public Node getResult() {
					return null;
				}

				public boolean isFault() {
					return false;
				}

			};
		} catch (AxisFault af) {
			logger.error("Failed to invoke rollback operation on caXchange consumer  service.URL:"+serviceUrl, af);

			throw new GridInvocationException("Failed to invoke rollback operation on caXchange consumer  service.URL:"+serviceUrl+" "+af.getFaultString(), af);
		} catch (Exception e) {
			logger.error("Failed to invoke rollback operation on caXchange consumer  service.URL:"+serviceUrl, e);
			throw new GridInvocationException("Failed to invoke rollback operation on caXchange consumer  service.URL:"+serviceUrl+" "+e.getMessage(), e);
		}
	}

}
