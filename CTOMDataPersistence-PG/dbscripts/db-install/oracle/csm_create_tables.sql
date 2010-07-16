
CREATE SEQUENCE CSM_APPLICATI_APPLICATION__SEQ
  START WITH 3
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/


CREATE SEQUENCE CSM_FILTER_CLAUSE_FILTE_ID_SEQ
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/


CREATE SEQUENCE CSM_GROUP_GROUP_ID_SEQ
  START WITH 199
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/



CREATE SEQUENCE CSM_MAPPING_MAPPING_SEQ
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/


CREATE SEQUENCE CSM_PG_PE_PG_PE_ID_SEQ
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/


CREATE SEQUENCE CSM_PRIVILEGE_PRIVILEGE_ID_SEQ
  START WITH 192
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/


CREATE SEQUENCE CSM_PROTECTIO_PROTECTION_E_SEQ
  START WITH 3
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/



CREATE SEQUENCE CSM_PROTECTIO_PROTECTION_G_SEQ
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/



CREATE SEQUENCE CSM_ROLE_PRIV_ROLE_PRIVILE_SEQ
  START WITH 139
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/


CREATE SEQUENCE CSM_ROLE_ROLE_ID_SEQ
  START WITH 166
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/


CREATE SEQUENCE CSM_USER_GROU_USER_GROUP_I_SEQ
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/


CREATE SEQUENCE CSM_USER_GROU_USER_GROUP_R_SEQ
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/


CREATE SEQUENCE CSM_USER_PE_USER_PROTECTIO_SEQ
  START WITH 3
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/


CREATE SEQUENCE CSM_USER_USER_ID_SEQ
  START WITH 2
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/

CREATE TABLE CSM_APPLICATION
(
  APPLICATION_ID           NUMBER(38)           NOT NULL,
  APPLICATION_NAME         VARCHAR2(255 BYTE)   NOT NULL,
  APPLICATION_DESCRIPTION  VARCHAR2(200 BYTE)   NOT NULL,
  DECLARATIVE_FLAG         NUMBER(1)            DEFAULT 0                     NOT NULL,
  ACTIVE_FLAG              NUMBER(1)            DEFAULT 0                     NOT NULL,
  UPDATE_DATE              DATE                 DEFAULT SYSDATE,
  DATABASE_URL             VARCHAR2(100 BYTE),
  DATABASE_USER_NAME       VARCHAR2(100 BYTE),
  DATABASE_PASSWORD        VARCHAR2(100 BYTE),
  DATABASE_DIALECT         VARCHAR2(100 BYTE),
  DATABASE_DRIVER          VARCHAR2(100 BYTE),
  CSM_VERSION              VARCHAR2(20 BYTE)
)
/


CREATE TABLE CSM_FILTER_CLAUSE
(
  FILTER_CLAUSE_ID              NUMBER(38)      NOT NULL,
  CLASS_NAME                    VARCHAR2(100 BYTE) NOT NULL,
  FILTER_CHAIN                  VARCHAR2(2000 BYTE) NOT NULL,
  TARGET_CLASS_NAME             VARCHAR2(100 BYTE) NOT NULL,
  TARGET_CLASS_ATTRIBUTE_NAME   VARCHAR2(100 BYTE) NOT NULL,
  TARGET_CLASS_ATTRIBUTE_TYPE   VARCHAR2(100 BYTE) NOT NULL,
  TARGET_CLASS_ALIAS            VARCHAR2(100 BYTE),
  TARGET_CLASS_ATTRIBUTE_ALIAS  VARCHAR2(100 BYTE),
  GENERATED_SQL_USER            VARCHAR2(4000 BYTE) NOT NULL,
  GENERATED_SQL_GROUP           VARCHAR2(4000 BYTE) NOT NULL,
  APPLICATION_ID                NUMBER(38)      NOT NULL,
  UPDATE_DATE                   DATE            DEFAULT SYSDATE               NOT NULL
)
/

