package gov.nih.nci.system.security.authentication.cagrid.executor.jboss;

import java.util.Hashtable;

import javax.naming.NamingException;

import org.springframework.jndi.JndiTemplate;

public class JNDIBinder
{

	public JNDIBinder(String jndiName, JndiTemplate template) throws NamingException
	{
		template.bind(jndiName, new Hashtable());
	}

}