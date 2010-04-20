CREATE USER ctodslabviewer_dev IDENTIFIED BY j83oihsa DEFAULT TABLESPACE USERS;
GRANT CREATE SESSION TO ctodslabviewer_dev;
GRANT CREATE TABLE TO ctodslabviewer_dev;
GRANT CREATE VIEW TO ctodslabviewer_dev;
GRANT CREATE SEQUENCE TO ctodslabviewer_dev; 
GRANT CREATE SYNONYM TO ctodslabviewer_dev;
GRANT CREATE TRIGGER TO ctodslabviewer_dev;
ALTER USER ctodslabviewer_dev QUOTA unlimited ON USERS;
exit


