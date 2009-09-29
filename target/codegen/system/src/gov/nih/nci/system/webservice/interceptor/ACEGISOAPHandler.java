package gov.nih.nci.system.webservice.interceptor;

import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;

import org.acegisecurity.AcegiSecurityException;
import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.AuthenticationProvider;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.rcp.RemoteAuthenticationException;
import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.transport.http.HTTPConstants;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ACEGISOAPHandler extends BasicHandler {

	/**
	 * Default serial version
	 */
	private static final long serialVersionUID = 1L;

	private static AuthenticationProvider authProvider;

	private synchronized void initialize()
	{
		if(authProvider == null)
		{
			try
			{
				HttpServlet srv = (HttpServlet) MessageContext.getCurrentContext().getProperty(HTTPConstants.MC_HTTP_SERVLET);
				ServletContext context = srv.getServletContext();
				WebApplicationContext ctx =  WebApplicationContextUtils.getWebApplicationContext(context);
				authProvider = (AuthenticationProvider) ctx.getBean("authenticationProvider");
			}
			catch(Exception e)
			{
				authProvider = null;
			}
		}
	}
	
	public void init()
	{
		initialize();
	}
	
	public void invoke(MessageContext messageContext) throws AxisFault {
		SecurityContextHolder.clearContext();
		
		Message message = messageContext.getRequestMessage();
		SOAPEnvelope envelope = message.getSOAPEnvelope();
		SOAPHeader header = null;
		try {
			header = envelope.getHeader();
		} catch (SOAPException e) {
			throw new AxisFault("Error retrieving the SOAP header",e);
		}
		Iterator iterator = header.examineAllHeaderElements();
		SOAPHeaderElement headerElement;
		while (iterator.hasNext()) 
		{
			headerElement = (SOAPHeaderElement) iterator.next();
			if (headerElement.getNodeName().equals("security:SecurityHeader"))
			{
				if(authProvider == null)
					initialize();
				
				if(authProvider == null)
					throw new AxisFault("Authentication Provider not present in configuration file");
				
				String username = null;
				String password = null;
				Iterator childElementsIterator = headerElement
						.getChildElements();
				while (childElementsIterator.hasNext()) {
					SOAPElement element = (SOAPElement) childElementsIterator
							.next();
					if (element.getNodeName().equals("security:username"))
						username = element.getValue();
					if (element.getNodeName().equals("security:password"))
						password = element.getValue();
				}
				Authentication auth = new UsernamePasswordAuthenticationToken(username,password);
				
				try
				{
					authProvider.authenticate(auth);
					SecurityContextHolder.getContext().setAuthentication(auth);
					break;
				}
				catch(Exception e)
				{
					String msg="";
					if(e instanceof RemoteAuthenticationException || e instanceof AcegiSecurityException)
						msg = "Error authenticating user:";
					else
						msg = "Unknown error in authenticating user:";
					throw new AxisFault(msg,e);
				}
			}
		}
		return;
	}
}
