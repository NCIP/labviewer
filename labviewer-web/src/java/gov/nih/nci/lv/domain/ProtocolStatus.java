/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Protocol Status class.
 * @author NAmiruddin
 *
 */
@Entity
@Table(name = "PROTOCOL_STATUS")
public class ProtocolStatus  extends AbstractDate {

    private String statusCode;
    private Protocol protocol;

    /**
     *
     * @return statusCode
     */
    @Column(name = "STATUS_CODE")
    public String getStatusCode() {
        return statusCode;
    }

    /**
     *
     * @param statusCode statusCode
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     *
     * @return protocol
     */
    @ManyToOne
    @JoinColumn(name = "PROTOCOL_ID", updatable = false)
    public Protocol getProtocol() {
        return protocol;
    }

    /**
     *
     * @param protocol protocol
     */
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }



}
