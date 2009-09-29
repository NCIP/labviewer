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

public class StudyParticipantAssignment
{

	private Long id;
	private Long studyPartId;
	private String studyPartIdOrig = null;
	private String armId;
	private String subGroupCode = null;
	private Date informedConsetFrmSignedDt;
	private String informedConsetFrmSignedDtOrg;
	private Long enrollAge;
	private String enrollAgeOrg;
	private Long studyAgentDoseLevel;
	private String studyAgentDoseLevelOrg;
	private String studyAgentDoseLevelUOMCD;
	private Date offStudyDt;
	private String offStudyDtOrg;
	private String offStudyResCode;
	private String offStudyResOthTxt;
	private String type;
	private Long participantId;
	private Long studySiteId;
	private Long securityKey;
	private String source;
	private Date srcExtractDt;
	private Date ctomInsertDt;
	private Date ctomUpdateDt;
	private Activity activity = null;
	private Participant participant = null;
	private Identifier identifier;

	public StudyParticipantAssignment()
	{
		identifier = new Identifier();
	}

	/**
	 * @return the studyPartId
	 */
	public Long getStudyPartId()
	{
		return studyPartId;
	}

	/**
	 * @param studyPartId
	 *            the studyPartId to set
	 */
	public void setStudyPartId(Long studyPartId)
	{
		this.studyPartId = studyPartId;
	}

	/**
	 * @return the armId
	 */
	public String getArmId()
	{
		return armId;
	}

	/**
	 * @param armId
	 *            the armId to set
	 */
	public void setArmId(String armId)
	{
		this.armId = armId;
	}

	/**
	 * @return the subGroupCode
	 */
	public String getSubGroupCode()
	{
		return subGroupCode;
	}

	/**
	 * @param subGroupCode
	 *            the subGroupCode to set
	 */
	public void setSubGroupCode(String subGroupCode)
	{
		this.subGroupCode = subGroupCode;
	}

	/**
	 * @return the informedConsetFrmSignedDt
	 */
	public Date getInformedConsetFrmSignedDt()
	{
		return informedConsetFrmSignedDt;
	}

	/**
	 * @param informedConsetFrmSignedDt
	 *            the informedConsetFrmSignedDt to set
	 */
	public void setInformedConsetFrmSignedDt(Date informedConsetFrmSignedDt)
	{
		this.informedConsetFrmSignedDt = informedConsetFrmSignedDt;
	}

	/**
	 * @return the informedConsetFrmSignedDtOrg
	 */
	public String getInformedConsetFrmSignedDtOrg()
	{
		return informedConsetFrmSignedDtOrg;
	}

	/**
	 * @param informedConsetFrmSignedDtOrg
	 *            the informedConsetFrmSignedDtOrg to set
	 */
	public void setInformedConsetFrmSignedDtOrg(
			String informedConsetFrmSignedDtOrg)
	{
		this.informedConsetFrmSignedDtOrg = informedConsetFrmSignedDtOrg;
	}

	/**
	 * @return the enrollAge
	 */
	public Long getEnrollAge()
	{
		return enrollAge;
	}

	/**
	 * @param enrollAge
	 *            the enrollAge to set
	 */
	public void setEnrollAge(Long enrollAge)
	{
		this.enrollAge = enrollAge;
	}

	/**
	 * @return the enrollAgeOrg
	 */
	public String getEnrollAgeOrg()
	{
		return enrollAgeOrg;
	}

	/**
	 * @param enrollAgeOrg
	 *            the enrollAgeOrg to set
	 */
	public void setEnrollAgeOrg(String enrollAgeOrg)
	{
		this.enrollAgeOrg = enrollAgeOrg;
	}

	/**
	 * @return the studyAgentDoseLevel
	 */
	public Long getStudyAgentDoseLevel()
	{
		return studyAgentDoseLevel;
	}

	/**
	 * @param studyAgentDoseLevel
	 *            the studyAgentDoseLevel to set
	 */
	public void setStudyAgentDoseLevel(Long studyAgentDoseLevel)
	{
		this.studyAgentDoseLevel = studyAgentDoseLevel;
	}

	/**
	 * @return the studyAgentDoseLevelOrg
	 */
	public String getStudyAgentDoseLevelOrg()
	{
		return studyAgentDoseLevelOrg;
	}

	/**
	 * @param studyAgentDoseLevelOrg
	 *            the studyAgentDoseLevelOrg to set
	 */
	public void setStudyAgentDoseLevelOrg(String studyAgentDoseLevelOrg)
	{
		this.studyAgentDoseLevelOrg = studyAgentDoseLevelOrg;
	}

