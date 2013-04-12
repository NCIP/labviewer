/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.util;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;

import java.io.*;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonUtil.
 * 
 * @author asharma
 */
public class CommonUtil
{

	/** The Constant CONFIG_FILE_1. */
	private static final String CONFIG_FILE_1 = "/baseURL.properties";

	/** The Constant CONFIG_FILE_2. */
	private static final String CONFIG_FILE_2 = "/labviewer.properties";

	/** The Constant log. */
	private static final Logger log = Logger.getLogger(CommonUtil.class);
    public static Properties props1 = new Properties();
    static {
        try {
            props1.load(CommonUtil.class.getResourceAsStream(CONFIG_FILE_1));
        } catch(IOException ioe) {
            log.error("Error reading the config file: " + CONFIG_FILE_1);
        }
    }

    /**
	 * Get the properties from properties file.
	 * 
	 * @param session the session
	 */
	public void getProperties(HttpSession session)
	{

		//try
		//{

            //File file = new File(session.getServletContext().getRealPath(CONFIG_FILE_1));
            //InputStream stream = getClass().getResourceAsStream(CONFIG_FILE_1);
			//props1.load(stream);
            //props1.load(new FileInputStream(CONFIG_FILE_1));
			String caAERSurl = (String) props1.getProperty("BaseURLcaAERS");
			String c3prurl = (String) props1.getProperty("BaseURLC3PR");
			String C3Durl = (String) props1.getProperty("BaseURLC3D");
			String url = (String) props1.getProperty("url");
			String tissueurl = (String) props1.getProperty("tissueURL");

			session.setAttribute("BaseURLcaAERS", caAERSurl);
			session.setAttribute("BaseURLC3PR", c3prurl);
			session.setAttribute("caXchangeURL", url);
			session.setAttribute("BaseURLC3D", C3Durl);
			session.setAttribute("tissueURL", tissueurl);
		/*}
		catch (FileNotFoundException e1)
		{
			log.error("The config file not found: " + CONFIG_FILE_1);
		}
		catch (IOException e1)
		{
			log.error("Error reading the config file: " + CONFIG_FILE_1);
		}
        */
		try
		{
			Properties props = new Properties();
			InputStream stream = getClass().getResourceAsStream(CONFIG_FILE_2);
			props.load(stream);
			String testEnabled = (String) props.getProperty("testEnabled");
			session.setAttribute("testEnabled", testEnabled);
			String webssoEnabled = (String) props.getProperty("websso.enabled");
			session.setAttribute("webssoEnabled", webssoEnabled);
			String webssoCasServer = (String) props.getProperty("websso.cas.server");
			session.setAttribute("webssoCasServer", webssoCasServer);
			String webssoCasServerPort = (String) props.getProperty("websso.cas.server.port");
			session.setAttribute("webssoCasServerPort", webssoCasServerPort);
			String version = (String) props.getProperty("version");
			session.setAttribute("version", version);
			// hotlink type can be _blank,_self,${hotLink_NAME}:value set in
			// properties file
			String hotLinkType = (String) props.getProperty("hotLink_Type");
			session.setAttribute("hotLinkType", hotLinkType);
			String propertyFilePath = (String) props.getProperty("propertyFilePath");
			session.setAttribute("propertyFilePath", propertyFilePath);

		}
		catch (FileNotFoundException e1)
		{
			log.error("The config file not found: " + CONFIG_FILE_2);
		}
		catch (IOException e1)
		{
			log.error("Error reading the config file: " + CONFIG_FILE_2);
		}
	}

	/**
	 * Check user login.
	 * 
	 * @param session the session
	 * 
	 * @return the string
	 */
	public String checkUserLogin(HttpSession session)
	{

		String userEmail = null;
		// If logged in via WEBSSO
		String gridIDentity = (String) session.getAttribute("CAGRID_SSO_GRID_IDENTITY");
		if (gridIDentity != null)
		{
			int beginIndex = gridIDentity.lastIndexOf("=");
			int endIndex = gridIDentity.length();
			userEmail = gridIDentity.substring(beginIndex + 1, endIndex);
		}
		// if logged in via CSM
		if (userEmail == null)
		{
			LoginForm loginForm = (LoginForm) session.getAttribute(DisplayConstants.LOGIN_OBJECT);
			if (loginForm != null)
			{
				userEmail = loginForm.getLoginId();
			}

		}
		return userEmail;
	}

	/**
	 * Clear menu session data.
	 * 
	 * @param session the session
	 */
	public void clearMenuSessionData(HttpSession session)
	{

		session.removeAttribute(DisplayConstants.CURRENT_ACTION);
		session.removeAttribute(DisplayConstants.CURRENT_FORM);
		session.removeAttribute("StudySearchForm");
		session.removeAttribute("ParticipantSearchForm");
		session.removeAttribute("LabActivitiesSearchForm");
		session.removeAttribute("HealthCareSiteForm");
		session.removeAttribute("PIForm");
		session.removeAttribute(DisplayConstants.SEARCH_RESULT);
		session.removeAttribute(DisplayConstants.SEARCH_RESULT_COUNT);
		session.removeAttribute("pageTitle");
		session.removeAttribute("StudyTitle");
		session.removeAttribute("studyId");
		session.removeAttribute("ID");
		session.removeAttribute("participantId");
		session.removeAttribute("patientId");
	}

	/**
	 * Clear session data.
	 * 
	 * @param session the session
	 */
	public void clearSessionData(HttpSession session)
	{

		clearMenuSessionData(session);
	}

	/**
	 * Clear study session data.
	 * 
	 * @param session the session
	 */
	public void clearStudySessionData(HttpSession session)
	{

		clearMenuSessionData(session);
	}

	/**
	 * Clear participant session data.
	 * 
	 * @param session the session
	 */
	public void clearParticipantSessionData(HttpSession session)
	{

		clearMenuSessionData(session);
	}

	/**
	 * Clear lab session data.
	 * 
	 * @param session the session
	 */
	public void clearLabSessionData(HttpSession session)
	{

		session.removeAttribute(DisplayConstants.CURRENT_FORM);
		session.removeAttribute(DisplayConstants.SEARCH_RESULT);
		session.removeAttribute(DisplayConstants.SEARCH_RESULT_COUNT);
		session.removeAttribute("ALL_SEARCH_RESULT");

	}

}
