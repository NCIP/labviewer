delete from csm_role_privilege;

delete from csm_user_group;

delete from csm_user_group_role_pg;

delete from csm_group;

delete from csm_role;

delete from csm_privilege where privilege_id > 0;

delete from csm_pg_pe;

delete from csm_protection_element;

delete from csm_protection_group;

delete from csm_application;


INSERT INTO csm_application(application_id, application_name, application_description, declarative_flag, active_flag) VALUES ((select nextval('csm_applicati_application__seq')), 'CTMS_SUITE', 'CTMS_SUITE', 0, 0);


INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'system_administrator',	'Configures the technical system level properties and behavior of the applications (i.e. the password policy, email setup, ESB, etc).', (select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'business_administrator',	'Manages the domain related application wide properties and behavior (i.e. label names, reference data lists, etc)',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'person_and_organization_information_manager',	'Manages organizations and rosters / Creates and updates person info including contact info, degrees/certifications, rosters theyre associated with',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'data_importer',	'Identifies studies defined by Coordinating Center and imports as a consumer that data defined elsewhere',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'user_administrator',	'Ability to read system personnel (research staff and investigators) and create/manage user accounts/application roles, defines Custom Combination Roles', 		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'study_qa_manager',	'Updates core study info after saving / opening study.(e.g. PI, title, phase, epochs/arms, basic study design)  Read-only review of study calendar template / releases templates to participating sites', 		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'study_creator',	'Creates the core study info (e.g. PI, title, description, phase, epochs/arms & basic study design, etc.) NOTE:  some sites may want to combine the supplemental study info roles into this role', 		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'supplemental_study_information_manager',	'Adds treatment assignment, drugs, adEERS-specific diseases, adEERs reporting criteria, CTC/MedDRA version, etc.  Update/manage registration metadata (e.g. stratifications, eligibility criteria, notifi',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'study_team_administrator',	'Connects study level people to study and internal staff to study; Assigns internal staff to protocol, determines study artifact accessiblity for staff (e.g. study calendar templates, CRFs, etc.)',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'study_site_participation_administrator',	'Connects participating sites to a protocol',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')), 'ae_rule_and_report_manager',	'Creates, manages, imports AE rules / Creates, manages, imports AE report definitions',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'study_calendar_template_builder',	'Creates and updates study calendar templates', 		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'registration_qa_manager',	'Updates registration information (study subject ID, Date of consent) after enrollment. Can waive the eligibility criteria for certain study subjects.', 		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')), 'subject_manager',	'Defines patient to system (remaining subject data managed by other roles which are not defined)',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'study_subject_calendar_manager',	'Creates and updates a subject-specific study calendar based on a study calendar template', 		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'registrar',	'Accepts and approves/denies subject registration requests; requests subject registration on a particular study',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'ae_reporter',	'Creates / updates info about AE that needs reported / submits report to appropriate parties per report definition. Enters set of required AEs to be assessed and any other AEs that patient experienced.',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'ae_expedited_report_reviewer',	'Read-only: reviews, provides comments, and routes expedited reports through the review workflow',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'ae_study_data_reviewer',	'Read-only: reviews, provides comments, and adverse event data through a review workflow',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'lab_impact_calendar_notifier',	'Creates a calendar notification for a potential lab-based treatment modification',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'lab_data_user',	'Enters, edits, and imports labs from LIMS, viewing labs, selecting and sending labs to CDMS and caAERS',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')),	'data_reader',	'Read-only: typically not part of org being audited, granted temporary access (no updates) to whole study or specific study/subjects, or any data entered by site for study/subject, crosses all apps.',		(select currval('csm_applicati_application__seq')));

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES ((select nextval('csm_group_group_id_seq')), 'data_analyst',	'Read-only: searches for data, uses built-in analysis tools, exports data to third party tools',		(select currval('csm_applicati_application__seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'system_administrator',	'Configures the technical system level properties and behavior of the applications (i.e. the password policy, email setup, ESB, etc).',	(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'system_administrator',	'Configures the technical system level properties and behavior of the applications (i.e. the password policy, email setup, ESB, etc).');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'business_administrator',	'Manages the domain related application wide properties and behavior (i.e. label names, reference data lists, etc)',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'business_administrator',	'Manages the domain related application wide properties and behavior (i.e. label names, reference data lists, etc)');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'person_and_organization_information_manager',	'Manages organizations and rosters / Creates and updates person info including contact info, degrees/certifications, rosters they?re associated with',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'person_and_organization_information_manager',	'Manages organizations and rosters / Creates and updates person info including contact info, degrees/certifications, rosters they?re associated with');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'data_importer',	'Identifies studies defined by Coordinating Center and imports as a consumer that data defined elsewhere',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'data_importer',	'Identifies studies defined by Coordinating Center and imports as a consumer that data defined elsewhere');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'user_administrator',	'Ability to read system personnel (research staff and investigators) and create/manage user accounts/application roles, defines Custom Combination Roles', 		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'user_administrator',	'Ability to read system personnel (research staff and investigators) and create/manage user accounts/application roles, defines Custom Combination Roles');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'study_qa_manager',	'Updates core study info after saving / opening study.(e.g. PI, title, phase, epochs/arms, basic study design)  Read-only review of study calendar template / releases templates to participating sites', 		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'study_qa_manager',	'Updates core study info after saving / opening study.(e.g. PI, title, phase, epochs/arms, basic study design)  Read-only review of study calendar template / releases templates to participating sites');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'study_creator',	'Creates the core study info (e.g. PI, title, description, phase, epochs/arms & basic study design, etc.) NOTE:  some sites may want to combine the supplemental study info roles into this role', 		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'study_creator',	'Creates the core study info (e.g. PI, title, description, phase, epochs/arms & basic study design, etc.) NOTE:  some sites may want to combine the supplemental study info roles into this role');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'supplemental_study_information_manager',	'Adds treatment assignment, drugs, adEERS-specific diseases, adEERs reporting criteria, CTC/MedDRA version, etc.  Update/manage registration metadata (e.g. stratifications, eligibility criteria, notifi',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'supplemental_study_information_manager',	'Adds treatment assignment, drugs, adEERS-specific diseases, adEERs reporting criteria, CTC/MedDRA version, etc.  Update/manage registration metadata (e.g. stratifications, eligibility criteria, notifi');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'study_team_administrator',	'Connects study level people to study and internal staff to study Assigns internal staff to protocol, determines study artifact accessiblity for staff (e.g. study calendar templates, CRFs, etc.)',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'study_team_administrator',	'Connects study level people to study and internal staff to study; Assigns internal staff to protocol, determines study artifact accessiblity for staff (e.g. study calendar templates, CRFs, etc.)');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'study_site_participation_administrator',	'Connects participating sites to a protocol',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'study_site_participation_administrator',	'Connects participating sites to a protocol');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),  'ae_rule_and_report_manager',	'Creates, manages, imports AE rules / Creates, manages, imports AE report definitions',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),  'ae_rule_and_report_manager',	'Creates, manages, imports AE rules / Creates, manages, imports AE report definitions');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'study_calendar_template_builder',	'Creates and updates study calendar templates', 		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'study_calendar_template_builder',	'Creates and updates study calendar templates');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'registration_qa_manager',	'Updates registration information (study subject ID, Date of consent) after enrollment. Can waive the eligibility criteria for certain study subjects.', 		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'registration_qa_manager',	'Updates registration information (study subject ID, Date of consent) after enrollment. Can waive the eligibility criteria for certain study subjects.');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')), 'subject_manager',	'Defines patient to system (remaining subject data managed by other roles which are not defined)',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')), 'subject_manager',	'Defines patient to system (remaining subject data managed by other roles which are not defined)');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'study_subject_calendar_manager',	'Creates and updates a subject-specific study calendar based on a study calendar template', 		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'study_subject_calendar_manager',	'Creates and updates a subject-specific study calendar based on a study calendar template');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'registrar',	'Accepts and approves/denies subject registration requests; requests subject registration on a particular study',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'registrar',	'Accepts and approves/denies subject registration requests; requests subject registration on a particular study');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'ae_reporter',	'Creates / updates info about AE that needs reported / submits report to appropriate parties per report definition. Enters set of required AEs to be assessed and any other AEs that patient experienced.',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'ae_reporter',	'Creates / updates info about AE that needs reported / submits report to appropriate parties per report definition. Enters set of required AEs to be assessed and any other AEs that patient experienced.');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'ae_expedited_report_reviewer',	'Read-only: reviews, provides comments, and routes expedited reports through the review workflow',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'ae_expedited_report_reviewer',	'Read-only: reviews, provides comments, and routes expedited reports through the review workflow');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'ae_study_data_reviewer',	'Read-only: reviews, provides comments, and adverse event data through a review workflow',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'ae_study_data_reviewer',	'Read-only: reviews, provides comments, and adverse event data through a review workflow');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'lab_impact_calendar_notifier',	'Creates a calendar notification for a potential lab-based treatment modification',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'lab_impact_calendar_notifier',	'Creates a calendar notification for a potential lab-based treatment modification');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'lab_data_user',	'Enters, edits, and imports labs from LIMS, viewing labs, selecting and sending labs to CDMS and caAERS',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'lab_data_user',	'Enters, edits, and imports labs from LIMS, viewing labs, selecting and sending labs to CDMS and caAERS');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')),	'data_reader',	'Read-only: typically not part of org being audited, granted temporary access (no updates) to whole study or specific study/subjects, or any data entered by site for study/subject, crosses all apps.',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')),	'data_reader',	'Read-only: typically not part of org being audited, granted temporary access (no updates) to whole study or specific study/subjects, or any data entered by site for study/subject, crosses all apps.');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));


INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES ((select nextval('csm_role_role_id_seq')), 'data_analyst',	'Read-only: searches for data, uses built-in analysis tools, exports data to third party tools',		(select currval('csm_applicati_application__seq')), 1);

INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES ((select nextval('csm_privilege_privilege_id_seq')), 'data_analyst',	'Read-only: searches for data, uses built-in analysis tools, exports data to third party tools');

INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES ((select nextval('csm_role_priv_role_privile_seq')),(select currval('csm_role_role_id_seq')),(select currval('csm_privilege_privilege_id_seq')));
