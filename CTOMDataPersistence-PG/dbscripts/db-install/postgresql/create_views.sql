CREATE OR REPLACE VIEW PERSON_VIEW
(ID, LAST_NAME, FIRST_NAME, MIDDLE_NAME, BIRTH_DATE, BIRTH_DATE_ORIG, 
 TELECOM_ADDRESS, ADMINISTRATIVE_GENDER_CODE, STREET_ADDRESS, CITY, STATE, ZIP_CODE, COUNTRY_CODE, PHONE, EDUCATION_LEVEL_CODE, ETHNIC_GROUP_CODE, 
 HOUSEHOLD_INCOME_CODE, MARITAL_STATUS_CODE, RACE_CODE, EMPLOYMENT_STATUS_CODE, EMPLOYMENT_STATUS_OTHER_TEXT, 
 SOURCE, SOURCE_EXTRACT_DATE, CTOM_INSERT_DATE, CTOM_UPDATE_DATE, SECURITY_KEY, SECURITY_ENABLED)
AS 
SELECT ID
       , LAST_NAME
       , FIRST_NAME
       , MIDDLE_NAME
       , BIRTH_DATE
       , BIRTH_DATE_ORIG
       , TELECOM_ADDRESS
       , ADMINISTRATIVE_GENDER_CODE
       , STREET_ADDRESS
       , CITY
       , STATE
       , ZIP_CODE
       , COUNTRY_CODE
       , PHONE
       , EDUCATION_LEVEL_CODE
       , ETHNIC_GROUP_CODE
       , HOUSEHOLD_INCOME_CODE
       , MARITAL_STATUS_CODE
       , RACE_CODE
       , EMPLOYMENT_STATUS_CODE
       , EMPLOYMENT_STATUS_OTHER_TEXT
       , SOURCE
       , SOURCE_EXTRACT_DATE
       , CTOM_INSERT_DATE
       , CTOM_UPDATE_DATE
       , SECURITY_KEY
       , 'F'
  FROM INVESTIGATOR
  UNION
  SELECT ID
       , LAST_NAME
       , FIRST_NAME
       , MIDDLE_NAME
       , BIRTH_DATE
       , BIRTH_DATE_ORIG
       , TELECOM_ADDRESS
       , ADMINISTRATIVE_GENDER_CODE
       , STREET_ADDRESS
       , CITY
       , STATE
       , ZIP_CODE
       , COUNTRY_CODE
       , PHONE
       , EDUCATION_LEVEL_CODE
       , ETHNIC_GROUP_CODE
       , HOUSEHOLD_INCOME_CODE
       , MARITAL_STATUS_CODE
       , RACE_CODE
       , EMPLOYMENT_STATUS_CODE
       , EMPLOYMENT_STATUS_OTHER_TEXT
       , SOURCE
       , SOURCE_EXTRACT_DATE
       , CTOM_INSERT_DATE
       , CTOM_UPDATE_DATE
       , SECURITY_KEY
       , 'T'
  FROM PARTICIPANT;



CREATE OR REPLACE VIEW ORGANIZATION_VIEW
(ID, DESCRIPTION_TEXT, STATUS_CODE, STATUS_DATE, 
 STATUS_DATE_ORIG, STREET_ADDRESS, CITY, STATE_CODE, POSTAL_CODE, 
 COUNTRY_CODE, TELECOM_ADDRESS, SOURCE, SOURCE_EXTRACT_DATE, CTOM_INSERT_DATE, 
 CTOM_UPDATE_DATE,NAME)
 AS      
 SELECT ID, DESCRIPTION_TEXT, STATUS_CODE, STATUS_DATE, STATUS_DATE_ORIG
		           , STREET_ADDRESS, CITY, STATE_CODE, POSTAL_CODE, COUNTRY_CODE, TELECOM_ADDRESS
		           , SOURCE, SOURCE_EXTRACT_DATE, CTOM_INSERT_DATE, CTOM_UPDATE_DATE
		           ,NAME|| CASE name WHEN null THEN '('||nci_institute_code||')'
		            	   ELSE CASE nci_institute_code WHEN NULL THEN ' '
		            		ELSE  '('||nci_institute_code||')' 
		                        END
		                  END
