/**
 *
 */
package gov.nih.nci.caXchange.outbound.operations;

import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridInvocationStrategy;
import gov.nih.nci.caXchange.outbound.GridMessage;
import gov.nih.nci.cabig.ccts.domain.Study;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.ccts.grid.studyconsumer.client.StudyConsumerClient;

import java.io.InputStream;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;
import javax.security.auth.Subject;

import org.apache.axis.AxisFault;
import org.apache.log4j.Category;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.globus.gsi.GlobusCredential;
import org.w3c.dom.Node;


/**
 * @author stevec
 */
public class StudyRollbackStrategy extends GridInvocationStrategy {

	private static final Category log = Category
			.getInstance(StudyRollbackStrategy.class);

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

			StudyConsumerClient client = new StudyConsumerClient(
					serviceUrl, cred);

			SourceTransformer transformer = new SourceTransformer();
			InputStream deseralizeStream = client.getClass().getResourceAsStream(
							"/study/client-config.wsdd");
			StringReader reader = new StringReader(transformer.toString(message
					.getPayload()));
			Study request = (Study) Utils.deserializeObject(
					reader, Study.class, deseralizeStream);

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
			log.error("Failed to invoke study service.", af);

			throw new GridInvocationException(af.getFaultString(), af);
		} catch (Exception e) {
			log.error("Failed to invoke study service.", e);
			throw new GridInvocationException(e.getMessage(), e);
		}
	}

}
