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

public class Protocol
{
	private Long id;
	private String nciIdentifier = null;
	private Long amendmentId;
	private String amendmentIdOrg;
	private Date amendmentDt;
	private String amendmentDtOrg;
	private String navyNCIId;
	private String longTxtTitle;
	private String shortTxtTitle;
	private String precisTxt;
	private String descTxt;
	private String diseaseCode;
	private String intentCode;
	private String monitorCode;
	private String phaseCode;
	private String sponsorCode;
	private String blindedId;
	private String multiInstId;
	private String randomId;
	private Long targetAccNum;
	private String targetAccNumOrg;
	private String idAssigningAuth;
	private String docURI;
	private String sponsorMontior;
	private Long securityKey;
	private String source;
	private Date srcExtractDt;
	private Date ctomInsertDt;
	private Date ctomUpdateDt;
	private String ctomInsertUser;
	private String ctomUpdateUser;

	private HealthCareSite healthCareSite = null;
	private Investigator investigator = null;
	private Identifier identifier = null;
	private ProtocolStatus status = null;

	public Protocol()
	{
	}

	/**
	 * @return the healthCareSite
	 */
	public HealthCareSite getHealthCareSite()
	{
		return healthCareSite;
	}

	/**
	 * @param healthCareSite
	 *            the healthCareSite to set
	 */
	public void setHealthCareSite(HealthCareSite healthCareSite)
	{
		this.healthCareSite = healthCareSite;
	}

	/**
	 * @return the investigator
	 */
	public Investigator getInvestigator()
	{
		return investigator;
	}

