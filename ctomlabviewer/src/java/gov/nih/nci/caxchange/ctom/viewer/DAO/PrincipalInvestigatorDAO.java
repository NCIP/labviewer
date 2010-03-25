/**
 * Copyright Notice. Copyright 2008 ScenPro, Inc ("caBIG(TM)
 * Participant").caXchange was created with NCI funding and is part of the
 * caBIG(TM) initiative. The software subject to this notice and license includes
 * both human readable source code form and machine readable, binary, object
 * code form (the "caBIG(TM) Software"). This caBIG(TM) Software License (the
 * "License") is between caBIG(TM) Participant and You. "You (or "Your") shall mean
 * a person or an entity, and all other entities that control, are controlled
 * by, or are under common control with the entity. "Control" for purposes of
 * this definition means (i) the direct or indirect power to cause the direction
 * or management of such entity, whether by contract or otherwise, or (ii)
 * ownership of fifty percent (50%) or more of the outstanding shares, or (iii)
 * beneficial ownership of such entity. License. Provided that You agree to the
 * conditions described below, caBIG(TM) Participant grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG(TM) Software,
 * including any copyright or patent rights therein, to (i) use, install,
 * disclose, access, operate, execute, reproduce, copy, modify, translate,
 * market, publicly display, publicly perform, and prepare derivative works of
 * the caBIG(TM) Software in any manner and for any purpose, and to have or permit
 * others to do so; (ii) make, have made, use, practice, sell, and offer for
 * sale, import, and/or otherwise dispose of caBIG(TM) Software (or portions
 * thereof); (iii) distribute and have distributed to and by third parties the
 * caBIG(TM) Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties.
 * For sake of clarity, and not by way of limitation, caBIG(TM) Participant shall
 * have no right of accounting or right of payment from You or Your sublicensees
 * for the rights granted under this License. This License is granted at no
 * charge to You. Your downloading, copying, modifying, displaying, distributing
 * or use of caBIG(TM) Software constitutes acceptance of all of the terms and
 * conditions of this Agreement. If you do not agree to such terms and
 * conditions, you have no right to download, copy, modify, display, distribute
 * or use the caBIG(TM) Software. 1. Your redistributions of the source code for
 * the caBIG(TM) Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6 below.
 * Your redistributions in object code form must reproduce the above copyright
 * notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 2. Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: "This product includes software
 * developed by ScenPro, Inc." If You do not include such end-user
 * documentation, You shall include this acknowledgment in the caBIG(TM) Software
 * itself, wherever such third-party acknowledgments normally appear. 3. You may
 * not use the names "ScenPro, Inc", "The National Cancer Institute", "NCI",
 * "Cancer Bioinformatics Grid" or "caBIG(TM)" to endorse or promote products
 * derived from this caBIG(TM) Software. This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either
 * caBIG(TM) Participant, NCI or caBIG(TM), except as required to comply with the
 * terms of this License. 4. For sake of clarity, and not by way of limitation,
 * You may incorporate this caBIG(TM) Software into Your proprietary programs and
 * into any third party proprietary programs. However, if You incorporate the
 * caBIG(TM) Software into third party proprietary programs, You agree that You are
 * solely responsible for obtaining any permission from such third parties
 * required to incorporate the caBIG(TM) Software into such third party proprietary
 * programs and for informing Your sublicensees, including without limitation
 * Your end-users, of their obligation to secure any required permissions from
 * such third parties before incorporating the caBIG(TM) Software into such third
 * party proprietary software programs. In the event that You fail to obtain
 * such permissions, You agree to indemnify caBIG(TM) Participant for any claims
 * against caBIG(TM) Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5.
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG(TM) Software, or any derivative works
 * of the caBIG(TM) Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in
 * this License. 6. THIS caBIG(TM) SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED
 * OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE)
 * ARE DISCLAIMED. IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG(TM) SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.caxchange.ctom.viewer.DAO;

import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;
import gov.nih.nci.caxchange.ctom.viewer.beans.PrincipalInvestigator;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.ctom.ctlab.domain.Investigator;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.handler.InvestigatorHandler;
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
import org.iso._21090.ENXP;
import org.iso._21090.EntityNamePartType;
import org.iso._21090.TEL;

/**
 * @author Lisa Kelley
 */
