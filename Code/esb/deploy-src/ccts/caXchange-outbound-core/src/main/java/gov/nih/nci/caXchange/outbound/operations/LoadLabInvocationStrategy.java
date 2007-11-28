/**
 * 
 */
package gov.nih.nci.caXchange.outbound.operations;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.ConnectException;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;
import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.apache.log4j.Category;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import webservices.Acknowledgement;
import webservices.LoadLabsRequest;

import gov.nih.nci.c3d.webservices.client.C3DGridServiceClient;
import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridInvocationStrategy;
import gov.nih.nci.caXchange.outbound.GridMessage;
import gov.nih.nci.cagrid.common.Utils;

/**
 * @author stevec
 */
public class LoadLabInvocationStrategy implements GridInvocationStrategy {

	private static final Category log = Category
			.getInstance(LoadLabInvocationStrategy.class);

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
			C3DGridServiceClient client = new C3DGridServiceClient(serviceUrl);

			SourceTransformer transformer = new SourceTransformer();
			InputStream deseralizeStream = client.getClass().getResourceAsStream(
							"/loadlab/client-config.wsdd");
			
			StringReader reader = new StringReader(transformer.toString(message
					.getPayload()));
			LoadLabsRequest request = (LoadLabsRequest) Utils.deserializeObject(
					reader, LoadLabsRequest.class, deseralizeStream);
			Acknowledgement reply = client.loadLabs(request);
			
			InputStream serializeStream = client.getClass().getResourceAsStream(
							"/loadlab/client-config.wsdd");
			StringWriter writer = new StringWriter();
			Utils.serializeObject(reply, new QName(
					"http://ccts.nci.nih.gov/LoadLab",
					"LoadLab"), writer, serializeStream);
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
			log.error("Failed to invoke load lab service.", af);
			GridInvocationException gie =
				new GridInvocationException(af.getFaultString(), af);
			if(af.getCause() instanceof ConnectException) {
				gie.setCanRetry(true);
			}
			throw gie;
		} catch (Exception e) {
			log.error("Failed to invoke loab lab service.", e);
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
