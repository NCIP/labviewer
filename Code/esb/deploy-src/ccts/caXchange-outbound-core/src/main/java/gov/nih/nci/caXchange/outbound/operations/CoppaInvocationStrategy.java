package gov.nih.nci.caXchange.outbound.operations;

import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridMessage;
import gov.nih.nci.cagrid.common.Utils;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;
import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;

import org.apache.axis.MessageContext;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.TypeMapping;
import org.apache.axis.server.AxisServer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.globus.wsrf.encoding.DeserializationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class CoppaInvocationStrategy extends GenericInvocationStrategy {
	
	private static TypeMapping typeMapping = null;
	
	private static Logger logger = LogManager.getLogger(CoppaInvocationStrategy.class);
	
	public void init()  {
	  try {
		if (typeMapping == null){
		InputStream deseralizeStream = Class.forName(getGridClientClassName()).getResourceAsStream("client-config.wsdd");
		org.apache.axis.EngineConfiguration engineConfig = new FileProvider(deseralizeStream);
		org.apache.axis.AxisEngine axisClient = new AxisServer(engineConfig);
		MessageContext messageContext = new MessageContext(axisClient);
		typeMapping = (TypeMapping)messageContext.getTypeMappingRegistry().getTypeMapping("");
		}
	  }catch(Exception e){
		  logger.error("Error initializing coppa invocation bean.",e);
		  throw new IllegalStateException("Error initializing coppa invocation bean.",e);
	  }

	}
	
	public GridInvocationResult invokeGridService(DeliveryChannel channel,
			MessageExchange exchange, GridMessage message)
			throws GridInvocationException {
		operationName = message.getOperationName();
		useCredentials = false;
		Object client = getNewGridClient(serviceUrl, null);
		Class requestPayloadClass = getRequestPayloadClass(client,message);
		requestPayloadClassName = requestPayloadClass.getName();
		QName typeDesc = getReturnTypeDescription(client, message);
		if (typeDesc != null) {
		   returnTypeNameSpace = typeDesc.getNamespaceURI();
		   returnTypeElement = typeDesc.getLocalPart();
		}
		return super.invokeGridService(channel, exchange, message);
	}
	
	public Class getRequestPayloadClass(Object client, GridMessage message) throws GridInvocationException {
		Class requestPayloadClass=null;
		   String operationName = message.getOperationName();
		   if ("".equals(operationName)) {
			   throw new GridInvocationException("No operation name specified for COPPA service:"+message.getServiceType());
		   }
		   Method invocationMethod = getClientMethod(client,message);
		   Class[] parameterTypes = invocationMethod.getParameterTypes();
		   if (!(parameterTypes.length == 1)) {
			   throw new GridInvocationException("Cannot determine the request payload class name for service:"+message.getServiceType()+" operation:"+message.getOperationName()+" Parameters:"+parameterTypes);
		   }
		   requestPayloadClass = parameterTypes[0];
		   logger.debug("Request payload class name:"+requestPayloadClass.getName());
		   return requestPayloadClass;

		
	}
	
	public Method getClientMethod(Object client, GridMessage message) throws GridInvocationException {
		try {
			   String operationName = message.getOperationName();
			   if ("".equals(operationName)) {
				   throw new GridInvocationException("No operation name specified for COPPA service:"+message.getServiceType());
			   }
			   Method[] methods = client.getClass().getMethods();
			   Method invocationMethod=null;
			   for (Method method:methods) {
				   if (method.getName().equals(operationName)) {
					   invocationMethod=method;
					   break;
				   }
			   }
			   if (invocationMethod == null) {
				   throw new GridInvocationException("Not found operation name "+operationName+" specified for COPPA service:"+message.getServiceType());			   
		       }
			   return invocationMethod;
		} catch(Exception e){
			logger.error("Error occurred finding the client method.",e);
			throw new GridInvocationException("Error occurred finding the client method.",e);
		}
	}
	
	public QName getReturnTypeDescription(Object client, GridMessage message) throws GridInvocationException {
		try {
			Method invocationMethod = getClientMethod(client, message);
			Class returnType = invocationMethod.getReturnType();
			QName typeDesc = typeMapping.getTypeQName(returnType);
			return typeDesc;
		}catch (Exception e) {
			logger.error("Error occurred finding the return type.",e);
			throw new GridInvocationException("Error occurred finding the return type.",e);
		}
	}
	
		
}
