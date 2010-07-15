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