CREATE TABLE CSM_GROUP
(
  GROUP_ID        NUMBER(38)                    NOT NULL,
  GROUP_NAME      VARCHAR2(255 BYTE)            NOT NULL,
  GROUP_DESC      VARCHAR2(200 BYTE),
  UPDATE_DATE     DATE                          DEFAULT SYSDATE               NOT NULL,
  APPLICATION_ID  NUMBER(38)                    NOT NULL
)
/


CREATE TABLE CSM_MAPPING
(
  MAPPING_ID           NUMBER(38)               NOT NULL,
  APPLICATION_ID       NUMBER(38)               NOT NULL,
  OBJECT_NAME          VARCHAR2(100 BYTE)       NOT NULL,
  ATTRIBUTE_NAME       VARCHAR2(100 BYTE)       NOT NULL,
  OBJECT_PACKAGE_NAME  VARCHAR2(100 BYTE),
  TABLE_NAME           VARCHAR2(100 BYTE),
  TABLE_NAME_GROUP     VARCHAR2(100 BYTE),
  TABLE_NAME_USER      VARCHAR2(100 BYTE),
  VIEW_NAME_GROUP      VARCHAR2(100 BYTE),
  VIEW_NAME_USER       VARCHAR2(100 BYTE),
  ACTIVE_FLAG          NUMBER(1)                DEFAULT 0                     NOT NULL,
  MAINTAINED_FLAG      NUMBER(1)                DEFAULT 0                     NOT NULL,
  UPDATE_DATE          DATE                     DEFAULT SYSDATE
)
/


CREATE TABLE CSM_PG_PE
(
  PG_PE_ID               NUMBER(38)             NOT NULL,
  PROTECTION_GROUP_ID    NUMBER(38)             NOT NULL,
  PROTECTION_ELEMENT_ID  NUMBER(38)             NOT NULL,
  UPDATE_DATE            DATE                   DEFAULT SYSDATE
)
/

CREATE TABLE CSM_PRIVILEGE
(
  PRIVILEGE_ID           NUMBER(38)             NOT NULL,
  PRIVILEGE_NAME         VARCHAR2(100 BYTE)     NOT NULL,
  PRIVILEGE_DESCRIPTION  VARCHAR2(200 BYTE),
  UPDATE_DATE            DATE                   DEFAULT SYSDATE               NOT NULL
)
/


CREATE TABLE CSM_PROTECTION_ELEMENT
(
  PROTECTION_ELEMENT_ID           NUMBER(38)    NOT NULL,
  PROTECTION_ELEMENT_NAME         VARCHAR2(100 BYTE) NOT NULL,
  PROTECTION_ELEMENT_DESCRIPTION  VARCHAR2(200 BYTE),
  OBJECT_ID                       VARCHAR2(100 BYTE) NOT NULL,
  ATTRIBUTE                       VARCHAR2(100 BYTE),
  ATTRIBUTE_VALUE                 VARCHAR2(100 BYTE),
  PROTECTION_ELEMENT_TYPE         VARCHAR2(100 BYTE),
  APPLICATION_ID                  NUMBER(38)    NOT NULL,
  UPDATE_DATE                     DATE          DEFAULT SYSDATE               NOT NULL
)
/


CREATE TABLE CSM_PROTECTION_GROUP
(
  PROTECTION_GROUP_ID           NUMBER(38)      NOT NULL,
  PROTECTION_GROUP_NAME         VARCHAR2(100 BYTE) NOT NULL,
  PROTECTION_GROUP_DESCRIPTION  VARCHAR2(200 BYTE),
  APPLICATION_ID                NUMBER(38)      NOT NULL,
  LARGE_ELEMENT_COUNT_FLAG      NUMBER(1)       NOT NULL,
  UPDATE_DATE                   DATE            DEFAULT SYSDATE               NOT NULL,
  PARENT_PROTECTION_GROUP_ID    NUMBER(38)
)
/

CREATE TABLE CSM_ROLE
(
  ROLE_ID           NUMBER(38)                  NOT NULL,
  ROLE_NAME         VARCHAR2(100 BYTE)          NOT NULL,
  ROLE_DESCRIPTION  VARCHAR2(200 BYTE),
  APPLICATION_ID    NUMBER(38)                  NOT NULL,
  ACTIVE_FLAG       NUMBER(1)                   NOT NULL,
  UPDATE_DATE       DATE                        DEFAULT SYSDATE               NOT NULL
)
/

