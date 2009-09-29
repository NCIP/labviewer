/* *****************************************************************************
********************************************************************************

SCRIPT NAME:  DDL_For_CTOM_Views.sql 
PURPOSE:      Create or recreate views on CTOM tables. 
AUTHOR:       ScenPro, Inc.
DATE:         June, 2007 
VERSION:      CTOM v0.5.3 
NOTE:         This script was created in Toad and is best viewed through that tool.
UPDATE LOG:
DATE        DESCRIPTION
----------  --------------------------------------------------------------------
2007-06-29  Added new columns to Person_View.
2007-07-18  Modified Organization_View name so that it is name and code.
2007-08-07  Added security_enabled column to Person_View.
2007-09-10  Added second decode to org view to handle null nci_institute_code 
2007-10-05  Added edited views to support caXchange's Lab Hub requirements.
            NOTE:  leaving the existing caCTUS/CTOMAPI views alone for now since
                   they are not used by caXchange and caCTUS will still need
                   the current definition - will have to change them once all apps
                   use the concept_descriptor for codes rather than embedded columns.
2007-10-17  Fixed bug in LAB_HUB_INVESTIGATOR
2007-10-29  Added lab_result_id to LAB_HUB_LAB_TEST view
2007-11-07  Added LAB_RESULT_ID to column name list in LAB_HUB_LAB_TEST view

********************************************************************************
***************************************************************************** */

SET ECHO ON

SPOOL DDL_For_CTOM_Views.lst


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
(ID, NAME, DESCRIPTION_TEXT, STATUS_CODE, STATUS_DATE, 
 STATUS_DATE_ORIG, STREET_ADDRESS, CITY, STATE_CODE, POSTAL_CODE, 
 COUNTRY_CODE, TELECOM_ADDRESS, SOURCE, SOURCE_EXTRACT_DATE, CTOM_INSERT_DATE, 
 CTOM_UPDATE_DATE)
AS 
SELECT ID, NAME||decode(name, null, '('||nci_institute_code||')', decode(nci_institute_code, null, '', ' ('||nci_institute_code||')'))
         , DESCRIPTION_TEXT, STATUS_CODE, STATUS_DATE, STATUS_DATE_ORIG
         , STREET_ADDRESS, CITY, STATE_CODE, POSTAL_CODE, COUNTRY_CODE, TELECOM_ADDRESS
         , SOURCE, SOURCE_EXTRACT_DATE, CTOM_INSERT_DATE, CTOM_UPDATE_DATE
FROM HEALTHCARE_SITE;


CREATE OR REPLACE VIEW LAB_HUB_ACTIVITY
(ID, IDENTIFIER, ACTUAL_START_DATE_TIME, ACTUAL_END_DATE_TIME, PLANNED_TIME_ELAPSED, 
 PLANNED_TIME_ELAPSED_DSCRPTN, REASON, PLANNED_INDICATOR, TYPE_MODIFIER, SUBJECT_ASSIGNMENT_ID)
AS 
SELECT a.id                              id,
       s.visit_name                      identifier,
       a.start_date                      actual_start_date_time,
       a.stop_date                       actual_end_date_time,
       a.planned_duration                planned_time_elapsed,
       a.planned_duration_description    planned_time_elapsed_dscrptn,
       a.reason_code                     reason,
       a.planned_indicator               planned_indicator,
       a.type                            type_modifier,
       a.study_participant_assignmnt_id  subject_assignment_id
FROM   activity                        a,
       study_time_point                s
WHERE  a.id = s.activity_id;


CREATE OR REPLACE VIEW LAB_HUB_INVESTIGATOR
(ID, DATE_OF_BIRTH, INITIALS, IDENTIFIER, NAME, 
 STUDY_SITE_ID, SEX_CONCEPT_DESCRIPTOR_ID, RACE_CONCEPT_DESCRIPTOR_ID)
AS 
SELECT i.id                              id,
       i.birth_date                      date_of_birth,
       i.initials                        initials,
       i.nci_identifier                  identifier,
       i.last_name                       name,
       s.id                              study_site_id,
       i.adm_gndr_concept_descriptor_id  sex_concept_descriptor_id,
       i.race_concept_descriptor_id      race_concept_descriptor_id
FROM   investigator                      i,
       study_investigator                s
WHERE  i.id = s.investigator_id;


CREATE OR REPLACE VIEW LAB_HUB_LAB_RESULT
(ID, NUMERIC_RESULT, NUMERIC_PRECISION, TEXT_RESULT, REFERENCE_RANGE_LOW, 
 REFERENCE_RANGE_HIGH, REFERENCE_RANGE_COMMENT, REFERENCE_TEXT_LIST, REPORTED_RESULT_STATUS, TEST_PERFORMED_DATE_TIME, 
 REFERENCE_FLAG, LAB_TEST_ID, NUM_UOM_CONCEPT_DESCRIPTOR_ID, PERFORMING_LABORATORY_ID)
