/**
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce, copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download, copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
 * This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
 * shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
 * appear.
 *
 * 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
 * caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
 * as required to comply with the terms of this License.
 *
 * 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
 * programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
 * party proprietary programs,  You agree  that You are solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro,Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
