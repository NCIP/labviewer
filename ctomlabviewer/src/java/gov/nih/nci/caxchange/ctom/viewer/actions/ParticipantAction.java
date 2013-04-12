/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
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