AS 
SELECT r.id                              id,
       decode(r.value,
                 translate(r.value,'+-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_=~`",<>?[]{}|/\','+-.0123456789'),r.value,
                 null)                   numeric_result,
       numeric_precision                 numeric_precision,
       decode(r.value,
              translate(r.value,'+-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_=~`",<>?[]{}|/\','+-.0123456789'),null,
              r.value)                   text_result,
       r.reference_range_low             reference_range_low,
       r.reference_range_high            reference_range_high,
       r.reference_range_comment         reference_range_comment,
       r.reference_text_list             reference_text_list,
       o.status_code                     reported_result_status,
       o.reporting_date                  test_performed_date_time,
       r.reference_flag                  reference_flag,
       r.id                              lab_test_id,
       r.valueuom_concept_descriptor_id  num_uom_concept_descriptor_id,
       r.performing_laboratory_id        performing_laboratory_id
FROM   clinical_result                   r,
       observation                       o
WHERE  o.id = r.id;


CREATE OR REPLACE VIEW LAB_HUB_LAB_TEST
(ID, STATUS, ADDITIONAL_TEST_DESCRIPTION, COMMENTS, STUDY_DEFINED_INDICATOR, 
 STUDY_SCHEDULED_INDICATOR, SPECIMEN_ID, LAB_TEST_CONCEPT_DESCRIPTOR_ID, PARENT_LAB_TEST_ID, 
 LAB_RESULT_ID)
AS 
SELECT r.id                              id,
       o.status_code                     status,
       r.additional_test_description     additional_test_description,
       o.comments                        comments,
       null                              study_defined_indicator,
       null                              study_scheduled_indicator,
       s.id                              specimen_id,
       r.lab_test_concept_descriptor_id  lab_test_concept_descriptor_id,
       null                              parent_lab_test_id,
       r.id                              lab_result_id
FROM   observation                       o,
       clinical_result                   r,
       activity                          a,
       procedure                         p,
       specimen_collection               sc,
       specimen                          s
WHERE  o.id          = r.id
AND    o.activity_id = a.id
AND    a.id          = p.id
AND    p.id          = sc.id
AND    sc.id         = s.specimen_collection_id;


CREATE OR REPLACE VIEW LAB_HUB_ORGANIZATION
(ID, IDENTIFIER, NAME)
AS 
SELECT c.id                  id,
       c.identifier          identifier,
       c.name                name
FROM   central_laboratory    c
UNION
SELECT p.id                  id,
       p.identifier          identifier,
       p.name                name
FROM   performing_laboratory p;


CREATE OR REPLACE VIEW LAB_HUB_PERSON
(ID, DATE_OF_BIRTH, INITIALS, SEX_CONCEPT_DESCRIPTOR_ID, RACE_CONCEPT_DESCRIPTOR_ID)
AS 
SELECT i.id                              id,
       i.birth_date                      date_of_birth,
       i.initials                        initials,
       i.adm_gndr_concept_descriptor_id  sex_concept_descriptor_id,
       i.race_concept_descriptor_id      race_concept_descriptor_id
FROM   investigator                      i
UNION
SELECT p.id                              id,
       p.birth_date                      date_of_birth,
       p.initials                        initials,
       p.adm_gndr_concept_descriptor_id  sex_concept_descriptor_id,
       p.race_concept_descriptor_id      race_concept_descriptor_id
FROM   participant                       p;


CREATE OR REPLACE VIEW LAB_HUB_SPECIMEN_COLLECTION
(ID, IDENTIFIER, ACTUAL_START_DATE_TIME, ACTUAL_END_DATE_TIME, PLANNED_TIME_ELAPSED, 
 PLANNED_TIME_ELAPSED_DSCRPTN, REASON, PLANNED_INDICATOR, TYPE_MODIFIER, SUBJECT_AGE_AT_COLLECTION, 
 SUBJECT_AGE_AT_COLLECTION_UNIT, FASTING_STATUS, METHOD, SUBJECT_ASSIGNMENT_ID, CENTRAL_LABORATORY_ID)
AS 
SELECT s.id                              id,
       t.visit_name                      identifier,
       a.start_date                      actual_start_date_time,
       a.stop_date                       actual_end_date_time,
       a.planned_duration                planned_time_elapsed,
       a.planned_duration_description    planned_time_elapsed_dscrptn,
       a.reason_code                     reason,
       a.planned_indicator               planned_indicator,
       a.type                            type_modifier,
       a.age_at_visit                    subject_age_at_collection,
       a.age_at_visit_units              subject_age_at_collection_unit,
       p.fasting_status                  fasting_status,
       s.method_code                     method,
       a.study_participant_assignmnt_id  subject_assignment_id,
       s.central_laboratory_id           central_laboratory_id
FROM   activity                          a,
       procedure                         p,
       specimen_collection               s,
       study_time_point                  t
WHERE  a.id = p.id
AND    p.id = s.id
AND    a.id = t.activity_id;


SPOOL OFF

SET ECHO OFF

