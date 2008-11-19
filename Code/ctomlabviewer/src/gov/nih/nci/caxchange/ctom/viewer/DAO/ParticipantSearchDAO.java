/**
 
* Copyright Notice.  Copyright 2008  Scenpro, Inc [Insert name of organization funded to participate in caBIG� ] (�caBIG� Participant�).  ____caXchange_____________ [insert name of caBIG� software program] was created with NCI funding and is part of the caBIG� initiative. The software subject to this notice and license includes both human readable source code form and machine readable, binary, object code form (the �caBIG� Software�).
* This caBIG� Software License (the �License�) is between caBIG� Participant and You.  �You (or �Your�) shall mean a person or an entity, and all other entities that control, are controlled by, or are under common control with the entity.  �Control� for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) beneficial ownership of such entity.  
* License.  Provided that You agree to the conditions described below, caBIG� Participant grants You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights in the caBIG� Software, including any copyright or patent rights therein, to (i) use, install, disclose, access, operate, execute, reproduce, copy, modify, translate, market, publicly display, publicly perform, and prepare derivative works of the caBIG� Software in any manner and for any purpose, and to have or permit others to do so; (ii) make, have made, use, practice, sell, and offer for sale, import, and/or otherwise dispose of caBIG� Software (or portions thereof); (iii) distribute and have distributed to and by third parties the caBIG� Software and any modifications and derivative works thereof; and (iv) sublicense the foregoing rights set out in (i), (ii) and (iii) to third parties, including the right to license such rights to further third parties.  For sake of clarity, and not by way of limitation, caBIG� Participant shall have no right of accounting or right of payment from You or Your sublicensees for the rights granted under this License.  This License is granted at no charge to You.  Your downloading, copying, modifying, displaying, distributing or use of caBIG� Software constitutes acceptance of all of the terms and conditions of this Agreement.  If you do not agree to such terms and conditions, you have no right to download, copy, modify, display, distribute or use the caBIG� Software.  
* 1.	Your redistributions of the source code for the caBIG� Software must retain the above copyright notice, this list of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials provided with the distribution, if any.
* 2.	Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: �This product includes software developed by Scenpro, Inc [insert name of organization funded to participate in caBIG�].�  If You do not include such end-user documentation, You shall include this acknowledgment in the caBIG� Software itself, wherever such third-party acknowledgments normally appear.
* 3.	You may not use the names  �Scenpro, Inc [insert name of organization funded to participate in caBIG�]�, �The National Cancer Institute�, �NCI�, �Cancer Bioinformatics Grid� or �caBIG�� to endorse or promote products derived from this caBIG� Software.  This License does not authorize You to use any trademarks, service marks, trade names, logos or product names of either caBIG� Participant, NCI or caBIG�, except as required to comply with the terms of this License.
* 4.	For sake of clarity, and not by way of limitation, You may incorporate this caBIG� Software into Your proprietary programs and into any third party proprietary programs.  However, if You incorporate the caBIG� Software into third party proprietary programs, You agree that You are solely responsible for obtaining any permission from such third parties required to incorporate the caBIG� Software into such third party proprietary programs and for informing Your sublicensees, including without limitation Your end-users, of their obligation to secure any required permissions from such third parties before incorporating the caBIG� Software into such third party proprietary software programs.  In the event that You fail to obtain such permissions, You agree to indemnify caBIG� Participant for any claims against caBIG� Participant by such third parties, except to the extent prohibited by law, resulting from Your failure to obtain such permissions.
* 5.	For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses of modifications of the caBIG� Software, or any derivative works of the caBIG� Software as a whole, provided Your use, reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.
* 6.	THIS caBIG� SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN NO EVENT SHALL THE Scenpro, Inc [insert name of name of organization funded to participate in caBIG�] OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG� SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* 
 */
package gov.nih.nci.caxchange.ctom.viewer.DAO;

import gov.nih.nci.caxchange.ctom.viewer.forms.ParticipantSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.ParticipantSearchResult;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.SearchResult;
import gov.nih.nci.labhub.domain.II;
import gov.nih.nci.labhub.domain.SubjectAssignment;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.cql.CQLAssociation;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLGroup;
import gov.nih.nci.system.query.cql.CQLLogicalOperator;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author asharma
 *
 */
public class ParticipantSearchDAO extends HibernateDaoSupport{
	
	private static final Logger logDB = Logger.getLogger(ParticipantSearchDAO.class);
	private static final String CONFIG_FILE = "/baseURL.properties";
	
