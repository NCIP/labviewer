/***
* caBIG Open Source Software License
* 
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
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

package gov.nih.nci.lv.util;

/**
 * An utitly class for strong all the constant values.
 * @author NAmiruddin
 *
 */
public class LVConstants {
    
    /** Study Search Dto . */
    public static final String STUDY_SEARCH_DTO = "studySearchDto";
    /** Study Participant Search Dto . */
    public static final String STUDY_PART_SEARCH_DTO = "studyPartSearchDto";    
    /** logged User. . */
    public static final String USER_NAME = "userName";
    /** logged User password. . */
    public static final String PASSWORD = "password";
    /** Results. . */
    public static final String RESULTS = "results";
    
    /** Failure Message. . */
    public static final String FAILURE_MESSAGE = "failureMessage";
    /** Success Message. . */
    public static final String SUCCESS_MESSAGE = "successMessage";
    /** topic. . */
    public static final String TOPIC = "topic";
    /** hub wait count. . */
    public static final int HUB_TOT_CNT = 25;
    /** hub sleep time. . */
    public static final int HUB_SLEEP_TIME = 1000;
    /** CAGRID_SSO_DELEGATION_SERVICE_EPR; . */
    public static final String CAGRID_SSO_DELEGATION_SERVICE_EPR = "CAGRID_SSO_DELEGATION_SERVICE_EPR";
    /** CAGRID_SSO_GRID_CREDENTIAL . */
    public static final String CAGRID_SSO_GRID_CREDENTIAL = "CAGRID_SSO_GRID_CREDENTIAL";
    /** CAGRID_SSO_GRID_IDENTITY . */
    public static final String CAGRID_SSO_GRID_IDENTITY = "CAGRID_SSO_GRID_IDENTITY";
    /** USER ROLES . */
    public static final String USER_ROLES = "USER_ROLES";    
    /** ADMIN ACCESS . */
    public static final String ADMIN_ACCESS = "adminAccess";    
    /** STUDY ACCESS . */
    public static final String STUDY_ACCESS = "studyAccess";    
    /** NO ACCESS . */
    public static final String ALLOW_ACCESS = "allowAccess";    
    /** LAB RESULTS . */
    public static final String LAB_RESULTS = "labResults";    
    /** caAERS . */
    public static final String CAAERS = "caAERS";    
    /** C3D . */
    public static final String C3D = "C3D";    
    /** version.   */
    public static final String VERSION = "version";
    /** caers.url. */
    public static final String CAERS_URL = "caers.url";
    /** c3d.url.*/
    public static final String C3D_URL = "c3d.url";
    /** hub url.*/
    public static final String HUB_URL = "hub.url";
    /** help.link.*/
    public static final String HELP_LINK = "help.link";
    /** The maxmium number of search results to be returned for a remote service method.*/
    public static final int MAX_SEARCH_RESULTS = 100;
    /** Study Protocol root.*/
    public static final String STUDY_PROTOCOL_ROOT = "2.16.840.1.113883.3.26.4.3";
    /** Study Protocol.*/
    public static final String STUDY_PROTOCOL = "STUDY_PROTOCOL";
    /** PERSON.*/
    public static final String PERSON = "PERSON";
    /** ORGANIZATION.*/
    public static final String ORGANIZATION = "ORGANIZATION";
    /** STUDY_ID_PREFIX.*/
    public static final String STUDY_ID_PREFIX = "NCI";
    /** 500.*/
    public static final int NUM_500 = 500;
    /** 200.*/
    public static final int NUM_200 = 200;
    /** ctep person root. **/
    public static final String CTEP_PERSON_IDENTIFIER_ROOT = 
        "Cancer Therapy Evaluation Program Person Other Identifier";
    /** ctep org root. **/
    public static final String CTEP_ORG_IDENTIFIER_ROOT =   "2.16.840.1.113883.3.26.6.2";
    
    
    
    
}
