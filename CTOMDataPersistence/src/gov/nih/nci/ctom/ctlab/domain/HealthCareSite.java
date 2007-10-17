package gov.nih.nci.ctom.ctlab.domain;

public class HealthCareSite {
	private String nciInstituteCd;
	private Long id = null;
	private StudyParticipantAssignment studyParticipantAssignment = new StudyParticipantAssignment(); 

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long hId) {
		this.id = hId;
	}

	/**
	 * @return the studyParticipantAssignment
	 */
	public StudyParticipantAssignment getStudyParticipantAssignment() {
		return studyParticipantAssignment;
	}

	/**
	 * @param studyParticipantAssignment the studyParticipantAssignment to set
	 */
	public void setStudyParticipantAssignment(
			StudyParticipantAssignment studyParticipantAssignment) {
		this.studyParticipantAssignment = studyParticipantAssignment;
	}

	/**
	 * @return the nciInstituteCd
	 */
	public String getNciInstituteCd() {
		return nciInstituteCd;
	}

	/**
	 * @param nciInstituteCd the nciInstituteCd to set
	 */
	public void setNciInstituteCd(String nciInstituteCd) {
		this.nciInstituteCd = nciInstituteCd;
	}
}