CREATE TABLE CSM_ROLE_PRIVILEGE
(
  ROLE_PRIVILEGE_ID  NUMBER(38)                 NOT NULL,
  ROLE_ID            NUMBER(38)                 NOT NULL,
  PRIVILEGE_ID       NUMBER(38)                 NOT NULL
)
/


CREATE TABLE CSM_USER
(
  USER_ID             NUMBER(38)                NOT NULL,
  LOGIN_NAME          VARCHAR2(500 BYTE)        NOT NULL,
  FIRST_NAME          VARCHAR2(100 BYTE)        NOT NULL,
  MIGRATED_FLAG       NUMBER(1)                 DEFAULT 0                     NOT NULL,
  LAST_NAME           VARCHAR2(100 BYTE)        NOT NULL,
  ORGANIZATION        VARCHAR2(100 BYTE),
  DEPARTMENT          VARCHAR2(100 BYTE),
  TITLE               VARCHAR2(100 BYTE),
  PHONE_NUMBER        VARCHAR2(15 BYTE),
  PASSWORD            VARCHAR2(100 BYTE),
  EMAIL_ID            VARCHAR2(100 BYTE),
  START_DATE          DATE,
  END_DATE            DATE,
  UPDATE_DATE         DATE                      DEFAULT SYSDATE               NOT NULL,
  PREMGRT_LOGIN_NAME  VARCHAR2(100 BYTE)
)
/

CREATE TABLE CSM_USER_GROUP
(
  USER_GROUP_ID  NUMBER(38)                     NOT NULL,
  USER_ID        NUMBER(38)                     NOT NULL,
  GROUP_ID       NUMBER(38)                     NOT NULL
)
/

CREATE TABLE CSM_USER_GROUP_ROLE_PG
(
  USER_GROUP_ROLE_PG_ID  NUMBER(38)             NOT NULL,
  USER_ID                NUMBER(38),
  GROUP_ID               NUMBER(38),
  ROLE_ID                NUMBER(38)             NOT NULL,
  PROTECTION_GROUP_ID    NUMBER(38)             NOT NULL,
  UPDATE_DATE            DATE                   DEFAULT SYSDATE               NOT NULL
)
/

CREATE TABLE CSM_USER_PE
(
  USER_PROTECTION_ELEMENT_ID  NUMBER(38)        NOT NULL,
  PROTECTION_ELEMENT_ID       NUMBER(38)        NOT NULL,
  USER_ID                     NUMBER(38)        NOT NULL
)
/


CREATE UNIQUE INDEX UQ_APPLICATION_NAME ON CSM_APPLICATION
(APPLICATION_NAME)
/


CREATE UNIQUE INDEX PK_APPLICATION ON CSM_APPLICATION
(APPLICATION_ID)
/


CREATE UNIQUE INDEX PK_FILTER_CLAUSE ON CSM_FILTER_CLAUSE
(FILTER_CLAUSE_ID)
/


CREATE UNIQUE INDEX UQ_GROUP_GROUP_NAME ON CSM_GROUP
(APPLICATION_ID, GROUP_NAME)
/


CREATE INDEX IDX_APPLICATION_ID ON CSM_GROUP
(APPLICATION_ID)
/


CREATE UNIQUE INDEX PK_GROUP ON CSM_GROUP
(GROUP_ID)
/


CREATE UNIQUE INDEX UQ_MP_OBJ_NAM_ATTRI_NAM_APP_ID ON CSM_MAPPING
(OBJECT_NAME, ATTRIBUTE_NAME, APPLICATION_ID)
/


CREATE UNIQUE INDEX UQ_PG_PE_PG_PE_ID ON CSM_PG_PE
(PROTECTION_ELEMENT_ID, PROTECTION_GROUP_ID)
/


CREATE INDEX IDX_PGPE_PE_ID ON CSM_PG_PE
(PROTECTION_ELEMENT_ID)
/


CREATE INDEX IDX_PGPE_PG_ID ON CSM_PG_PE
(PROTECTION_GROUP_ID)
/


