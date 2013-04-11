/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.lv.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Some of the tables have just ctom insert and upgate date only. So we are keeping these seperate classes.
 * @author NAmiruddin
 *
 */
@MappedSuperclass
public class AbstractDate extends AbstractId  {
    
    private Date ctomInsertDate;
    private Date ctomUpdateDate;
    /**
     * 
     * @return insert date
     */
    @Column(name = "CTOM_INSERT_DATE" , updatable = false)
    @Temporal(TemporalType.TIMESTAMP)    
    public Date getCtomInsertDate() {
        return ctomInsertDate;
    }
    /**
     * 
     * @param ctomInsertDate insert date
     */
    

    public void setCtomInsertDate(Date ctomInsertDate) {
        this.ctomInsertDate = ctomInsertDate;
    }

    /**
     * 
     * @return ctomUpdateDt
     */
    @Column(name = "CTOM_UPDATE_DATE" , updatable = false)
    public Date getCtomUpdateDate() {
        return ctomUpdateDate;
    }
    
    /**
     * 
     * @param ctomUpdateDate ctomUpdateDate
     */
    public void setCtomUpdateDate(Date ctomUpdateDate) {
        this.ctomUpdateDate = ctomUpdateDate;
    }
    
    


}
