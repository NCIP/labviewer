/**
 * Copyright Notice. Copyright 2008 ScenPro, Inc (“caBIG™
 * Participant”).caXchange was created with NCI funding and is part of the
 * caBIG™ initiative. The software subject to this notice and license includes
 * both human readable source code form and machine readable, binary, object
 * code form (the “caBIG™ Software”). This caBIG™ Software License (the
 * “License”) is between caBIG™ Participant and You. “You (or “Your”) shall mean
 * a person or an entity, and all other entities that control, are controlled
 * by, or are under common control with the entity. “Control” for purposes of
 * this definition means (i) the direct or indirect power to cause the direction
 * or management of such entity, whether by contract or otherwise, or (ii)
 * ownership of fifty percent (50%) or more of the outstanding shares, or (iii)
 * beneficial ownership of such entity. License. Provided that You agree to the
 * conditions described below, caBIG™ Participant grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG™ Software,
 * including any copyright or patent rights therein, to (i) use, install,
 * disclose, access, operate, execute, reproduce, copy, modify, translate,
 * market, publicly display, publicly perform, and prepare derivative works of
 * the caBIG™ Software in any manner and for any purpose, and to have or permit
 * others to do so; (ii) make, have made, use, practice, sell, and offer for
 * sale, import, and/or otherwise dispose of caBIG™ Software (or portions
 * thereof); (iii) distribute and have distributed to and by third parties the
 * caBIG™ Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties.
 * For sake of clarity, and not by way of limitation, caBIG™ Participant shall
 * have no right of accounting or right of payment from You or Your sublicensees
 * for the rights granted under this License. This License is granted at no
 * charge to You. Your downloading, copying, modifying, displaying, distributing
 * or use of caBIG™ Software constitutes acceptance of all of the terms and
 * conditions of this Agreement. If you do not agree to such terms and
 * conditions, you have no right to download, copy, modify, display, distribute
 * or use the caBIG™ Software. 1. Your redistributions of the source code for
 * the caBIG™ Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6 below.
 * Your redistributions in object code form must reproduce the above copyright
 * notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 2. Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: “This product includes software
 * developed by ScenPro, Inc.” If You do not include such end-user
 * documentation, You shall include this acknowledgment in the caBIG™ Software
 * itself, wherever such third-party acknowledgments normally appear. 3. You may
 * not use the names “ScenPro, Inc”, “The National Cancer Institute”, “NCI”,
 * “Cancer Bioinformatics Grid” or “caBIG™” to endorse or promote products
 * derived from this caBIG™ Software. This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either
 * caBIG™ Participant, NCI or caBIG™, except as required to comply with the
 * terms of this License. 4. For sake of clarity, and not by way of limitation,
 * You may incorporate this caBIG™ Software into Your proprietary programs and
 * into any third party proprietary programs. However, if You incorporate the
 * caBIG™ Software into third party proprietary programs, You agree that You are
 * solely responsible for obtaining any permission from such third parties
 * required to incorporate the caBIG™ Software into such third party proprietary
 * programs and for informing Your sublicensees, including without limitation
 * Your end-users, of their obligation to secure any required permissions from
 * such third parties before incorporating the caBIG™ Software into such third
 * party proprietary software programs. In the event that You fail to obtain
 * such permissions, You agree to indemnify caBIG™ Participant for any claims
 * against caBIG™ Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5.
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG™ Software, or any derivative works
 * of the caBIG™ Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in
 * this License. 6. THIS caBIG™ SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED
 * OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE)
 * ARE DISCLAIMED. IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.DAO.CTODSStudyDAO;
import gov.nih.nci.caxchange.ctom.viewer.DAO.PAStudyDAO;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.StudySearchResult;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.handler.ProtocolHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class performs the study search action. The search action calls the
 * searchObjects method with the user entered search criteria and queries the
 * database. If the search returns a non null result set; it forwards the user
 * to study search page and displays the results.
 * 
 * @author Lisa Kelley
 */
