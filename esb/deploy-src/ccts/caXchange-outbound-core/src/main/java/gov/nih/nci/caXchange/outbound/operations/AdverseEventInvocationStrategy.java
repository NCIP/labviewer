package gov.nih.nci.caXchange.outbound.operations;

import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridInvocationStrategy;
import gov.nih.nci.caXchange.outbound.GridMessage;
import aenotification.AENotificationType;
import gov.nih.nci.cabig.ctms.grid.ae.client.AdverseEventConsumerClient;
import gov.nih.nci.cabig.ctms.grid.ae.stubs.RegisterRequestAeNotification;
import gov.nih.nci.cabig.ctms.grid.ae.stubs.RegisterResponse;
import gov.nih.nci.cagrid.common.Utils;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.ConnectException;
import java.util.Set;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;
import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.apache.log4j.Category;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.globus.gsi.GlobusCredential;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class AdverseEventInvocationStrategy extends GridInvocationStrategy {

	private static final Category log = Category
			.getInstance(AdverseEventInvocationStrategy.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.nih.nci.caXchange.outbound.GridInvocationStrategy#invokeGridService(javax.jbi.messaging.DeliveryChannel,
	 *      javax.jbi.messaging.MessageExchange,
	 *      gov.nih.nci.caXchange.outbound.GridMessage)
	 */
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

			AdverseEventConsumerClient client = new AdverseEventConsumerClient(
					serviceUrl, cred);

			SourceTransformer transformer = new SourceTransformer();
			InputStream deseralizeStream = client.getClass().getResourceAsStream(
							"client-config.wsdd");
			StringReader reader = new StringReader(transformer.toString(message
					.getPayload()));

			AENotificationType request = (AENotificationType) Utils.deserializeObject(
					reader, AENotificationType.class, deseralizeStream);


			client.register(request);

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
		} catch (AxisFault af) {
			log.error("Failed to invoke load lab service.", af);
			GridInvocationException gie =
				new GridInvocationException(af.getFaultString(), af);
			if(af.getCause() instanceof ConnectException) {
				gie.setCanRetry(true);
			}
			throw gie;
		} catch (Throwable e) {
			log.error("Failed to invoke loab lab service.", e);
			throw new GridInvocationException(e.getMessage(), e);
		}
	}

}
