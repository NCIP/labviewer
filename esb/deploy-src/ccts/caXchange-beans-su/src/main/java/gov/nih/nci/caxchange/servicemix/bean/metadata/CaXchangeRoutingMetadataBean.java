package gov.nih.nci.caxchange.servicemix.bean.metadata;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.CaxchangeErrors;
import gov.nih.nci.caxchange.eip.EipConfig;
import gov.nih.nci.caxchange.jdbc.CaxchangeMetadata;
import gov.nih.nci.caxchange.persistence.CaxchangeMetadataDAO;
import gov.nih.nci.caxchange.persistence.DAOFactory;
import gov.nih.nci.caxchange.servicemix.bean.CaXchangeMessagingBean;

import java.io.StringReader;
import java.net.URL;

import javax.jbi.messaging.Fault;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.util.MessageUtil;
import org.springframework.core.io.Resource;

public class CaXchangeRoutingMetadataBean extends CaXchangeMessagingBean {

	private Resource location;

    private EipConfig eipRoutingConfig=null;
    private Logger logger = LogManager.getLogger(CaXchangeRoutingMetadataBean.class);
    private String caXchangeRoutingMetadata = "<messageTypeMetadata> "+
                                              " <messageType>@messageType@</messageType> "+
                                              " <payloadNamespace>@payloadNamespace@</payloadNamespace> "+
                                              "</messageTypeMetadata> ";


	public void processMessageExchange(MessageExchange exchange)
			throws MessagingException {
        logger.debug("Received exchange: " + exchange);
        NormalizedMessage out = exchange.createMessage();
        try {
          String metadata = getRoutingMetadata();
          Source source = new StreamSource(new StringReader(metadata));
          out.setContent(source);
        }catch(Exception e){
            logger.error("An error occurred getting metadata.", e);
			Fault fault = getFault(CaxchangeErrors.ERROR_GETTING_METADATA,"Error occurred getting metadata."+e.getMessage(), exchange);
			exchange.setFault(fault);
			channel.send(exchange);
			return;
        }
        exchange.setMessage(out, "out");
        channel.send(exchange);
	}

	public void initializeEipConfig() throws Exception {
		EipConfig routingConfig = new EipConfig();
		URL eipUrl = location.getURL();
        if (eipUrl != null) {
           logger.info("Found the eip configuration file."+eipUrl.getPath());
           routingConfig.setURL(eipUrl);
           try {
             routingConfig.initialize();
           }catch(Exception e) {
        	   logger.error("Error reading/ parsing routing configuration. ");
        	   throw e;
           }
           eipRoutingConfig = routingConfig;
        }else {
        	throw new Exception("Unable to find the eip.xml routing configuration file.");
        }

	}

	public String getRoutingMetadata() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<caXchangeRoutingMetadata>");
		if (eipRoutingConfig != null) {
 			String[] messageTypes = eipRoutingConfig.getConfiguredMessageTypes();
    		CaxchangeMetadataDAO metadataDAO = DAOFactory.getCaxchangeMetadataDAO();
 			for(String messageType:messageTypes) {
 				CaxchangeMetadata metadata = metadataDAO.getMetadata(messageType);
 				if (metadata != null) {
 				    buffer.append(caXchangeRoutingMetadata.replace("@messageType@", messageType)
 						      .replace("@payloadNamespace@", (metadata.getPayloadNamespace()==null?"":metadata.getPayloadNamespace())));
				}else {
					buffer.append(caXchangeRoutingMetadata.replace("@messageType@", messageType)
					          .replace("@payloadUri@",""));
				}
 			}
		}
		buffer.append("</caXchangeRoutingMetadata>");
		return buffer.toString();
	}


	public void setLocation(Resource location) {
		this.location=location;
    }

    public Resource getLocation() {
		return this.location;
    }


}