public class PrincipalInvestigatorDAO
{
	private static final Logger log = Logger.getLogger(PrincipalInvestigatorDAO.class);
	private static final String NBSP = "&nbsp;";

	/**
	 * @param request
	 * @param baseDBForm
	 * @throws Exception
	 */
	public List<PrincipalInvestigator> getInvestigators(Long protocolId, HttpSession session) throws Exception
	{
		List<PrincipalInvestigator> investigators = new ArrayList<PrincipalInvestigator>();

		// using the protocolId obtain the CTEP id for the HCS from CTODS DB
		List<String> ctepIds = getCtepIds(protocolId);

		for (String ctepId : ctepIds)
		{
			// create message elements for caXchange message
			List<MessageElement> messageElements = createMessageElements(ctepId);

			// create caXchange message
			Message requestMessage = COPPAServiceHelper.createMessage(session, "PERSON_BUSINESS_SERVICE", "getPersonByCTEPId", messageElements);
			
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
					PrincipalInvestigator investigatorView = null;
					
					if (responseMessage.getResponse().getResponseStatus().equals(Statuses.SUCCESS))
					{
						Person person = getPOPerson(responseMessage.getResponse());
						
						if (person != null)
						{
							Investigator investigator = createInvestigator(person);
							investigator.setNciId(ctepId);
						
						    persistInvestigator(protocolId, investigator);
						
						    investigatorView = createInvestigatorView(investigator);
						    investigatorView.setUpdatedDate(new Date().toString());
						    investigatorView.setCoppaUpdate("Y"); // lisa - figure out how this is used and why it's not a boolean
						}
					}
					
					if (investigatorView == null)
					{
						// retrieve the HCS details based on the CTEP Identifier
						Investigator investigator = getInvestigator(ctepId);
						investigatorView = createInvestigatorView(investigator);
						investigatorView.setUpdatedDate(investigator.getCtomUpdateDt().toString());
					}
					
					investigators.add(investigatorView);
				}
			}
		}
		
		return investigators;
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
			ctepIds = dao.retrieveCTEPIDForPI(dao.getConnection(), protocolId);
		}
		catch (Exception e)
		{
			log.error("getCtepIds: Exception occurred: ", e);
			throw e;
		}
		
