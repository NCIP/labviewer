delete from csm_role_privilege
/

delete from csm_user_group
/

delete from csm_user_group_role_pg
/

delete from csm_group
/

delete from csm_role
/

delete from csm_privilege where privilege_id > 0
/

delete from csm_pg_pe
/

delete from csm_protection_element
/

delete from csm_protection_group
/

delete from csm_application
/


INSERT INTO csm_application(application_id, application_name, application_description) VALUES (CSM_APPLICATI_APPLICATION__SEQ.nextval, 'CTMS_SUITE', 'CTMS_SUITE')
/


INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('system_administrator',	'Configures the technical system level properties and behavior of the applications (i.e. the password policy, email setup, ESB, etc).',  csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('business_administrator',	'Manages the domain related application wide properties and behavior (i.e. label names, reference data lists, etc)',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('person_and_organization_information_manager',	'Manages organizations and rosters / Creates and updates person info including contact info, degrees/certifications, rosters they''re associated with',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('data_importer',	'Identifies studies defined by Coordinating Center and imports as a consumer that data defined elsewhere',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('user_administrator',	'Ability to read system personnel (research staff and investigators) and create/manage user accounts/application roles, defines Custom Combination Roles', 		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('study_qa_manager',	'Updates core study info after saving / opening study.(e.g. PI, title, phase, epochs/arms, basic study design)  Read-only review of study calendar template / releases templates to participating sites', 		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('study_creator',	'Creates the core study info (e.g. PI, title, description, phase, epochs/arms & basic study design, etc.) NOTE:  some sites may want to combine the supplemental study info roles into this role', 		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('supplemental_study_information_manager',	'Adds treatment assignment, drugs, adEERS-specific diseases, adEERs reporting criteria, CTC/MedDRA version, etc.  Update/manage registration metadata (e.g. stratifications, eligibility criteria, notifi',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('study_team_administrator',	'Connects study level people to study and internal staff to study; Assigns internal staff to protocol, determines study artifact accessiblity for staff (e.g. study calendar templates, CRFs, etc.)',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('study_site_participation_administrator',	'Connects participating sites to a protocol',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('ae_rule_and_report_manager',	'Creates, manages, imports AE rules / Creates, manages, imports AE report definitions',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('study_calendar_template_builder',	'Creates and updates study calendar templates', 		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('registration_qa_manager',	'Updates registration information (study subject ID, Date of consent) after enrollment. Can waive the eligibility criteria for certain study subjects.', 		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('subject_manager',	'Defines patient to system (remaining subject data managed by other roles which are not defined)',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('study_subject_calendar_manager',	'Creates and updates a subject-specific study calendar based on a study calendar template', 		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('registrar',	'Accepts and approves/denies subject registration requests; requests subject registration on a particular study',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('ae_reporter',	'Creates / updates info about AE that needs reported / submits report to appropriate parties per report definition. Enters set of required AEs to be assessed and any other AEs that patient experienced.',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('ae_expedited_report_reviewer',	'Read-only: reviews, provides comments, and routes expedited reports through the review workflow',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('ae_study_data_reviewer',	'Read-only: reviews, provides comments, and adverse event data through a review workflow',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('lab_impact_calendar_notifier',	'Creates a calendar notification for a potential lab-based treatment modification',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('lab_data_user',	'Enters, edits, and imports labs from LIMS, viewing labs, selecting and sending labs to CDMS and caAERS',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('data_reader',	'Read-only: typically not part of org being audited, granted temporary access (no updates) to whole study or specific study/subjects, or any data entered by site for study/subject, crosses all apps.',		csm_applicati_application__seq.currval)
/

INSERT INTO csm_group(group_name, group_desc, application_id) VALUES ('data_analyst',	'Read-only: searches for data, uses built-in analysis tools, exports data to third party tools',		csm_applicati_application__seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('system_administrator',	'Configures the technical system level properties and behavior of the applications (i.e. the password policy, email setup, ESB, etc).',	csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('system_administrator',	'Configures the technical system level properties and behavior of the applications (i.e. the password policy, email setup, ESB, etc).')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('business_administrator',	'Manages the domain related application wide properties and behavior (i.e. label names, reference data lists, etc)',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('business_administrator',	'Manages the domain related application wide properties and behavior (i.e. label names, reference data lists, etc)')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('person_and_organization_information_manager',	'Manages organizations and rosters / Creates and updates person info including contact info, degrees/certifications, rosters they?re associated with',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('person_and_organization_information_manager',	'Manages organizations and rosters / Creates and updates person info including contact info, degrees/certifications, rosters they?re associated with')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('data_importer',	'Identifies studies defined by Coordinating Center and imports as a consumer that data defined elsewhere',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('data_importer',	'Identifies studies defined by Coordinating Center and imports as a consumer that data defined elsewhere')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('user_administrator',	'Ability to read system personnel (research staff and investigators) and create/manage user accounts/application roles, defines Custom Combination Roles', 		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('user_administrator',	'Ability to read system personnel (research staff and investigators) and create/manage user accounts/application roles, defines Custom Combination Roles')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('study_qa_manager',	'Updates core study info after saving / opening study.(e.g. PI, title, phase, epochs/arms, basic study design)  Read-only review of study calendar template / releases templates to participating sites', 		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('study_qa_manager',	'Updates core study info after saving / opening study.(e.g. PI, title, phase, epochs/arms, basic study design)  Read-only review of study calendar template / releases templates to participating sites')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('study_creator',	'Creates the core study info (e.g. PI, title, description, phase, epochs/arms & basic study design, etc.) NOTE:  some sites may want to combine the supplemental study info roles into this role', 		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('study_creator',	'Creates the core study info (e.g. PI, title, description, phase, epochs/arms & basic study design, etc.) NOTE:  some sites may want to combine the supplemental study info roles into this role')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('supplemental_study_information_manager',	'Adds treatment assignment, drugs, adEERS-specific diseases, adEERs reporting criteria, CTC/MedDRA version, etc.  Update/manage registration metadata (e.g. stratifications, eligibility criteria, notifi',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('supplemental_study_information_manager',	'Adds treatment assignment, drugs, adEERS-specific diseases, adEERs reporting criteria, CTC/MedDRA version, etc.  Update/manage registration metadata (e.g. stratifications, eligibility criteria, notifi')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('study_team_administrator',	'Connects study level people to study and internal staff to study; Assigns internal staff to protocol, determines study artifact accessiblity for staff (e.g. study calendar templates, CRFs, etc.)',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('study_team_administrator',	'Connects study level people to study and internal staff to study; Assigns internal staff to protocol, determines study artifact accessiblity for staff (e.g. study calendar templates, CRFs, etc.)')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('study_site_participation_administrator',	'Connects participating sites to a protocol',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('study_site_participation_administrator',	'Connects participating sites to a protocol')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('ae_rule_and_report_manager',	'Creates, manages, imports AE rules / Creates, manages, imports AE report definitions',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ( 'ae_rule_and_report_manager',	'Creates, manages, imports AE rules / Creates, manages, imports AE report definitions')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('study_calendar_template_builder',	'Creates and updates study calendar templates', 		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('study_calendar_template_builder',	'Creates and updates study calendar templates')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('registration_qa_manager',	'Updates registration information (study subject ID, Date of consent) after enrollment. Can waive the eligibility criteria for certain study subjects.', 		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('registration_qa_manager',	'Updates registration information (study subject ID, Date of consent) after enrollment. Can waive the eligibility criteria for certain study subjects.')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('subject_manager',	'Defines patient to system (remaining subject data managed by other roles which are not defined)',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('subject_manager',	'Defines patient to system (remaining subject data managed by other roles which are not defined)')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('study_subject_calendar_manager',	'Creates and updates a subject-specific study calendar based on a study calendar template', 		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('study_subject_calendar_manager',	'Creates and updates a subject-specific study calendar based on a study calendar template')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('registrar',	'Accepts and approves/denies subject registration requests; requests subject registration on a particular study',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('registrar',	'Accepts and approves/denies subject registration requests; requests subject registration on a particular study')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('ae_reporter',	'Creates / updates info about AE that needs reported / submits report to appropriate parties per report definition. Enters set of required AEs to be assessed and any other AEs that patient experienced.',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('ae_reporter',	'Creates / updates info about AE that needs reported / submits report to appropriate parties per report definition. Enters set of required AEs to be assessed and any other AEs that patient experienced.')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('expedited_report_reviewer',	'Read-only: reviews, provides comments, and routes expedited reports through the review workflow',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('expedited_report_reviewer',	'Read-only: reviews, provides comments, and routes expedited reports through the review workflow')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('adverse_event_study_data_reviewer',	'Read-only: reviews, provides comments, and adverse event data through a review workflow',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('adverse_event_study_data_reviewer',	'Read-only: reviews, provides comments, and adverse event data through a review workflow')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('lab_impact_calendar_notifier',	'Creates a calendar notification for a potential lab-based treatment modification',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('lab_impact_calendar_notifier',	'Creates a calendar notification for a potential lab-based treatment modification')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('lab_data_user',	'Enters, edits, and imports labs from LIMS, viewing labs, selecting and sending labs to CDMS and caAERS',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('lab_data_user',	'Enters, edits, and imports labs from LIMS, viewing labs, selecting and sending labs to CDMS and caAERS')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('data_reader',	'Read-only: typically not part of org being audited, granted temporary access (no updates) to whole study or specific study/subjects, or any data entered by site for study/subject, crosses all apps.',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('data_reader',	'Read-only: typically not part of org being audited, granted temporary access (no updates) to whole study or specific study/subjects, or any data entered by site for study/subject, crosses all apps.')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/


INSERT INTO csm_role(role_name, role_description, application_id, active_flag) VALUES ('data_analyst',	'Read-only: searches for data, uses built-in analysis tools, exports data to third party tools',		csm_applicati_application__seq.currval, 1)
/

INSERT INTO csm_privilege(privilege_name, privilege_description) VALUES ('data_analyst',	'Read-only: searches for data, uses built-in analysis tools, exports data to third party tools')
/

INSERT INTO csm_role_privilege(role_id, privilege_id) VALUES (csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)
/
