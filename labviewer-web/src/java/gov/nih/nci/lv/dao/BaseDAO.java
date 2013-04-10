/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.dao;

import gov.nih.nci.lv.convert.AbstractConverter;
import gov.nih.nci.lv.domain.Protocol;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;

/**
 * A Base DAO class.
 * @author NAmiruddin
 * @param <BO> domain object
 * @param <DTO> dto object
 * @param <CONVERTER> converter object
 */
public abstract class BaseDAO <BO, DTO , CONVERTER extends AbstractConverter<DTO, BO>>  extends AbstractDAO { 
    
    private static final Logger LOG  = Logger.getLogger(AbstractDAO.class);

    
    /**
     * 
     * @param sql sql for execution
     * @return list of domain objs
     */
    List<BO> executeSql(String sql) {
        LOG.info("sql " + sql);
        Query query = getSession().createQuery(sql);
        List<BO> bos = query.list();
        LOG.debug("total retrieved " + bos.size());
        return bos;
    }
    
    /**
     * 
     * @param bos domian
     * @param converter dto converter
     * @return list of dtos
     */
    List<DTO> convertToDto(List<BO> bos , CONVERTER converter) {
        return converter.convertToDTOs(bos);
    }
    
    /**
     * 
     * @param protocol protocol
     * @return list of dtos
     */
    public abstract List<DTO> getByStudyProtocol(Protocol protocol);

    
}


