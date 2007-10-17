package gov.nih.nci.ctom.ctlab.domain;

public class StudyParticipantAssignment {

	Long id;
	String  studyPartIdOrig = null;
	String  studytype = null;
	Activity activity;
	Participant participant;
	
	
	/**
	 * @return the studyPartIdOrig
	 */
	public String getStudyPartIdOrig() {
		return studyPartIdOrig;
	}
	/**
	 * @param studyPartIdOrig the studyPartIdOrig to set
	 */
	public void setStudyPartIdOrig(String studyPartIdOrig) {
		this.studyPartIdOrig = studyPartIdOrig;
	}
	/**
	 * @return the studytype
	 */
	public String getStudytype() {
		return studytype;
	}
	/**
	 * @param studytype the studytype to set
	 */
	public void setStudytype(String studytype) {
		this.studytype = studytype;
	}
	/**
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}
	/**
	 * @param activity the activity to set
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
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

}
