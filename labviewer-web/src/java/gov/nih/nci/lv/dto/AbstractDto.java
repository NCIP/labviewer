

package gov.nih.nci.lv.dto;

import java.util.Date;

/** 
 * Abstract dto.
 * @author NAmiruddin
 * @param <DTO> dto object
 */
public class AbstractDto<DTO> { 
    
    private Long id;
    private Long protocolIdentifier; // this will be removed
    private Long studyProtocolId;
    
    private String loggedUser;
    private Date ctomInsertDate;
    private Date ctomUpdateDate;
    private String ctomInsertUser;
    private String ctomUpdateUser;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id identifier
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * 
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * 
     * @return protocolIdentifier
     */
    public Long getProtocolIdentifier() {
        return protocolIdentifier;
    }
    
    /**
     * 
     * @param protocolIdentifier protocolIdentifier
     */
    public void setProtocolIdentifier(Long protocolIdentifier) {
        this.protocolIdentifier = protocolIdentifier;
    }
    
    /**
     * 
     * @return loggedUser
     */
    public String getLoggedUser() {
        return loggedUser;
    }

     /**
     * 
     * @param loggedUser loggedUser
     */
    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }

    /**
     * 
     * @return ctomInsertUser
     */
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

    
    
    /**
     * 
     * @return insert date
     */
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