CREATE UNIQUE INDEX PK_PG_PE ON CSM_PG_PE
(PG_PE_ID)
/


CREATE UNIQUE INDEX UQ_PRIVILEGE_NAME ON CSM_PRIVILEGE
(PRIVILEGE_NAME)
/


CREATE UNIQUE INDEX PK_PRIVILEGE ON CSM_PRIVILEGE
(PRIVILEGE_ID)
/


CREATE UNIQUE INDEX UQ_PE_OBJ_ATT_APP_ID ON CSM_PROTECTION_ELEMENT
(OBJECT_ID, ATTRIBUTE, APPLICATION_ID)
/


CREATE INDEX IDX_CSM_PE_APPLICATION_ID ON CSM_PROTECTION_ELEMENT
(APPLICATION_ID)
/


CREATE UNIQUE INDEX UQ_PE_OBJ_ATTR_ATTR_VAL_APP_ID ON CSM_PROTECTION_ELEMENT
(OBJECT_ID, ATTRIBUTE, ATTRIBUTE_VALUE, APPLICATION_ID)
/


CREATE UNIQUE INDEX PK_PROTECTION_ELEMENT ON CSM_PROTECTION_ELEMENT
(PROTECTION_ELEMENT_ID)
/


CREATE UNIQUE INDEX UQ_PG_PG_NAME ON CSM_PROTECTION_GROUP
(APPLICATION_ID, PROTECTION_GROUP_NAME)
/


CREATE INDEX IDX_PG_APP_ID ON CSM_PROTECTION_GROUP
(APPLICATION_ID)
/


CREATE INDEX IDX_PG_PARNT_PG_ID ON CSM_PROTECTION_GROUP
(PARENT_PROTECTION_GROUP_ID)
/


CREATE UNIQUE INDEX PK_PROTECTION_GROUP ON CSM_PROTECTION_GROUP
(PROTECTION_GROUP_ID)
/


CREATE UNIQUE INDEX UQ_ROLE_ROLE_NAME ON CSM_ROLE
(APPLICATION_ID, ROLE_NAME)
/


CREATE INDEX IDX_R_APP_ID ON CSM_ROLE
(APPLICATION_ID)
/


CREATE UNIQUE INDEX PK_ROLE ON CSM_ROLE
(ROLE_ID)
/


CREATE UNIQUE INDEX PK_ROLE_PRIVILEGE ON CSM_ROLE_PRIVILEGE
(ROLE_PRIVILEGE_ID)
/


CREATE UNIQUE INDEX UQ_ROLE_ID_PRIVILEGE_ID ON CSM_ROLE_PRIVILEGE
(PRIVILEGE_ID, ROLE_ID)
/


CREATE INDEX IDX_RP_PRIV_ID ON CSM_ROLE_PRIVILEGE
(PRIVILEGE_ID)
/


CREATE INDEX IDX_RP_ROLE_ID ON CSM_ROLE_PRIVILEGE
(ROLE_ID)
/


CREATE UNIQUE INDEX PK_USER ON CSM_USER
(USER_ID)
/


CREATE UNIQUE INDEX UQ_LOGIN_NAME ON CSM_USER
(LOGIN_NAME)
/


CREATE UNIQUE INDEX PK_USER_GROUP ON CSM_USER_GROUP
(USER_GROUP_ID)
/


CREATE INDEX IDX_UG_USER_ID ON CSM_USER_GROUP
(USER_ID)
/


CREATE INDEX IDX_UG_GROUP_ID ON CSM_USER_GROUP
(GROUP_ID)
/


CREATE UNIQUE INDEX PK_USER_GROUP_ROLE_PG ON CSM_USER_GROUP_ROLE_PG
(USER_GROUP_ROLE_PG_ID)
/


CREATE INDEX IDX_UGRPG_GROUP_ID ON CSM_USER_GROUP_ROLE_PG
(GROUP_ID)
/


CREATE INDEX IDX_UGRPG_ROLE_ID ON CSM_USER_GROUP_ROLE_PG
(ROLE_ID)
/


CREATE INDEX IDX_UGRPG_PG_ID ON CSM_USER_GROUP_ROLE_PG
(PROTECTION_GROUP_ID)
/


