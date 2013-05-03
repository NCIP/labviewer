Welcome to the LabViewer Project!
=====================================

LabViewer is a web application used to view and store clinical laboratory data. It is distributed under the BSD 3-Clause License. 

Please see the NOTICE and LICENSE files for details.

You will find more details about LabViewer in the following links:

 * [Community wiki](https://wiki.nci.nih.gov/display/LabViewer/caBIG+Lab+Viewer)
 * [Forums](https://cabig-kc.nci.nih.gov/CTMS/forums/viewforum.php?f=13&start=0&sid=5b6f012d9a22fded386e767a6d2ccdad)
 * [Issue tracker](https://tracker.nci.nih.gov/browse/CTMSLV)
 * [Documentation wiki](https://wiki.nci.nih.gov/display/LabViewer/caBIG+Lab+Viewer+Documentation)
 * [Documentation Git repo](https://github.com/NCIP/labviewer-docs)
 * [Development Git repo](https://github.com/NCIP/labviewer)


Please join us in further developing and improving LabViewer.

# Prerequisites
 * [Requiremements Matrix] (https://wiki.nci.nih.gov/display/LabViewer/caBIG+Lab+Viewer+2010+System+Requirement+Matrix)
 * JDK 1.5.0_16
 * PostgreSQL 8.2.x
 * Apache Tomcat-5.5.x  (SSL enabled)

# Installation
 * First setup database for LabViewer.
 * Modify labviewer-dev1-install.properties to have values for the new environment.
 * From build folder run "ant -Dproperties.file=labviewer-dev1-install.properties dist:installer"
 * From target/dist/exploded folder run "ant -Dtomcat.home=<user_home>/<tomcat_folder> install:tomcat:LabViewer