public class StudyAction extends LabViewerActionSupport
{
	private static final Logger log = Logger.getLogger(StudyAction.class);
	private List<StudySearchResult> studySearchResults;
	private static final String STUDY_SEARCH_ID = "STUDY_SEARCH_ID";
	private static final String STUDY_SEARCH_TITLE = "STUDY_SEARCH_TITLE";
	private static final String EMPTY_STRING = "";
	private static final String NBSP = "&nbsp;";

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String search()
	{		
		String loginStatus = this.getLoginStatus();
		if (!loginStatus.equals(SUCCESS))
		{
			return loginStatus;
		}
		
// lisa - why is this done all over the place and not just once, when application is accessed? 
//		UserInfoHelper.setUserInfo(((LoginForm) session
//				.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId(),
//				session.getId());
		
		String studyId    = (String)session.getAttribute(STUDY_SEARCH_ID);
		String studyTitle = (String)session.getAttribute(STUDY_SEARCH_TITLE);
		log.debug("Study search called with search term(s): " + studyId + studyTitle);
		
		if (studyId != null || studyTitle != null)
		{
			List<String> studyTitleTerms = getStudyTitleTerms(studyTitle);
			
			try
		    {
			    CTODSStudyDAO ctodsStudyDAO = new CTODSStudyDAO();
			    List<StudySearchResult> ctodsSearchResults = ctodsStudyDAO.getCTODSSearchResults(studyId, studyTitleTerms, session);
		    
			    PAStudyDAO paStudyDAO = new PAStudyDAO();
			    List<Protocol> paStudies = paStudyDAO.getPAStudies(studyId, studyTitleTerms, session);
			    
			    studySearchResults = determineStudySearchResults(ctodsSearchResults, paStudies);			
		    }
		    catch (Exception e)
		    {
			    // error message has already been logged
			    this.addActionError(e.getMessage());
		    }
		}

		return SUCCESS;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private List<String> getStudyTitleTerms(String studyTitle)
	{	
		List<String> studyTitleTerms = new ArrayList<String>();
		String[] studyTitleTermsArray = studyTitle.split(" ");
	    List<String> studyTitleTermsList = Arrays.asList(studyTitleTermsArray);
	    
	    for (Iterator<String> it = studyTitleTermsList.iterator(); it.hasNext(); )
	    {
	    	String studyTitleTerm = (String) it.next();	    	
	        if (!studyTitleTerm.equals(EMPTY_STRING)) // this handles situation when user enters multiple spaces between search terms
	        {
	        	studyTitleTerms.add(studyTitleTerm);
	        	// for now, break after one search term has been added to the list
	        	// the search method in the PA study protocol service can only handle one search term
	        	// this may be enhanced to accommodate multiple search terms in the future
	        	break;
	        }
	    }
	    
	    return studyTitleTerms;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private List<StudySearchResult> determineStudySearchResults(List<StudySearchResult> ctodsSearchResults, List<Protocol> paStudies)
	{
		List<StudySearchResult> studySearchResults = new ArrayList<StudySearchResult>();
					
		if (paStudies.isEmpty())
		{					
		    studySearchResults = ctodsSearchResults;
		}
		else
		{
			if (ctodsSearchResults.isEmpty())
			{					
				for (Protocol paStudy : paStudies)
		        {
					StudySearchResult paSearchResult = createSearchResult(paStudy);
					studySearchResults.add(paSearchResult);
		        }
			}
			else
			{
		        for (Protocol paStudy : paStudies)
		        {
		        	StudySearchResult paSearchResult = createSearchResult(paStudy);
		        	StudySearchResult removeObject = null;
		        	
			        for (StudySearchResult ctodsSearchResult : ctodsSearchResults)
			        {
			        	// for now, just compare the study ID's (do not include the grid ID's)
			        	// the search method in the PA study protocol service always returns the root as null
			        	// this will be fixed in 3.2
			        	int index = paStudy.getNciIdentifier().indexOf(".");
			        	if (paStudy.getNciIdentifier().substring(index + 1).equals(ctodsSearchResult.getStudyId()))
			        	//if (paStudy.getNciIdentifier().equals(ctodsSearchResult.getGridId() + "." + ctodsSearchResult.getStudyId()))
			            {						        								        	
			        		// if the study status from PA is the same as the current study status stored in the CTODS database,
			        		// leave the current status as is (i.e. do not insert a new row in the protocol_status table)
			        		// to accomplish this, set the study status from PA to null in the Protocol object
			        		// and a new row will not get inserted in the protocol_status table
			        		String paStudyStatus = paStudy.getStatus().getStatus_code();
			        		if (paStudyStatus == null || paStudyStatus.equalsIgnoreCase(ctodsSearchResult.getStatus()))
			        		{
			        			paStudy.setStatus(null);
			        		}
			        		
			        		paStudy.setId(ctodsSearchResult.getId().longValue());
			        		updateCTODSStudy(paStudy);
			        		
			        		paSearchResult.setId(ctodsSearchResult.getId());
			        		
			            	removeObject = ctodsSearchResult;
			    	        break; // since match has been found
			            }
			        }
			        						        
			        studySearchResults.add(paSearchResult);
			        if (removeObject != null){
			            ctodsSearchResults.remove(removeObject);
			        }
			    }
		        
		        // add any remaining CTODS search results
		        studySearchResults.addAll(ctodsSearchResults);
			}												
		}
		
		return studySearchResults;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private StudySearchResult createSearchResult(Protocol paStudy)
	{		
		StudySearchResult paSearchResult = new StudySearchResult();
		
		int index = paStudy.getNciIdentifier().indexOf(".");
		paSearchResult.setStudyId(paStudy.getNciIdentifier().substring(index + 1));
		paSearchResult.setLongTitle(paStudy.getLongTxtTitle() == null ? NBSP : paStudy.getLongTxtTitle());
		paSearchResult.setSponsorCode(paStudy.getSponsorCode() == null ? NBSP : paStudy.getSponsorCode());
		paSearchResult.setPhaseCode(paStudy.getPhaseCode() == null ? NBSP : paStudy.getPhaseCode());
		paSearchResult.setStatus(paStudy.getStatus().getStatus_code() == null ? NBSP : paStudy.getStatus().getStatus_code());
		paSearchResult.setGridId(paStudy.getNciIdentifier().substring(0, index));
		
		// TO DO: set the details
		// link: https://cbvapp-d1017.nci.nih.gov:28443/c3pr/pages/study/viewStudy?studyId=14
		paSearchResult.setDetails(EMPTY_STRING); // lisa - anu - what is this?
			
		return paSearchResult;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void updateCTODSStudy(Protocol paStudy)
	{	
		ProtocolHandler protocolDAO = new ProtocolHandler();
		try
		{
			protocolDAO.update(protocolDAO.getConnection(), paStudy);
		}
		catch (Exception e)
		{
			log.error("Exception occured while updating CTODS study: ", e);

		}
	}
	
	/**
	   * set method for the unit name.  Unit name is URL-encoded, decode it before saving.
	   * 
	   * @param unitName - the unit name
	   */
	public void setStudyId(String studyId)
	{
	    session.setAttribute(STUDY_SEARCH_ID, studyId);
	}

	  /**
	   * get method for the unit name
	   * 
	   * @return String - unit name
	   */
	public String getStudyId()
	{
	    return (String)session.getAttribute(STUDY_SEARCH_ID);
	}
	
	/**
	   * set method for the unit name.  Unit name is URL-encoded, decode it before saving.
	   * 
	   * @param unitName - the unit name
	   */
	public void setStudyTitle(String studyTitle)
	{
	    session.setAttribute(STUDY_SEARCH_TITLE, studyTitle);
	}

	  /**
	   * get method for the unit name
	   * 
	   * @return String - unit name
	   */
	public String getStudyTitle()
	{
		return (String)session.getAttribute(STUDY_SEARCH_TITLE);
	}
	
	/**
	   * get method for the list of workload objects
	   * 
	   * @return List<Workload> - list of workload objects
	   */
	public List<StudySearchResult> getStudySearchResults()
	{
	    return studySearchResults;
	}

}
