/**
 * 
 */
package gov.nih.nci.caXchange.outbound.operations;

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

import webservices.Acknowledgement;
import webservices.LoadLabsRequest;

import gov.nih.nci.c3d.domainobjects.LabsLoadRequest;
import gov.nih.nci.c3d.webservices.client.C3DGridServiceClient;
import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridInvocationStrategy;
import gov.nih.nci.caXchange.outbound.GridMessage;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.labviewer.client.LabLoaderClient;


public class LabLoaderInvocationStrategy implements GridInvocationStrategy {

	private static final Category log = Category
			.getInstance(LabLoaderInvocationStrategy.class);

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
			GlobusCredential cred=null;
/*			Set <GlobusCredential> s = exchange.getMessage("in").getSecuritySubject().getPrivateCredentials(GlobusCredential.class);
			
			if(s.size()>0){
				cred=s.iterator().next();
			}else{
				throw new GridInvocationException("no credentials found");
			}
			*/
			LabLoaderClient client = new LabLoaderClient(serviceUrl);//, cred);
			//C3DGridServiceClient client = new C3DGridServiceClient(serviceUrl, cred);

			////////SourceTransformer transformer = new SourceTransformer();
			////////InputStream deseralizeStream = client.getClass().getResourceAsStream(
			////////				"/loadlab/client-config.wsdd");
			
			/*StringReader reader = new StringReader(transformer.toString(message
					.getPayload()));
			LabsLoadRequest request = (LabsLoadRequest) Utils.deserializeObject(
					reader, LabsLoadRequest.class, deseralizeStream);*/

			
			//log.debug("messsssssage:"+message.getPayload().getNodeValue());
			String xml=(new SourceTransformer()).toString(message.getPayload());
			//String msg=xml.substring(xml.indexOf("<study"));
			log.debug("text messsssssage:"+xml);
			//log.debug("messsssssage:"+(new SourceTransformer()).contentToString(exchange.getMessage("in")));
			client.loadLab(xml);

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
			log.error("Failed to invoke lab loader service.", af);
			GridInvocationException gie =
				new GridInvocationException(af.getFaultString(), af);
			if(af.getCause() instanceof ConnectException) {
				gie.setCanRetry(true);
			}
			throw gie;
		} catch (Exception e) {
			log.error("Failed to invoke lab loader service.", e);
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
