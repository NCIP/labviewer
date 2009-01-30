package gov.nih.nci.caXchange.outbound.operations;

import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridMessage;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;

import org.apache.axis.description.TypeDesc;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CoppaInvocationStrategy extends GenericInvocationStrategy {

	private static Logger logger = LogManager.getLogger(CoppaInvocationStrategy.class); 
	
	public GridInvocationResult invokeGridService(DeliveryChannel channel,
			MessageExchange exchange, GridMessage message)
			throws GridInvocationException {
		operationName = message.getOperationName();
		useCredentials = false;
		Object client = getNewGridClient(serviceUrl, null);
		Class requestPayloadClass = getRequestPayloadClass(client,message);
		requestPayloadClassName = requestPayloadClass.getName();
		TypeDesc typeDesc = getReturnTypeDescription(client, message);
		returnTypeNameSpace = typeDesc.getXmlType().getNamespaceURI();
		returnTypeElement = typeDesc.getXmlType().getLocalPart();
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
			logger.error("Error occurred find the client method.",e);
			throw new GridInvocationException("Error occurred find the client method.",e);
		}
	}
	
	public TypeDesc getReturnTypeDescription(Object client, GridMessage message) throws GridInvocationException {
		try {
			Method invocationMethod = getClientMethod(client, message);
			Class returnType = invocationMethod.getReturnType();
			Method typeDescMethod = returnType.getMethod("getTypeDesc", null);
			TypeDesc typeDesc = (TypeDesc)typeDescMethod.invoke(null, new Object[]{});
			return typeDesc;
		}catch (Exception e) {
			logger.error("Error occurred finding the return type.",e);
			throw new GridInvocationException("Error occurred finding the return type.",e);
		}
	}
		
}