FROM HEALTHCARE_SITE;
		
CREATE OR REPLACE VIEW LAB_HUB_ACTIVITY
(ID, IDENTIFIER, ACTUAL_START_DATE_TIME, ACTUAL_END_DATE_TIME, PLANNED_TIME_ELAPSED, 
 PLANNED_TIME_ELAPSED_DSCRPTN, REASON, PLANNED_INDICATOR, TYPE_MODIFIER, SUBJECT_ASSIGNMENT_ID)
AS 
SELECT activity.id  AS                            id,
       study_time_point.visit_name AS                      identifier,
       activity.start_date AS                      actual_start_date_time,
       activity.stop_date AS                      actual_end_date_time,
       activity.planned_duration AS                planned_time_elapsed,
       activity.planned_duration_description AS    planned_time_elapsed_dscrptn,
       activity.reason_code AS                    reason,
       activity.planned_indicator AS               planned_indicator,
       activity.type  AS                          type_modifier,
       activity.study_participant_assignmnt_id AS subject_assignment_id

FROM   activity, study_time_point            
WHERE  activity.id = study_time_point.activity_id;

CREATE OR REPLACE VIEW lab_hub_investigator AS 
  SELECT investigator.id, investigator.birth_date AS date_of_birth, 
  investigator.initials, investigator.nci_identifier AS identifier, 
  investigator.last_name AS name,
  investigator.last_name, investigator.first_name, investigator.middle_name, investigator.telecom_address, 
 investigator.street_address, investigator.city, investigator.state, investigator.zip_code, 
 investigator.country_code, investigator.phone, investigator.source, investigator.source_extract_date, 
 study_investigator.id AS study_site_id, 
 investigator.adm_gndr_concept_descriptor_id AS sex_concept_descriptor_id, 
 investigator.race_concept_descriptor_id
 FROM investigator, study_investigator
 WHERE investigator.id = study_investigator.investigator_id;

CREATE OR REPLACE VIEW LAB_HUB_LAB_RESULT
(ID, NUMERIC_RESULT, NUMERIC_PRECISION, TEXT_RESULT, REFERENCE_RANGE_LOW, 
 REFERENCE_RANGE_HIGH, REFERENCE_RANGE_COMMENT, REFERENCE_TEXT_LIST, REPORTED_RESULT_STATUS, TEST_PERFORMED_DATE_TIME, 
 REFERENCE_FLAG, LAB_TEST_ID, NUM_UOM_CONCEPT_DESCRIPTOR_ID, PERFORMING_LABORATORY_ID)
AS 
SELECT clinical_result.id    AS id,
       CASE clinical_result.value WHEN translate(clinical_result.value,'+-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_=~`",<>?[]{}|/\\', '+-.0123456789') THEN clinical_result.value
       				  ELSE NULL
       END AS  numeric_result,				  
       numeric_precision AS                numeric_precision,
       CASE clinical_result.value WHEN translate(clinical_result.value,'+-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_=~`",<>?[]{}|/\\', '+-.0123456789') THEN NULL
       				  ELSE clinical_result.value
              END AS   text_result,				  
       clinical_result.reference_range_low AS            reference_range_low,
       clinical_result.reference_range_high AS            reference_range_high,
       clinical_result.reference_range_comment AS        reference_range_comment,
       clinical_result.reference_text_list AS            reference_text_list,
       observation.status_code AS                    reported_result_status,
       observation.reporting_date AS                  test_performed_date_time,
       clinical_result.reference_flag  AS                reference_flag,
       clinical_result.id AS                             lab_test_id,
       clinical_result.valueuom_concept_descriptor_id AS num_uom_concept_descriptor_id,
       clinical_result.performing_laboratory_id AS       performing_laboratory_id
FROM   clinical_result, observation
WHERE  observation.id = clinical_result.id;


CREATE OR REPLACE VIEW LAB_HUB_LAB_TEST
(ID, STATUS, ADDITIONAL_TEST_DESCRIPTION, COMMENTS, STUDY_DEFINED_INDICATOR, 
 STUDY_SCHEDULED_INDICATOR, SPECIMEN_ID, LAB_TEST_CONCEPT_DESCRIPTOR_ID, PARENT_LAB_TEST_ID)
