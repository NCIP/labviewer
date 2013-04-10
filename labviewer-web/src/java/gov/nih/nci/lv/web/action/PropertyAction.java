/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.web.action;

import gov.nih.nci.lv.util.LVConstants;
import gov.nih.nci.lv.util.LVPropertyReader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

/**
 *
 * @author Naveen Amiruddin
 *
 */
public class PropertyAction extends LabViewerAction {
    private String version;
    private String caaersUrl;
    private String c3dUrl;
    private String hubUrl;
    private String helpLink;

    /**
     *
     * @return Success
     * @throws Exception on error
     */
    @SkipValidation
    public String list() throws Exception {
        setVersion(LVPropertyReader.getPropertyValue(LVConstants.VERSION));
        setCaaersUrl(LVPropertyReader.getPropertyValue(LVConstants.CAAERS_URL));
        setC3dUrl(LVPropertyReader.getPropertyValue(LVConstants.C3D_URL));
        setHubUrl(LVPropertyReader.getPropertyValue(LVConstants.HUB_URL));
        setHelpLink(LVPropertyReader.getPropertyValue(LVConstants.HELP_LINK));
        return SUCCESS;
    }

    /**
     *
     * @return String
     * @throws Exception on error
     */
    public String save() throws Exception {
        Properties prop = LVPropertyReader.getProperty();
        prop.setProperty(LVConstants.VERSION, version);
        prop.setProperty(LVConstants.HELP_LINK, helpLink);
        prop.setProperty(LVConstants.CAAERS_URL, caaersUrl);
        prop.setProperty(LVConstants.C3D, c3dUrl);
        prop.setProperty(LVConstants.HUB_URL, hubUrl);
        File file = new File(getSession().getServletContext().getRealPath("/WEB-INF/classes/lv.properties"));
        OutputStream out =  new BufferedOutputStream(new FileOutputStream(file));
        prop.store(out, "");
        out.close();
        setProperty(prop);
        setAttribute(LVConstants.SUCCESS_MESSAGE, "Property file updated");
        return SUCCESS;
    }
    /**
     *
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     *
     * @param version version
     */
    @RequiredStringValidator(type = ValidatorType.SIMPLE, fieldName = "version", message = "Version must be entered")
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     *
     * @return caaersUrl
     */
    public String getCaaersUrl() {
        return caaersUrl;
    }

    /**
     *
     * @param caaersUrl caaersUrl
     */
   @RequiredStringValidator(type = ValidatorType.SIMPLE, fieldName = "caaersUrl",
           message = "caAERS Url must be entered")
    public void setCaaersUrl(String caaersUrl) {
        this.caaersUrl = caaersUrl;
    }

    /**
     *
     * @return c3dUrl
     */
    public String getC3dUrl() {
        return c3dUrl;
    }

    /**
     *
     * @param c3dUrl c3dUrl
     */
    @RequiredStringValidator(type = ValidatorType.SIMPLE, fieldName = "c3dUrl", message = "C3D Url must be entered")
    public void setC3dUrl(String c3dUrl) {
        this.c3dUrl = c3dUrl;
    }

    /**
     *
     * @return hubUrl
     */
    @RequiredStringValidator(type = ValidatorType.SIMPLE, fieldName = "hubUrl", message = "Hub Url must be entered")
    public String getHubUrl() {
        return hubUrl;
    }

    /**
     *
     * @param hubUrl hubUrl
     */
    public void setHubUrl(String hubUrl) {
        this.hubUrl = hubUrl;
    }

    /**
     *
     * @return helpLink
     */
    public String getHelpLink() {
        return helpLink;
    }

    /**
     *
     * @param helpLink helpLink
     */
    @RequiredStringValidator(type = ValidatorType.SIMPLE, fieldName = "helpLink", message = "Help link must be entered")
    public void setHelpLink(String helpLink) {
        this.helpLink = helpLink;
    }

}
