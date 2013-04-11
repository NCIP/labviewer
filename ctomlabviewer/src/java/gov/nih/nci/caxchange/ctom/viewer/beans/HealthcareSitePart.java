/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.beans;

import gov.nih.nci.labhub.domain.HealthCareSite;
import gov.nih.nci.labhub.domain.Participant;

import java.util.Date;

/**
 * @author asharma
 */
public class HealthcareSitePart
{
	Integer id;
	String participantIdentifier;
	Integer healthcareSiteId;
	Integer participantId;
	Integer securityKey;
	String source;
	Date sourceExtractDate;
	Date ctomInsertDate;
	Date ctomUpdateDate;

	HealthCareSite healthCareSite;
	Participant participant;

	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * @return the participantIdentifier
	 */
	public String getParticipantIdentifier()
	{
		return participantIdentifier;
	}

	/**
	 * @param participantIdentifier
	 *            the participantIdentifier to set
	 */
	public void setParticipantIdentifier(String participantIdentifier)
	{
		this.participantIdentifier = participantIdentifier;
	}

	/**
	 * @return the healthcareSiteId
	 */
	public Integer getHealthcareSiteId()
	{
		return healthcareSiteId;
	}

	/**
	 * @param healthcareSiteId
	 *            the healthcareSiteId to set
	 */
	public void setHealthcareSiteId(Integer healthcareSiteId)
	{
		this.healthcareSiteId = healthcareSiteId;
	}

	/**
	 * @return the participantId
	 */
	public Integer getParticipantId()
	{
		return participantId;
	}

	/**
	 * @param participantId
	 *            the participantId to set
	 */
	public void setParticipantId(Integer participantId)
	{
		this.participantId = participantId;
	}

	/**
	 * @return the securityKey
	 */
	public Integer getSecurityKey()
	{
		return securityKey;
	}

	/**
	 * @param securityKey
	 *            the securityKey to set
	 */
	public void setSecurityKey(Integer securityKey)
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

}
