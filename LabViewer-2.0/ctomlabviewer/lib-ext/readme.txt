this is a classpath issue that exists with the caGrid 1.0 components. The solution is to make 
sure that code from the following jars are loaded from only one location within the JVM.  If deploying
to JBoss, these jars should be put in the server lib and not in your .war or .ear.  These jars
cannot exist in the lib of any other application that is deployed to the applications server either.

 cog-jglobus.jar
 cryptix*
 jce-jdk13-125.jar
 jgss.jar
 puretls.jar
