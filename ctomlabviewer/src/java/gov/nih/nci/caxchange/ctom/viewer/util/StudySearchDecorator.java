/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.util;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.StudySearchResult;

import org.displaytag.decorator.TableDecorator;

/**
 * @author Anupama Sharma
 *
 */
public class StudySearchDecorator extends TableDecorator
{
    private static final String NBSP = "&nbsp;";
	 public StudySearchDecorator()
	  {
	    super();
	  }
	  
	  public final String getShortTitle()
	  {
		StudySearchResult studySearchResult = (StudySearchResult)getCurrentRowObject();
	    String shortTitle = NBSP;
	    if(studySearchResult.getLongTitle()!= null && !studySearchResult.getLongTitle().equals("") && !studySearchResult.getLongTitle().equals("null")){
	    	shortTitle = studySearchResult.getLongTitle(); 
	    }
	    return shortTitle;
	  }
	  
	   public final String getSponsorCode()
	  {
		StudySearchResult studySearchResult = (StudySearchResult)getCurrentRowObject();
	    String sponsorCd = NBSP;
	    if(studySearchResult.getSponsorCode()!= null && !studySearchResult.getSponsorCode().equals("") && !studySearchResult.getSponsorCode().equals("null")){
	    	sponsorCd = studySearchResult.getSponsorCode(); 
	    }
	    return sponsorCd;
	  }
	  
	  public final String getPhaseCode()
	  {
		StudySearchResult studySearchResult = (StudySearchResult)getCurrentRowObject();
	    String phaseCd = NBSP;
	    if(studySearchResult.getPhaseCode()!= null && !studySearchResult.getPhaseCode().equals("") && !studySearchResult.getPhaseCode().equals("null")){
	    	phaseCd = studySearchResult.getPhaseCode(); 
	    }
	    return phaseCd;
	  }
	  
	  public final String getStatus()
	  {
		StudySearchResult studySearchResult = (StudySearchResult)getCurrentRowObject();
	    String status = NBSP;
	    if(studySearchResult.getStatus()!= null && !studySearchResult.getStatus().equals("") && !studySearchResult.getStatus().equals("null")){
	    	status = studySearchResult.getStatus(); 
	    }
	    return status;
	  }
	  
	  
}


