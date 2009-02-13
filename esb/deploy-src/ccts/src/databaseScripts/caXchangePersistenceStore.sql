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
INSERT INTO `CAXCHANGE_MESSAGE_TYPES`(`MESSAGE_TYPE`,`PAYLOAD_NAMESPACE`) 
VALUES ('STUDY_CREATION','gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain'),
       ('REGISTER_SUBJECT','gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain'),
       ('LAB_BASED_AE','gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain.loadlabs'),
       ('LOAD_LAB_TO_CDMS','gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain.loadlabs'),
       ('COPPA','http://po.coppa.nci.nih.gov'),
       ('SCHEDULE_MODIFICATION','gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.ae.domain');

