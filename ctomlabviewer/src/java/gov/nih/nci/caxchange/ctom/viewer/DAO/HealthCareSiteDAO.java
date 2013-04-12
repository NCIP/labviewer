/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.DAO;

import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;
import gov.nih.nci.caxchange.ctom.viewer.beans.HCSite;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.ctom.ctlab.domain.HealthCareSite;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.handler.HealthCareSiteHandler;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import org.apache.axis.message.MessageElement;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.iso._21090.ADXP;
import org.iso._21090.AddressPartType;

/**
 * @author Lisa Kelley
 */
public class HealthCareSiteDAO
{
	private static final Logger log = Logger.getLogger(HealthCareSiteDAO.class);
	private static final String NBSP = "&nbsp;";

	/**
	 * @param request
	 * @param baseDBForm
	 * @throws Exception
	 */
	public List<HCSite> getHealthCareSites(Long protocolId, HttpSession session) throws Exception
	{
		List<HCSite> healthCareSites = new ArrayList<HCSite>();

		// using the protocolId obtain the CTEP id for the HCS from CTODS DB
		List<String> ctepIds = getCtepIds(protocolId);

		for (String ctepId : ctepIds)
		{
			// create message elements for caXchange message
			List<MessageElement> messageElements = createMessageElements(ctepId);

			// create caXchange message
			Message requestMessage = COPPAServiceHelper.createMessage(session, "ORGANIZATION_BUSINESS_SERVICE", "getOrganizationByCTEPId", messageElements);
			
			CaXchangeRequestProcessorClient client = COPPAServiceHelper.getCaXchangeClient(session);

			boolean responseReceived = false;
			int attempts = 0;

			while (!responseReceived)
			{
				ResponseMessage responseMessage = null;
				try
				{
					responseMessage = client.processRequestSynchronously(requestMessage);
					responseReceived = true;
				}
				catch (Exception e)
				{
					attempts++;
					log.info("No response from caXchange(attempt #" + attempts + ")", e);
					if (attempts > 25)
					{
						log.error("Never got a response from caXchange");
						throw new Exception("No response from caXchange");
					}
					
					Thread.sleep(1000); // sleep 1 second
				}
				
				if (responseReceived)
				{
					// note that the HealthCareSite class is a domain class and the HCSite class is a viewer bean used in the jsp
					// lisa - this is a little confusing, maybe change the names later
					HCSite healthCareSiteView = null;
					
					if (responseMessage.getResponse().getResponseStatus().equals(Statuses.SUCCESS))
					{
						Organization organization = getPOOrganization(responseMessage.getResponse());
						
						if (organization != null)
						{
						    HealthCareSite healthCareSite = createHealthCareSite(organization);
						    healthCareSite.setNciInstituteCd(ctepId);
						
						    persistHealthCareSite(protocolId, healthCareSite);
						
						    healthCareSiteView = createHealthCareSiteView(healthCareSite);
						    healthCareSiteView.setUpdatedDate(new Date().toString());
						    healthCareSiteView.setCoppaUpdate("Y"); // lisa - figure out how this is used and why it's not a boolean
						}
					}
					
					if (healthCareSiteView == null)
					{
						// retrieve the HCS details based on the CTEP Identifier
						HealthCareSite healthCareSite = getHealthCareSite(ctepId);
						healthCareSiteView = createHealthCareSiteView(healthCareSite);
						healthCareSiteView.setUpdatedDate(healthCareSite.getCtomUpdateDt().toString());
					}
					
					healthCareSites.add(healthCareSiteView);
				}
			}
		}
		
		return healthCareSites;
	}
	
