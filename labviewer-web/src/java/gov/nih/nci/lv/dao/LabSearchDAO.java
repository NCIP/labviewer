/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
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