//		ctepIds.add("69443"); // for PO service testing
		
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
	private Person getPOPerson(Response response) throws Exception
	{		
		String resourceName = "/gov/nih/nci/coppa/services/client/client-config.wsdd";
		COPPAServiceHelper helper = new COPPAServiceHelper(); // lisa - try COPPAServiceHelper.getInstance()
		Person person = (Person) helper.getCOPPAObject(response, resourceName, Person.class);
		return person;
	}
	
	/**
	 * @param poPerson
	 * @return
	 */
	private Investigator createInvestigator(Person person)
	{
		Investigator investigator = new Investigator();

		// get the name
		for (Iterator<ENXP> it = person.getName().getPart().iterator(); it.hasNext(); )
		{
			ENXP part = (ENXP) it.next();
			if (EntityNamePartType.FAM == part.getType())
			{
				investigator.setLastName(part.getValue());
			}
			else if (EntityNamePartType.GIV == part.getType())
			{
				if (investigator.getFirstName() == null)
				{
					investigator.setFirstName(part.getValue());
				}
				else
				{
					investigator.setMiddleNAle(part.getValue());
				}
			}
		}
		
		// get the address
		for (Iterator<ADXP> it = person.getPostalAddress().getPart().iterator(); it.hasNext(); )
	    {
			ADXP part = (ADXP) it.next();
			AddressPartType type = part.getType();
			
			if (AddressPartType.AL == type)
			{
				investigator.setStreetAddr(part.getValue() == null ? "" : part.getValue());
			}
			else if (AddressPartType.ADL == type)
			{
				investigator.setStreetAddr(investigator.getStreetAddr() + part.getValue() == null ? "" : part.getValue());
			}
			else if (AddressPartType.CTY == type)
			{
				investigator.setCity(part.getValue() == null ? "" : part.getValue());
			}
			else if (AddressPartType.STA == type)
			{
				investigator.setState(part.getValue() == null ? "" : part.getValue());
			}
			else if (AddressPartType.ZIP == type)
			{
				investigator.setZipCode(part.getValue() == null ? "" : part.getValue());
			}
			else if (AddressPartType.CNT == type)
			{
				investigator.setCountryCode(part.getCode() == null ? "" : part.getCode());
			}
	    }
		
		List<TEL> contactInfo = person.getTelecomAddress().getItem();
		investigator.setTelecomAddr(contactInfo.get(0).getValue());
		investigator.setPhone(contactInfo.get(1).getValue());
		
		return investigator;
	}
	
	/**
	 * Persist the PI details retrieved from COPPA Service into CTODS DB
	 * 
	 * @param investigator
	 * @param protocol
	 */
	private void persistInvestigator(Long protocolId, Investigator investigator)
	{
		Protocol protocol = new Protocol();
		protocol.setId(protocolId);
		protocol.setInvestigator(investigator);
		InvestigatorHandler dao = new InvestigatorHandler();

		try
		{
			dao.update(dao.getConnection(), protocol);
		}
		catch (Exception e)
		{
			log.error("persistInvestigator: Exception occurred: ", e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// note that the HealthCareSite class is a domain class and the HCSite class is a viewer bean used in the jsp
	// lisa - this is a little confusing, maybe change the names later
	private PrincipalInvestigator createInvestigatorView(Investigator investigator)
	{
		PrincipalInvestigator investigatorView = new PrincipalInvestigator();
		
		String firstName  = StringUtils.isEmpty(investigator.getFirstName())  ? "" : investigator.getFirstName() + " ";
		String middleName = StringUtils.isEmpty(investigator.getMiddleNAle()) ? "" : investigator.getMiddleNAle() + " ";
		String lastName   = StringUtils.isEmpty(investigator.getLastName())   ? "" : investigator.getLastName();
		String fullName = firstName + middleName + lastName;
		investigatorView.setName(StringUtils.isEmpty(fullName) ? NBSP : fullName);
		
		String streetAddress = StringUtils.isEmpty(investigator.getStreetAddr())  ? "" : investigator.getStreetAddr() + " ";
		String city          = StringUtils.isEmpty(investigator.getCity())        ? "" : investigator.getCity() + " ";
		String stateCode     = StringUtils.isEmpty(investigator.getState())       ? "" : investigator.getState() + " ";
		String postalCode    = StringUtils.isEmpty(investigator.getZipCode())     ? "" : investigator.getZipCode() + " ";
		String countryCode   = StringUtils.isEmpty(investigator.getCountryCode()) ? "" : investigator.getCountryCode();
		String address = streetAddress + city + stateCode + postalCode + countryCode;
		investigatorView.setAddress(StringUtils.isEmpty(address) ? NBSP : address);
			
		investigatorView.setEmail(StringUtils.isEmpty(investigator.getTelecomAddr()) ? NBSP : investigator.getTelecomAddr());
		investigatorView.setPhone(StringUtils.isEmpty(investigator.getPhone())       ? NBSP : investigator.getPhone());
		
		return investigatorView;
	}

	/**
	 * If the call to COPPA service fails; Retrieve HealthCareSite details from
	 * the DB to display on the UI.
	 * 
	 * @param ctepId
	 * @param baseDBForm
	 */
	private Investigator getInvestigator(String ctepId) throws Exception
	{
		Investigator investigator;
		CTLabDAO dao = new CTLabDAO();
		try
		{
			investigator = dao.retrieveInvestigator(dao.getConnection(), ctepId);
		}
		catch (Exception e)
		{
			log.error("getInvestigator: Exception occurred: ", e);
			throw e;
		}
		
		return investigator;
	}
}
