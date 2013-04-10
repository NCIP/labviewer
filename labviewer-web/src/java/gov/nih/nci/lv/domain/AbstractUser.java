/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;



/**
 * Base class for all domain classes.
 * @author Naveen Amiruddin
 * @since 05/22/2008
 */
@MappedSuperclass
public class AbstractUser  extends AbstractDate  {

    private static final long serialVersionUID = 1234567890L;

    private String ctomInsertUser;
    private String ctomUpdateUser;


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
