Prerequisites:
4)MySql Tomcat and caGrid 1.1 are already insatlled in the system

2)Please set following environment variables to use servicemix
JAVA_HOME(JDK installation path)
CAX_HOME(Home folder for the downlaoded caxchange distribution form the Gforge)
CATALINA_HOME(Your tomcat home directory)
ANT_HOME(Ant installed directory)
GLOBUS_LOCATION(Globus installed directory)



3)Please replce the <<REPLACE_THIS_VALUE>> in install.properties files with the values 
specific to your environment


4)We assume that database schema is already created for the caxchange and LLT
with required credentials


Notes:

1)Kill the servicemix process using CTRL+C to stop servicemix 
and kill java.exe process to restart it in windows task manager

2)The ant script is only tested in windows system



