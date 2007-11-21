/**
 * 
 */
package gov.nih.nci.caXchange.outbound.operations;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.rmi.RemoteException;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;
import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.apache.log4j.Category;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridInvocationStrategy;
import gov.nih.nci.caXchange.outbound.GridMessage;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.ccts.grid.HealthcareSiteType;
import gov.nih.nci.ccts.grid.IdentifierType;
import gov.nih.nci.ccts.grid.Registration;
import gov.nih.nci.ccts.grid.StudyRefType;
import gov.nih.nci.ccts.grid.StudySiteType;
import gov.nih.nci.ccts.grid.SystemAssignedIdentifierType;
import gov.nih.nci.ccts.grid.client.RegistrationConsumerClient;

/**
 * @author stevec
 */
public class RegistrationRollbackStrategy implements GridInvocationStrategy {

	private static final Category log = Category
			.getInstance(RegistrationRollbackStrategy.class);

	private String serviceUrl;

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
			RegistrationConsumerClient client = new RegistrationConsumerClient(
					serviceUrl);

			SourceTransformer transformer = new SourceTransformer();
			InputStream deseralizeStream = client.getClass().getResourceAsStream(
							"client-config.wsdd");
			StringReader reader = new StringReader(transformer.toString(message
					.getPayload()));
			Registration request = (Registration) Utils.deserializeObject(
					reader, Registration.class, deseralizeStream);
			
			client.rollback(request);
			return new GridInvocationResult() {

				public Node getResult() {
					return null;
				}

				public boolean isFault() {
					return false;
				}

			};
		} catch (AxisFault af) {
			log.error("Failed to invoke registration service.", af);

			throw new GridInvocationException(af.getFaultString(), af);
		} catch (Exception e) {
			log.error("Failed to invoke registration service.", e);
			throw new GridInvocationException(e.getMessage(), e);
		}
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

}
