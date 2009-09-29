package gov.nih.nci.system.web.struts.action;

import java.util.Map;

//import gov.nih.nci.common.util.Constant;
//import gov.nih.nci.common.util.SecurityConfiguration;
//import gov.nih.nci.system.applicationservice.AuthenticationException;
//import gov.nih.nci.system.server.mgmt.SecurityEnabler;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1234567890L;
//	private SecurityEnabler securityEnabler;
	private Map session;

	private static Logger log = Logger.getLogger(Login.class
			.getName());
	
	public String execute() throws Exception {

		log.debug("username: " + getUsername());
		log.debug("password: " + getPassword());
		
		if (isInvalid(getUsername()) || isInvalid(getPassword()))
			return INPUT;

//		try {
//			log.debug("Will now try to authenticate user");
//			securityEnabler = new SecurityEnabler(SecurityConfiguration.getApplicationName());
//			securityEnabler.authenticate(getUsername(), getPassword());
//			log.debug("User has been successfully authenticated");
//		} catch (AuthenticationException ae){
//			log.error("Authentication Exception caught: ", ae);
//			return INPUT;
//		}
//
//		session.put(Constant.USER_NAME, getUsername());
//		session.put(Constant.PASSWORD, getPassword());
		
		return SUCCESS;
	}

	private boolean isInvalid(String value) {
		return (value == null || value.length() == 0);
	}

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSession(Map session) {
		this.session = session;
	}
}