# 
# The following entries creates a super admin application incase you decide 
# to use this database to run UPT also. In that case you need to provide
# the project login id and name for the super admin.
# However in incase you are using this database just to host the application's
# authorization schema, these enteries are not used and hence they can be left as 
# it is.
#

SET FOREIGN_KEY_CHECKS=0;

USE ccts_csm;

insert into csm_application(APPLICATION_ID, APPLICATION_NAME,APPLICATION_DESCRIPTION,DECLARATIVE_FLAG,ACTIVE_FLAG,UPDATE_DATE)
values (1, "csmupt","CSM UPT Super Admin Application",0,0,sysdate());

insert into csm_application(APPLICATION_ID, APPLICATION_NAME,APPLICATION_DESCRIPTION,DECLARATIVE_FLAG,ACTIVE_FLAG,UPDATE_DATE)
values (2, "caxchange","Application Description",0,0,sysdate());

insert into csm_application(APPLICATION_ID, APPLICATION_NAME,APPLICATION_DESCRIPTION,DECLARATIVE_FLAG,ACTIVE_FLAG,UPDATE_DATE)
values (3, "CLM","CLM",0,0,sysdate());

insert into csm_user (USER_ID, LOGIN_NAME,FIRST_NAME,LAST_NAME,PASSWORD,UPDATE_DATE)
values (1, "cctsbunduser","first","last","zJPWCwDeSgG8j2uyHEABIQ==",sysdate());


insert into csm_protection_element(PROTECTION_ELEMENT_NAME,PROTECTION_ELEMENT_DESCRIPTION,OBJECT_ID,APPLICATION_ID,UPDATE_DATE)
values ("csmupt","CSM UPT Super Admin Application Protection Element","csmupt",1,sysdate());

insert into csm_protection_element(PROTECTION_ELEMENT_NAME,PROTECTION_ELEMENT_DESCRIPTION,OBJECT_ID,APPLICATION_ID,UPDATE_DATE)
values ("caxchange","caxchange Admin Application Protection Element","caxchange",1,sysdate());

insert into csm_protection_element(PROTECTION_ELEMENT_NAME,PROTECTION_ELEMENT_DESCRIPTION,OBJECT_ID,APPLICATION_ID,UPDATE_DATE)
values ("CLM","CLM Admin Application Protection Element","CLM",1,sysdate());

insert into csm_protection_element(PROTECTION_ELEMENT_NAME,PROTECTION_ELEMENT_DESCRIPTION,OBJECT_ID,APPLICATION_ID,UPDATE_DATE)
values ('APPLICATION_NAME:caxchange','APPLICATION_NAME:caxchange','APPLICATION_NAME:caxchange',3,sysdate());


insert into csm_user_pe(USER_PROTECTION_ELEMENT_ID,PROTECTION_ELEMENT_ID,USER_ID,UPDATE_DATE) values (1,1,1, sysdate());
insert into csm_user_pe(USER_PROTECTION_ELEMENT_ID,PROTECTION_ELEMENT_ID,USER_ID,UPDATE_DATE) values (3,2,1, sysdate());
insert into csm_user_pe(USER_PROTECTION_ELEMENT_ID,PROTECTION_ELEMENT_ID,USER_ID,UPDATE_DATE) values (4,3,1, sysdate());


insert into csm_protection_group(PROTECTION_GROUP_ID,PROTECTION_GROUP_NAME, APPLICATION_ID,LARGE_ELEMENT_COUNT_FLAG,UPDATE_DATE)
values (1,'Application caxchange',3,0, sysdate());



insert into csm_pg_pe(PG_PE_ID,PROTECTION_GROUP_ID,PROTECTION_ELEMENT_ID,UPDATE_DATE) values (1,1,4, sysdate());

insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,USER_ID,GROUP_ID,ROLE_ID,PROTECTION_GROUP_ID,UPDATE_DATE) 
values (1,1,null,1,1,sysdate());




#
# The following entries are Common Set of Privileges
#

INSERT INTO csm_privilege (privilege_name, privilege_description, update_date)
VALUES("CREATE","This privilege grants permission to a user to create an entity. This entity can be an object, a database entry, or a resource such as a network connection", sysdate());

INSERT INTO csm_privilege (privilege_name, privilege_description, update_date)
VALUES("ACCESS","This privilege allows a user to access a particular resource.  Examples of resources include a network or database connection, socket, module of the application, or even the application itself", sysdate());

INSERT INTO csm_privilege (privilege_name, privilege_description, update_date)
VALUES("READ","This privilege permits the user to read data from a file, URL, database, an object, etc. This can be used at an entity level signifying that the user is allowed to read data about a particular entry", sysdate());

INSERT INTO csm_privilege (privilege_name, privilege_description, update_date)
VALUES("WRITE","This privilege allows a user to write data to a file, URL, database, an object, etc. This can be used at an entity level signifying that the user is allowed to write data about a particular entity", sysdate());

INSERT INTO csm_privilege (privilege_name, privilege_description, update_date)
VALUES("UPDATE","This privilege grants permission at an entity level and signifies that the user is allowed to update data for a particular entity. Entities may include an object, object attribute, database row etc", sysdate());

INSERT INTO csm_privilege (privilege_name, privilege_description, update_date)
VALUES("DELETE","This privilege permits a user to delete a logical entity. This entity can be an object, a database entry, a resource such as a network connection, etc", sysdate());

INSERT INTO csm_privilege (privilege_name, privilege_description, update_date)
VALUES("EXECUTE","This privilege allows a user to execute a particular resource. The resource can be a method, function, behavior of the application, URL, button etc", sysdate());





insert into csm_role(ROLE_ID,ROLE_NAME,ROLE_DESCRIPTION,APPLICATION_ID,ACTIVE_FLAG,UPDATE_DATE) 
values (1,'READ Role','',3,1,sysdate());

insert into csm_role_privilege(ROLE_PRIVILEGE_ID, ROLE_ID, PRIVILEGE_ID, UPDATE_DATE)
values (1,1,3, sysdate());



COMMIT;