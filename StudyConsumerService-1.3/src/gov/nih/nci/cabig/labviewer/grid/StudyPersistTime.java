/**
 * 
 */
package gov.nih.nci.cabig.labviewer.grid;

import gov.nih.nci.ctom.ctlab.domain.Protocol;

import java.util.Calendar;

/**
 * @author asharma
 *
 */
public class StudyPersistTime {
  
	 private Protocol protocol;
	 private Calendar persistTime;
	/**
	 * @return the participant
	 */
	public Protocol getProtocol() {
		return protocol;
	}
	/**
	 * @param participant the participant to set
	 */
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
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
