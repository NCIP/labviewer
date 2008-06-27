Prerequisites:
1)MySql 5.0.27 Tomcat 5.0.28  and caGrid 1.1 are already insatlled in the system

2)Please set following environment variables to use servicemix
JAVA_HOME(JDK installation path)
CATALINA_HOME(Your tomcat home directory)


3)Please replce the <<REPLACE_THIS_VALUE>> in install.properties files with the values 
specific to your environment


4)We assume that database schema is already created for the caxchange and LLT and activemq
with required credentials

5)Copy ant paste the servicemix.xml  context information from the conf/tomcat folder in caxchange
distribution to the appropriate place in the tomcat used. 

6)Copy ant paste the login.conf  datasource information from the conf/tomcat folder in caxchange
distribution to the appropriate place in the tomcat used. 

7)Copy ant paste the catalina.properties  additional line  information from the conf/tomcat folder in caxchange
distribution to the appropriate place in the tomcat used. 



Notes:

1)Kill the servicemix process using CTRL+C to stop servicemix 
and kill java.exe process to restart it in windows task manager

2)The ant script is only tested in windows system

3)Please refer caxchange-installation guide for details

4)Reset the caxchange service assemblies by deleting the data folder in the servicemix if 
all the components are not started

5)Delete tomcat work folder to clean of the cache settings 

6)Copy the certificates folder from .globus in dev to your user .globus folder

7)Give the location of your local certs and key in the install.properties file


Useful commands:
1)mvn -Denv=local -Dmaven.test.skip=true clean install assembly:directory
2)ant clean all test -Dcaxchange.url=http://localhost:8080/wsrf/services/cagrid/CaXchangeRequestProcessor