CREATE INDEX IDX_UGRPG_USER_ID ON CSM_USER_GROUP_ROLE_PG
(USER_ID)
/


CREATE UNIQUE INDEX PK_USER_PROTECTION_ELEMENT ON CSM_USER_PE
(USER_PROTECTION_ELEMENT_ID)
/


CREATE UNIQUE INDEX UQ_USER_PE_PE_ID ON CSM_USER_PE
(USER_ID, PROTECTION_ELEMENT_ID)
/


CREATE INDEX IDX_UPE_USER_ID ON CSM_USER_PE
(USER_ID)
/


CREATE INDEX IDX_UPE_PE_ID ON CSM_USER_PE
(PROTECTION_ELEMENT_ID)
/


CREATE OR REPLACE TRIGGER SET_CSM_USER_GROU_USER_GROUP_R
BEFORE INSERT
ON CSM_USER_GROUP_ROLE_PG
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN

   SELECT CSM_USER_GROU_USER_GROUP_R_SEQ.NEXTVAL
     INTO :NEW.USER_GROUP_ROLE_PG_ID
	 FROM dual;

END ;
/




CREATE OR REPLACE TRIGGER SET_CSM_USER_USER_ID
BEFORE INSERT
ON CSM_USER
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN

   SELECT CSM_USER_USER_ID_SEQ.NEXTVAL
     INTO :NEW.USER_ID
	 FROM dual;

END ;
/




CREATE OR REPLACE TRIGGER SET_CSM_PG_PE_PG_PE_ID
BEFORE INSERT
ON CSM_PG_PE
FOR EACH ROW
BEGIN
  SELECT CSM_PG_PE_PG_PE_ID_SEQ.NEXTVAL
  INTO :NEW.PG_PE_ID
  FROM DUAL;
END;
/




CREATE OR REPLACE TRIGGER SET_CSM_PG_PE_UPDATE_DATE
BEFORE UPDATE
ON CSM_PG_PE
FOR EACH ROW
BEGIN
  SELECT SYSDATE
  INTO :NEW.UPDATE_DATE
  FROM DUAL;
END;
/




CREATE OR REPLACE TRIGGER SET_CSM_ROLE_PRIV_ROLE_PRIVILE
BEFORE INSERT
ON CSM_ROLE_PRIVILEGE
FOR EACH ROW
BEGIN
  SELECT CSM_ROLE_PRIV_ROLE_PRIVILE_SEQ.NEXTVAL
  INTO :NEW.ROLE_PRIVILEGE_ID
  FROM DUAL;
END;
/




CREATE OR REPLACE TRIGGER SET_CSM_USER_GROU_USER_GROUP_I
BEFORE INSERT
ON CSM_USER_GROUP
FOR EACH ROW
BEGIN
  SELECT CSM_USER_GROU_USER_GROUP_I_SEQ.NEXTVAL
  INTO :NEW.USER_GROUP_ID
  FROM DUAL;
END;
/




CREATE OR REPLACE TRIGGER SET_CSM_USER_PE_USER_PROTECTIO
BEFORE INSERT
ON CSM_USER_PE
FOR EACH ROW
BEGIN
  SELECT CSM_USER_PE_USER_PROTECTIO_SEQ.NEXTVAL
  INTO :NEW.USER_PROTECTION_ELEMENT_ID
  FROM DUAL;
END;
/




CREATE OR REPLACE TRIGGER SET_CSM_APPLICATI_APPLICATION
BEFORE INSERT
ON CSM_APPLICATION
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
BEGIN

   SELECT CSM_APPLICATI_APPLICATION__SEQ.NEXTVAL
     INTO :NEW.APPLICATION_ID
	 FROM dual;

END ;
/




CREATE OR REPLACE TRIGGER SET_CSM_MAPPING_MAPPING_ID
BEFORE INSERT
ON CSM_MAPPING
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN

   SELECT CSM_MAPPING_MAPPING_SEQ.NEXTVAL
     INTO :NEW.MAPPING_ID
	 FROM dual;

END ;
/




