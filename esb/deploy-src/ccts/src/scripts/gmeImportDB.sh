#!/bin/sh

#
# Import gme table data into a gme database on another server
#



GME_DATABASE_PREFIX=@GME_DATABASE_PREFIX@
GME_DATABASE_USER=@GME_DATABASE_USER@
GME_DATABASE_PASSWORD=@GME_DATABASE_PASSWORD@
GME_DATABASE_HOST=@GME_DATABASE_HOST@
GME_DATABASE_PORT=@GME_DATABASE_PORT@
MY_SQL_HOME="@MY_SQL_HOME@"
GME_SERVICE_URL=@GME_SERVICE_URL@



importFileName=gmeDBExport.tar
oldservicename=http://localhost:8090/wsrf/services/cagrid/GlobalModelExchange

export PATH=${MY_SQL_HOME}/bin:${PATH}

tar -xvf ${importFileName}

databases="GME_REGISTRY GME_SCHEMA_STORE GME_SCHEMA_CACHE"

for database in $databases ; do

echo Importing gme database table data into ${database}

gunzip ${database}.sql.gz

mysql -u ${GME_DATABASE_USER} --password=${GME_DATABASE_PASSWORD} --host=${GME_DATABASE_HOST} --port=${GME_DATABASE_PORT} ${GME_DATABASE_PREFIX}_${database} < ${database}.sql

rm -fr ${database}.sql.gz
rm -fr ${database}.sql

done

echo Finished Importing databases

database="${GME_DATABASE_PREFIX}_GME_REGISTRY"


echo changing hostname to ${GME_SERVICE_URL}

echo "use ${database}; update NAMESPACES set SERVICE_ID='${GME_SERVICE_URL}' where SERVICE_id='${oldservicename}';" | mysql -u ${GME_DATABASE_USER} --password=${GME_DATABASE_PASSWORD} --host=${GME_DATABASE_HOST} --port=${GME_DATABASE_PORT}

echo Finished Modifying Databases

exit 