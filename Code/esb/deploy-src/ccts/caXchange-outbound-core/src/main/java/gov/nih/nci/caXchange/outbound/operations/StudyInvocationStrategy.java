/**
 * 
 */
package gov.nih.nci.caXchange.outbound.operations;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.ConnectException;
import java.util.Properties;
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

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridInvocationStrategy;
import gov.nih.nci.caXchange.outbound.GridMessage;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cabig.ccts.domain.CoordinatingCenterStudyStatusType;
import gov.nih.nci.cabig.ccts.domain.HealthcareSiteType;
import gov.nih.nci.cabig.ccts.domain.IdentifierType;
import gov.nih.nci.cabig.ccts.domain.Study;
import gov.nih.nci.cabig.ccts.domain.StudyRefType;
import gov.nih.nci.cabig.ccts.domain.StudySiteType;
import gov.nih.nci.cabig.ccts.domain.SystemAssignedIdentifierType;
import gov.nih.nci.ccts.grid.client.StudyConsumerClient;

/**
 * @author stevec
 */
public class StudyInvocationStrategy extends GridInvocationStrategy {

	private static final Category log = Category
			.getInstance(StudyInvocationStrategy.class);

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
			Set <GlobusCredential> s = exchange.getMessage("in").getSecuritySubject().getPrivateCredentials(GlobusCredential.class);
			
			if(s.size()>0){
				cred=s.iterator().next();
			}else{
				throw new GridInvocationException("no credentials found");
			}
			
			String url=serviceUrl;
			if(isItineraryBased){
				url=caxchangeProps.getProperty(exchange.getMessage("in").getProperty(CaxchangeConstants.TARGET_ID)+".study.url");
			}
			
			StudyConsumerClient client = new StudyConsumerClient(
					url, cred);

			SourceTransformer transformer = new SourceTransformer();
			InputStream deseralizeStream = client.getClass().getResourceAsStream(
							"/study/client-config.wsdd");
			StringReader reader = new StringReader(transformer.toString(message
					.getPayload()));
			Study request = (Study) Utils.deserializeObject(
					reader, Study.class, deseralizeStream);

			client.createStudy(request);
			//commented to remove commit
			//client.commit(request);

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
			log.error("Failed to invoke study service.", af);
			GridInvocationException gie =
				new GridInvocationException(af.getFaultString(), af);
			if(af.getCause() instanceof ConnectException) {
				gie.setCanRetry(true);
			}
			throw gie;
		} catch (Exception e) {
			log.error("Failed to invoke study service.", e);
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
