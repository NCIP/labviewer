/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Outcome of a laboratory analysis expressed as a number or text value.
 * @author Naveen Amiruddin
 *
 */
@Entity
@Table(name = "Lab_Hub_Lab_Result")
public class LaboratoryResult extends AbstractId {

    private Integer numericPrecision;
    private Float numericResult;
    private String referenceFlag;
    private String referenceRangeComment;
    private Integer referenceRangeHigh;
    private Integer referenceRangeLow;
    private String referenceTextList;
    private String reportedResultStatus;
    private java.util.Date testPerformedDateTime;
    private String textResult;
    private Cd units;
    private LaboratoryTest laboratoryTest;
    private PerformingLaboratory performingLaboratory;
    /**
     * Retreives the value of numericPrecision attribute.
     * @return numericPrecision
     **/
     @Column(name = "NUMERIC_PRECISION")
     public Integer getNumericPrecision() {
         return numericPrecision;
     }

    /**
    * Sets the value of numericPrecision attribue.
    * @param numericPrecision numericPrecision
    **/
    public void setNumericPrecision(Integer numericPrecision) {
       this.numericPrecision = numericPrecision;
    }
    /**
    * Retreives the value of numericResult attribute.
    * @return numericResult
    **/
    @Column(name = "NUMERIC_RESULT")
    public Float getNumericResult() {
        return numericResult;
    }
    /**
    * Sets the value of numericResult attribue.
    * @param numericResult numericResult
    **/
    public void setNumericResult(Float numericResult) {
        this.numericResult = numericResult;
    }
    /**
    * Retreives the value of referenceFlag attribute.
    * @return referenceFlag
    **/
    @Column(name = "REFERENCE_FLAG")
    public String getReferenceFlag() {
        return referenceFlag;
    }
    /**
    * Sets the value of referenceFlag attribue.
    * @param referenceFlag referenceFlag
    **/
    public void setReferenceFlag(String referenceFlag) {
        this.referenceFlag = referenceFlag;
    }

   /**
    * Retreives the value of referenceRangeComment attribute.
    * @return referenceRangeComment
    **/
    @Column(name = "REFERENCE_RANGE_COMMENT")
    public String getReferenceRangeComment() {
        return referenceRangeComment;
    }
    /**
    * Sets the value of referenceRangeComment attribue.
    * @param referenceRangeComment referenceRangeComment
    **/
    public void setReferenceRangeComment(String referenceRangeComment) {
        this.referenceRangeComment = referenceRangeComment;
    }
    /**
    * Retreives the value of referenceRangeHigh attribute.
    * @return referenceRangeHigh
    **/
    @Column(name = "REFERENCE_RANGE_HIGH")
    public Integer getReferenceRangeHigh() {
        return referenceRangeHigh;
    }

    /**
    * Sets the value of referenceRangeHigh attribue.
    * @param referenceRangeHigh referenceRangeHigh
    **/
   public void setReferenceRangeHigh(Integer referenceRangeHigh) {
      this.referenceRangeHigh = referenceRangeHigh;
    }

    /**
    * Retreives the value of referenceRangeLow attribute.
    * @return referenceRangeLow
    **/
    @Column(name = "REFERENCE_RANGE_LOW")
    public Integer getReferenceRangeLow() {
        return referenceRangeLow;
    }

    /**
    * Sets the value of referenceRangeLow attribue.
    * @param referenceRangeLow referenceRangeLow
    **/

    public void setReferenceRangeLow(Integer referenceRangeLow) {
        this.referenceRangeLow = referenceRangeLow;
    }
    /**
     * Retreives the value of referenceTextList attribute.
     * @return referenceTextList
    **/
    @Column(name = "REFERENCE_TEXT_LIST")
    public String getReferenceTextList() {
        return referenceTextList;
    }
    /**
    * Sets the value of referencTextList attribue.
    * @param referenceTextList referenceTextList
    **/
    public void setReferenceTextList(String referenceTextList) {
        this.referenceTextList = referenceTextList;
    }
    /**
    * Retreives the value of reportedResultStatus attribute.
    * @return reportedResultStatus
    **/
    @Column(name = "REPORTED_RESULT_STATUS")
    public String getReportedResultStatus() {
        return reportedResultStatus;
    }
    /**
    * Sets the value of reportedResultStatus attribue.
    * @param reportedResultStatus reportedResultStatus
    **/
    public void setReportedResultStatus(String reportedResultStatus) {
        this.reportedResultStatus = reportedResultStatus;
    }
    /**
    * Retreives the value of testPerformedDateTime attribute.
    * @return testPerformedDateTime
    **/
    @Column(name = "TEST_PERFORMED_DATE_TIME")
    public java.util.Date getTestPerformedDateTime() {
        return testPerformedDateTime;
    }
    /**
    * Sets the value of testPerformedDateTime attribue.
    * @param testPerformedDateTime testPerformedDateTime
    **/
    @Column(name = "TEST_PERFORMED_DATE_TIME")
    public void setTestPerformedDateTime(java.util.Date testPerformedDateTime) {
        this.testPerformedDateTime = testPerformedDateTime;
    }
    /**
    * Retreives the value of textResult attribute.
    * @return textResult
    **/
    @Column(name = "TEXT_RESULT")
    public String getTextResult() {
        return textResult;
    }
    /**
    * Sets the value of textResult attribue.
    * @param textResult textResult
    **/
    public void setTextResult(String textResult) {
        this.textResult = textResult;
    }
    /**
    * Retreives the value of units attribue.
    * @return units
    **/
    @ManyToOne
    @JoinColumn(name = "NUM_UOM_CONCEPT_DESCRIPTOR_ID")
    public Cd getUnits() {
        return units;
    }
    /**
    * Sets the value of units attribue.
    * @param units units
    **/
    public void setUnits(Cd units) {
        this.units = units;
    }
    /**
     * Retreives the value of laboratoryTest attribue.
     * @return laboratoryTest
     **/

    @ManyToOne
    @JoinColumn(name = "LAB_TEST_ID")
    public LaboratoryTest getLaboratoryTest() {
         return laboratoryTest;
     }
     /**
     * Sets the value of laboratoryTest attribue.
     * @param laboratoryTest laboratoryTest
     **/

     public void setLaboratoryTest(LaboratoryTest laboratoryTest) {
         this.laboratoryTest = laboratoryTest;
     }

     /**
      * Retreives the value of performingLaboratory attribue.
      * @return performingLaboratory
      **/
     @ManyToOne
     @JoinColumn(name = "PERFORMING_LABORATORY_ID")
     public PerformingLaboratory getPerformingLaboratory() {
          return performingLaboratory;
      }
     /**
      * Sets the value of performingLaboratory attribue.
      * @param performingLaboratory performingLaboratory
      **/
      public void setPerformingLaboratory(PerformingLaboratory performingLaboratory) {
          this.performingLaboratory = performingLaboratory;
      }


}
