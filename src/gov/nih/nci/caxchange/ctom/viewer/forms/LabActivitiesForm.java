/*
 * Created on Dec 3, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.caxchange.ctom.viewer.forms;

/**
 *
 *<!-- LICENSE_TEXT_START -->
 *
 *The NCICB Common Security Module's User Provisioning Tool (UPT) Software License,
 *Version 3.0 Copyright 2004-2005 Ekagra Software Technologies Limited ('Ekagra')
 *
 *Copyright Notice.  The software subject to this notice and license includes both
 *human readable source code form and machine readable, binary, object code form
 *(the 'UPT Software').  The UPT Software was developed in conjunction with the
 *National Cancer Institute ('NCI') by NCI employees and employees of Ekagra.  To
 *the extent government employees are authors, any rights in such works shall be
 *subject to Title 17 of the United States Code, section 105.    
 *
 *This UPT Software License (the 'License') is between NCI and You.  'You (or
 *'Your') shall mean a person or an entity, and all other entities that control,
 *are controlled by, or are under common control with the entity.  'Control' for
 *purposes of this definition means (i) the direct or indirect power to cause the
 *direction or management of such entity, whether by contract or otherwise, or
 *(ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *(iii) beneficial ownership of such entity.  
 *
 *This License is granted provided that You agree to the conditions described
 *below.  NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 *no-charge, irrevocable, transferable and royalty-free right and license in its
 *rights in the UPT Software to (i) use, install, access, operate, execute, copy,
 *modify, translate, market, publicly display, publicly perform, and prepare
 *derivative works of the UPT Software; (ii) distribute and have distributed to
 *and by third parties the UPT Software and any modifications and derivative works
 *thereof; and (iii) sublicense the foregoing rights set out in (i) and (ii) to
 *third parties, including the right to license such rights to further third
 *parties.  For sake of clarity, and not by way of limitation, NCI shall have no
 *right of accounting or right of payment from You or Your sublicensees for the
 *rights granted under this License.  This License is granted at no charge to You.
 *
 *1.	Your redistributions of the source code for the Software must retain the
 *above copyright notice, this list of conditions and the disclaimer and
 *limitation of liability of Article 6 below.  Your redistributions in object code
 *form must reproduce the above copyright notice, this list of conditions and the
 *disclaimer of Article 6 in the documentation and/or other materials provided
 *with the distribution, if any.
 *2.	Your end-user documentation included with the redistribution, if any, must
 *include the following acknowledgment: 'This product includes software developed
 *by Ekagra and the National Cancer Institute.'  If You do not include such
 *end-user documentation, You shall include this acknowledgment in the Software
 *itself, wherever such third-party acknowledgments normally appear.
 *
 *3.	You may not use the names 'The National Cancer Institute', 'NCI' 'Ekagra
 *Software Technologies Limited' and 'Ekagra' to endorse or promote products
 *derived from this Software.  This License does not authorize You to use any
 *trademarks, service marks, trade names, logos or product names of either NCI or
 *Ekagra, except as required to comply with the terms of this License.
 *
 *4.	For sake of clarity, and not by way of limitation, You may incorporate this
 *Software into Your proprietary programs and into any third party proprietary
 *programs.  However, if You incorporate the Software into third party proprietary
 *programs, You agree that You are solely responsible for obtaining any permission
 *from such third parties required to incorporate the Software into such third
 *party proprietary programs and for informing Your sublicensees, including
 *without limitation Your end-users, of their obligation to secure any required
 *permissions from such third parties before incorporating the Software into such
 *third party proprietary software programs.  In the event that You fail to obtain
 *such permissions, You agree to indemnify NCI for any claims against NCI by such
 *third parties, except to the extent prohibited by law, resulting from Your
 *failure to obtain such permissions.
 *
 *5.	For sake of clarity, and not by way of limitation, You may add Your own
 *copyright statement to Your modifications and to the derivative works, and You
 *may provide additional or different license terms and conditions in Your
 *sublicenses of modifications of the Software, or any derivative works of the
 *Software as a whole, provided Your use, reproduction, and distribution of the
 *Work otherwise complies with the conditions stated in this License.
 *
 *6.	THIS SOFTWARE IS PROVIDED 'AS IS,' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 *(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 *NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN NO
 *EVENT SHALL THE NATIONAL CANCER INSTITUTE, EKAGRA, OR THEIR AFFILIATES BE LIABLE
 *FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 *TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *<!-- LICENSE_TEXT_END -->
 *
 */