AS 
SELECT clinical_result.id    AS                          id,
       observation.status_code AS                    status,
       clinical_result.additional_test_description As     additional_test_description,
       observation.comments      AS                  comments,
       null                     AS         study_defined_indicator,
       null                     AS         study_scheduled_indicator,
       specimen.id              AS                specimen_id,
       clinical_result.lab_test_concept_descriptor_id AS  lab_test_concept_descriptor_id,
       null                    AS          parent_lab_test_id,
       clinical_result.id   AS                           lab_result_id
FROM   observation,    clinical_result,  activity,  procedure, specimen_collection,specimen
WHERE  observation.id          = clinical_result.id
AND    observation.activity_id = activity.id
AND    activity.id          = procedure.id
AND    procedure.id          =specimen_collection.id
AND    specimen_collection.id         = specimen.specimen_collection_id;


CREATE OR REPLACE VIEW LAB_HUB_SPECIMEN_COLLECTION
(ID, IDENTIFIER, ACTUAL_START_DATE_TIME, ACTUAL_END_DATE_TIME, PLANNED_TIME_ELAPSED, 
 PLANNED_TIME_ELAPSED_DSCRPTN, REASON, PLANNED_INDICATOR, TYPE_MODIFIER, SUBJECT_AGE_AT_COLLECTION, 
 SUBJECT_AGE_AT_COLLECTION_UNIT, FASTING_STATUS, METHOD, SUBJECT_ASSIGNMENT_ID, CENTRAL_LABORATORY_ID)
AS 
SELECT s.id  AS                            id,
       t.visit_name AS                     identifier,
       a.start_date AS                     actual_start_date_time,
       a.stop_date  AS                     actual_end_date_time,
       a.planned_duration AS                planned_time_elapsed,
       a.planned_duration_description  AS  planned_time_elapsed_dscrptn,
       a.reason_code   AS                  reason,
       a.planned_indicator AS               planned_indicator,
       a.type    AS                        type_modifier,
       a.age_at_visit AS                    subject_age_at_collection,
       a.age_at_visit_units  AS            subject_age_at_collection_unit,
       p.fasting_status   AS               fasting_status,
       s.method_code  AS                   method,
       a.study_participant_assignmnt_id  AS subject_assignment_id,
       s.central_laboratory_id        AS   central_laboratory_id
FROM   activity  As                        a,
       procedure AS                        p,
       specimen_collection  AS             s,
       study_time_point  AS                t
WHERE  a.id = p.id
AND    p.id = s.id
AND    a.id = t.activity_id;


CREATE OR REPLACE VIEW LAB_HUB_ORGANIZATION
(ID, IDENTIFIER, NAME)
AS 
SELECT c.id   AS               id,
       c.identifier AS          identifier,
       c.name     AS           name
FROM   central_laboratory As   c
UNION
SELECT p.id               AS   id,
       p.identifier       As   identifier,
       p.name             AS   name
FROM   performing_laboratory AS p
UNION
SELECT h.id                 AS   id,
       h.nci_institute_code As   identifier,
       h.name               AS   name
FROM   healthcare_site AS h;


CREATE OR REPLACE VIEW LAB_HUB_PERSON
(ID, DATE_OF_BIRTH, INITIALS, SEX_CONCEPT_DESCRIPTOR_ID, RACE_CONCEPT_DESCRIPTOR_ID)
AS 
SELECT i.id      AS                        id,
       i.birth_date  AS                     date_of_birth,
       i.initials   AS                     initials,
       i.adm_gndr_concept_descriptor_id AS  sex_concept_descriptor_id,
       i.race_concept_descriptor_id    AS  race_concept_descriptor_id
FROM   investigator                    AS  i
UNION
SELECT p.id  AS                            id,
       p.birth_date AS                      date_of_birth,
       p.initials  AS                      initials,
       p.adm_gndr_concept_descriptor_id AS  sex_concept_descriptor_id,
       p.race_concept_descriptor_id    AS  race_concept_descriptor_id
FROM   participant                     AS  p;