CREATE OR REPLACE TRIGGER SET_CSM_GROUP_GROUP_ID
BEFORE INSERT
ON CSM_GROUP
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN

   SELECT CSM_GROUP_GROUP_ID_SEQ.NEXTVAL
     INTO :NEW.GROUP_ID
	 FROM dual;

END ;
/




CREATE OR REPLACE TRIGGER SET_CSM_PRIVILEGE_PRIVILEGE_ID
BEFORE INSERT
ON CSM_PRIVILEGE
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN

   SELECT CSM_PRIVILEGE_PRIVILEGE_ID_SEQ.NEXTVAL
     INTO :NEW.PRIVILEGE_ID
	 FROM dual;

END;
/




CREATE OR REPLACE TRIGGER SET_CSM_FILTER_CLAUSE_FILTE_ID
BEFORE INSERT
ON CSM_FILTER_CLAUSE
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN

   SELECT CSM_FILTER_CLAUSE_FILTE_ID_SEQ.NEXTVAL
     INTO :NEW.FILTER_CLAUSE_ID
	 FROM dual;

END ;
/




CREATE OR REPLACE TRIGGER SET_CSM_PROTECTIO_PROTECTION_E
BEFORE INSERT
ON CSM_PROTECTION_ELEMENT
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN

   SELECT CSM_PROTECTIO_PROTECTION_E_SEQ.NEXTVAL
     INTO :NEW.PROTECTION_ELEMENT_ID
	 FROM dual;

END ;
/




CREATE OR REPLACE TRIGGER SET_CSM_PROTECTIO_PROTECTION_G
BEFORE INSERT
ON CSM_PROTECTION_GROUP
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN

   SELECT CSM_PROTECTIO_PROTECTION_G_SEQ.NEXTVAL
     INTO :NEW.PROTECTION_GROUP_ID
	 FROM dual;

END ;
/




CREATE OR REPLACE TRIGGER SET_CSM_ROLE_ROLE_ID
BEFORE INSERT
ON CSM_ROLE
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN

   SELECT CSM_ROLE_ROLE_ID_SEQ.NEXTVAL
     INTO :NEW.ROLE_ID
	 FROM dual;

END ;
/




ALTER TABLE CSM_APPLICATION ADD (
  CONSTRAINT PK_APPLICATION
 PRIMARY KEY
 (APPLICATION_ID))
/

ALTER TABLE CSM_APPLICATION ADD (
  CONSTRAINT UQ_APPLICATION_NAME
 UNIQUE (APPLICATION_NAME))
/


ALTER TABLE CSM_FILTER_CLAUSE ADD (
  CONSTRAINT PK_FILTER_CLAUSE
 PRIMARY KEY
 (FILTER_CLAUSE_ID))
/


ALTER TABLE CSM_GROUP ADD (
  CONSTRAINT PK_GROUP
 PRIMARY KEY
 (GROUP_ID))
/

ALTER TABLE CSM_GROUP ADD (
  CONSTRAINT UQ_GROUP_GROUP_NAME
 UNIQUE (APPLICATION_ID, GROUP_NAME))
/


ALTER TABLE CSM_MAPPING ADD (
  PRIMARY KEY
 (MAPPING_ID))
/

ALTER TABLE CSM_MAPPING ADD (
  CONSTRAINT UQ_MP_OBJ_NAM_ATTRI_NAM_APP_ID
 UNIQUE (OBJECT_NAME, ATTRIBUTE_NAME, APPLICATION_ID))
/


ALTER TABLE CSM_PG_PE ADD (
  CONSTRAINT PK_PG_PE
 PRIMARY KEY
 (PG_PE_ID))
/

ALTER TABLE CSM_PG_PE ADD (
  CONSTRAINT UQ_PG_PE_PG_PE_ID
 UNIQUE (PROTECTION_ELEMENT_ID, PROTECTION_GROUP_ID))
/


ALTER TABLE CSM_PRIVILEGE ADD (
  CONSTRAINT PK_PRIVILEGE
 PRIMARY KEY
 (PRIVILEGE_ID))
/

ALTER TABLE CSM_PRIVILEGE ADD (
  CONSTRAINT UQ_PRIVILEGE_NAME
 UNIQUE (PRIVILEGE_NAME))