	/**
	 * @param investigator
	 *            the investigator to set
	 */
	public void setInvestigator(Investigator investigator)
	{
		this.investigator = investigator;
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
	 * @return the nciIdentifier
	 */
	public String getNciIdentifier()
	{
		return nciIdentifier;
	}

	/**
	 * @param nciIdentifier
	 *            the nciIdentifier to set
	 */
	public void setNciIdentifier(String nciIdentifier)
	{
		this.nciIdentifier = nciIdentifier;
	}

	/**
	 * @return the amendmentId
	 */
	public Long getAmendmentId()
	{
		return amendmentId;
	}

	/**
	 * @param amendmentId
	 *            the amendmentId to set
	 */
	public void setAmendmentId(Long amendmentId)
	{
		this.amendmentId = amendmentId;
	}

	/**
	 * @return the amendmentIdOrg
	 */
	public String getAmendmentIdOrg()
	{
		return amendmentIdOrg;
	}

	/**
	 * @param amendmentIdOrg
	 *            the amendmentIdOrg to set
	 */
	public void setAmendmentIdOrg(String amendmentIdOrg)
	{
		this.amendmentIdOrg = amendmentIdOrg;
	}

	/**
	 * @return the amendmentDt
	 */
	public Date getAmendmentDt()
	{
		return amendmentDt;
	}

	/**
	 * @param amendmentDt
	 *            the amendmentDt to set
	 */
	public void setAmendmentDt(Date amendmentDt)
	{
		this.amendmentDt = amendmentDt;
	}

	/**
	 * @return the amendmentDtOrg
	 */
	public String getAmendmentDtOrg()
	{
		return amendmentDtOrg;
	}

	/**
	 * @param amendmentDtOrg
	 *            the amendmentDtOrg to set
	 */
	public void setAmendmentDtOrg(String amendmentDtOrg)
	{
		this.amendmentDtOrg = amendmentDtOrg;
	}

	/**
	 * @return the navyNCIId
	 */
	public String getNavyNCIId()
	{
		return navyNCIId;
	}

	/**
	 * @param navyNCIId
	 *            the navyNCIId to set
	 */
	public void setNavyNCIId(String navyNCIId)
	{
		this.navyNCIId = navyNCIId;
	}

	/**
	 * @return the longTxtTitle
	 */
	public String getLongTxtTitle()
	{
		return longTxtTitle;
	}

	/**
	 * @param longTxtTitle
	 *            the longTxtTitle to set
	 */
	public void setLongTxtTitle(String longTxtTitle)
	{
		this.longTxtTitle = longTxtTitle;
	}

	/**
	 * @return the shortTxtTitle
	 */
	public String getShortTxtTitle()
	{
		return shortTxtTitle;
	}

	/**
	 * @param shortTxtTitle
	 *            the shortTxtTitle to set
	 */
	public void setShortTxtTitle(String shortTxtTitle)
	{
		this.shortTxtTitle = shortTxtTitle;
	}

	/**
	 * @return the precisTxt
	 */
	public String getPrecisTxt()
	{
		return precisTxt;
	}

	/**
	 * @param precisTxt
	 *            the precisTxt to set
	 */
	public void setPrecisTxt(String precisTxt)
	{
		this.precisTxt = precisTxt;
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
	 * @return the diseaseCode
	 */
	public String getDiseaseCode()
	{
		return diseaseCode;
	}

	/**
	 * @param diseaseCode
	 *            the diseaseCode to set
	 */
	public void setDiseaseCode(String diseaseCode)
	{
		this.diseaseCode = diseaseCode;
	}

	/**
	 * @return the intentCode
	 */
	public String getIntentCode()
	{
		return intentCode;
	}

	/**
	 * @param intentCode
	 *            the intentCode to set
	 */
	public void setIntentCode(String intentCode)
	{
		this.intentCode = intentCode;
	}

	/**
	 * @return the monitorCode
	 */
	public String getMonitorCode()
	{
		return monitorCode;
	}

	/**
	 * @param monitorCode
	 *            the monitorCode to set
	 */
	public void setMonitorCode(String monitorCode)
	{
		this.monitorCode = monitorCode;
	}

	/**
	 * @return the phaseCode
	 */
	public String getPhaseCode()
	{
		return phaseCode;
	}

	/**
	 * @param phaseCode
	 *            the phaseCode to set
	 */
	public void setPhaseCode(String phaseCode)
	{
		this.phaseCode = phaseCode;
	}

	/**
	 * @return the sponsorCode
	 */
	public String getSponsorCode()
	{
		return sponsorCode;
	}

	/**
	 * @param sponsorCode
	 *            the sponsorCode to set
	 */
	public void setSponsorCode(String sponsorCode)
	{
		this.sponsorCode = sponsorCode;
	}

	/**
	 * @return the blindedId
	 */
	public String getBlindedId()
	{
		return blindedId;
	}

	/**
	 * @param blindedId
	 *            the blindedId to set
	 */
	public void setBlindedId(String blindedId)
	{
		this.blindedId = blindedId;
	}

	/**
	 * @return the multiInstId
	 */
	public String getMultiInstId()
	{
		return multiInstId;
	}

	/**
	 * @param multiInstId
	 *            the multiInstId to set
	 */
	public void setMultiInstId(String multiInstId)
	{
		this.multiInstId = multiInstId;
	}

	/**
	 * @return the randomId
	 */
	public String getRandomId()
	{
		return randomId;
	}

	/**
	 * @param randomId
	 *            the randomId to set
	 */
	public void setRandomId(String randomId)
	{
		this.randomId = randomId;
	}

	/**
	 * @return the targetAccNum
	 */
	public Long getTargetAccNum()
	{
		return targetAccNum;
	}

	/**
	 * @param targetAccNum
	 *            the targetAccNum to set
	 */
	public void setTargetAccNum(Long targetAccNum)
	{
		this.targetAccNum = targetAccNum;
	}

	/**
	 * @return the targetAccNumOrg
	 */
	public String getTargetAccNumOrg()
	{
		return targetAccNumOrg;
	}

	/**
	 * @param targetAccNumOrg
	 *            the targetAccNumOrg to set
	 */
	public void setTargetAccNumOrg(String targetAccNumOrg)
	{
		this.targetAccNumOrg = targetAccNumOrg;
	}

	/**
	 * @return the idAssigningAuth
	 */
	public String getIdAssigningAuth()
	{
		return idAssigningAuth;
	}

	/**
	 * @param idAssigningAuth
	 *            the idAssigningAuth to set
	 */
	public void setIdAssigningAuth(String idAssigningAuth)
	{
		this.idAssigningAuth = idAssigningAuth;
	}

	/**
	 * @return the docURI
	 */
	public String getDocURI()
	{
		return docURI;
	}

	/**
	 * @param docURI
	 *            the docURI to set
	 */
	public void setDocURI(String docURI)
	{
		this.docURI = docURI;
	}

	/**
	 * @return the sponsorMontior
	 */
	public String getSponsorMontior()
	{
		return sponsorMontior;
	}

	/**
	 * @param sponsorMontior
	 *            the sponsorMontior to set
	 */
	public void setSponsorMontior(String sponsorMontior)
	{
		this.sponsorMontior = sponsorMontior;
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
	 * @return the ctomInsertUser
	 */
	public String getCtomInsertUser()
	{
		return ctomInsertUser;
	}

	/**
	 * @param ctomInsertUser
	 *            the ctomInsertUser to set
	 */
	public void setCtomInsertUser(String ctomInsertUser)
	{
		this.ctomInsertUser = ctomInsertUser;
	}

	/**
	 * @return the ctomUpdateUser
	 */
	public String getCtomUpdateUser()
	{
		return ctomUpdateUser;
	}

	/**
	 * @param ctomUpdateUser
	 *            the ctomUpdateUser to set
	 */
	public void setCtomUpdateUser(String ctomUpdateUser)
	{
		this.ctomUpdateUser = ctomUpdateUser;
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

	/**
	 * @return the status
	 */
	public ProtocolStatus getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(ProtocolStatus status)
	{
		this.status = status;
	}

}
