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

package gov.nih.nci.lv.dao;

import gov.nih.nci.lv.domain.Activity;
import gov.nih.nci.lv.domain.Identifier;
import gov.nih.nci.lv.domain.LaboratoryResult;
import gov.nih.nci.lv.domain.LaboratoryTest;
import gov.nih.nci.lv.domain.LoadLabStatus;
import gov.nih.nci.lv.domain.Specimen;
import gov.nih.nci.lv.domain.SpecimenCollection;
import gov.nih.nci.lv.domain.StudyParticipantAssignment;
import gov.nih.nci.lv.dto.LabSearchDto;
import gov.nih.nci.lv.util.LVConstants;
import gov.nih.nci.lv.util.LVUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

/**
 * 
 * @author Naveen Amiruddin
 *
 */
public class LabSearchDAO  extends AbstractDAO {

    private static Logger logger = Logger.getLogger(LabSearchDAO.class);

    /**
     * SearchObjects retrieves the user entered search criteria and returns the study search results.
     * 
     * @param labDto study participant search dto
     * @return searchResult
     * @throws Exception on error
     */
    
    public List<LabSearchDto> search(LabSearchDto labDto) 
    throws Exception {
        List<LabSearchDto> labs = new ArrayList<LabSearchDto>();
        StringBuffer hql = new StringBuffer(" select spa from StudyParticipantAssignment as spa ");
        hql.append("join spa.studySite as ss ");
        hql.append("join ss.protocol as p ");
        hql.append("join spa.participant as part where 1= 1 ");
        hql.append(" and p.id = " + labDto.getStudyProtocolId());
        hql.append(" and part.id = " + labDto.getStudyParticipantId());
        List<StudyParticipantAssignment> spas = getSession().createQuery(hql.toString()).list();
        List<Long>  labIds = new ArrayList<Long>();
        for (StudyParticipantAssignment spa : spas) {
            List<Activity> acts = spa.getActivities();
            List<Identifier> ids = spa.getStudySubjectIdentifiers();
            for (Activity act : acts) {
                SpecimenCollection spc = (SpecimenCollection) act;
                List<Specimen> sps = spc.getSpeciments();
                for (Specimen sp : sps) {
                    List<LaboratoryTest>  laboratoryTests = sp.getLaboratoryTests();
                    for (LaboratoryTest laboratoryTest : laboratoryTests) {
                        LabSearchDto lab = new LabSearchDto();
                        lab.setActualStartDate(spc.getActualEndDateTime());
                        lab.setStudySubjectId(ids);
                        LaboratoryResult labResult =  laboratoryTest.getLaboratoryResult();
                        LaboratoryTest labTest = labResult.getLaboratoryTest();
                        if (labDto.getLabTestCode() != null  
                                && !labDto.getLabTestCode().equals("ALL") 
                                && !labDto.getLabTestCode().equals(labTest.getLaboratoryTestCode().getCode())) {
                            continue;
                        }
                        float range = 0;
                        if (labResult != null && labResult.getNumericResult() != null) {
                            range = labResult.getNumericResult().floatValue();
                        }
                        if (labDto.getRange() != null  && !labDto.getRange().equals("ALL")) {
                            // check for in range
                            if (labDto.getRange().equals("INRANGE")) {
                                if (!(labResult.getReferenceRangeLow().floatValue() <= range
                                         &&  range <= labResult.getReferenceRangeHigh().floatValue())) {
                                    continue;
                                }
                            }
                            if (labDto.getRange().equals("OUTOFRANGE")) {
                                if ((labResult.getReferenceRangeLow().floatValue() <= range
                                         &&  range <= labResult.getReferenceRangeHigh().floatValue())) {
                                    continue;
                                }
                            }
                        }
                        if (labResult.getReferenceRangeLow().floatValue() <= range
                                &&  range <= labResult.getReferenceRangeHigh().floatValue()) {
                            lab.setIsWithinRange(true); 
                        }
                        lab.setLabTestCode(labTest.getLaboratoryTestCode().getCode());
                        lab.setNumericResult(labResult.getNumericResult());
                        lab.setId(labResult.getId());
                        lab.setTextResult(labResult.getTextResult());
                        lab.setUom(labResult.getUnits().getCode());
                        lab.setReferenceLowRange(labResult.getReferenceRangeLow());
                        lab.setReferenceHighRange(labResult.getReferenceRangeHigh());
                        labs.add(lab);
                        labIds.add(labResult.getId());
                    }
                }
            }
            // update the dates
            Map<Long, LoadLabStatus> labStatuses = getLoadLabStatus(labIds);
            LoadLabStatus labStatus = null;
            for (LabSearchDto lab : labs) {
                if (labStatuses.containsKey(lab.getId())) {
                    labStatus = labStatuses.get(lab.getId());
                    lab.setAeSentDate(labStatus.getAeSentDate());
                    lab.setCdmsSentDate(labStatus.getCdmsDate());
                }
            }
        }
        return labs;
    }
    
    /**
     * 
     * @param clinicalIds ids
     * @param target target
     */
    public void saveLoadLabStatus(List<Long> clinicalIds, String target) {
        Session session = getSession();
        Transaction tran = session.beginTransaction();
        for (Long id : clinicalIds) {
            Criteria labCrit = session.createCriteria(LoadLabStatus.class);
            LoadLabStatus llstatus = new LoadLabStatus();
            llstatus.setClinicalResultId(id);
            labCrit.add(Example.create(llstatus));
            if (labCrit.list().isEmpty()) {
                if (LVConstants.CAAERS.equals(target)) {
                    llstatus.setAeIndicator("true");
                    llstatus.setAeSentDate(new Date());
                } else {
                    llstatus.setCdmsIndicator("true");
                    llstatus.setCdmsDate(new Date());
                }
                session.save(llstatus);
            }
        }
        updateLabStatus(clinicalIds, target, session);
        tran.commit();
    }
    
    /**
     * update the records for the selected labs.
     * @param clinicalIds clinical ids
     * @param target caAERS or C3D
     */
    private void updateLabStatus(List<Long> clinicalIds , String target , Session session) {
        String sql = "";
        String ids = LVUtils.convertListToNumberConcat(clinicalIds, ",");
        if (LVConstants.CAAERS.equals(target)) {
            sql = "update lab_viewer_status set adverse_event_sent_date = current_timestamp , " 
            		+ " adverse_event_indicator = 'true'  where clinical_result_id  in ( "  + ids + " )";
        } else if (LVConstants.C3D.equals(target)) {
            sql = "update lab_viewer_status set cdms_sent_date = current_timestamp , " 
                + " cdms_indicator = 'true'  where clinical_result_id  in ( "  + ids + " )";
        }
        session.createSQLQuery(sql).executeUpdate();
    }
    
    private Map<Long, LoadLabStatus> getLoadLabStatus(List<Long> ids) {
        Map<Long, LoadLabStatus> map = new HashMap<Long, LoadLabStatus>();
        String data = LVUtils.convertListToNumberConcat(ids, ",");
        System.out.println(" data ..." + data);
        if (StringUtils.isEmpty(data)) {
            return map;
        }
        StringBuffer hql = new StringBuffer(
                " Select lls from LoadLabStatus lls where clinicalResultId in ( " + data + " )");
        List<LoadLabStatus> loadLabs = (List<LoadLabStatus>) getSession().createQuery(hql.toString()).list();        
        
        for (LoadLabStatus loadLab : loadLabs) {
            map.put(loadLab.getClinicalResultId(), loadLab);
        }
        return map;
    }
}