/


ALTER TABLE CSM_PROTECTION_ELEMENT ADD (
  CONSTRAINT PK_PROTECTION_ELEMENT
 PRIMARY KEY
 (PROTECTION_ELEMENT_ID))
/

ALTER TABLE CSM_PROTECTION_ELEMENT ADD (
  CONSTRAINT UQ_PE_OBJ_ATT_APP_ID
 UNIQUE (OBJECT_ID, ATTRIBUTE, APPLICATION_ID))
/


ALTER TABLE CSM_PROTECTION_GROUP ADD (
  CONSTRAINT PK_PROTECTION_GROUP
 PRIMARY KEY
 (PROTECTION_GROUP_ID))
/

ALTER TABLE CSM_PROTECTION_GROUP ADD (
  CONSTRAINT UQ_PG_PG_NAME
 UNIQUE (APPLICATION_ID, PROTECTION_GROUP_NAME))
/


ALTER TABLE CSM_ROLE ADD (
  CONSTRAINT PK_ROLE
 PRIMARY KEY
 (ROLE_ID))
/

ALTER TABLE CSM_ROLE ADD (
  CONSTRAINT UQ_ROLE_ROLE_NAME
 UNIQUE (APPLICATION_ID, ROLE_NAME))
/


ALTER TABLE CSM_ROLE_PRIVILEGE ADD (
  CONSTRAINT PK_ROLE_PRIVILEGE
 PRIMARY KEY
 (ROLE_PRIVILEGE_ID))
/

ALTER TABLE CSM_ROLE_PRIVILEGE ADD (
  CONSTRAINT UQ_ROLE_ID_PRIVILEGE_ID
 UNIQUE (PRIVILEGE_ID, ROLE_ID))
/


ALTER TABLE CSM_USER ADD (
  CONSTRAINT PK_USER
 PRIMARY KEY
 (USER_ID))
/

ALTER TABLE CSM_USER ADD (
  CONSTRAINT UQ_LOGIN_NAME
 UNIQUE (LOGIN_NAME))
/


ALTER TABLE CSM_USER_GROUP ADD (
  CONSTRAINT PK_USER_GROUP
 PRIMARY KEY
 (USER_GROUP_ID))
/


ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD (
  CONSTRAINT PK_USER_GROUP_ROLE_PG
 PRIMARY KEY
 (USER_GROUP_ROLE_PG_ID))
/


ALTER TABLE CSM_USER_PE ADD (
  CONSTRAINT PK_USER_PROTECTION_ELEMENT
 PRIMARY KEY
 (USER_PROTECTION_ELEMENT_ID))
/

ALTER TABLE CSM_USER_PE ADD (
  CONSTRAINT UQ_USER_PE_PE_ID
 UNIQUE (USER_ID, PROTECTION_ELEMENT_ID))
/


ALTER TABLE CSM_FILTER_CLAUSE ADD (
  CONSTRAINT FK_CSM_FC_CSM_APPLICATION
 FOREIGN KEY (APPLICATION_ID)
 REFERENCES CSM_APPLICATION (APPLICATION_ID)
    ON DELETE CASCADE)
/


ALTER TABLE CSM_GROUP ADD (
  CONSTRAINT FK_CSM_GROUP_CSM_APPLICATION
 FOREIGN KEY (APPLICATION_ID)
 REFERENCES CSM_APPLICATION (APPLICATION_ID)
    ON DELETE CASCADE)
/


ALTER TABLE CSM_MAPPING ADD (
  FOREIGN KEY (APPLICATION_ID)
 REFERENCES CSM_APPLICATION (APPLICATION_ID)
    ON DELETE CASCADE)
/


ALTER TABLE CSM_PG_PE ADD (
  CONSTRAINT FK_PG_PE_PE
 FOREIGN KEY (PROTECTION_ELEMENT_ID)
 REFERENCES CSM_PROTECTION_ELEMENT (PROTECTION_ELEMENT_ID)
    ON DELETE CASCADE)
/