	/**
	 * @param protocolId
	 * @return
	 */
	private List<String> getCtepIds(Long protocolId) throws Exception
	{
		List<String> ctepIds = null;
		
		try
		{
			CTLabDAO dao = new CTLabDAO();
			ctepIds = dao.retrieveCTEPIDForHCS(dao.getConnection(), protocolId);
		}
		catch (Exception e)
		{
			log.error("getCtepIds: Exception occurred: ", e);
			throw e;
		}
		
//		ctepIds.add("MN026"); // for PO service testing
//		ctepIds.add("CTEP");  // for PO service testing
		
		return ctepIds;
	}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private List<MessageElement> createMessageElements(String ctepId) throws Exception
	{
		List<MessageElement> messageElements = new ArrayList<MessageElement>();
		
		Id id = new Id();
		id.setExtension(ctepId);
		
		QName qName = new QName("http://po.coppa.nci.nih.gov", "Id");
		
		// create stream containing the WSDD configuration
		InputStream wsdd = getClass().getResourceAsStream("/gov/nih/nci/coppa/services/client/client-config.wsdd");
		
		// create message element for the object
		messageElements.add(COPPAServiceHelper.createMessageElement(id, qName, wsdd));
		
		return messageElements;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private Organization getPOOrganization(Response response) throws Exception
	{		
		String resourceName = "/gov/nih/nci/coppa/services/client/client-config.wsdd";
		COPPAServiceHelper helper = new COPPAServiceHelper(); // lisa - try COPPAServiceHelper.getInstance()
		Organization organization = (Organization) helper.getCOPPAObject(response, resourceName, Organization.class);
		return organization;
	}

	/**
	 * @param poPerson
	 * @return
	 */
	private HealthCareSite createHealthCareSite(Organization organization)
	{
		HealthCareSite healthCareSite = new HealthCareSite();

		// get the name
		healthCareSite.setName(organization.getName().getPart().get(0).getValue());
		
		// get the address
		for (Iterator<ADXP> it = organization.getPostalAddress().getPart().iterator(); it.hasNext(); )
	    {
			ADXP part = (ADXP) it.next();
			AddressPartType type = part.getType();
			
			if (AddressPartType.AL == type)
			{
				healthCareSite.setStreetAddr(part.getValue() == null ? "" : part.getValue());
			}
			else if (AddressPartType.ADL == type)
			{
				healthCareSite.setStreetAddr(healthCareSite.getStreetAddr() + part.getValue() == null ? "" : part.getValue());
			}
			else if (AddressPartType.CTY == type)
			{
				healthCareSite.setCity(part.getValue() == null ? "" : part.getValue());
			}
			else if (AddressPartType.STA == type)
			{
				healthCareSite.setStateCode(part.getValue() == null ? "" : part.getValue());
			}
			else if (AddressPartType.ZIP == type)
			{
				healthCareSite.setPostalCode(part.getValue() == null ? "" : part.getValue());
			}
			else if (AddressPartType.CNT == type)
			{
				healthCareSite.setCountryCode(part.getCode() == null ? "" : part.getCode());
			}
	    }
		
		// get the Telecom Address
		healthCareSite.setTelecomAddr(organization.getTelecomAddress().getItem().get(0).getValue());
		
		return healthCareSite;
	}

	/**
	 * @param investigator
	 */
	private void persistHealthCareSite(Long protocolId, HealthCareSite healthCareSite)
	{
		Protocol protocol = new Protocol();
		protocol.setId(protocolId);
		protocol.setHealthCareSite(healthCareSite);
		HealthCareSiteHandler dao = new HealthCareSiteHandler();
		
		try
		{			
			dao.update(dao.getConnection(), protocol);
		}
		catch (Exception e)
		{
			log.error("persistHealthCareSite: Exception occurred: ", e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// note that the HealthCareSite class is a domain class and the HCSite class is a viewer bean used in the jsp
	// lisa - this is a little confusing, maybe change the names later
	private HCSite createHealthCareSiteView(HealthCareSite healthCareSite)
	{
		HCSite healthCareSiteView = new HCSite();
		// lisa - figure out why these fields are not 'null' when not populated
		healthCareSiteView.setName(StringUtils.isEmpty(healthCareSite.getName()) ? NBSP : healthCareSite.getName());
		
		String streetAddress = StringUtils.isEmpty(healthCareSite.getStreetAddr())  ? "" : healthCareSite.getStreetAddr() + " ";
		String city          = StringUtils.isEmpty(healthCareSite.getCity())        ? "" : healthCareSite.getCity() + " ";
		String stateCode     = StringUtils.isEmpty(healthCareSite.getStateCode())   ? "" : healthCareSite.getStateCode() + " ";
		String postalCode    = StringUtils.isEmpty(healthCareSite.getPostalCode())  ? "" : healthCareSite.getPostalCode() + " ";
		String countryCode   = StringUtils.isEmpty(healthCareSite.getCountryCode()) ? "" : healthCareSite.getCountryCode();
		String address = streetAddress + city + stateCode + postalCode + countryCode;
		healthCareSiteView.setAddress(StringUtils.isEmpty(address) ? NBSP : address);
			
		healthCareSiteView.setEmail(StringUtils.isEmpty(healthCareSite.getTelecomAddr()) ? NBSP : healthCareSite.getTelecomAddr());
		
		return healthCareSiteView;
	}

	/**
	 * If the call to COPPA service fails; Retrieve HealthCareSite details from
	 * the DB to display on the UI.
	 * 
	 * @param ctepId
	 * @param baseDBForm
	 */
	private HealthCareSite getHealthCareSite(String ctepId) throws Exception
	{
		HealthCareSite healthCareSite;
		CTLabDAO dao = new CTLabDAO();
		try
		{
			healthCareSite = dao.retrieveHealCareSite(dao.getConnection(), ctepId);
		}
		catch (Exception e)
		{
			log.error("getHealthCareSite: Exception occurred: ", e);
			throw e;
		}
		
		return healthCareSite;
	}
}
