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
 * Base class for all domain classes.
 * @author Naveen Amiruddin
 * @since 05/22/2008
 */
@MappedSuperclass
public class AbstractEntity  extends AbstractId  {

    private static final long serialVersionUID = 1234567890L;

    private Date ctomInsertDt;
    private String ctomInsertUser;
    private Date ctomUpdateDt;
    private String ctomUpdateUser;

    /**
     * 
     * @return insert date
     */
    @Column(name = "CTOM_INSERT_DATE" , updatable = false)
    @Temporal(TemporalType.TIMESTAMP)    
    public Date getCtomInsertDt() {
        return ctomInsertDt;
    }
    /**
     * 
     * @param ctomInsertDt insert date
     */
    

    public void setCtomInsertDt(Date ctomInsertDt) {
        this.ctomInsertDt = ctomInsertDt;
    }

    /**
     * 
     * @return ctomInsertUser
     */
    @Column(name = "CTOM_INSERT_USER")
    public String getCtomInsertUser() {
        return ctomInsertUser;
    }

    /**
     * 
     * @param ctomInsertUser ctomInsertUser
     */
    public void setCtomInsertUser(String ctomInsertUser) {
        this.ctomInsertUser = ctomInsertUser;
    }

    /**
     * 
     * @return ctomUpdateDt
     */
    @Column(name = "CTOM_UPDATE_DATE" , updatable = false)
    public Date getCtomUpdateDt() {
        return ctomUpdateDt;
    }
    
    /**
     * 
     * @param ctomUpdateDt ctomUpdateDt
     */
    public void setCtomUpdateDt(Date ctomUpdateDt) {
        this.ctomUpdateDt = ctomUpdateDt;
    }
    /**
     * 
     * @return ctomUpdateUser
     */
    @Column(name = "CTOM_UPDATE_USER" , updatable = false)
    public String getCtomUpdateUser() {
        return ctomUpdateUser;
    }
    /**
     * 
     * @param ctomUpdateUser ctomUpdateUser
     */
    public void setCtomUpdateUser(String ctomUpdateUser) {
        this.ctomUpdateUser = ctomUpdateUser;
    }

    
}
