/**
 *
 */
package gov.nih.nci.caXchange.outbound.operations;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridInvocationStrategy;
import gov.nih.nci.caXchange.outbound.GridMessage;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cabig.ccts.domain.Registration;
import gov.nih.nci.ccts.grid.client.RegistrationConsumerClient;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.ConnectException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;
import javax.security.auth.Subject;
import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.apache.log4j.Category;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.globus.gsi.GlobusCredential;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author stevec
 */
public class RegistrationInvocationStrategy extends GridInvocationStrategy {

	private static final Category log = Category
			.getInstance(RegistrationInvocationStrategy.class);

	private Properties caxchangeProps;

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
			Subject subject = exchange.getMessage("in").getSecuritySubject();
			Set<GlobusCredential> globusCredentials = new HashSet<GlobusCredential>();
			if (subject != null) {
				globusCredentials = subject.getPrivateCredentials(GlobusCredential.class);
			}

			if(globusCredentials.size()>0){
				cred=globusCredentials.iterator().next();
			}else{
				throw new GridInvocationException("no credentials found");
			}

			String url=serviceUrl;
			if(isItineraryBased){
				url=caxchangeProps.getProperty(exchange.getMessage("in").getProperty(CaxchangeConstants.TARGET_ID)+".registration.url");
			}
			RegistrationConsumerClient client=null;
            if (cred != null) {
			   client = new RegistrationConsumerClient(
					url, cred);
            }else {
            	client = new RegistrationConsumerClient(
    					url);
            }
			SourceTransformer transformer = new SourceTransformer();
			InputStream deseralizeStream = client.getClass().getResourceAsStream(
							"/registration/client-config.wsdd");
			StringReader reader = new StringReader(transformer.toString(message
					.getPayload()));
			Registration request = (Registration) Utils.deserializeObject(
					reader, Registration.class, deseralizeStream);
			Registration reply = client.register(request);

			//commented to remove commit
			//client.commit(request);

			InputStream serializeStream = client.getClass().getResourceAsStream(
							"/registration/client-config.wsdd");
			StringWriter writer = new StringWriter();
			Utils.serializeObject(reply, new QName(
					"gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain",
					"registration"), writer, serializeStream);
			String response = writer.getBuffer().toString();
			final Document resp = new SourceTransformer()
					.toDOMDocument(new StringSource(response));
			return new GridInvocationResult() {

				public Node getResult() {
					return resp.getDocumentElement();
				}

				public boolean isFault() {
					return false;
				}

			};
		} catch (AxisFault af) {
			log.error("Failed to invoke registration service.", af);
			GridInvocationException gie =
				new GridInvocationException(af.getFaultString(), af);
			if(af.getCause() instanceof ConnectException) {
				gie.setCanRetry(true);
			}
			throw gie;
		} catch (Throwable e) {
			log.error("Failed to invoke registration service.", e);
			throw new GridInvocationException(e.getMessage(), e);
		}
	}

	public Properties getCaxchangeProps() {
		return caxchangeProps;
	}

	public void setCaxchangeProps(Properties caxchangeProps) {
		this.caxchangeProps = caxchangeProps;
	}

}
