/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
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
	public static final String STUDYSEARCH_ID = "searchStudy";
	public static final String PARTICIPANTSEARCH_ID = "searchParticipant";
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
