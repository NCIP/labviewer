/**
 * Copyright Notice. Copyright 2008 ScenPro, Inc (“caBIG™
 * Participant”).caXchange was created with NCI funding and is part of the
 * caBIG™ initiative. The software subject to this notice and license includes
 * both human readable source code form and machine readable, binary, object
 * code form (the “caBIG™ Software”). This caBIG™ Software License (the
 * “License”) is between caBIG™ Participant and You. “You (or “Your”) shall mean
 * a person or an entity, and all other entities that control, are controlled
 * by, or are under common control with the entity. “Control” for purposes of
 * this definition means (i) the direct or indirect power to cause the direction
 * or management of such entity, whether by contract or otherwise, or (ii)
 * ownership of fifty percent (50%) or more of the outstanding shares, or (iii)
 * beneficial ownership of such entity. License. Provided that You agree to the
 * conditions described below, caBIG™ Participant grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG™ Software,
 * including any copyright or patent rights therein, to (i) use, install,
 * disclose, access, operate, execute, reproduce, copy, modify, translate,
 * market, publicly display, publicly perform, and prepare derivative works of
 * the caBIG™ Software in any manner and for any purpose, and to have or permit
 * others to do so; (ii) make, have made, use, practice, sell, and offer for
 * sale, import, and/or otherwise dispose of caBIG™ Software (or portions
 * thereof); (iii) distribute and have distributed to and by third parties the
 * caBIG™ Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties.
 * For sake of clarity, and not by way of limitation, caBIG™ Participant shall
 * have no right of accounting or right of payment from You or Your sublicensees
 * for the rights granted under this License. This License is granted at no
 * charge to You. Your downloading, copying, modifying, displaying, distributing
 * or use of caBIG™ Software constitutes acceptance of all of the terms and
 * conditions of this Agreement. If you do not agree to such terms and
 * conditions, you have no right to download, copy, modify, display, distribute
 * or use the caBIG™ Software. 1. Your redistributions of the source code for
 * the caBIG™ Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6 below.
 * Your redistributions in object code form must reproduce the above copyright
 * notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 2. Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: “This product includes software
 * developed by ScenPro, Inc.” If You do not include such end-user
 * documentation, You shall include this acknowledgment in the caBIG™ Software
 * itself, wherever such third-party acknowledgments normally appear. 3. You may
 * not use the names “ScenPro, Inc”, “The National Cancer Institute”, “NCI”,
 * “Cancer Bioinformatics Grid” or “caBIG™” to endorse or promote products
 * derived from this caBIG™ Software. This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either
 * caBIG™ Participant, NCI or caBIG™, except as required to comply with the
 * terms of this License. 4. For sake of clarity, and not by way of limitation,
 * You may incorporate this caBIG™ Software into Your proprietary programs and
 * into any third party proprietary programs. However, if You incorporate the
 * caBIG™ Software into third party proprietary programs, You agree that You are
 * solely responsible for obtaining any permission from such third parties
 * required to incorporate the caBIG™ Software into such third party proprietary
 * programs and for informing Your sublicensees, including without limitation
 * Your end-users, of their obligation to secure any required permissions from
 * such third parties before incorporating the caBIG™ Software into such third
 * party proprietary software programs. In the event that You fail to obtain
 * such permissions, You agree to indemnify caBIG™ Participant for any claims
 * against caBIG™ Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5.
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG™ Software, or any derivative works
 * of the caBIG™ Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in
 * this License. 6. THIS caBIG™ SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED
 * OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE)
 * ARE DISCLAIMED. IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.caxchange.ctom.viewer.constants;

import gov.nih.nci.caxchange.ctom.viewer.util.CommonUtil;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 */
public class DisplayConstants
{
	// QA
	public static String CAAERS_URL =
			//"http://cbiovqa5010.nci.nih.gov:38080/caaers/pages/ae/list";
            (String) CommonUtil.props1.getProperty("BaseURLcaAERS");
    // Dev
	// public static String CAAERS_URL =
	// "http://cbiovdev5041.nci.nih.gov:38080/caaers/pages/ae/list";
	public static String APPLICATION_CONTEXT_NAME = "labviewer";

