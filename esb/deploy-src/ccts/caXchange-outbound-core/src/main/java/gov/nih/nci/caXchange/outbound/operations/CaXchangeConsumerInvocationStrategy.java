package gov.nih.nci.caXchange.outbound.operations;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridInvocationStrategy;
import gov.nih.nci.caXchange.outbound.GridMessage;
import gov.nih.nci.cabig.ccts.domain.Registration;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.client.CaXchangeConsumerServiceClient;
import gov.nih.nci.ccts.grid.client.RegistrationConsumerClient;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.ConnectException;
import java.util.Set;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.apache.axis.message.MessageElement;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.globus.gsi.GlobusCredential;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class CaXchangeConsumerInvocationStrategy extends GridInvocationStrategy {
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
			gov.nih.nci.caxchange.ConsumerResponseMessage reply = client.process(caXchangeRequestMessage);
			MessageElement[] elements = reply.get_any();
			if ((elements != null)&&(elements.length>0)){
			   final Document resp = elements[0].getAsDocument();
			   return new GridInvocationResult() {

   				   public Node getResult() {
					  return resp.getDocumentElement();
				   }

				   public boolean isFault() {
					  return false;
				   }
			     };
			}else {
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
			}
		} catch (AxisFault af) {
			logger.error("Failed to invoke caXchange consumer service.", af);
			GridInvocationException gie =
				new GridInvocationException(af.getFaultString(), af);
			if(af.getCause() instanceof ConnectException) {
				gie.setCanRetry(true);
			}
			throw gie;
		} catch (Throwable e) {
			logger.error("Failed to invoke caXchange consumer service.", e);
			throw new GridInvocationException(e.getMessage(), e);
		}
	}

}
