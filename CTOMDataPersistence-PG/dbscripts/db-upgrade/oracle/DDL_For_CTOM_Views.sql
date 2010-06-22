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


SPOOL OFF

SET ECHO OFF

