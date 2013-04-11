/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.lv.util;

import org.apache.log4j.Logger;

/**
 * LabViewer Exception Class.
 * @author Naveen Amiruddin
 *
 */
public class LVException extends Exception {

    private static final long serialVersionUID = -412014421822871391L;
    private static final Logger LOG = Logger.getLogger(LVException.class);

    /**
     * no argument constructor.
     */
    public LVException() {
        super();
    }

    /**
     * String constructor.
     * @param message message
     */
    public LVException(String message) {
        super(message);
        LOG.error(message);
    }

    /**
     * String and Throwable constructor.
     * @param message message
     * @param t t
     */
    public LVException(String message, Throwable t) {
        super(message, t);
        LOG.error(message, t);
    }

    /**
     *
     * @param t t
     */
    public LVException(Throwable t) {
        super(t);
        LOG.error(t);
    }


}
