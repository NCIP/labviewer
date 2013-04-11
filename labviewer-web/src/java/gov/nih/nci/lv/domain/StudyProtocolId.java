/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * abstract class for mainting study protocol link.
 * @author NAmiruddin
 *
 */
@MappedSuperclass
public class StudyProtocolId extends AbstractId {

    /** The study protocol. */
    private Protocol protocol;
    /**
     *
     * @return protocol
     */
    @ManyToOne
    @JoinColumn(name = "PROTOCOL_ID")
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
