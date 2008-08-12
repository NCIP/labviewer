/**
 * 
 */
package gov.nih.nci.cabig.labviewer.grid;

import gov.nih.nci.ctom.ctlab.domain.Participant;

import java.util.Calendar;

/**
 * @author asharma
 *
 */
public class ParticipantPersistTime {
  
	 private Participant participant;
	 private Calendar persistTime;
	/**
	 * @return the participant
	 */
	public Participant getParticipant() {
		return participant;
	}
	/**
	 * @param participant the participant to set
	 */
	public void setParticipant(Participant participant) {
		this.participant = participant;
	}
	/**
	 * @return the persistTime
	 */
	public Calendar getPersistTime() {
		return persistTime;
	}
	/**
	 * @param persistTime the persistTime to set
	 */
	public void setPersistTime(Calendar persistTime) {
		this.persistTime = persistTime;
	}
	 
	 
	
}
