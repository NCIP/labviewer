/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.dao;

import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.lv.convert.HealthcareSiteConverter;
import gov.nih.nci.lv.domain.HealthcareSite;
import gov.nih.nci.lv.domain.Protocol;
import gov.nih.nci.lv.dto.HealthcareSiteDto;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.iso._21090.ADXP;
import org.iso._21090.AddressPartType;
import org.iso._21090.DSETTEL;
import org.iso._21090.ENXP;
import org.iso._21090.TEL;

/**
 * DAO for healtcare site.
 * @author Naveen Amiruddin
 *
 */
public class HealthcareSiteDAO  extends BaseDAO<HealthcareSite, HealthcareSiteDto , HealthcareSiteConverter> {
    

    /**
     * returns a list of healtcareSites for a given study.
     * @param protocol protocol
     * @return list of healtcareSites
     */
    public List<HealthcareSiteDto> getByStudyProtocol(Protocol protocol) {
        StringBuffer hql = new StringBuffer(" select distinct h from HealthcareSite as h ");
        hql.append("join h.studySites as ss join ss.protocol as p where p.id = " + protocol.getId());
        return convertToDto(executeSql(hql.toString()) , new HealthcareSiteConverter());
    }
    
    /**
     * 
     * @param dto dto
     * @param organization coppa person
     */
    public void  update(HealthcareSiteDto dto , Organization organization) {
        Session session = getSession();
        Transaction tran = session.beginTransaction();
        HealthcareSite healthcareSite = (HealthcareSite) session.load(HealthcareSite.class, dto.getId());
        healthcareSite = convertToHealthcareSite(healthcareSite, organization);
        session.save(healthcareSite);
        tran.commit();
    }
    
    private HealthcareSite convertToHealthcareSite(HealthcareSite healthcareSite , Organization organization) {
        List<ENXP> list = organization.getName().getPart();
        Iterator iteName = list.iterator();
        while (iteName.hasNext()) {
            ENXP part = (ENXP) iteName.next();
            healthcareSite.setName(part.getValue());
        }
        // get the address
        List<ADXP> address = organization.getPostalAddress().getPart();
        String addressStr = "";
        Iterator iteAddr = address.iterator();
        while (iteAddr.hasNext()) {
            ADXP part = (ADXP) iteAddr.next();
            if (AddressPartType.AL == part.getType()) {
                healthcareSite.setStreetAddress(part.getValue() != null ? part.getValue() : "");
            } else if (AddressPartType.ADL == part.getType()) {
                healthcareSite.setStreetAddress(healthcareSite.getStreetAddress() + part.getValue() != null  
                        ? part.getValue() : "");
            } else if (AddressPartType.CTY == part.getType()) {
                healthcareSite.setCity(part.getValue() != null ? part.getValue() : "");
            } else if (AddressPartType.STA == part.getType()) {
                healthcareSite.setStateCode(part.getValue() != null ? part.getValue() : "");
            } else if (AddressPartType.ZIP == part.getType()) {
                healthcareSite.setPostalCode(part.getValue() != null ? part.getValue() : "");
            } else if (AddressPartType.CNT == part.getType()) {
                healthcareSite.setCountryCode(part.getCode() != null ? part.getCode() : "");
            }
        }
        // get the Telecom Address
        DSETTEL telList = organization.getTelecomAddress();
        List<TEL> set = telList.getItem();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            TEL obj = (TEL) iter.next();
            healthcareSite.setTelecomAddress(obj.getValue().toString());
        }
        healthcareSite.setCtomUpdateDate(new Date());
        return healthcareSite;
    }
    
    

}
