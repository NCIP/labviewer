/**
 * 
 */
package gov.nih.nci.caxchange.ctom.viewer.forms;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * The LabActivitiesSearchResultForm class is the form for lab activites search results
 * 
 * @author asharma
 *
 */
public class LabActivitiesSearchResultForm extends ActionForm implements BaseAssociationForm{
	
	//recordId
	private String recordId;
	//recordIds
	private String[] recordIds;
	//mrn
	private String mrn;
	//nciIdentifier
	private String nciIdentifier;
	
	
	/**
	 * The reset method is called on the loading of this form and anytime the
	 * user selects the reset button.  It can be used to set the default starting
	 * values for the form.
	 * <P>
	 * @param mapping The ActionMapping for the posted action
	 * @param request The HttpServletRequest for this post
	 */	
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		this.recordId = "";
		this.recordIds = null;
	}	
	
	public void resetForm()
	{
		
		this.recordId = "";
		this.recordIds = null;
		
	}
	/**
	 * @return the recordId
	 */
	public String getRecordId() {
		return recordId;
	}
	/**
	 * @param recordId the recordId to set
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	/**
	 * @return the recordIds
	 */
	public String[] getRecordIds() {
		return recordIds;
	}
	/**
	 * @param recordIds the recordIds to set
	 */
	public void setRecordIds(String[] recordIds) {
		this.recordIds = recordIds;
	}
	
	public String getFormName() {
		return DisplayConstants.LABACTIVITES_ID;
	}
	public String[] getAssociatedIds() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the mrn
	 */
	public String getMrn() {
		return mrn;
	}

	/**
	 * @param mrn the mrn to set
	 */
	public void setMrn(String mrn) {
		this.mrn = mrn;
	}

	/**
	 * @return the nciIdentifier
	 */
	public String getNciIdentifier() {
		return nciIdentifier;
	}

	/**
	 * @param nciIdentifier the nciIdentifier to set
	 */
	public void setNciIdentifier(String nciIdentifier) {
		this.nciIdentifier = nciIdentifier;
	}
	
}
