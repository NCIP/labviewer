/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Has only the id ( as some of the tables don't have all the 5 columns, id, ctominsert & update.
 * @author NAmiruddin
 *
 */
@MappedSuperclass
public class AbstractId implements Serializable {
    
    private static final long serialVersionUID = 1234567890L;
    
    private Long id;
    /**
     * set id.
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the id of the object.
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }
    

}
