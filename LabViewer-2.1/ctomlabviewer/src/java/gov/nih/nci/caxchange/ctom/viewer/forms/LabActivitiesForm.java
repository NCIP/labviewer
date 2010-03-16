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

package gov.nih.nci.caxchange.ctom.viewer.forms;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.FormElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 */
public class LabActivitiesForm extends ValidatorForm implements
		BaseAssociationForm
{
	private String recordId;
	private String[] recordIds;
	private String studyId;
	private String patientId;
	private String beginDate;
	private String endDate;
	private String gridProxy;
	private String mrn;
	private String nciIdentifier;

	public String getNciIdentifier()
	{
		return nciIdentifier;
	}

	public void setNciIdentifier(String nciIdentifier)
	{
		this.nciIdentifier = nciIdentifier;
	}

	public String getMrn()
	{
		return mrn;
	}

	public void setMrn(String mrn)
	{
		this.mrn = mrn;
	}

	public String getGridProxy()
	{
		return gridProxy;
	}

	public void setGridProxy(String gridProxy)
	{
		this.gridProxy = gridProxy;
	}

	public String getRecordId()
	{
		return recordId;
	}

	public void setRecordId(String recordId)
	{
		this.recordId = recordId;
	}

	public String[] getRecordIds()
	{
		return recordIds;
	}

	public void setRecordIds(String[] recordIds)
	{
		this.recordIds = recordIds;
	}

	/**
	 * @return the beginDate
	 */
	public String getBeginDate()
	{
		return beginDate;
	}

	/**
	 * @param beginDate
	 *            the beginDate to set
	 */
	public void setBeginDate(String beginDate)
	{
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate()
	{
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	/**
	 * @return the patientId
	 */
	public String getPatientId()
	{
		return patientId;
	}

	/**
	 * @param patientId
	 *            the patientId to set
	 */
	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}

	public ArrayList getAddFormElements()
	{
		return null;
	}

	public ArrayList getDisplayFormElements()
	{
		return null;
	}

	public ArrayList getSearchFormElements()
	{
		ArrayList formElementList = new ArrayList();

		formElementList.add(new FormElement("Study Identifier", "studyId",
				getStudyId(), DisplayConstants.INPUT_BOX,
				DisplayConstants.REQUIRED, DisplayConstants.NOT_DISABLED));
		formElementList.add(new FormElement("Patient Identifier", "patientId",
				getPatientId(), DisplayConstants.INPUT_BOX,
				DisplayConstants.REQUIRED, DisplayConstants.NOT_DISABLED));
		formElementList.add(new FormElement("Begin Date (MM/DD/YYYY)",
				"beginDate", getBeginDate(), DisplayConstants.INPUT_BOX,
				DisplayConstants.REQUIRED, DisplayConstants.NOT_DISABLED));
		formElementList.add(new FormElement("End Date (MM/DD/YYYY)", "endDate",
				getEndDate(), DisplayConstants.INPUT_BOX,
				DisplayConstants.REQUIRED, DisplayConstants.NOT_DISABLED));

		return formElementList;
	}

	public void resetForm()
	{
		this.recordId = "";
		this.recordIds = null;
		this.patientId = "";
		this.beginDate = "";
		this.endDate = "";
		this.studyId = "";
	}

	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		this.recordId = "";
		this.recordIds = null;
		this.patientId = "";
		this.beginDate = "";
		this.endDate = "";
		this.studyId = "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		errors = super.validate(mapping, request);
		try
		{
			if (new SimpleDateFormat("MM/dd/yyyy").parse(this.beginDate).after(
					new SimpleDateFormat("MM/dd/yyyy").parse(this.endDate)))
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						DisplayConstants.ERROR_ID,
						"End Date cannot be earlier than Begin Date"));
			}
		}
		catch (ParseException e)
		{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					DisplayConstants.ERROR_ID, "Incorrect Date Format"));
		}
		return errors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.forms.BaseDBForm#buildDisplayForm(javax.servlet.http.HttpServletRequest)
	 */
	public void buildDisplayForm(HttpServletRequest request) throws Exception
	{
		// Will Never Be Called
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.forms.BaseDBForm#removeDBObject(javax.servlet.http.HttpServletRequest)
	 */
	public void removeDBObject(HttpServletRequest request) throws Exception
	{
		// Will Never Be Called
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.forms.BaseDBForm#getPrimaryId()
	 */
	public String getPrimaryId()
	{
		if (this.patientId == null)
		{
			return new String("");
		}
		else
		{
			return "";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.upt.forms.BaseAssociationForm#buildAssociationObject(javax.servlet.http.HttpServletRequest)
	 */
	public void buildAssociationObject(HttpServletRequest request)
			throws Exception
	{
		// Will Never Be Called
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.upt.forms.BaseAssociationForm#setAssociationObject(javax.servlet.http.HttpServletRequest)
	 */
	public void setAssociationObject(HttpServletRequest request)
			throws Exception
	{
		// Will Never Be Called
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.upt.forms.BaseDBForm#getFormName()
	 */
	public String getFormName()
	{
		return DisplayConstants.LABACTIVITES_ID;
	}

	public String[] getAssociatedIds()
	{
		return null;
	}

	private static String convertToString(java.util.Date date)
	{
		if (date == null)
			return "-";
		SimpleDateFormat formatter = new SimpleDateFormat();
		String str = formatter.format(date);
		return str;
	}

	public String getStudyId()
	{
		return studyId;
	}

	public void setStudyId(String studyId)
	{
		this.studyId = studyId;
	}

}
