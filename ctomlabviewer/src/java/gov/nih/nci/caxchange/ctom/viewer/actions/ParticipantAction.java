/**
 * Copyright Notice. Copyright 2008 ScenPro, Inc (�caBIG�
 * Participant�).caXchange was created with NCI funding and is part of the
 * caBIG� initiative. The software subject to this notice and license includes
 * both human readable source code form and machine readable, binary, object
 * code form (the �caBIG� Software�). This caBIG� Software License (the
 * �License�) is between caBIG� Participant and You. �You (or �Your�) shall mean
 * a person or an entity, and all other entities that control, are controlled
 * by, or are under common control with the entity. �Control� for purposes of
 * this definition means (i) the direct or indirect power to cause the direction
 * or management of such entity, whether by contract or otherwise, or (ii)
 * ownership of fifty percent (50%) or more of the outstanding shares, or (iii)
 * beneficial ownership of such entity. License. Provided that You agree to the
 * conditions described below, caBIG� Participant grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG� Software,
 * including any copyright or patent rights therein, to (i) use, install,
 * disclose, access, operate, execute, reproduce, copy, modify, translate,
 * market, publicly display, publicly perform, and prepare derivative works of
 * the caBIG� Software in any manner and for any purpose, and to have or permit
 * others to do so; (ii) make, have made, use, practice, sell, and offer for
 * sale, import, and/or otherwise dispose of caBIG� Software (or portions
 * thereof); (iii) distribute and have distributed to and by third parties the
 * caBIG� Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties.
 * For sake of clarity, and not by way of limitation, caBIG� Participant shall
 * have no right of accounting or right of payment from You or Your sublicensees
 * for the rights granted under this License. This License is granted at no
 * charge to You. Your downloading, copying, modifying, displaying, distributing
 * or use of caBIG� Software constitutes acceptance of all of the terms and
 * conditions of this Agreement. If you do not agree to such terms and
 * conditions, you have no right to download, copy, modify, display, distribute
 * or use the caBIG� Software. 1. Your redistributions of the source code for
 * the caBIG� Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6 below.
 * Your redistributions in object code form must reproduce the above copyright
 * notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 2. Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: �This product includes software
 * developed by ScenPro, Inc.� If You do not include such end-user
 * documentation, You shall include this acknowledgment in the caBIG� Software
 * itself, wherever such third-party acknowledgments normally appear. 3. You may
 * not use the names �ScenPro, Inc�, �The National Cancer Institute�, �NCI�,
 * �Cancer Bioinformatics Grid� or �caBIG�� to endorse or promote products
 * derived from this caBIG� Software. This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either
 * caBIG� Participant, NCI or caBIG�, except as required to comply with the
 * terms of this License. 4. For sake of clarity, and not by way of limitation,
 * You may incorporate this caBIG� Software into Your proprietary programs and
 * into any third party proprietary programs. However, if You incorporate the
 * caBIG� Software into third party proprietary programs, You agree that You are
 * solely responsible for obtaining any permission from such third parties
 * required to incorporate the caBIG� Software into such third party proprietary
 * programs and for informing Your sublicensees, including without limitation
 * Your end-users, of their obligation to secure any required permissions from
 * such third parties before incorporating the caBIG� Software into such third
 * party proprietary software programs. In the event that You fail to obtain
 * such permissions, You agree to indemnify caBIG� Participant for any claims
 * against caBIG� Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5.
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG� Software, or any derivative works
 * of the caBIG� Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in
 * this License. 6. THIS caBIG� SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED
 * OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE)
 * ARE DISCLAIMED. IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG� SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.DAO.ParticipantDAO;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.ParticipantSearchResult;

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
public class ParticipantAction extends LabViewerActionSupport
{
	private static final Logger log = Logger.getLogger(ParticipantAction.class);
	private static final String PARTICIPANT_SEARCH_TERM        = "PARTICIPANT_SEARCH_TERM";
	private static final String PARTICIPANT_STUDY_SEARCH_ID    = "PARTICIPANT_STUDY_SEARCH_ID";
	private static final String PARTICIPANT_STUDY_SEARCH_TITLE = "PARTICIPANT_STUDY_SEARCH_TITLE";	
	private List<ParticipantSearchResult> searchResults;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String display()
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
		
		String studyId = (String)session.getAttribute(PARTICIPANT_STUDY_SEARCH_ID);
				
		try
		{
			ParticipantDAO participantDAO = new ParticipantDAO();
			searchResults = participantDAO.getSearchResults(studyId);
		}
		catch (Exception e)
		{
			// error message has already been logged
			this.addActionError(e.getMessage());
		}

		return SUCCESS;
	}
	
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
		
		String searchTerm = (String)session.getAttribute(PARTICIPANT_SEARCH_TERM);
		String studyId    = (String)session.getAttribute(PARTICIPANT_STUDY_SEARCH_ID);
		String studyTitle = (String)session.getAttribute(PARTICIPANT_STUDY_SEARCH_TITLE);
		
		if (studyId != null)
		{
			List<String> searchTerms = getSearchTerms(searchTerm);
			
			try
		    {
			    ParticipantDAO participantDAO = new ParticipantDAO();
			    searchResults = participantDAO.getSearchResults(studyId, searchTerms);
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
	private List<String> getSearchTerms(String searchTermString)
	{
		if (searchTermString == null)
		{
			return null;
		}
		
		List<String> searchTerms = new ArrayList<String>();
		String[] searchTermsArray = searchTermString.split(" ");
	    List<String> searchTermsList = Arrays.asList(searchTermsArray);
	    
	    for (Iterator<String> it = searchTermsList.iterator(); it.hasNext(); )
	    {
	    	String searchTerm = (String) it.next();	    	
	        if (!searchTerm.equals("")) // this handles situation when user enters multiple spaces between search terms
	        {
	        	searchTerms.add(searchTerm);
	        }
	    }
	    
	    return searchTerms;
	}
	
	/**
	   * set method for the unit name.  Unit name is URL-encoded, decode it before saving.
	   * 
	   * @param unitName - the unit name
	   */
	public void setSearchTerm(String searchTerm)
	{
		session.setAttribute(PARTICIPANT_SEARCH_TERM, searchTerm);
	}

	  /**
	   * get method for the unit name
	   * 
	   * @return String - unit name
	   */
	public String getSearchTerm()
	{
		return (String)session.getAttribute(PARTICIPANT_SEARCH_TERM);
	}
	
	/**
	   * set method for the unit name.  Unit name is URL-encoded, decode it before saving.
	   * 
	   * @param unitName - the unit name
	   */
	public void setStudyId(String studyId)
	{
		session.setAttribute(PARTICIPANT_STUDY_SEARCH_ID, studyId);
	}
	
	/**
	   * get method for the unit name
	   * 
	   * @return String - unit name
	   */
	public String getStudyId()
	{
	    return (String)session.getAttribute(PARTICIPANT_STUDY_SEARCH_ID);
	}
	
	/**
	   * set method for the unit name.  Unit name is URL-encoded, decode it before saving.
	   * 
	   * @param unitName - the unit name
	   */
	public void setStudyTitle(String studyTitle)
	{
		session.setAttribute(PARTICIPANT_STUDY_SEARCH_TITLE, studyTitle);
	}
	
	/**
	   * get method for the unit name
	   * 
	   * @return String - unit name
	   */
	public String getStudyTitle()
	{
	    return (String)session.getAttribute(PARTICIPANT_STUDY_SEARCH_TITLE);
	}
	
	/**
	   * get method for the list of workload objects
	   * 
	   * @return List<Workload> - list of workload objects
	   */
	public List<ParticipantSearchResult> getSearchResults()
	{
	    return searchResults;
	}

}