
-- The following entries creates a super admin application incase you decide 
-- to use this database to run UPT also. In that case you need to provide
-- the project login id and name for the super admin.
-- However in incase you are using this database just to host the application's
-- authorization schema, these enteries are not used and hence they can be left as
-- it is.
--

	
	insert into csm_application(APPLICATION_ID, APPLICATION_NAME,APPLICATION_DESCRIPTION,DECLARATIVE_FLAG,ACTIVE_FLAG,UPDATE_DATE)
	values (1, 'csmupt','UPT Super Admin Application',0,0,now());
	select nextval(' CSM_APPLICATI_APPLICATION__SEQ');
	
	insert into csm_user (USER_ID, LOGIN_NAME,FIRST_NAME,LAST_NAME,PASSWORD,UPDATE_DATE)
	values (1, 'admin','UPT','Administrator','zJPWCwDeSgG8j2uyHEABIQ==',now());
	select nextval('CSM_USER_USER_ID_SEQ');
	 
	insert into csm_protection_element(PROTECTION_ELEMENT_ID, PROTECTION_ELEMENT_NAME,PROTECTION_ELEMENT_DESCRIPTION,OBJECT_ID,APPLICATION_ID,UPDATE_DATE)
	values(1, 'csmupt','UPT Super Admin Application','csmupt',1,now());
	select nextval('CSM_PROTECTIO_PROTECTION_E_SEQ');
	
	insert into csm_user_pe(USER_PROTECTION_ELEMENT_ID, PROTECTION_ELEMENT_ID,USER_ID,UPDATE_DATE)
	values(1,1,1,now());
	select nextval('CSM_USER_PE_USER_PROTECTIO_SEQ');
	
	
	INSERT INTO csm_application(APPLICATION_ID, APPLICATION_NAME,APPLICATION_DESCRIPTION,DECLARATIVE_FLAG,ACTIVE_FLAG,UPDATE_DATE)
	VALUES (2, 'labviewer','Lab Viewer Application',0,0,now());
	select nextval('CSM_APPLICATI_APPLICATION__SEQ');
	
	insert into csm_user (USER_ID, LOGIN_NAME,FIRST_NAME,LAST_NAME,PASSWORD,UPDATE_DATE)
	values (2, 'cctsdemo1@nci.nih.gov','CCTS','Demo','L5DdDAvHmUGthtxFP2ZrIA==',now());
	select nextval('CSM_USER_USER_ID_SEQ');
	
	insert into csm_protection_element(PROTECTION_ELEMENT_ID, PROTECTION_ELEMENT_NAME,PROTECTION_ELEMENT_DESCRIPTION,OBJECT_ID,APPLICATION_ID,UPDATE_DATE)
	values(2, 'labviewer','Lab Viewer Application','labviewer',2,now());
	select nextval('CSM_PROTECTIO_PROTECTION_E_SEQ');
	
	insert into csm_user_pe(USER_PROTECTION_ELEMENT_ID, PROTECTION_ELEMENT_ID,USER_ID,UPDATE_DATE)
	values(2,2,2,now());
	select nextval('CSM_USER_PE_USER_PROTECTIO_SEQ');
	
insert into csm_user (USER_ID, LOGIN_NAME,FIRST_NAME,LAST_NAME,PASSWORD,UPDATE_DATE)
	values (3, 'ccts@nih.gov','CCTS','Demo','7n9/VP+v3XYWD5UHP32oFQ==',now());
	select nextval('CSM_USER_USER_ID_SEQ');

COMMIT;
--  
--  The following entry is for your application. 
--  Replace <<application_context_name>> with your application name.
-- 


--	INSERT INTO CSM_APPLICATION(
--             APPLICATION_NAME, APPLICATION_DESCRIPTION, DECLARATIVE_FLAG, ACTIVE_FLAG,UPDATE_DATE)
--	VALUES ('<<application_context_name>>','Application Description','0','0',now());

--	INSERT INTO CSM_PROTECTION_ELEMENT(
--             PROTECTION_ELEMENT_NAME, PROTECTION_ELEMENT_DESCRIPTION, OBJECT_ID, APPLICATION_ID,UPDATE_DATE)
--    VALUES ( '<<application_context_name>>','<<application_context_name>> Admin Application Protection Element','<<application_context_name>>',1,now());
--COMMIT;
-- 
--  The following entries are Common Set of Privileges
-- 

	INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
	VALUES(1,'CREATE','This privilege grants permission to a user to create an entity. This entity can be an object, a database entry, or a resource such as a network connection', now());
	SELECT nextval('CSM_PRIVILEGE_PRIVILEGE_ID_SEQ');
	
	INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
	VALUES(2,'ACCESS','This privilege allows a user to access a particular resource.  Examples of resources include a network connection, database connection, socket, module of the application, or even the application', now());
	SELECT nextval('CSM_PRIVILEGE_PRIVILEGE_ID_SEQ');
	
	INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
	VALUES(3,'READ','This privilege permits the user to read data from a file, URL, socket, database, or an object. This can be used at an entity level signifying that the user is allowed to read data about a particular', now());
	SELECT nextval('CSM_PRIVILEGE_PRIVILEGE_ID_SEQ');
	
	INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
	VALUES(4,'WRITE','This privilege allows a user to write data to a file, URL, socket, database, or object. This can also be used at an entity level signifying that the user is allowed to write data about a particular', now());
	SELECT nextval('CSM_PRIVILEGE_PRIVILEGE_ID_SEQ');
	
	INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
	VALUES(5,'UPDATE','This privilege grants permission at an entity level and signifies that the user is allowed to update and modify data for a particular entity.  Entities may include an object, an attribute of the', now());
	SELECT nextval('CSM_PRIVILEGE_PRIVILEGE_ID_SEQ');
	
	INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
	VALUES(6,'DELETE','This privilege permits a user to delete a logical entity. This entity can be an object, a database entry, a resource such as a network connection, etc', now());
	SELECT nextval('CSM_PRIVILEGE_PRIVILEGE_ID_SEQ');
	
	INSERT INTO csm_privilege (privilege_id, privilege_name, privilege_description, update_date)
	VALUES(7,'EXECUTE','This privilege allows a user to execute a particular resource. The resource can be a method, function, behavior of the application, URL, button etc', now());
	SELECT nextval('CSM_PRIVILEGE_PRIVILEGE_ID_SEQ');
	

COMMIT;
