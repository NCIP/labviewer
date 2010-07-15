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
FROM   performing_laboratory p
UNION
SELECT h.id                  id,
       h.nci_institute_code  identifier,
       h.name                name
FROM   healthcare_site h;
