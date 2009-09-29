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
package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class Activity
{

	private Long id;
	private String name = null;
	private String type = null;
	private String descTxt = null;

	private Date startDate = null;
	private String startDateOrig = null;
	private Date stopDate = null;
	private String stopDateOrig = null;
	private Long durationValue = null;
	private String durationValueOrg = null;
	private String durationUOMCode = null;
	private String plannedInd = null;
	private String reasonCode = null;
	private String ageAtVisit = null;
	private String ageAtVisitUnits = null;
	private String plannedDuration = null;
	private String plannedDurDesc = null;
	private String subTypeCode = null;
	private Long studyParticipantAssignId;
	private Long securityKey;
	private String source = null;
	private Date sourceExtractDate = null;
	private Date ctomInsertDate = null;
	private Date ctomUpdateDate = null;

	private Observation observation = null;
	private Procedure procedure = null;
	private StudyTimePoint studyTimePoint = null;

	/**
	 * @return the ageAtVisit
	 */
	public String getAgeAtVisit()
	{
		return ageAtVisit;
	}

	/**
	 * @param ageAtVisit
	 *            the ageAtVisit to set
	 */
	public void setAgeAtVisit(String ageAtVisit)
	{
		this.ageAtVisit = ageAtVisit;
	}

	/**
	 * @return the ageAtVisitUnits
	 */
	public String getAgeAtVisitUnits()
	{
		return ageAtVisitUnits;
	}

	/**
	 * @param ageAtVisitUnits
	 *            the ageAtVisitUnits to set
	 */
	public void setAgeAtVisitUnits(String ageAtVisitUnits)
	{
		this.ageAtVisitUnits = ageAtVisitUnits;
	}

	/**
	 * @return the reasonCode
	 */
	public String getReasonCode()
	{
		return reasonCode;
	}

	/**
	 * @param reasonCode
	 *            the reasonCode to set
	 */
	public void setReasonCode(String reasonCode)
	{
		this.reasonCode = reasonCode;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return the startDateOrig
	 */
	public String getStartDateOrig()
	{
		return startDateOrig;
	}

	/**
	 * @param startDateOrig
	 *            the startDateOrig to set
	 */
	public void setStartDateOrig(String startDateOrig)
	{
		this.startDateOrig = startDateOrig;
	}

	/**
	 * @return the stopDate
	 */
	public Date getStopDate()
	{
		return stopDate;
	}

	/**
	 * @param stopDate
	 *            the stopDate to set
	 */
	public void setStopDate(Date stopDate)
	{
		this.stopDate = stopDate;
	}

	/**
	 * @return the stopDateOrig
	 */
	public String getStopDateOrig()
	{
		return stopDateOrig;
	}

	/**
	 * @param stopDateOrig
	 *            the stopDateOrig to set
	 */
	public void setStopDateOrig(String stopDateOrig)
	{
		this.stopDateOrig = stopDateOrig;
	}

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return the observation
	 */
	public Observation getObservation()
	{
		return observation;
	}

	/**
	 * @param observation
	 *            the observation to set
	 */
	public void setObservation(Observation observation)
	{
		this.observation = observation;
	}

	/**
	 * @return the procedure
	 */
	public Procedure getProcedure()
	{
		return procedure;
	}

	/**
	 * @param procedure
	 *            the procedure to set
	 */
	public void setProcedure(Procedure procedure)
	{
		this.procedure = procedure;
	}

	/**
	 * @return the studyTimePoint
	 */
	public StudyTimePoint getStudyTimePoint()
	{
		return studyTimePoint;
	}

	/**
	 * @param studyTimePoint
	 *            the studyTimePoint to set
	 */
	public void setStudyTimePoint(StudyTimePoint studyTimePoint)
	{
		this.studyTimePoint = studyTimePoint;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return the descTxt
	 */
	public String getDescTxt()
	{
		return descTxt;
	}

	/**
	 * @param descTxt
	 *            the descTxt to set
	 */
	public void setDescTxt(String descTxt)
	{
		this.descTxt = descTxt;
	}

	/**
	 * @return the durationValue
	 */
	public Long getDurationValue()
	{
		return durationValue;
	}

	/**
	 * @param durationValue
	 *            the durationValue to set
	 */
	public void setDurationValue(Long durationValue)
	{
		this.durationValue = durationValue;
	}

	/**
	 * @return the durationValueOrg
	 */
	public String getDurationValueOrg()
	{
		return durationValueOrg;
	}

	/**
	 * @param durationValueOrg
	 *            the durationValueOrg to set
	 */
	public void setDurationValueOrg(String durationValueOrg)
	{
		this.durationValueOrg = durationValueOrg;
	}

	/**
	 * @return the durationUOMCode
	 */
	public String getDurationUOMCode()
	{
		return durationUOMCode;
	}

	/**
	 * @param durationUOMCode
	 *            the durationUOMCode to set
	 */
	public void setDurationUOMCode(String durationUOMCode)
	{
		this.durationUOMCode = durationUOMCode;
	}

	/**
	 * @return the plannedInd
	 */
	public String getPlannedInd()
	{
		return plannedInd;
	}

	/**
	 * @param plannedInd
	 *            the plannedInd to set
	 */
	public void setPlannedInd(String plannedInd)
	{
		this.plannedInd = plannedInd;
	}

	/**
	 * @return the plannedDuration
	 */
	public String getPlannedDuration()
	{
		return plannedDuration;
	}

	/**
	 * @param plannedDuration
	 *            the plannedDuration to set
	 */
	public void setPlannedDuration(String plannedDuration)
	{
		this.plannedDuration = plannedDuration;
	}

	/**
	 * @return the plannedDurDesc
	 */
	public String getPlannedDurDesc()
	{
		return plannedDurDesc;
	}

	/**
	 * @param plannedDurDesc
	 *            the plannedDurDesc to set
	 */
	public void setPlannedDurDesc(String plannedDurDesc)
	{
		this.plannedDurDesc = plannedDurDesc;
	}

	/**
	 * @return the subTypeCode
	 */
	public String getSubTypeCode()
	{
		return subTypeCode;
	}

	/**
	 * @param subTypeCode
	 *            the subTypeCode to set
	 */
	public void setSubTypeCode(String subTypeCode)
	{
		this.subTypeCode = subTypeCode;
	}

	/**
	 * @return the studyParticipantAssignId
	 */
	public Long getStudyParticipantAssignId()
	{
		return studyParticipantAssignId;
	}

	/**
	 * @param studyParticipantAssignId
	 *            the studyParticipantAssignId to set
	 */
	public void setStudyParticipantAssignId(Long studyParticipantAssignId)
	{
		this.studyParticipantAssignId = studyParticipantAssignId;
	}

	/**
	 * @return the securityKey
	 */
	public Long getSecurityKey()
	{
		return securityKey;
	}

	/**
	 * @param securityKey
	 *            the securityKey to set
	 */
	public void setSecurityKey(Long securityKey)
	{
		this.securityKey = securityKey;
	}

	/**
	 * @return the source
	 */
	public String getSource()
	{
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source)
	{
		this.source = source;
	}

	/**
	 * @return the sourceExtractDate
	 */
	public Date getSourceExtractDate()
	{
		return sourceExtractDate;
	}

	/**
	 * @param sourceExtractDate
	 *            the sourceExtractDate to set
	 */
	public void setSourceExtractDate(Date sourceExtractDate)
	{
		this.sourceExtractDate = sourceExtractDate;
	}

	/**
	 * @return the ctomInsertDate
	 */
	public Date getCtomInsertDate()
	{
		return ctomInsertDate;
	}

	/**
	 * @param ctomInsertDate
	 *            the ctomInsertDate to set
	 */
	public void setCtomInsertDate(Date ctomInsertDate)
	{
		this.ctomInsertDate = ctomInsertDate;
	}

	/**
	 * @return the ctomUpdateDate
	 */
	public Date getCtomUpdateDate()
	{
		return ctomUpdateDate;
	}

	/**
	 * @param ctomUpdateDate
	 *            the ctomUpdateDate to set
	 */
	public void setCtomUpdateDate(Date ctomUpdateDate)
	{
		this.ctomUpdateDate = ctomUpdateDate;
	}

}
