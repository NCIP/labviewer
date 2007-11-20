Introduce Generated Service Skeleton:
======================================
This is an Introduce generated service.  

All that is needed for this service at this point is to populate the 
service side implemntation in the <service package>.service.<service name>Impl.java

Prerequisits:
=======================================
Java 1.5 and JAVA_HOME env defined
Ant 1.6.5 and ANT_HOME env defined
Globus 4.0.3 installed and GLOBUS_LOCATION env defined
(optional)Tomcat 5.0.28 installed and "CATALINA_HOME" env defined with globus deployed to it
Note: The globus webapp can be named something besides wsrf(default). See below for details


To Build:
=======================================
"ant all" will build
"ant deployGlobus" will deploy to "GLOBUS_LOCATION"
"ant deployTomcat" will deploy to "CATALINA_HOME"

"ant -f build-ctms.xml deploySkeletonService" will build the skeleton grid
service in the build/StudyConsumer_Grid_Service.zip.

You can unzip the StudyConsumer_Grid_Service.zip file in the
${CATALINA_HOME} directory.

CCTS Application teams will usually do the following
1. ant all
2. ant deployTomcat
3. Copy their implementation jar to ${CATALINA_HOME}/webapps/wsrf/WEB-INF/lib

Note: If your GLOBUS webapp is named something besides wsrf (default). You can
call the ant deployTomcat target as follows

ant deployTomcat -Dwebapp.name=<globus_webapp_name>