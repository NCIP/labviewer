===============================================================================
              caCORE Software Development Kit (SDK) 4.0 - README FILE
===============================================================================

-------------------------------------------------------------------------------
	LICENSING
-------------------------------------------------------------------------------

	This software is licensed under the terms you may find in the file 
  	named "LICENSE.txt" in the same directory as this README file.

-------------------------------------------------------------------------------
	OVERVIEW
-------------------------------------------------------------------------------

	NCI CBIIT provides biomedical informatics support and integration capabilities to 
	the cancer research community. NCI CBIIT has created caCORE Software Development 
	Kit, or caCORE SDK, which is a data management framework designed for 
	researchers who need to be able to navigate through a large number of data 
	sources. caCORE SDK is NCI CBIIT's platform for data management and semantic 
	integration, built using formal techniques from the software engineering and 
	computer science communities. By providing a common data management framework, 
	caCORE SDK helps streamline the informatics development throughout academic, 
	government and private research labs and clinics. The SDK generated system is 
	built on the principles of Model Driven Architecture (MDA) and n-tier 
	architecture & consistent API.  Model Driven Architecture (MDA) is a software 
	development practice that uses a structured modeling language to describe the 
	requirements, objects, and interactions of a data system prior to its 
	construction. The use of MDA and n-tier architecture, both standard software 
	engineering practices, allows for easy access to data, particularly by other 
	applications.

	For a detailed list of features, please see the "What's New in caCORE SDK 4.0" 
	section of the caCORE 4.0 Developer's Guide.

-------------------------------------------------------------------------------
	BUILDING
-------------------------------------------------------------------------------

	caCORE SDK 4.0 is built using Ant.  The main build script, "build.xml", is 
	located under the root directory of the caCORE SDK 4.0 distribution, in the same
	directory as this README file.

	Please see "Chapter 9 Configuring and Running the SDK" of the caCORE 4.0 
	Developer's Guide for more information.

-------------------------------------------------------------------------------
	DOCUMENTATION
-------------------------------------------------------------------------------

	The caCORE SDK comes with two guides.  The first, the "caCORE 4.0 SDK Developer's 
	Guide", combines information formerly contained within the caCORE SDK 
	Programmer's, Technical, and Basic Installation and Test guides.  The second,
	the "caCORE 4.0 SDK Migration Guide", contains information on migrating from
	previous versions of the SDK.

	JavaDocs are also available for the generated domain classes.  To view them,
	first build the system for your model using the instructions contained 
	in "Chapter 9 Configuring and Running the SDK" of the caCORE 4.0 Developer's 
	Guide.  The JavaDocs are generated within the /output/<project_name>/build/docs
	folder, and also included in the project WAR file located in /output/<project_
	name>/package/webapp/ directory.  Once the WAR file is deployed, the JavaDocs
	are also viewable online via the "JavaDocs" link on the SDK Home page.

-------------------------------------------------------------------------------
	QUICK START
-------------------------------------------------------------------------------

	Please first read and/or refer to the caCORE 4.0 Developer's Guide for an 
	overview and detailed instructions on using the SDK 4.0.

	If migrating from a previous version of the SDK, please also read the "caCORE 
	4.0 SDK Migration Guide".

-------------------------------------------------------------------------------
	OBTAINING SUPPORT
-------------------------------------------------------------------------------

	The NCI CBIIT Application Support group can be contacted at:

		http://ncicb.nci.nih.gov/NCICB/support
		Telephone: 301-451-4384 	
		Toll free: 888-478-4423

	Submitting a Support Issue

	A GForge Support tracker group, which is actively monitored by caCORE SDK 
	developers, has been created to track any support requests. If you believe 
	there is a bug/issue in the caCORE SDK software itself, or have a technical 
	issue that cannot be resolved by contacting the NCI CBIIT Application Support group, 
	please submit a new support tracker using the following link: 

	https://gforge.nci.nih.gov/tracker/?group_id=148&atid=731. 

	Prior to submitting a new tracker, review any existing support request trackers 
	in order to help avoid duplicate submissions.

	A list summarizing the online SDK resources is provided below:

		Mailing List:			CACORESDK_USERS-L@mail.nih.gov
		Mailing List Archive:		https://list.nih.gov/archives/ cacore_sdk_users-l.html
		Project Home (GForge):		https://gforge.nci.nih.gov/projects/cacoresdk/
		Support Tracker (GForge):	https://gforge.nci.nih.gov/tracker/?group_id=148&atid=731

