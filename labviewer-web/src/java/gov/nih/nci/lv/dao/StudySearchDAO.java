/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.dao;

import gov.nih.nci.coppa.services.pa.StudyProtocol;
import gov.nih.nci.lv.auth.LabViewerAuthorizationHelper;
import gov.nih.nci.lv.domain.Identifier;
import gov.nih.nci.lv.domain.Protocol;
import gov.nih.nci.lv.domain.ProtocolStatus;
import gov.nih.nci.lv.dto.StudySearchDto;
import gov.nih.nci.lv.util.LVConstants;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author Naveen Amiruddin
 */
public class StudySearchDAO extends AbstractDAO {
    
	private static Logger logger = Logger.getLogger(StudySearchDAO.class);
	

	/**
	 * SearchObjects retrieves the user entered search criteria and returns the study search results.
	 * 
	 * @param studySearchDto study search dto
	 * @return searchResult
	 * @throws Exception on error
	 */
	public List<StudySearchDto> search(StudySearchDto studySearchDto) 
	throws Exception {
	    String nciIdentifier = studySearchDto.getNciIdentifier();
	    String shortTitle = studySearchDto.getShortTitle();
	    String userName = studySearchDto.getUserName();
	    String ids = null;
	    LabViewerAuthorizationHelper labAuth = new LabViewerAuthorizationHelper();
	    Long id = studySearchDto.getId();
	    List<StudySearchDto> ssDtos = new ArrayList<StudySearchDto>();
	    StringBuffer  hql = new StringBuffer(" Select i , p , ps  from Identifier as i ");
	    hql.append("left outer join i.protocol as p ");
	    hql.append(" join p.protocolStatuses as ps where 1 = 1 ");
        if (nciIdentifier != null) {
            hql.append(" and upper(p.nciIdentifier) like '%" + nciIdentifier.toUpperCase() + "%'");
        }
        if (shortTitle != null) {
            hql.append(" and upper(p.shortTitleText) like '%" + shortTitle.toUpperCase() + "%'");
        }
        if (id != null) {
            hql.append(" and p.id = " + id);
        }
        // add studies id
        // in 2.4, implement admin and cge. for now comment this functionality due to user info not set, while coming
        // from psc or caAERS
//        if (userName != null && !labAuth.isAllStudies(userName)) {
//            ids = LVUtils.convertListToStringConcat(labAuth.getAuthStudies(userName), ",");
//            if (StringUtils.isNotEmpty(ids)) {
//                hql.append(" and i.extension in (" + ids + ")");
//            }
//        }

        hql.append(" and ( ps.id in (select max(id) from ProtocolStatus as ps1 "
                + "                where ps.protocol = ps1.protocol )"
                + " or ps.id is null ) ");
        List<Object> obs = getSession().createQuery(hql.toString()).list();
        Object[] data = null;
        logger.debug(" Study Protocol sql = " + hql.toString());
        logger.info(" records retreived  size= " + obs.size());
        for (Object d : obs) {
            data = (Object[]) d;
            Identifier identifier = (Identifier) data[0];
            Protocol protocol = (Protocol) data[1];
            ProtocolStatus ps = (ProtocolStatus) data[2];
            
            ssDtos.add(new StudySearchDto(protocol, identifier , ps));
        }
	    return ssDtos;
	}
	
	/**
	 * update the ctods study based on coppa study.
	 * @param studyProtocol coppa study protocol
	 * @throws Exception on error
	 */
	public void update(StudyProtocol studyProtocol) throws Exception {
        if (studyProtocol == null) {
            return;
        }
        StudySearchDto ssDto = new StudySearchDto();
        ssDto.setNciIdentifier(studyProtocol.getAssignedIdentifier().getExtension());
        List<StudySearchDto> ssDtos = search(ssDto);
        Session session = getSession();
        Transaction tran = session.beginTransaction();
        for (StudySearchDto searchDto : ssDtos) {
            logger.debug(" Updating Coppa Studies for NCI Id = " + ssDto.getNciIdentifier() 
                    + " ctods id = " + searchDto.getId());
            Protocol protocol = (Protocol) session.load(Protocol.class, searchDto.getId());
            protocol.setLongTitleText(StringUtils.substring(studyProtocol.getOfficialTitle().getValue(), 0 ,
                    LVConstants.NUM_500));
            if (studyProtocol.getPublicTitle() != null) {
                protocol.setShortTitleText(StringUtils.substring(studyProtocol.getPublicTitle().getValue() , 0 , 
                        LVConstants.NUM_200));
            }
            session.update(protocol);
        }
        tran.commit();
    }
	
	
}
