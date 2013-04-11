/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.lv.web.action;

import gov.nih.nci.lv.util.HibernateUtil;
import gov.nih.nci.lv.util.LVConstants;
import gov.nih.nci.lv.util.LVException;
import gov.nih.nci.lv.util.LVPropertyReader;
import gov.nih.nci.security.util.StringEncrypter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Session;


/**
 * Labviewer login page.
 * @author Naveen Amiruddin
 *
 */
public class LoginAction extends LabViewerAction {
    private static final long serialVersionUID = 1234573645L;
    private static Logger logger = Logger.getLogger(LoginAction.class);

    private String username;
    private String password;
    private String url;


    /**
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * {@inheritDoc}
     */
    public String submit() throws Exception {
        String encryptedPassword;
        StringEncrypter se;
        if (username == null || password == null) {
            setAttribute(LVConstants.FAILURE_MESSAGE, "Either Username or password is null");
            return ERROR;
        }

        try {
             se = new StringEncrypter();
             encryptedPassword = se.encrypt(new String(password));
        } catch (StringEncrypter.EncryptionException ep) {
             logger.error("Error while Encrypting password" , ep);
             setAttribute(LVConstants.FAILURE_MESSAGE, "Error while Encrypting password " + ep.getMessage());
             return ERROR;
        }
        if (executeQuery(username, encryptedPassword)) {
            logger.debug("Login sucessful for " + username);
            setUserInfoInSession(username);
            return "welcome";
        } else {
            logger.error("Could not login for user " + username);
            setAttribute(LVConstants.FAILURE_MESSAGE,
                    "Sorry, your username and password are incorrect - please try again");
            return ERROR;
        }
    }

    /**
     * {@inheritDoc}
     */
    public String logout() throws Exception {
        getSession().invalidate();
        return SUCCESS;
    }
    /**
     * {@inheritDoc}
     */
    public String websso() throws Exception {
        String webssoCasServer = LVPropertyReader.getPropertyValue("websso.cas.server");
        String webssoCasServerPort = LVPropertyReader.getPropertyValue("websso.cas.server.port");
        getSession().invalidate();
        String url1 = "https://" + webssoCasServer + ":" + webssoCasServerPort + "/webssoserver/logout";
        logger.debug("logging out of cas url" + url1);
        setUrl(url1);
        return "redirect";
    }

    /**
     * {@inheritDoc}
     */
    public String execute() throws Exception {
        return SUCCESS;
    }



    /**
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }
    /**
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    private static boolean executeQuery(String username, String password) throws Exception {
        Session se = HibernateUtil.getCurrentSession();
        Connection conn   = se.connection();
        String query = "SELECT * FROM CSM_USER WHERE LOGIN_NAME = ? and PASSWORD = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean validLogin = false;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    validLogin = true;
                    break;
                }
            }
        } catch (SQLException e) {
            throw new LVException("Error while executing the query to validate user credentials", e);
        } finally {
            try {
                if (resultSet != null) { resultSet.close(); }
                if (statement != null) { statement.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException sqe) {
                    //log.error("Closing the connection {} failed", connection, sqe);
            }
        }
        return validLogin;
    }

}
