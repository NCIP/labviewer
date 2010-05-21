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

public class Participant
{

	private Long id;
	private String initials = null;
	private String lastName = null;
	private String firstName = null;
	private String middleName = null;

	private Date birthDate = null;
	private String birthDateOrig = null;
	private String adminGenderCode = null;
	private String adminGenderCodeSystem = null;
	private String adminGenderCodeSystemName = null;

	private String telecomAddress = null;
	private String streetAddress = null;
	private String city = null;
	private String state = null;
	private String zipCode = null;
	private String countryCode;
	private String phone = null;

	private String educationLevelCode = null;

	private String ethnicGroupCode = null;
	private String householdIncomeCode = null;
	private String maritalStatusCode;
	private String raceCode;
	private String raceCodeSystem;
	private String raceCodeSystemName;
	private String employmentStatusCode = null;
	private String employmentStatusOtherTxt = null;

	private String paymentMethodCode = null;
	private String confidentialityInd = null;

	private Long admGndrConceptDescId = null;
	private Long raceConceptDescId = null;

	private Long securityKey = null;
	private String source = null;
	private Date sourceExtractDate = null;
	private Date ctomInsertDate = null;
	private Date ctomUpdateDate = null;

	private Identifier identifier;

	public Participant()
	{
		identifier = new Identifier();
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
	 * @return the initials
	 */
	public String getInitials()
	{
		return initials;
	}

	/**
	 * @param initials
	 *            the initials to set
	 */
	public void setInitials(String initials)
	{
		this.initials = initials;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName()
	{
		return middleName;
	}

	/**
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate()
	{
		return birthDate;
	}

	/**
	 * @param birthDate
	 *            the birthDate to set
	 */
	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	/**
	 * @return the birthDateOrig
	 */
	public String getBirthDateOrig()
	{
		return birthDateOrig;
	}

	/**
	 * @param birthDateOrig
	 *            the birthDateOrig to set
	 */
	public void setBirthDateOrig(String birthDateOrig)
	{
		this.birthDateOrig = birthDateOrig;
	}

	/**
	 * @return the adminGenderCode
	 */
	public String getAdminGenderCode()
	{
		return adminGenderCode;
	}

	/**
	 * @param adminGenderCode
	 *            the adminGenderCode to set
	 */
	public void setAdminGenderCode(String adminGenderCode)
	{
		this.adminGenderCode = adminGenderCode;
	}

	/**
	 * @return the telecomAddress
	 */
	public String getTelecomAddress()
	{
		return telecomAddress;
	}

	/**
	 * @param telecomAddress
	 *            the telecomAddress to set
	 */
	public void setTelecomAddress(String telecomAddress)
	{
		this.telecomAddress = telecomAddress;
	}

	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress()
	{
		return streetAddress;
	}

	/**
	 * @param streetAddress
	 *            the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress)
	{
		this.streetAddress = streetAddress;
	}

	/**
	 * @return the city
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode()
	{
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode()
	{
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            the countryCode to set
	 */
	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}

	/**
	 * @return the phone
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return the educationLevelCode
	 */
	public String getEducationLevelCode()
	{
		return educationLevelCode;
	}

	/**
	 * @param educationLevelCode
	 *            the educationLevelCode to set
	 */
	public void setEducationLevelCode(String educationLevelCode)
	{
		this.educationLevelCode = educationLevelCode;
	}

	/**
	 * @return the ethnicGroupCode
	 */
	public String getEthnicGroupCode()
	{
		return ethnicGroupCode;
	}

	/**
	 * @param ethnicGroupCode
	 *            the ethnicGroupCode to set
	 */
	public void setEthnicGroupCode(String ethnicGroupCode)
	{
		this.ethnicGroupCode = ethnicGroupCode;
	}

	/**
	 * @return the householdIncomeCode
	 */
	public String getHouseholdIncomeCode()
	{
		return householdIncomeCode;
	}

	/**
	 * @param householdIncomeCode
	 *            the householdIncomeCode to set
	 */
	public void setHouseholdIncomeCode(String householdIncomeCode)
	{
		this.householdIncomeCode = householdIncomeCode;
	}

	/**
	 * @return the maritalStatusCode
	 */
	public String getMaritalStatusCode()
	{
		return maritalStatusCode;
	}

	/**
	 * @param maritalStatusCode
	 *            the maritalStatusCode to set
	 */
	public void setMaritalStatusCode(String maritalStatusCode)
	{
		this.maritalStatusCode = maritalStatusCode;
	}

	/**
	 * @return the raceCode
	 */
	public String getRaceCode()
	{
		return raceCode;
	}

	/**
	 * @param raceCode
	 *            the raceCode to set
	 */
	public void setRaceCode(String raceCode)
	{
		this.raceCode = raceCode;
	}

	/**
	 * @return the employmentStatusCode
	 */
	public String getEmploymentStatusCode()
	{
		return employmentStatusCode;
	}

	/**
	 * @param employmentStatusCode
	 *            the employmentStatusCode to set
	 */
	public void setEmploymentStatusCode(String employmentStatusCode)
	{
		this.employmentStatusCode = employmentStatusCode;
	}

	/**
	 * @return the employmentStatusOtherTxt
	 */
	public String getEmploymentStatusOtherTxt()
	{
		return employmentStatusOtherTxt;
	}

	/**
	 * @param employmentStatusOtherTxt
	 *            the employmentStatusOtherTxt to set
	 */
	public void setEmploymentStatusOtherTxt(String employmentStatusOtherTxt)
	{
		this.employmentStatusOtherTxt = employmentStatusOtherTxt;
	}

	/**
	 * @return the paymentMethodCode
	 */
	public String getPaymentMethodCode()
	{
		return paymentMethodCode;
	}

	/**
	 * @param paymentMethodCode
	 *            the paymentMethodCode to set
	 */
	public void setPaymentMethodCode(String paymentMethodCode)
	{
		this.paymentMethodCode = paymentMethodCode;
	}

	/**
	 * @return the confidentialityInd
	 */
	public String getConfidentialityInd()
	{
		return confidentialityInd;
	}

	/**
	 * @param confidentialityInd
	 *            the confidentialityInd to set
	 */
	public void setConfidentialityInd(String confidentialityInd)
	{
		this.confidentialityInd = confidentialityInd;
	}

	/**
	 * @return the admGndrConceptDescId
	 */
	public Long getAdmGndrConceptDescId()
	{
		return admGndrConceptDescId;
	}

	/**
	 * @param admGndrConceptDescId
	 *            the admGndrConceptDescId to set
	 */
	public void setAdmGndrConceptDescId(Long admGndrConceptDescId)
	{
		this.admGndrConceptDescId = admGndrConceptDescId;
	}

	/**
	 * @return the raceConceptDescId
	 */
	public Long getRaceConceptDescId()
	{
		return raceConceptDescId;
	}

	/**
	 * @param raceConceptDescId
	 *            the raceConceptDescId to set
	 */
	public void setRaceConceptDescId(Long raceConceptDescId)
	{
		this.raceConceptDescId = raceConceptDescId;
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
	 * @return the adminGenderCodeSystem
	 */
	public String getAdminGenderCodeSystem()
	{
		return adminGenderCodeSystem;
	}

	/**
	 * @param adminGenderCodeSystem
	 *            the adminGenderCodeSystem to set
	 */
	public void setAdminGenderCodeSystem(String adminGenderCodeSystem)
	{
		this.adminGenderCodeSystem = adminGenderCodeSystem;
	}

	/**
	 * @return the adminGenderCodeSystemName
	 */
	public String getAdminGenderCodeSystemName()
	{
		return adminGenderCodeSystemName;
	}

	/**
	 * @param adminGenderCodeSystemName
	 *            the adminGenderCodeSystemName to set
	 */
	public void setAdminGenderCodeSystemName(String adminGenderCodeSystemName)
	{
		this.adminGenderCodeSystemName = adminGenderCodeSystemName;
	}

	/**
	 * @return the raceCodeSystem
	 */
	public String getRaceCodeSystem()
	{
		return raceCodeSystem;
	}

	/**
	 * @param raceCodeSystem
	 *            the raceCodeSystem to set
	 */
	public void setRaceCodeSystem(String raceCodeSystem)
	{
		this.raceCodeSystem = raceCodeSystem;
	}

	/**
	 * @return the raceCodeSystemName
	 */
	public String getRaceCodeSystemName()
	{
		return raceCodeSystemName;
	}

	/**
	 * @param raceCodeSystemName
	 *            the raceCodeSystemName to set
	 */
	public void setRaceCodeSystemName(String raceCodeSystemName)
	{
		this.raceCodeSystemName = raceCodeSystemName;
	}

}
