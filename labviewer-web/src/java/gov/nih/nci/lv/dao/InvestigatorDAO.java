/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.dao;

import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.lv.convert.AbstractConverter;
import gov.nih.nci.lv.convert.InvestigatorConverter;
import gov.nih.nci.lv.domain.Investigator;
import gov.nih.nci.lv.domain.Protocol;
import gov.nih.nci.lv.dto.InvestigatorDto;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.iso._21090.ADXP;
import org.iso._21090.AddressPartType;
import org.iso._21090.ENXP;
import org.iso._21090.EntityNamePartType;
import org.iso._21090.TEL;

/**
 * DAO for Inv site.
 * @author Naveen Amiruddin
 *
 */
public class InvestigatorDAO extends 
    BaseDAO<Investigator, InvestigatorDto, AbstractConverter<InvestigatorDto, Investigator> > {

    @Override
    public List<InvestigatorDto> getByStudyProtocol(Protocol protocol) {
        StringBuffer hql = new StringBuffer(" select distinct i from Investigator i ");
        hql.append("join i.studyInvestigators as p ");
        hql.append("join p.protocol as p ");
        hql.append("where p.id =  " + protocol.getId());
        return convertToDto(executeSql(hql.toString()) , new InvestigatorConverter());

    }

    /**
     * 
     * @param dto dto
     * @param poPerson coppa person
     */
    public void  update(InvestigatorDto dto , Person poPerson) {
        Session session = getSession();
        Transaction tran = session.beginTransaction();
        Investigator investigator = (Investigator) session.load(Investigator.class, dto.getId());
        investigator = convertToInvestigator(investigator, poPerson);
        session.save(investigator);
        tran.commit();
    }
    /**
     * @param poPerson
     * @return
     */
    private Investigator convertToInvestigator(Investigator investigator , Person poPerson) {
        // get the name
        List<ENXP> list = poPerson.getName().getPart();
        Iterator iteName = list.iterator();
        investigator.setFirstName(null);
        while (iteName.hasNext()) {
            ENXP part = (ENXP) iteName.next();
            if (EntityNamePartType.FAM == part.getType()) {
                investigator.setLastName(part.getValue());
            } else if (EntityNamePartType.GIV == part.getType()) {
                if (investigator.getFirstName() == null) {
                    investigator.setFirstName(part.getValue());
                } else {
                    // do nothing
                }
            }
        }
        // get the address
        List<ADXP> address = poPerson.getPostalAddress().getPart();
        Iterator iteAddr = address.iterator();
        while (iteAddr.hasNext()) {
            ADXP part = (ADXP) iteAddr.next();
            if (AddressPartType.AL == part.getType()) {
                investigator.setStreetAddress(part.getValue() != null ? part.getValue() : "");
            } else if (AddressPartType.ADL == part.getType()) {
                investigator.setStreetAddress(
                        investigator.getStreetAddress() + " " + part.getValue() != null ? part.getValue() : "");
            } else if (AddressPartType.CTY == part.getType()) {
                investigator.setCity(part.getValue() != null ? part.getValue() : "");
            } else if (AddressPartType.STA == part.getType()) {
                investigator.setStateCode(part.getValue() != null ? part.getValue() : "");
            } else if (AddressPartType.ZIP == part.getType()) {
                investigator.setPostalCode(part.getValue() != null ? part.getValue() : "");
            } else if (AddressPartType.CNT == part.getType()) {
                investigator.setCountryCode(part.getCode() != null ? part.getCode() : "");
            }
        }
        List<TEL> telephone = poPerson.getTelecomAddress().getItem();
        investigator.setTelecomAddress(telephone.get(0).getValue());
        investigator.setCtomUpdateDate(new Date());
        return investigator;
    }
    


}