	public static String CONFIG_FILE_PATH_PROPERTY_NAME =
			"gov.nih.nci.security.configFile";
	public static String USER_PROVISIONING_MANAGER =
			"USER_PROVISIONING_MANAGER";

	public static final String INPUT_TEXTAREA = "INPUT_TEXTAREA";
	public static final String INPUT_CHECKBOX = "INPUT_CHECKBOX";
	public static final String INPUT_BOX = "INPUT_BOX";
	public static final String INPUT_DATE = "INPUT_DATE";
	public static final String INPUT_RADIO = "INPUT_RADIO";
	public static final String PASSWORD = "PASSWORD";

	public static final String SEARCH_RESULT = "SEARCH_RESULT";
	public static final String SEARCH_RESULT_COUNT="SEARCH_RESULT_COUNT";
	public static final String ALL_SEARCH_RESULT = "ALL_SEARCH_RESULT";
	public static final String SEARCH_RESULT_STUDY = "SEARCH_RESULT_STUDY";
	public static final String SEARCH_RESULT_PART = "SEARCH_RESULT_PART";
	public static final String CURRENT_TABLE_ID = "CURRENT_TABLE_ID";
	public static final String CURRENT_FORM = "CURRENT_FORM";
	public static final String CURRENT_ACTION = "CURRENT_ACTION";
	public static final String HOT_LINK = "HOT_LINK";

	public static final String MESSAGE_ID = "messageId";
	public static final String ERROR_ID = "errorId";

	public static final String LOGIN_OBJECT = "LOGIN_OBJECT";

	public static final String ADD = "ADD";
	public static final String SEARCH = "SEARCH";

	public static final String BLANK = "";

	public static final String YES = "YES";
	public static final String NO = "NO";

	public static final String REQUIRED = "REQUIRED";
	public static final String NOT_REQUIRED = "NOT_REQUIRED";

	public static final String HOME_ID = "Home";
	public static final String PI_ID = "PI";
	public static final String HEALTH_CARE_SITE_ID = "HealthCareSite";
	public static final String ADMIN_ID = "Admin";
	public static final String ADMIN_HOME_ID = "AdminHome";
	public static final String LABACTIVITES_ID = "LabActivities";
	public static final String STUDYSEARCH_ID = "StudySearch";
	public static final String PARTICIPANTSEARCH_ID = "ParticipantSearch";
	public static final String TEST_ID = "Happy";
	public static final String LOGOUT_ID = "Logout";
	public static final String USER_CONFG_ID = "UserConfig";

	public static final String HOME_NAME = "Home";
	public static final String PRIVILEGE_NAME = "Privilege";
	public static final String ROLE_NAME = "Role";
	public static final String GROUP_NAME = "Group";
	public static final String USER_NAME = "User";
	public static final String PROTECTION_GROUP_NAME = "Protection Group";
	public static final String PROTECTION_ELEMENT_NAME = "Protection Element";

	public static final boolean NOT_DISABLED = false;
	public static final boolean DISABLED = true;

	public static final String ERROR_DETAILS = "ERROR_DETAILS";

	public static final String ADMIN_USER = "ADMIN_USER";

	public static final String ASSIGNED_SET = "ASSIGNED_SET";
	public static final String AVAILABLE_SET = "AVAILABLE_SET";
	public static final String AVAILABLE_PROTECTIONGROUP_SET =
			"AVAILABLE_PROTECTIONGROUP_SET";
	public static final String AVAILABLE_ROLE_SET = "AVAILABLE_ROLE_SET";
	public static final String AVAILABLE_PROTECTIONGROUPROLECONTEXT_SET =
			"AVAILABLE_PROTECTIONGROUPROLECONTEXT_SET";
	public static final String AVAILABLE_PROTECTIONELEMENTPRIVILEGESCONTEXT_SET =
			"AVAILABLE_PROTECTIONELEMENTPRIVILEGESCONTEXT_SET";
	public static final String ASSIGNED_ROLE_SET = "ASSIGNED_ROLE_SET";

	public static final String ONLY_ROLES = "ONLY_ROLES";

	public static final byte ONE = 1;
	public static final byte ZERO = 0;

}
