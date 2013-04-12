/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
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
