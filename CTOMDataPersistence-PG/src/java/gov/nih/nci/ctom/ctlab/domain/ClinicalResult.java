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
package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class ClinicalResult
{

	private String crCode;
	private String crCodeSystem;
	private String crCodeSystemName;
	private Double crCodeSystemVersion;
	private String crCodeDisplayText;
	private String valueUnitOfMeasureCdSystem = null;
	private String valueUnitOfMeasureCdSystemName = null;

	private Long id;
	private String panelName;
	private String assayMtdCode;
	private String bodyPosCode;
	private String labRefRangeCode;
	private String labTechCd;
	private String meansVitStatObtCd;
	private String normalAbnorInd;
	private String bioMarkerInd;
	private String sigInd;
	private String addTestDesc;
	private String refFlag;
	private Long numPrecision;
	private String numPrecisionOrg;
	private Double refRangeLow;
	private String refRangeLowOrg;
	private Double refRangeHigh;
	private String refRangeHighOrg;
	private String refRangeComment;
	private String refTxtList;
	private Long labTestConDesId;
	private Long performLabId;
	private Long valUOMConDesId;
	private Long conDesId;
	private String source;
	private Date srcExtractDt = null;
	private Date ctomInsertDt = null;
	private Date ctomUpdateDt = null;
	private String value;
	private String valueUnitOfMeasureCd;

	private Long observationId;
	private PerformingLaboratory performingLaboratory = null;

	/**
	 * @return the labTechCd
	 */
	public String getLabTechCd()
	{
		return labTechCd;
	}

	/**
	 * @param labTechCd
	 *            the labTechCd to set
	 */
	public void setLabTechCd(String labTechCd)
	{
		this.labTechCd = labTechCd;
	}

	/**
	 * @return the panelName
	 */
	public String getPanelName()
	{
		return panelName;
	}

	/**
	 * @param panelName
	 *            the panelName to set
	 */
	public void setPanelName(String panelName)
	{
		this.panelName = panelName;
	}

	/**
	 * @return the refFlag
	 */
	public String getRefFlag()
	{
		return refFlag;
	}

	/**
	 * @param refFlag
	 *            the refFlag to set
	 */
	public void setRefFlag(String refFlag)
	{
		this.refFlag = refFlag;
	}

	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * @return the valueUnitOfMeasureCd
	 */
	public String getValueUnitOfMeasureCd()
	{
		return valueUnitOfMeasureCd;
	}

	/**
	 * @param valueUnitOfMeasureCd
	 *            the valueUnitOfMeasureCd to set
	 */
	public void setValueUnitOfMeasureCd(String valueUnitOfMeasureCd)
	{
		this.valueUnitOfMeasureCd = valueUnitOfMeasureCd;
	}

	/**
	 * @return the performingLaboratory
	 */
	public PerformingLaboratory getPerformingLaboratory()
	{
		return performingLaboratory;
	}

	/**
	 * @param performingLaboratory
	 *            the performingLaboratory to set
	 */
	public void setPerformingLaboratory(
			PerformingLaboratory performingLaboratory)
	{
		this.performingLaboratory = performingLaboratory;
	}

	/**
	 * @return the observationId
	 */
	public Long getObservationId()
	{
		return observationId;
	}

	/**
	 * @param observationId
	 *            the observationId to set
	 */
	public void setObservationId(Long observationId)
	{
		this.observationId = observationId;
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
	 * @return the assayMtdCode
	 */
	public String getAssayMtdCode()
	{
		return assayMtdCode;
	}

	/**
	 * @param assayMtdCode
	 *            the assayMtdCode to set
	 */
	public void setAssayMtdCode(String assayMtdCode)
	{
		this.assayMtdCode = assayMtdCode;
	}

	/**
	 * @return the bodyPosCode
	 */
	public String getBodyPosCode()
	{
		return bodyPosCode;
	}

	/**
	 * @param bodyPosCode
	 *            the bodyPosCode to set
	 */
	public void setBodyPosCode(String bodyPosCode)
	{
		this.bodyPosCode = bodyPosCode;
	}

	/**
	 * @return the labRefRangeCode
	 */
	public String getLabRefRangeCode()
	{
		return labRefRangeCode;
	}

	/**
	 * @param labRefRangeCode
	 *            the labRefRangeCode to set
	 */
	public void setLabRefRangeCode(String labRefRangeCode)
	{
		this.labRefRangeCode = labRefRangeCode;
	}

	/**
	 * @return the meansVitStatObtCd
	 */
	public String getMeansVitStatObtCd()
	{
		return meansVitStatObtCd;
	}

	/**
	 * @param meansVitStatObtCd
	 *            the meansVitStatObtCd to set
	 */
	public void setMeansVitStatObtCd(String meansVitStatObtCd)
	{
		this.meansVitStatObtCd = meansVitStatObtCd;
	}

	/**
	 * @return the normalAbnorInd
	 */
	public String getNormalAbnorInd()
	{
		return normalAbnorInd;
	}

	/**
	 * @param normalAbnorInd
	 *            the normalAbnorInd to set
	 */
	public void setNormalAbnorInd(String normalAbnorInd)
	{
		this.normalAbnorInd = normalAbnorInd;
	}

	/**
	 * @return the bioMarkerInd
	 */
	public String getBioMarkerInd()
	{
		return bioMarkerInd;
	}

	/**
	 * @param bioMarkerInd
	 *            the bioMarkerInd to set
	 */
	public void setBioMarkerInd(String bioMarkerInd)
	{
		this.bioMarkerInd = bioMarkerInd;
	}

	/**
	 * @return the sigInd
	 */
	public String getSigInd()
	{
		return sigInd;
	}

	/**
	 * @param sigInd
	 *            the sigInd to set
	 */
	public void setSigInd(String sigInd)
	{
		this.sigInd = sigInd;
	}

	/**
	 * @return the addTestDesc
	 */
	public String getAddTestDesc()
	{
		return addTestDesc;
	}

	/**
	 * @param addTestDesc
	 *            the addTestDesc to set
	 */
	public void setAddTestDesc(String addTestDesc)
	{
		this.addTestDesc = addTestDesc;
	}

	/**
	 * @return the numPrecision
	 */
	public Long getNumPrecision()
	{
		return numPrecision;
	}

	/**
	 * @param numPrecision
	 *            the numPrecision to set
	 */
	public void setNumPrecision(Long numPrecision)
	{
		this.numPrecision = numPrecision;
	}

	/**
	 * @return the numPrecisionOrg
	 */
	public String getNumPrecisionOrg()
	{
		return numPrecisionOrg;
	}

	/**
	 * @param numPrecisionOrg
	 *            the numPrecisionOrg to set
	 */
	public void setNumPrecisionOrg(String numPrecisionOrg)
	{
		this.numPrecisionOrg = numPrecisionOrg;
	}

	/**
	 * @return the refRangeLow
	 */
	public Double getRefRangeLow()
	{
		return refRangeLow;
	}

	/**
	 * @param refRangeLow
	 *            the refRangeLow to set
	 */
	public void setRefRangeLow(Double refRangeLow)
	{
		this.refRangeLow = refRangeLow;
	}

	/**
	 * @return the refRangeLowOrg
	 */
	public String getRefRangeLowOrg()
	{
		return refRangeLowOrg;
	}

	/**
	 * @param refRangeLowOrg
	 *            the refRangeLowOrg to set
	 */
	public void setRefRangeLowOrg(String refRangeLowOrg)
	{
		this.refRangeLowOrg = refRangeLowOrg;
	}

	/**
	 * @return the refRangeHigh
	 */
	public Double getRefRangeHigh()
	{
		return refRangeHigh;
	}

	/**
	 * @param refRangeHigh
	 *            the refRangeHigh to set
	 */
	public void setRefRangeHigh(Double refRangeHigh)
	{
		this.refRangeHigh = refRangeHigh;
	}

	/**
	 * @return the refRangeHighOrg
	 */
	public String getRefRangeHighOrg()
	{
		return refRangeHighOrg;
	}

	/**
	 * @param refRangeHighOrg
	 *            the refRangeHighOrg to set
	 */
	public void setRefRangeHighOrg(String refRangeHighOrg)
	{
		this.refRangeHighOrg = refRangeHighOrg;
	}

	/**
	 * @return the refRangeComment
	 */
	public String getRefRangeComment()
	{
		return refRangeComment;
	}

	/**
	 * @param refRangeComment
	 *            the refRangeComment to set
	 */
	public void setRefRangeComment(String refRangeComment)
	{
		this.refRangeComment = refRangeComment;
	}

	/**
	 * @return the refTxtList
	 */
	public String getRefTxtList()
	{
		return refTxtList;
	}

	/**
	 * @param refTxtList
	 *            the refTxtList to set
	 */
	public void setRefTxtList(String refTxtList)
	{
		this.refTxtList = refTxtList;
	}

	/**
	 * @return the labTestConDesId
	 */
	public Long getLabTestConDesId()
	{
		return labTestConDesId;
	}

	/**
	 * @param labTestConDesId
	 *            the labTestConDesId to set
	 */
	public void setLabTestConDesId(Long labTestConDesId)
	{
		this.labTestConDesId = labTestConDesId;
	}

	/**
	 * @return the performLabId
	 */
	public Long getPerformLabId()
	{
		return performLabId;
	}

	/**
	 * @param performLabId
	 *            the performLabId to set
	 */
	public void setPerformLabId(Long performLabId)
	{
		this.performLabId = performLabId;
	}

	/**
	 * @return the valUOMConDesId
	 */
	public Long getValUOMConDesId()
	{
		return valUOMConDesId;
	}

	/**
	 * @param valUOMConDesId
	 *            the valUOMConDesId to set
	 */
	public void setValUOMConDesId(Long valUOMConDesId)
	{
		this.valUOMConDesId = valUOMConDesId;
	}

	/**
	 * @return the conDesId
	 */
	public Long getConDesId()
	{
		return conDesId;
	}

	/**
	 * @param conDesId
	 *            the conDesId to set
	 */
	public void setConDesId(Long conDesId)
	{
		this.conDesId = conDesId;
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
	 * @return the crCode
	 */
	public String getCrCode()
	{
		return crCode;
	}

	/**
	 * @param crCode
	 *            the crCode to set
	 */
	public void setCrCode(String crCode)
	{
		this.crCode = crCode;
	}

	/**
	 * @return the crCodeSystem
	 */
	public String getCrCodeSystem()
	{
		return crCodeSystem;
	}

	/**
	 * @param crCodeSystem
	 *            the crCodeSystem to set
	 */
	public void setCrCodeSystem(String crCodeSystem)
	{
		this.crCodeSystem = crCodeSystem;
	}

	/**
	 * @return the crCodeSystemName
	 */
	public String getCrCodeSystemName()
	{
		return crCodeSystemName;
	}

	/**
	 * @param crCodeSystemName
	 *            the crCodeSystemName to set
	 */
	public void setCrCodeSystemName(String crCodeSystemName)
	{
		this.crCodeSystemName = crCodeSystemName;
	}

	/**
	 * @return the crCodeSystemVersion
	 */
	public Double getCrCodeSystemVersion()
	{
		return crCodeSystemVersion;
	}

	/**
	 * @param crCodeSystemVersion
	 *            the crCodeSystemVersion to set
	 */
	public void setCrCodeSystemVersion(Double crCodeSystemVersion)
	{
		this.crCodeSystemVersion = crCodeSystemVersion;
	}

	/**
	 * @return the crCodeDisplayText
	 */
	public String getCrCodeDisplayText()
	{
		return crCodeDisplayText;
	}

	/**
	 * @param crCodeDisplayText
	 *            the crCodeDisplayText to set
	 */
	public void setCrCodeDisplayText(String crCodeDisplayText)
	{
		this.crCodeDisplayText = crCodeDisplayText;
	}

	/**
	 * @return the valueUnitOfMeasureCdSystem
	 */
	public String getValueUnitOfMeasureCdSystem()
	{
		return valueUnitOfMeasureCdSystem;
	}

	/**
	 * @param valueUnitOfMeasureCdSystem
	 *            the valueUnitOfMeasureCdSystem to set
	 */
	public void setValueUnitOfMeasureCdSystem(String valueUnitOfMeasureCdSystem)
	{
		this.valueUnitOfMeasureCdSystem = valueUnitOfMeasureCdSystem;
	}

	/**
	 * @return the valueUnitOfMeasureCdSystemName
	 */
	public String getValueUnitOfMeasureCdSystemName()
	{
		return valueUnitOfMeasureCdSystemName;
	}

	/**
	 * @param valueUnitOfMeasureCdSystemName
	 *            the valueUnitOfMeasureCdSystemName to set
	 */
	public void setValueUnitOfMeasureCdSystemName(
			String valueUnitOfMeasureCdSystemName)
	{
		this.valueUnitOfMeasureCdSystemName = valueUnitOfMeasureCdSystemName;
	}

}
