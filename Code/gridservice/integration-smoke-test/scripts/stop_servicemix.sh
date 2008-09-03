#!/bin/bash
# -----------------------------------------------------------------------------
# Stop the servicemix server
#
# -----------------------------------------------------------------------------

# User specific environment and startup programs

SERVICEMIX_HOME=@SERVICEMIX_HOME@
PATH=$PATH:$SERVICEMIX_HOME/bin:$HOME/bin

export PATH SERVICEMIX_HOME
SERVICEMIX_COUNT=`ps -ef|grep servicemix|grep org.codehaus.classworlds.Launcher|wc -l`
if [ $SERVICEMIX_COUNT -gt 0 ]
then
   SERVICEMIX_PID=`ps -ef|grep servicemix|grep org.codehaus.classworlds.Launcher|cut -c8-15`
   echo "Shutting down servicemix :$SERVICEMIX_PID"
   kill -9 $SERVICEMIX_PID
   exit $?
else
   echo "Servicemix is not running."
fi
exit 0
