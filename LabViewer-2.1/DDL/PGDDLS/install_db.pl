#!/usr/bin/perl

use Cwd;

if (($^O eq "linux") || ($^O eq "solaris")) {
	$REDIRECT=">NULL 2>&1";
} elsif ($^O eq "MSWin32") {
	$REDIRECT=">NUL 2>&1";
} else {
	print "Unsupported OS Type.\n";
	exit 1;
}
#---------- Database env properties-------#
$DDL_DIR=".";
$PGBINDIR="C:/Program Files/PostgreSQL/8.2/bin";
$DATABASE="ctods";
$DATABASE_OWNER="ctods";
$DB_IP="cbiodb590.nci.nih.gov";
$DB_PORT="5455";
#$DATABASE="postgres";
#$DATABASE_OWNER="postgres";
#$DB_IP="localhost";
#$DB_PORT="5432";


#print "Dropping database $DATABASE...\n";
#system("\"$PGBINDIR/dropdb\" -U postgres -p $DB_PORT -h $DB_IP $DATABASE");

#print "Dropping database user $DATABASE_OWNER...\n";
#system("\"$PGBINDIR/dropuser\" -U postgres -p $DB_PORT -h $DB_IP -e $DATABASE_OWNER");

#print "Creating database user $DATABASE_OWNER...\n";
#system("\"$PGBINDIR/createuser\" -U postgres -a -p 5432 -h $DB_IP -d -P $DATABASE_OWNER");

#print "Creating database $DATABASE...\n";
#system("\"$PGBINDIR/createdb\" -U postgres -p $DB_PORT -h $DB_IP -O postgres $DATABASE");
print "Working, please wait...\n";


#---------------------------------Drop the plpgsql language---------------------------------#
#$cmd_string = "\"$PGBINDIR/psql\" -a -U $DATABASE_OWNER -p $DB_PORT -h $DB_IP -d $DATABASE -c \"drop language plpgsql cascade;\"";
#print $cmd_string;
#system("$cmd_string");
#---------------------------------Create the plpgsql language---------------------------------#
#$cmd_string = "\"$PGBINDIR/psql\" -a -U $DATABASE_OWNER -p $DB_PORT -h $DB_IP -d $DATABASE -c \"create language plpgsql;\"";
#print $cmd_string;
#system("$cmd_string");


#---------------------------------Create the caxchange role/schema---------------------------------#
#print "Creating schema auth...\n";
#system("\"$PGBINDIR/psql\" -a -U $DATABASE_OWNER -p $DB_PORT -h $DB_IP -d $DATABASE -c \"create schema $DATABASE authorization $DATABASE_OWNER;\"");
print "Tearing down DB...\n";
system("\"$PGBINDIR/psql\" -a -U $DATABASE_OWNER -p $DB_PORT -h $DB_IP -d $DATABASE -f \"$DDL_DIR/ClearDB.sql\" $REDIRECT");
print "Creating schema tables...\n";
system("\"$PGBINDIR/psql\" -a -U $DATABASE_OWNER -p $DB_PORT -h $DB_IP -d $DATABASE -f \"$DDL_DIR/DataModel.sql\" $REDIRECT");
print "Creating sequences...\n";
system("\"$PGBINDIR/psql\" -a -U $DATABASE_OWNER -p $DB_PORT -h $DB_IP -d $DATABASE -f \"$DDL_DIR/PG_Seq.sql\" $REDIRECT");
print "Creating views...\n";
system("\"$PGBINDIR/psql\" -a -U $DATABASE_OWNER -p $DB_PORT -h $DB_IP -d $DATABASE -f \"$DDL_DIR/PG_Views.sql\" $REDIRECT");
print "Creating Auth Schema...\n";
system("\"$PGBINDIR/psql\" -a -U $DATABASE_OWNER -p $DB_PORT -h $DB_IP -d $DATABASE -f \"$DDL_DIR/AuthSchemaPostgres.sql\" $REDIRECT");
print "Creating Migration tables...\n";
system("\"$PGBINDIR/psql\" -a -U $DATABASE_OWNER -p $DB_PORT -h $DB_IP -d $DATABASE -f \"$DDL_DIR/migration4.0.0.sql\" $REDIRECT");
print "Creating CSM data...\n";
system("\"$PGBINDIR/psql\" -a -U $DATABASE_OWNER -p $DB_PORT -h $DB_IP -d $DATABASE -f \"$DDL_DIR/DataPrimingPostgres.sql\" $REDIRECT");


#------------------------------------------------------------------------------------------------#

print "All done.\n";