ALTER TABLE CSM_PG_PE ADD (
  CONSTRAINT FK_PG_PE_PG
 FOREIGN KEY (PROTECTION_GROUP_ID)
 REFERENCES CSM_PROTECTION_GROUP (PROTECTION_GROUP_ID)
    ON DELETE CASCADE)
/


ALTER TABLE CSM_PROTECTION_ELEMENT ADD (
  CONSTRAINT FK_PE_APPLICATION
 FOREIGN KEY (APPLICATION_ID)
 REFERENCES CSM_APPLICATION (APPLICATION_ID)
    ON DELETE CASCADE)
/


ALTER TABLE CSM_PROTECTION_GROUP ADD (
  CONSTRAINT FK_PG_APPLICATION
 FOREIGN KEY (APPLICATION_ID)
 REFERENCES CSM_APPLICATION (APPLICATION_ID)
    ON DELETE CASCADE)
/

ALTER TABLE CSM_PROTECTION_GROUP ADD (
  CONSTRAINT FK_PG_PG
 FOREIGN KEY (PARENT_PROTECTION_GROUP_ID)
 REFERENCES CSM_PROTECTION_GROUP (PROTECTION_GROUP_ID))
/


ALTER TABLE CSM_ROLE ADD (
  CONSTRAINT FK_ROLE_APPLICATION
 FOREIGN KEY (APPLICATION_ID)
 REFERENCES CSM_APPLICATION (APPLICATION_ID)
    ON DELETE CASCADE)
/


ALTER TABLE CSM_ROLE_PRIVILEGE ADD (
  CONSTRAINT FK_ROLE_PRIVILEGE_PRIVILEGE
 FOREIGN KEY (PRIVILEGE_ID)
 REFERENCES CSM_PRIVILEGE (PRIVILEGE_ID)
    ON DELETE CASCADE)
/

ALTER TABLE CSM_ROLE_PRIVILEGE ADD (
  CONSTRAINT FK_ROLE_PRIVILEGE_ROLE
 FOREIGN KEY (ROLE_ID)
 REFERENCES CSM_ROLE (ROLE_ID)
    ON DELETE CASCADE)
/


ALTER TABLE CSM_USER_GROUP ADD (
  CONSTRAINT FK_USER_GROUP_GROUP
 FOREIGN KEY (GROUP_ID)
 REFERENCES CSM_GROUP (GROUP_ID)
    ON DELETE CASCADE)
/

ALTER TABLE CSM_USER_GROUP ADD (
  CONSTRAINT FK_USER_GROUP_USER
 FOREIGN KEY (USER_ID)
 REFERENCES CSM_USER (USER_ID)
    ON DELETE CASCADE)
/


ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD (
  CONSTRAINT FK_USER_GROUP_ROLE_PG_GROUP
 FOREIGN KEY (GROUP_ID)
 REFERENCES CSM_GROUP (GROUP_ID)
    ON DELETE CASCADE)
/

ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD (
  CONSTRAINT FK_USER_GROUP_ROLE_PG_PG
 FOREIGN KEY (PROTECTION_GROUP_ID)
 REFERENCES CSM_PROTECTION_GROUP (PROTECTION_GROUP_ID)
    ON DELETE CASCADE)
/

ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD (
  CONSTRAINT FK_USER_GROUP_ROLE_PG_ROLE
 FOREIGN KEY (ROLE_ID)
 REFERENCES CSM_ROLE (ROLE_ID)
    ON DELETE CASCADE)
/

ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD (
  CONSTRAINT FK_USER_GROUP_ROLE_PG_USER
 FOREIGN KEY (USER_ID)
 REFERENCES CSM_USER (USER_ID)
    ON DELETE CASCADE)
/


ALTER TABLE CSM_USER_PE ADD (
  CONSTRAINT FK_USER_PE_PE
 FOREIGN KEY (PROTECTION_ELEMENT_ID)
 REFERENCES CSM_PROTECTION_ELEMENT (PROTECTION_ELEMENT_ID)
    ON DELETE CASCADE)
/

ALTER TABLE CSM_USER_PE ADD (
  CONSTRAINT FK_USER_PE_USER
 FOREIGN KEY (USER_ID)
 REFERENCES CSM_USER (USER_ID)
    ON DELETE CASCADE)
/


