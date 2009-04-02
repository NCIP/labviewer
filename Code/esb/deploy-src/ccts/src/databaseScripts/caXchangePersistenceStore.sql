# Replace the <<database_name>> with proper database name that is to be created. 

USE @CAXCHANGE_DATABASE@;
commit;
CREATE TABLE  IF NOT EXISTS CAXCHANGE_MESSAGES
(
   MESSAGE_ID  VARCHAR(500) NOT NULL,
   ORIGINAL_MESSAGE  MEDIUMTEXT,
   CONSTRAINT PRIMARY KEY (MESSAGE_ID(500))
);
CREATE TABLE  IF NOT EXISTS CAXCHANGE_MESSAGE_TYPES
(
   MESSAGE_TYPE  VARCHAR(500) NOT NULL,
   PAYLOAD_NAMESPACE  VARCHAR(500),
   CONSTRAINT PRIMARY KEY (MESSAGE_TYPE(500))
);
CREATE TABLE  IF NOT EXISTS CAXCHANGE_NAMESPACES
(
   PAYLOAD_NAMESPACE  VARCHAR(500),
   SCHEMA_FILE_NAME   VARCHAR(500),
   CONSTRAINT PRIMARY KEY (PAYLOAD_NAMESPACE(500))
);
INSERT INTO `CAXCHANGE_MESSAGE_TYPES`(`MESSAGE_TYPE`,`PAYLOAD_NAMESPACE`) 
VALUES ('STUDY_CREATION','gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain'),
       ('REGISTER_SUBJECT','gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain'),
       ('LAB_BASED_AE','gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain.loadlabs'),
       ('LOAD_LAB_TO_CDMS','gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain.loadlabs'),
       ('PERSON','http://po.coppa.nci.nih.gov'),
       ('IDENTIFIED_PERSON','http://po.coppa.nci.nih.gov'),
       ('ORGANIZATION','http://po.coppa.nci.nih.gov'),
       ('ORGANIZATIONAL_CONTACT','http://po.coppa.nci.nih.gov'),
       ('OVERSIGHT_COMMITTEE','http://po.coppa.nci.nih.gov'),
       ('RESEARCH_ORGANIZATION','http://po.coppa.nci.nih.gov'),
       ('PERSON_BUSINESS_SERVICE','http://po.coppa.nci.nih.gov'),
       ('ORGANIZATION_BUSINESS_SERVICE','http://po.coppa.nci.nih.gov'),       
       ('IDENTIFIED_ORGANIZATION','http://po.coppa.nci.nih.gov'),
       ('CLINICAL_RESEARCH_STAFF','http://po.coppa.nci.nih.gov'),
       ('HEALTH_CARE_FACILITY','http://po.coppa.nci.nih.gov'),
       ('HEALTH_CARE_PROVIDER','http://po.coppa.nci.nih.gov'),
       ('SCHEDULE_MODIFICATION','gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.ae.domain');

INSERT INTO `CAXCHANGE_NAMESPACES`(`PAYLOAD_NAMESPACE`,`SCHEMA_FILE_NAME`)
VALUES ('gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain','ccts.cabig-1.0_gov.nih.nci.cabig.ccts.domain.xsd'),
       ('gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain.loadlabs','ccts.cabig-1.0_gov.nih.nci.cabig.ccts.domain.loadlabs.xsd'),
       ('http://po.coppa.nci.nih.gov','http_po.coppa.nci.nih.gov.xsd'),
       ('gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.ae.domain','ccts.cabig-1.0_gov.nih.nci.cabig.ccts.ae.domain.xsd');