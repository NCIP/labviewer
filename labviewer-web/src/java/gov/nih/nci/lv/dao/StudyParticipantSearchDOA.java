/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.dao;

import gov.nih.nci.lv.auth.LabViewerAuthorizationHelper;
import gov.nih.nci.lv.domain.HealthcareSite;
import gov.nih.nci.lv.domain.Identifier;
import gov.nih.nci.lv.domain.Participant;
import gov.nih.nci.lv.domain.Protocol;
import gov.nih.nci.lv.domain.StudyParticipantAssignment;
import gov.nih.nci.lv.dto.StudyParticipantSearchDto;
import gov.nih.nci.lv.util.LVUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

/**
 * 
 * @author Naveen Amiruddin
 *
 */
public class StudyParticipantSearchDOA extends AbstractDAO {

    private static Logger logger = Logger.getLogger(StudyParticipantSearchDOA.class);
    

    /**
     * SearchObjects retrieves the user entered search criteria and returns the study search results.
     * 
     * @param spsDto study participant search dto
     * @return searchResult
     * @throws Exception on error
     */
    
    public List<StudyParticipantSearchDto> search(StudyParticipantSearchDto spsDto) 
    throws Exception {
        List<StudyParticipantSearchDto> spsDtos = new ArrayList<StudyParticipantSearchDto>();
        LabViewerAuthorizationHelper labAuth = new LabViewerAuthorizationHelper();
        boolean allSiteAccess = true; 
        // in 2.4, implement admin and cge. for now comment this functionality due to user info not set, while coming
        // from psc or caAERS
//        if (spsDto.getUserName() != null) {
//            allSiteAccess = labAuth.isAllSites(spsDto.getUserName());
//        }
        String ids = null;
        StringBuffer hql = new StringBuffer(" Select i , part , hc from Identifier as i ");
        hql.append(" left outer join i.studyParticipantAssignment as spa ");
        hql.append(" join spa.studySite as ss ");
        hql.append(" join ss.healthcareSite as hc ");
        hql.append(" join spa.participant as part ");
        hql.append(" join ss.protocol as p where p.id = " + spsDto.getStudyProtocolId());
        hql.append(" and i.studyParticipantAssignment is not null");
        if (StringUtils.isNotEmpty(spsDto.getFirstName())) {
            hql.append(" and upper(part.firstName) like '%" + spsDto.getFirstName().toUpperCase() + "%'");
        }
        if (StringUtils.isNotEmpty(spsDto.getLastName())) {
            hql.append(" and upper(part.lastName) like '%" + spsDto.getLastName().toUpperCase() + "%'");
        }
        if (StringUtils.isNotEmpty(spsDto.getIdentifier())) {
            hql.append(" and upper(i.extension) like '%" + spsDto.getIdentifier().toUpperCase() + "%'");
        }
        if (spsDto.getId() != null) {
            hql.append(" and part.id = " + spsDto.getId());
        }
        if (!allSiteAccess) {
            ids = LVUtils.convertListToStringConcat(labAuth.getAuthSites(spsDto.getUserName()), ",");
            if (StringUtils.isNotEmpty(ids)) {
                hql.append(" and hc.nciInstituteCode in (" + ids + ")");
            }
        }

        List<Object> obs = getSession().createQuery(hql.toString()).list();
        Object[] data = null;
        for (Object d : obs) {
            data = (Object[]) d;
            Identifier identifier = (Identifier) data[0];
            Participant participant = (Participant) data[1];
            HealthcareSite healtcareSite = (HealthcareSite) data[2];
            spsDtos.add(new StudyParticipantSearchDto(identifier, participant, healtcareSite));
        }
        
        return spsDtos;
    }
    

    /**
     * retrieves Identifier for a given Study Subject Grid id.
     * @param gridId study subject grid id
     * @return Protocol;
     * @throws Exception on error
     */
    public Identifier getIdentiferByStudySubjectGridId(String gridId) throws Exception {
        Identifier identifier = new Identifier();
        logger.debug("Retrieving Identifier for a given Grid Id = " + gridId);
        if (gridId == null) {
            throw new Exception("Grid Id is null");
        }
        identifier.setRoot(gridId);
        identifier = getIdentifier(identifier);
        Protocol protocol = null;
        StudyParticipantAssignment spa = identifier.getStudyParticipantAssignment();
        protocol = identifier.getStudyParticipantAssignment().getStudySite().getProtocol();
        if (protocol == null) {
            throw new Exception("Protocol cannot be retrieved for Grid id = " + gridId);
        }
        identifier.setStudyParticipantAssignment(spa);
        identifier.setParticipant(spa.getParticipant());
        identifier.setProtocol(protocol);
        return identifier;
    }
    
    private Identifier getIdentifier(Identifier identifier) throws Exception {
        Criteria crit = getSession().createCriteria(Identifier.class);
        crit.add(Example.create(identifier));
        List<Identifier> identifiers = crit.list();
        Identifier identify = null;
        if (!identifiers.isEmpty()) {
            identify = (Identifier) identifiers.get(0);
        }
        if (identify == null) {
            throw new Exception("Identifier cannot be retrived for given parameters ");
        }
        return identify;
    }
    
}
