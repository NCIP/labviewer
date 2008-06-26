SET FOREIGN_KEY_CHECKS=0;
INSERT INTO `csm_application` (`APPLICATION_ID`,`APPLICATION_NAME`,`APPLICATION_DESCRIPTION`,`DECLARATIVE_FLAG`,`ACTIVE_FLAG`,`UPDATE_DATE`,`DATABASE_URL`,`DATABASE_USER_NAME`,`DATABASE_PASSWORD`,`DATABASE_DIALECT`,`DATABASE_DRIVER`) VALUES 
 (1,'csmupt','CSM UPT Super Admin Application',0,0,'2008-05-15',NULL,NULL,NULL,NULL,NULL),
 (2,'caxchange','Application Description',0,0,'2008-05-15',NULL,NULL,NULL,NULL,NULL),
 (3,'CLM','CLM',0,0,'2008-05-15',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) VALUES 
 (1,1,4,'2008-05-15');

INSERT INTO `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) VALUES 
 (1,'CREATE','This privilege grants permission to a user to create an entity. This entity can be an object, a database entry, or a resource such as a network connection','2008-05-15'),
 (2,'ACCESS','This privilege allows a user to access a particular resource.  Examples of resources include a network or database connection, socket, module of the application, or even the application itself','2008-05-15'),
 (3,'READ','This privilege permits the user to read data from a file, URL, database, an object, etc. This can be used at an entity level signifying that the user is allowed to read data about a particular entry','2008-05-15'),
 (4,'WRITE','This privilege allows a user to write data to a file, URL, database, an object, etc. This can be used at an entity level signifying that the user is allowed to write data about a particular entity','2008-05-15'),
 (5,'UPDATE','This privilege grants permission at an entity level and signifies that the user is allowed to update data for a particular entity. Entities may include an object, object attribute, database row etc','2008-05-15'),
 (6,'DELETE','This privilege permits a user to delete a logical entity. This entity can be an object, a database entry, a resource such as a network connection, etc','2008-05-15'),
 (7,'EXECUTE','This privilege allows a user to execute a particular resource. The resource can be a method, function, behavior of the application, URL, button etc','2008-05-15');
/*!40000 ALTER TABLE `csm_privilege` ENABLE KEYS */;


INSERT INTO `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) VALUES 
 (1,'csmupt','CSM UPT Super Admin Application Protection Element','csmupt',NULL,NULL,1,'2008-05-15'),
 (2,'caxchange','caxchange Admin Application Protection Element','caxchange',NULL,NULL,1,'2008-05-15'),
 (3,'CLM','CLM Admin Application Protection Element','CLM',NULL,NULL,1,'2008-05-15'),
 (4,'APPLICATION_NAME:caxchange','APPLICATION_NAME:caxchange','APPLICATION_NAME:caxchange',NULL,NULL,3,'2008-05-15');

INSERT INTO `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) VALUES 
 (1,'Application caxchange',NULL,3,0,'2008-05-15',NULL);

INSERT INTO `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) VALUES 
 (1,'READ Role','',3,1,'2008-05-15');

INSERT INTO `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) VALUES 
 (1,1,3,'2008-05-15');

INSERT INTO `csm_user` (`USER_ID`,`LOGIN_NAME`,`FIRST_NAME`,`LAST_NAME`,`ORGANIZATION`,`DEPARTMENT`,`TITLE`,`PHONE_NUMBER`,`PASSWORD`,`EMAIL_ID`,`START_DATE`,`END_DATE`,`UPDATE_DATE`) VALUES 
 (1,'cctsbunduser','first','last',NULL,NULL,NULL,NULL,'zJPWCwDeSgG8j2uyHEABIQ==',NULL,NULL,NULL,'2008-05-15');

INSERT INTO `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) VALUES 
 (1,1,NULL,1,1,'2008-05-15');


INSERT INTO `csm_user_pe` (`USER_PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_ID`,`USER_ID`,`UPDATE_DATE`) VALUES 
 (1,1,1,'2008-05-15'),
 (3,2,1,'2008-05-15'),
 (4,3,1,'2008-05-15');