	/**
	 * SearchObjects queries the database with the user entered search criteria.
	 * returns the search results
	 * @param mapping
	 * @param form
	 * @param request
	 * @param errors
	 * @param messages
	 * @return searchResult
	 * @throws Exception
	 */
	public SearchResult searchObjects(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			ActionErrors errors, ActionMessages messages) throws Exception
	{
		HashMap map = new HashMap();
		HttpSession session = request.getSession();
		List <ParticipantSearchResult>allLarList = new ArrayList<ParticipantSearchResult>();
		 
		//search form 
		ParticipantSearchForm pForm =(ParticipantSearchForm) form;
		try
		{
			ApplicationService appService = ApplicationServiceProvider.getApplicationService();
			String searchTerm = "%"+pForm.getParticipantPhrase().trim()+"%"; 
			String studyId = (String)session.getAttribute("studyId")!=null?(String)session.getAttribute("studyId"):"";
			
			// Create the query to get Study object
			CQLQuery query = new CQLQuery();
			CQLObject target = new CQLObject();
			target.setName("gov.nih.nci.labhub.domain.SubjectAssignment");
			
			// Set the subject Identifier on the association to II
			CQLAssociation subjectAssignmentAssociation1 = new CQLAssociation();
			subjectAssignmentAssociation1.setName("gov.nih.nci.labhub.domain.II");
			subjectAssignmentAssociation1.setTargetRoleName("participantIdentifier");
			subjectAssignmentAssociation1.setAttribute(new CQLAttribute("extension",
					CQLPredicate.EQUAL_TO, pForm.getParticipantPhrase().trim()));
			
			// Set the search term conditions
			CQLGroup group = new CQLGroup();
			group.addAttribute(new CQLAttribute("firstName",
					CQLPredicate.LIKE, searchTerm));
			group.addAttribute(new CQLAttribute("lastName",
					CQLPredicate.LIKE, searchTerm));
			group.addAssociation(subjectAssignmentAssociation1);
			
			group.setLogicOperator(CQLLogicalOperator.OR);
			
			// get the participant
			CQLAssociation participantAssociation = new CQLAssociation();
			participantAssociation.setName("gov.nih.nci.labhub.domain.Participant");
			participantAssociation.setTargetRoleName("participant");
			participantAssociation.setGroup(group);
			
			// Now get to StudySite
			CQLAssociation studySiteAssignmentAssociation = new CQLAssociation();
			studySiteAssignmentAssociation.setName("gov.nih.nci.labhub.domain.StudySite");
			studySiteAssignmentAssociation.setTargetRoleName("studySite");

			// Now get to Study
			CQLAssociation studyAssociation = new CQLAssociation();
			studyAssociation.setName("gov.nih.nci.labhub.domain.Study");
			studyAssociation.setTargetRoleName("study");
			studySiteAssignmentAssociation.setAssociation(studyAssociation);
			
			// Now set the study identifier on the association to II
			CQLAssociation iiAssociation = new CQLAssociation();
			iiAssociation.setName("gov.nih.nci.labhub.domain.II");
			iiAssociation.setTargetRoleName("studyIdentifier");
			iiAssociation.setAttribute(new CQLAttribute("extension",
					CQLPredicate.EQUAL_TO, studyId.trim()));
			studyAssociation.setAssociation(iiAssociation);
			
			CQLGroup finalgroup = new CQLGroup();
			finalgroup.addAssociation(participantAssociation);
			finalgroup.addAssociation(studySiteAssignmentAssociation);
			finalgroup.setLogicOperator(CQLLogicalOperator.AND);
			target.setGroup(finalgroup);
			
    		query.setTarget(target);
    		// Query the database
			List resultList = appService.query(query);
			allLarList = printRecord(resultList,request);
   			
		}
		catch (Exception ex)
		{
			logDB.error(ex.getMessage());
		}
		SearchResult searchResult = new SearchResult();
		searchResult.setSearchResultObjects(allLarList);
		request.getSession().setAttribute("SEARCH_RESULT_PART", searchResult);
		return searchResult;
	}

	/**
	 * printRecord creates the view object that will properly display the results
	 * @param resultList
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	private ArrayList<ParticipantSearchResult> printRecord(List resultList, HttpServletRequest request)
		throws ParseException
	{
	
		ArrayList<ParticipantSearchResult> list = new ArrayList<ParticipantSearchResult>();
		String participantSearchTerm = null;
		String participantGridId=null;
		String studyId="";
	
		if (resultList == null)
			return null;

		if (resultList != null)
		{
			for (Iterator resultsIterator = resultList.iterator(); resultsIterator.hasNext();)
			{
				SubjectAssignment participant = (SubjectAssignment) resultsIterator.next();
				ParticipantSearchResult participantSearchResult = new ParticipantSearchResult();
				//retrieve the study Id
				Collection<II>partidentifiers =participant.getStudySite().getStudy().getStudyIdentifier();
				II partidentifier = retrieveIdentifier(partidentifiers);
				participantSearchResult.setStudyId(partidentifier.getExtension());
				studyId = partidentifier.getRoot();
				request.getSession().setAttribute("studyGridId", studyId);
				//retrieve the first name
				participantSearchResult.setFirstName(participant.getParticipant().getFirstName());
				//retrieve the last name
				participantSearchResult.setLastName(participant.getParticipant().getLastName());
				//retrieve the extension and root for the participant
				Collection<II>identifiers = participant.getParticipant().getParticipantIdentifier();
				II identifier = retrieveIdentifier(identifiers);
				participantSearchResult.setPatientId(identifier.getExtension());
				participantGridId= identifier.getRoot();
				participantSearchResult.setGridId(participantGridId);
				request.getSession().setAttribute("participantGridId", participantGridId);
				
				//TO DO: set the details link
				String details ="https://cbvapp-d1017.nci.nih.gov:28443/c3pr/pages/participant/viewParticipant?participantId=28";
				participantSearchResult.setDetails(details);
				list.add(participantSearchResult);
			}
	    }	
		
		return list;
	}
	
	/**
	 * @param identifiers
	 * @return
	 */
	private II retrieveIdentifier(Collection<II> identifiers)
	{
		//String identifier = null;
		
		Iterator<II> idIterator = identifiers.iterator();
		II identifier = idIterator.next();
		
		return identifier;
	}

	/**
	 * @param identifiers
	 * @return
	 */
	private String retriveGridId(Collection<II> identifiers)
	{
		String identifier = null;
		
		Iterator<II> idIterator = identifiers.iterator();
		identifier = idIterator.next().getRoot();
		
		return identifier;
	}
	
}
