
This is the skeleton Registration consumer grid service intended for CCTS
applications that will comsumer C3PR Registration messages. CCTS applications
need to provide an implementation for

gov.nih.nci.ccts.grid.common.RegistrationConsumer interface

The implementation class is injected at runtime using Spring.
Please see the c3pr sample implementation for details

http://gforge.nci.nih.gov/plugins/scmcvs/cvsweb.php/c3prv2/codebase/projects/grid/RegistrationConsumerImpl/?cvsroot=c3prv2


Prerequisits:
=======================================
1.Java 1.5 and JAVA_HOME env defined
2. Ant 1.6.5 and ANT_HOME env defined
3. Tomcat 5.0.28 installed and "CATALINA_HOME" env defined with globus deployed to it
4. Globus 4.0.3 installed and GLOBUS_LOCATION env defined
5. Globus 4.0.3 is deployed in Tomcat at the following location ${CATALINA_HOME}/webapps/wsrf
Note: The globus webapp can be named something besides wsrf(default). See below for details


To Build:
=======================================
"ant all" will build 
"ant deployGlobus" will deploy to "GLOBUS_LOCATION"
"ant deployTomcat" will deploy to "CATALINA_HOME"

"ant -f build-ctms.xml deploySkeletonService" will build the skeleton grid
service in the build/RegistrationConsumer_Grid_Service.zip.

You can unzip the RegistrationConsumer_Grid_Service.zip file in the
${CATALINA_HOME} directory.

CCTS Application teams will usually do the following
1. ant all
2. ant deployTomcat
3. Copy their implementation jar to ${CATALINA_HOME}/webapps/wsrf/WEB-INF/lib

Note: If your GLOBUS webapp is named something besides wsrf (default). You can
call the ant deployTomcat target as follows

ant deployTomcat -Dwebapp.name=<globus_webapp_name>