import gov.nih.nci.caxchange.ctom.viewer.applicationservice.EventsManager;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.util.ObjectFactory;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.FormElement;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResultComparator;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.SearchResult;
import gov.nih.nci.labhub.domain.Specimen;
import gov.nih.nci.labhub.domain.SpecimenCollection;
import gov.nih.nci.labhub.domain.Study;
import gov.nih.nci.labhub.domain.StudySite;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.ValidatorForm;
import org.hibernate.Session;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 *
 */
public class LabActivitiesForm extends ValidatorForm implements BaseAssociationForm
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
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the patientId
	 */
	public String getPatientId() {
		return patientId;
	}

	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(String patientId) {
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

		formElementList.add(new FormElement("Study Identifier", "studyId", getStudyId(), DisplayConstants.INPUT_BOX, DisplayConstants.REQUIRED, DisplayConstants.NOT_DISABLED));		
		formElementList.add(new FormElement("Patient Identifier", "patientId", getPatientId(), DisplayConstants.INPUT_BOX, DisplayConstants.REQUIRED, DisplayConstants.NOT_DISABLED));
		formElementList.add(new FormElement("Begin Date (MM/DD/YYYY)", "beginDate", getBeginDate(), DisplayConstants.INPUT_BOX, DisplayConstants.REQUIRED, DisplayConstants.NOT_DISABLED));
		formElementList.add(new FormElement("End Date (MM/DD/YYYY)", "endDate", getEndDate(), DisplayConstants.INPUT_BOX, DisplayConstants.REQUIRED, DisplayConstants.NOT_DISABLED));

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
	
	/* (non-Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{
		ActionErrors errors = new ActionErrors();
		errors = super.validate(mapping,request);
		try
		{
			if (new SimpleDateFormat("MM/dd/yyyy").parse(this.beginDate).after(new SimpleDateFormat("MM/dd/yyyy").parse(this.endDate)))
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "End Date cannot be earlier than Begin Date"));
			}
		}
		catch (ParseException e)
		{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "Incorrect Date Format"));
		}
		return errors;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.security.forms.BaseDBForm#buildDisplayForm(javax.servlet.http.HttpServletRequest)
	 */
	public void buildDisplayForm(HttpServletRequest request) throws Exception
	{
		// Will Never Be Called
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.security.forms.BaseDBForm#buildDBObject(javax.servlet.http.HttpServletRequest)
	 */
	public void submitAdverseEvent(HttpServletRequest request) throws Exception
	{
		/*
		Session session = null;
		Connection connection = null;
		try {
		session = ORMConnection.openSession("gov.nih.nci.labhub.domain.SubjectAssignment");
		connection = session.connection();
		connection.setAutoCommit(false);
		HashMap map = (HashMap) request.getSession().getAttribute("RESULT_SET");
		LabActivityResult labActivityResult = (LabActivityResult)map.get(this.recordId);
		String sql = "insert into LV_ADVERSE_EVENTS (CLINICAL_RESULT_ID, ADVERSE_EVENT_INDICATOR, UPDATE_DATE) values ( ? , '1' , sysdate )";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, labActivityResult.getLabResultId());
		stmt.executeUpdate();
		try {
		EventsManager mgr = (EventsManager) ObjectFactory.getObject("eventsManager");
		mgr.sendAdverseEvent(labActivityResult, "TESTUSER");
		connection.commit();
		stmt.close();
		} catch (Exception ex ){
			try {connection.rollback();} catch (Exception e){}
			throw ex;
		} 

//		connection.createStatement().execute("insert into LV_ADVERSE_EVENTS (CLINICAL_RESULT_ID, ADVERSE_EVENT_INDICATOR, UPDATE_DATE) values (" + labActivityResult.getLabResultId() + " , '1' , sysdate )");
//		connection.commit();
		labActivityResult.setAdverseEventReported("1");
		labActivityResult.setAdverseEventReportedDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));	
		map.put(this.recordId,labActivityResult);
		request.getSession().setAttribute("RESULT_SET", map);
		this.recordId = "";
		this.recordIds = null;
		
		
		
		}finally {
			try{connection.setAutoCommit(true);} catch(Exception ex){}
			try{session.close();} catch(Exception ex){}
		}
		*/

	}

	public void loadToCTMS(HttpServletRequest request) throws Exception
	{
		HashMap map = (HashMap) request.getSession().getAttribute("RESULT_SET");
		ArrayList list = new ArrayList();
		String test = recordId;
		StringTokenizer stringTokenizer = new StringTokenizer(test, ",");
		int count = stringTokenizer.countTokens();
		if (count >= 1)
		{
			while (stringTokenizer.hasMoreTokens())
			{
				list.add(map.get(stringTokenizer.nextToken()));
			}
		}
		else
		{
			list.add(map.get(this.recordId));
		}

		EventsManager mgr =  (EventsManager) ObjectFactory.getObject("eventsManager");
		
		mgr.sendLabActivities(list, "TESTUSER");
		
		this.recordId = "";
		this.recordIds = null;
	}	
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.security.forms.BaseDBForm#removeDBObject(javax.servlet.http.HttpServletRequest)
	 */
	public void removeDBObject(HttpServletRequest request) throws Exception 
	{
		// Will Never Be Called
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.security.forms.BaseDBForm#searchObjects(javax.servlet.http.HttpServletRequest)
	 */
	/*
	public SearchResult searchObjects(HttpServletRequest request, ActionErrors errors, ActionMessages messages) throws Exception 
	{
		HashMap map = new HashMap();
		List allLarList = new ArrayList();
		try{
			
			ApplicationService appService = ApplicationServiceProvider.getApplicationService();
			
			CQLQuery query = new CQLQuery();
			CQLObject target = new CQLObject();
			target.setName("gov.nih.nci.labhub.domain.SubjectAssignment");
			target.setAttribute(new CQLAttribute("studySubjectIdentifier",CQLPredicate.EQUAL_TO,patientId.trim()));
			
			
			CQLAssociation association3 = new CQLAssociation();
			association3.setName("gov.nih.nci.labhub.domain.StudySite");
			association3.setTargetRoleName("studySite");

			
			
			CQLAssociation association4 = new CQLAssociation();
			association4.setName("gov.nih.nci.labhub.domain.Study");
			association4.setTargetRoleName("study");
			//association4.setAttribute(new CQLAttribute("identifier",CQLPredicate.EQUAL_TO,"04_C_0121"));
			association4.setAttribute(new CQLAttribute("identifier",CQLPredicate.EQUAL_TO,studyId.trim()));
			association3.setAssociation(association4);
			
			
			CQLAssociation association1 = new CQLAssociation();
			association1.setName("gov.nih.nci.labhub.domain.SpecimenCollection");
			association1.setTargetRoleName("activityCollection");
			
			CQLGroup group = new CQLGroup();
			//group.addAttribute(new CQLAttribute("actualStartDateTime",CQLPredicate.GREATER_THAN,"11/11/2005"));
			//group.addAttribute(new CQLAttribute("actualStartDateTime",CQLPredicate.LESS_THAN,"11/11/2009"));
			group.addAttribute(new CQLAttribute("actualStartDateTime",CQLPredicate.GREATER_THAN,beginDate));
			group.addAttribute(new CQLAttribute("actualStartDateTime",CQLPredicate.LESS_THAN,endDate));
			

			
			group.setLogicOperator(CQLLogicalOperator.AND);
			association1.setGroup(group);

			CQLGroup finalgroup = new CQLGroup();
			finalgroup.addAssociation(association1);
			finalgroup.addAssociation(association3);
			finalgroup.setLogicOperator(CQLLogicalOperator.AND);
			target.setGroup(finalgroup);
			
			query.setTarget(target);
			

			
			List resultList = appService.query(query, "gov.nih.nci.labhub.domain.SubjectAssignment");
			Connection connection = null;
			Session session = null;
			
			try {
			
				session = ORMConnection.openSession("gov.nih.nci.labhub.domain.SubjectAssignment");
				connection = session.connection();
			List larlist = null;
			
			int i = 0;
			for (Iterator resultsIterator = resultList.iterator(); resultsIterator.hasNext();) 
			{
				SubjectAssignment sa = (SubjectAssignment) resultsIterator.next();

				sa.getStudySite().getStudy().getIdentifier();
				
				LabActivityResult labActivityResult = null;
				
				larlist = this.printRecord(sa, beginDate, endDate);
				
				
				
				
				for (int j=0; j < larlist.size(); j++)
				{
					i++;
					labActivityResult = (LabActivityResult)larlist.get(j);
					ResultSet rs= connection.createStatement().executeQuery("select ADVERSE_EVENT_INDICATOR, UPDATE_DATE from LV_ADVERSE_EVENTS where CLINICAL_RESULT_ID = " + labActivityResult.getLabResultId());
					if (rs.next())
					{
						labActivityResult.setAdverseEventReported(rs.getString(1));
						labActivityResult.setAdverseEventReportedDate(new SimpleDateFormat("MM-dd-yyyy").format(rs.getDate(2)));
					}
					else
					{
						labActivityResult.setAdverseEventReported("0");
					}
					//labActivityResult.setRecordId(Integer.toString(i));
					
					
					//map.put(Integer.toString(i), labActivityResult);
					allLarList.add(labActivityResult);
				}
			}
			if (allLarList != null && allLarList.size() > 0) {
				LabActivityResultComparator comp = (LabActivityResultComparator)ObjectFactory.getObject("labActivityResultComparator");
				Collections.sort(allLarList, comp);
				int k= 0;
				for (int j=0; j < allLarList.size(); j++) {
					k++;
					LabActivityResult labActivityResult = null;
					labActivityResult = (LabActivityResult) allLarList.get(j);
					labActivityResult.setRecordId(Integer.toString(k));
					map.put(Integer.toString(k), labActivityResult);
				}
			}
				
			
			
			
			
			
			} finally {
				try {session.close();} catch (Exception ex){}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Test client throws Exception = "+ ex);
		}
		SearchResult searchResult = new SearchResult();
		//searchResult.setSearchResultObjects(new ArrayList(map.values()));
		searchResult.setSearchResultObjects(allLarList);
		request.getSession().setAttribute("RESULT_SET", map);
		return searchResult;
	}
	*/

	
	/* (non-Javadoc)
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


	/* (non-Javadoc)
	 * @see gov.nih.nci.security.upt.forms.BaseAssociationForm#buildAssociationObject(javax.servlet.http.HttpServletRequest)
	 */
	public void buildAssociationObject(HttpServletRequest request) throws Exception 
	{
		// Will Never Be Called
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.security.upt.forms.BaseAssociationForm#setAssociationObject(javax.servlet.http.HttpServletRequest)
	 */
	public void setAssociationObject(HttpServletRequest request) throws Exception 
	{
		// Will Never Be Called
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.security.upt.forms.BaseDBForm#getFormName()
	 */
	public String getFormName() {
		return DisplayConstants.LABACTIVITES_ID;
	}

	public String[] getAssociatedIds() {
		return null;
	}
	
    private static String convertToString(java.util.Date date)
    {
    	if(date == null) return "-";
    	SimpleDateFormat formatter = new SimpleDateFormat();
    	String str = formatter.format(date);
    	return str;
    }

    /*
    private static ArrayList printRecord(SubjectAssignment sa, String beginDate2, String endDate2) throws ParseException
    {
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    	
    	ArrayList list = new ArrayList();
    	
    	Date beginActDate = null;
    	Date endActDate = null;
    	
    	beginActDate = formatter.parse(beginDate2);
    	endActDate = formatter.parse(endDate2);
    	
    	
		String patientId = null;
		String date = null;
		Date actualDate = null;
		String labTestId = null;
		String textResult = null;
		String numericResult = null;
		String unitOfMeasure = null;
		Double lowRange;
		Double highRange;

    	if(sa==null) return null;
    	
		if(sa!=null)
		{
	    	LabActivityResult labActivityResult = new LabActivityResult();

			labActivityResult.setSubjectAssignment(sa);
			patientId = sa.getStudySubjectIdentifier();
		
			StudySite site = sa.getStudySite();
			if(site!=null)
			{
				Study study=site.getStudy();
				labActivityResult.setStudyId(study.getIdentifier());
			}
			labActivityResult.setPatientId(sa.getStudySubjectIdentifier());
			Collection activityCollection = sa.getActivityCollection();
			if(activityCollection !=null && activityCollection.size()>0)
			{
				for(Iterator activityIterator=activityCollection.iterator();activityIterator.hasNext();)
				{
					SpecimenCollection activity = (SpecimenCollection)activityIterator.next();
					
					
					actualDate = activity.getActualStartDateTime();
					
					if (actualDate.before(beginActDate) || actualDate.after(endActDate))
						continue;
					
					
					date = convertToString(activity.getActualStartDateTime());
					labActivityResult.setDate(date);
					labActivityResult.setActualDate(actualDate);
					Collection specimenCollection = activity.getSpecimenCollection();
					if(specimenCollection!=null && specimenCollection.size()>0)
					{
						for(Iterator specimenIterator = specimenCollection.iterator();specimenIterator.hasNext();)
						{
							Specimen specimen = (Specimen)specimenIterator.next();
							Collection labTestCollection = specimen.getLabTestCollection();
							
							if(labTestCollection!=null && labTestCollection.size()>0)
							{
								for(Iterator labTestIterator = labTestCollection.iterator();labTestIterator.hasNext();)
								{
									LabTest labTest = (LabTest)labTestIterator.next();
									if(labTest!=null)
									{
										ConceptDescriptorDataType labTestIde = labTest.getLabTestId();
										if(labTestIde!=null){
											labTestId = labTestIde.getCode();
											labActivityResult.setLabTestId(labTestId);
										}
										LabResult labResult = labTest.getLabResult();
										
										if(labResult!=null)
										{
											Double numResult = null;
											numericResult = labResult.getNumericResult();
											labActivityResult.setLabResultId(labResult.getId().toString());
											
											try {
												numResult = Double.parseDouble(numericResult);
												labActivityResult.setNumericResult(numericResult);
											} catch (Exception ex){
												labActivityResult.setNumericResult("");
											}
											
											
											
											textResult = labResult.getTextResult();
											labActivityResult.setTextResult(textResult);
											ConceptDescriptorDataType units = labResult.getUnits();
											if(units!=null) {
												unitOfMeasure = units.getCode();
												labActivityResult.setUnitOfMeasure(unitOfMeasure);
											}
											
											
											lowRange = labResult.getReferenceRangeLow();
											labActivityResult.setLowRange(String.valueOf(lowRange));
											highRange = labResult.getReferenceRangeHigh();
											
											if (numResult != null){
												if(numResult < lowRange || numResult > highRange){
													labActivityResult.setIsAdverseEvent(DisplayConstants.YES);	
													System.out.println(DisplayConstants.YES);
												} else {
													labActivityResult.setIsAdverseEvent(DisplayConstants.NO);
												}
											} else {
												labActivityResult.setIsAdverseEvent(DisplayConstants.NO);
											}
											
											labActivityResult.setHighRange(String.valueOf(highRange));
											labActivityResult.setLabResult(labResult);
											list.add(labActivityResult);
											labActivityResult = new LabActivityResult();
											// Re set the values
											
											SubjectAssignment sac = new SubjectAssignment();
											sac.setId(sa.getId());
											sac.setStudySite(sa.getStudySite());
											sac.setStudySubjectIdentifier(sa.getStudySubjectIdentifier());
											sac.setType(sa.getType());
											Collection actCol = new ArrayList();
											SpecimenCollection speccollClo = new SpecimenCollection();
										
											
											Specimen specClo = new Specimen;
											actCol.add(activity);
											sac.setActivityCollection(actCol);
											
											labActivityResult.setSubjectAssignment(sa);
											labActivityResult.setPatientId(sa.getStudySubjectIdentifier());
											labActivityResult.setStudyId(sa.getStudySite().getStudy().getIdentifier());
											labActivityResult.setDate(date);
											labActivityResult.setActualDate(actualDate);
											
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return list;
    }
    */

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

}
