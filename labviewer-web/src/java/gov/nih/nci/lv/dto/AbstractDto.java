/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.lv.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Abstract dto.
 * @author NAmiruddin
 * @param <DTO> dto object
 */
public class AbstractDto<DTO>  implements Serializable {

    private Long id;
    private Long studyProtocolId;

    private String userName;
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
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
