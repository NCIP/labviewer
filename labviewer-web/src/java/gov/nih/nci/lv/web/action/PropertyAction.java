/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The LabViewer (LV) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
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