	/**
	 * @return the studyAgentDoseLevelUOMCD
	 */
	public String getStudyAgentDoseLevelUOMCD()
	{
		return studyAgentDoseLevelUOMCD;
	}

	/**
	 * @param studyAgentDoseLevelUOMCD
	 *            the studyAgentDoseLevelUOMCD to set
	 */
	public void setStudyAgentDoseLevelUOMCD(String studyAgentDoseLevelUOMCD)
	{
		this.studyAgentDoseLevelUOMCD = studyAgentDoseLevelUOMCD;
	}

	/**
	 * @return the offStudyDt
	 */
	public Date getOffStudyDt()
	{
		return offStudyDt;
	}

	/**
	 * @param offStudyDt
	 *            the offStudyDt to set
	 */
	public void setOffStudyDt(Date offStudyDt)
	{
		this.offStudyDt = offStudyDt;
	}

	/**
	 * @return the offStudyDtOrg
	 */
	public String getOffStudyDtOrg()
	{
		return offStudyDtOrg;
	}

	/**
	 * @param offStudyDtOrg
	 *            the offStudyDtOrg to set
	 */
	public void setOffStudyDtOrg(String offStudyDtOrg)
	{
		this.offStudyDtOrg = offStudyDtOrg;
	}

	/**
	 * @return the offStudyResCode
	 */
	public String getOffStudyResCode()
	{
		return offStudyResCode;
	}

	/**
	 * @param offStudyResCode
	 *            the offStudyResCode to set
	 */
	public void setOffStudyResCode(String offStudyResCode)
	{
		this.offStudyResCode = offStudyResCode;
	}

	/**
	 * @return the offStudyResOthTxt
	 */
	public String getOffStudyResOthTxt()
	{
		return offStudyResOthTxt;
	}

	/**
	 * @param offStudyResOthTxt
	 *            the offStudyResOthTxt to set
	 */
	public void setOffStudyResOthTxt(String offStudyResOthTxt)
	{
		this.offStudyResOthTxt = offStudyResOthTxt;
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
	 * @return the participantId
	 */
	public Long getParticipantId()
	{
		return participantId;
	}

	/**
	 * @param participantId
	 *            the participantId to set
	 */
	public void setParticipantId(Long participantId)
	{
		this.participantId = participantId;
	}

	/**
	 * @return the studySiteId
	 */
	public Long getStudySiteId()
	{
		return studySiteId;
	}

	/**
	 * @param studySiteId
	 *            the studySiteId to set
	 */
	public void setStudySiteId(Long studySiteId)
	{
		this.studySiteId = studySiteId;
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
	 * @return the srcExtractDt
	 */
	public Date getSrcExtractDt()
	{
		return srcExtractDt;
	}

	/**
	 * @param srcExtractDt
	 *            the srcExtractDt to set
	 */
	public void setSrcExtractDt(Date srcExtractDt)
	{
		this.srcExtractDt = srcExtractDt;
	}

	/**
	 * @return the ctomInsertDt
	 */
	public Date getCtomInsertDt()
	{
		return ctomInsertDt;
	}

	/**
	 * @param ctomInsertDt
	 *            the ctomInsertDt to set
	 */
	public void setCtomInsertDt(Date ctomInsertDt)
	{
		this.ctomInsertDt = ctomInsertDt;
	}

	/**
	 * @return the ctomUpdateDt
	 */
	public Date getCtomUpdateDt()
	{
		return ctomUpdateDt;
	}

	/**
	 * @param ctomUpdateDt
	 *            the ctomUpdateDt to set
	 */
	public void setCtomUpdateDt(Date ctomUpdateDt)
	{
		this.ctomUpdateDt = ctomUpdateDt;
	}

	/**
	 * @return the studyPartIdOrig
	 */
	public String getStudyPartIdOrig()
	{
		return studyPartIdOrig;
	}

	/**
	 * @param studyPartIdOrig
	 *            the studyPartIdOrig to set
	 */
	public void setStudyPartIdOrig(String studyPartIdOrig)
	{
		this.studyPartIdOrig = studyPartIdOrig;
	}

	/**
	 * @return the activity
	 */
	public Activity getActivity()
	{
		return activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(Activity activity)
	{
		this.activity = activity;
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
	 * @return the participant
	 */
	public Participant getParticipant()
	{
		return participant;
	}

	/**
	 * @param participant
	 *            the participant to set
	 */
	public void setParticipant(Participant participant)
	{
		this.participant = participant;
	}

	/**
	 * @return the identifier
	 */
	public Identifier getIdentifier()
	{
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(Identifier identifier)
	{
		this.identifier = identifier;
	}
}
