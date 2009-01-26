package gov.nih.nci.caXchange.outbound.operations;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;
import javax.security.auth.Subject;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.axis.AxisFault;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.globus.gsi.GlobusCredential;
import org.globus.wsrf.encoding.DeserializationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.utils.URI.MalformedURIException;

import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridInvocationStrategy;
import gov.nih.nci.caXchange.outbound.GridMessage;
import gov.nih.nci.cagrid.common.Utils;

/**
 * A generic class that can be configured to invoke an operation on a grid service.
 * 
 * @author Ekagra Software Technologies
 *
 */
public class GenericInvocationStrategy extends GridInvocationStrategy {

	protected Properties caxchangeProps;
	protected String gridClientClassName;
	protected String requestPayloadClassName;
	protected String operationName;
	protected String returnTypeNameSpace;
	protected String returnTypeElement;
	protected boolean useCredentials=true;

	private static Logger logger = LogManager.getLogger(GenericInvocationStrategy.class); 
	
	@Override
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
			}else if(isUseCredentials()){
				throw new GridInvocationException("no credentials found");
			}
			String url=getServiceUrl();
			logger.debug("The service url is:"+url);
			Object  client = getNewGridClient(url, cred);
			Object requestPayload = getRequestPayload(client, message);
            Object returnObject = invokeGridOperation(client, requestPayload);
		   
			//commented to remove commit
			//client.commit(request);
            GridInvocationResult result = null;
            if ((returnTypeNameSpace != null)&&(returnTypeElement != null)){
            	 QName returnType = new QName(returnTypeNameSpace,returnTypeElement);
            	   result = getServiceResponsePayload(client, returnObject, returnType);
            }else {
            	result = getSuccessResult();
            }
            return result;
		} catch (AxisFault af) {
			logger.error("Failed to invoke grid service. "+serviceUrl, af);
			GridInvocationException gie =
				new GridInvocationException(af.getFaultString(), af);
			if(af.getCause() instanceof ConnectException) {
				gie.setCanRetry(true);
			}
			throw gie;
		} catch (Exception e) {
			logger.error("Failed to invoke grid service."+serviceUrl, e);
			throw new GridInvocationException(e.getMessage(), e);
		}
	}
	/**
	 * Returns a new instance of the grid client identified by gridClientClassName.
	 * 
	 * @param url
	 * @param creds
	 * @return
	 * @throws GridInvocationException
	 */
	public Object getNewGridClient(String url, GlobusCredential creds) throws  GridInvocationException {
		if ((gridClientClassName==null)||(gridClientClassName.equals(""))) {
			logger.error("Grid class name is not configured for GenericInvocationStrategy.");
			throw new GridInvocationException("Grid class name is not configured for GenericInvocationStrategy.");
		}
		Class gridClientClass = null;
		try{
		  gridClientClass = this.getClass().getClassLoader().loadClass(gridClientClassName);
		}catch(ClassNotFoundException cnfe) {
			logger.error("Grid client class not found:"+gridClientClassName);
			throw new GridInvocationException("Grid client class not found:"+gridClientClassName);
		}catch(NoClassDefFoundError ncdfe) {
			logger.error("Grid client classes not found. Grid client libraries are not in the servicemix classpath.");
			throw new GridInvocationException("Grid client classes not found. Grid client libraries are not in the servicemix classpath."+ncdfe.getMessage());
		}
		Constructor gridClientConstructor=null;
		Object client=null;
		boolean secureConstructor=true;
		try {
		  if (isUseCredentials()) {	
		     gridClientConstructor = gridClientClass.getConstructor(String.class, GlobusCredential.class);
		  } else {
			  gridClientConstructor = gridClientClass.getConstructor(String.class);
			  secureConstructor=false;
		  }
		}catch(NoSuchMethodException nsme) {
			try {
			  gridClientConstructor = gridClientClass.getConstructor(String.class);
			  secureConstructor=false;
			 }catch(NoSuchMethodException nsme2) {
				logger.error("No constructor found for creating the new grid client:"+gridClientClassName,nsme2);
				throw new GridInvocationException("No constructor found for creating the new grid client:"+gridClientClassName);
			}
		}
		try {
			if (secureConstructor){
				  client = gridClientConstructor.newInstance(url,creds);
			}else {
				  client = gridClientConstructor.newInstance(url);
			}
		}catch(InstantiationException ie){
			logger.error("Error creating grid client for "+gridClientClassName,ie);
			throw new GridInvocationException("Error creating grid client for "+gridClientClassName,ie);
	    }catch(InvocationTargetException mue){
			logger.error("Error constructing new client for:"+gridClientClassName+" url:"+serviceUrl,mue);
			throw new GridInvocationException("Error constructing new client for:"+gridClientClassName+" url:"+serviceUrl+" "+mue.getMessage(),mue);
		}catch(IllegalAccessException re) {
			logger.error("Error constructing new client for:"+gridClientClassName+" url:"+serviceUrl,re);
			throw new GridInvocationException("Error constructing new client for:"+gridClientClassName+" url:"+serviceUrl+" "+re.getMessage(),re);
		}
		return client;
	}
	
	public Object getRequestPayload(Object client, GridMessage message) throws GridInvocationException {
		Class requestPayloadClass=null;
		try {
		   requestPayloadClass = Class.forName(requestPayloadClassName);
		}catch(ClassNotFoundException cnfe) {
			logger.error("Request payload class not found:"+requestPayloadClassName,cnfe);
			throw new GridInvocationException("Request payload class not found:"+requestPayloadClassName);
		}
		SourceTransformer transformer = new SourceTransformer();
		InputStream deseralizeStream = client.getClass().getResourceAsStream(
						"client-config.wsdd");
		Object requestPayload = null;
		try {
		    StringReader reader = new StringReader(transformer.toString(message.getPayload()));
		    requestPayload = Utils.deserializeObject(
				reader, requestPayloadClass, deseralizeStream);
		}catch(TransformerException te) {
			logger.error("Error transforming payload to string.",te);
			throw new GridInvocationException("Error transforming payload to string.",te);
		}catch(SAXException se) {
			logger.error("Error parsing request payload .",se);
			throw new GridInvocationException("Error parsing request payload.",se);
		}catch(DeserializationException de) {
			logger.error("Error parsing request payload .",de);
			throw new GridInvocationException("Error parsing request payload.",de);
		}
		return requestPayload;
		
	}
	
	public Object invokeGridOperation(Object client, Object requestPayload) throws GridInvocationException {
		Class gridClientClass=null;
		Class requestPayloadClass = null; 
		try{
			  gridClientClass = Class.forName(gridClientClassName);
	    }catch(ClassNotFoundException cnfe) {
				logger.error("Grid client class not found:"+gridClientClassName);
				throw new GridInvocationException("Grid client class not found:"+gridClientClassName);
		}
		try {
			  requestPayloadClass = Class.forName(requestPayloadClassName);
		}catch(ClassNotFoundException cnfe) {
				logger.error("Request payload class not found:"+requestPayloadClassName,cnfe);
				throw new GridInvocationException("Request payload class not found:"+requestPayloadClassName);
		}
		try {
		  Method gridOperation = gridClientClass.getMethod(operationName,requestPayloadClass);
		  Object returnObject = gridOperation.invoke(client, requestPayload);
		  return returnObject;
		}catch(NoSuchMethodException nsme) {
			logger.error("Method not found in the client:"+operationName,nsme);
			throw new GridInvocationException("Method not found in the client:"+operationName,nsme);
		}catch(IllegalAccessException iae) {
			logger.error("Illegal access to "+operationName,iae);
			throw new GridInvocationException("Illegal access to "+operationName,iae);
		}catch(InvocationTargetException ite) {
			logger.error("Exception invoking "+operationName,ite);
			Throwable cause = ite.getCause();
			String errorDetails = ite.getMessage()+":"+((cause==null)?"":cause.getMessage());
			throw new GridInvocationException("Exception invoking "+operationName+" on "+gridClientClassName+"."+errorDetails,ite);
		}
	}
	
	public GridInvocationResult getSuccessResult() throws Exception{
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
	}
	
	
	public GridInvocationResult getServiceResponsePayload(Object client,Object reply, QName returnType) throws Exception {
		SourceTransformer transformer = new SourceTransformer();
		InputStream seralizeStream = client.getClass().getResourceAsStream(
						"client-config.wsdd");
		String serviceResponsePayload = null;
		try {
		    StringWriter writer = new StringWriter();
		    Utils.serializeObject(reply, returnType, writer, seralizeStream);
		    serviceResponsePayload = writer.getBuffer().toString();
		    final Document resp = new SourceTransformer()
			.toDOMDocument(new StringSource(serviceResponsePayload));
	        return new GridInvocationResult() {
		                    public Node getResult() {
			                   return resp.getDocumentElement();
		                    }
		                    public boolean isFault() {
			                    return false;
		                    }
	               };		    
		}catch(Exception e) {
			logger.error("Error transforming response payload to string.",e);
			throw e;
		}
		
	}	
	
	public Properties getCaxchangeProps() {
		return caxchangeProps;
	}

	public void setCaxchangeProps(Properties caxchangeProps) {
		this.caxchangeProps = caxchangeProps;
	}	
	
	public String getGridClientClassName() {
		return this.gridClientClassName;
	}
	
	public void setGridClientClassName(String gridClientClassName) {
		this.gridClientClassName =  gridClientClassName;
	}
	
	public String getRequestPayloadClassName() {
		return this.requestPayloadClassName;
	}
	
	public void setRequestPayloadClassName(String requestPayloadClassName) {
		this.requestPayloadClassName =  requestPayloadClassName;
	}
	
	public String getOperationName() {
		return operationName;
	}
	
	public void setOperationName(String operationName){
		this.operationName =operationName;
	}
	public String getReturnTypeNameSpace() {
		return returnTypeNameSpace;
	}
	public void setReturnTypeNameSpace(String returnTypeNameSpace) {
		this.returnTypeNameSpace = returnTypeNameSpace;
	}
	public String getReturnTypeElement() {
		return returnTypeElement;
	}
	public void setReturnTypeElement(String returnTypeElement) {
		this.returnTypeElement = returnTypeElement;
	}
	public boolean isUseCredentials() {
		return useCredentials;
	}
	public void setUseCredentials(boolean useCredentials) {
		this.useCredentials = useCredentials;
	}

}
