package gov.nih.nci.cagrid.labviewer.service.test;

import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.bean.Credential;
import gov.nih.nci.cagrid.authentication.client.AuthenticationClient;
import gov.nih.nci.cagrid.dorian.client.IFSUserClient;
import gov.nih.nci.cagrid.dorian.ifs.bean.ProxyLifetime;
import gov.nih.nci.cagrid.labviewer.client.LabLoaderClient;

import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.globus.gsi.GlobusCredential;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;

public class LabLoaderTest
{
	String serviceUrl = "https://localhost:8443/ctom-wsrf/services/cagrid/LabLoader";
	//String serviceUrl="https://cbvapp-d1017.nci.nih.gov:28445/ctom-wsrf/services/cagrid/LabLoader";
	//String serviceUrl="https://cbvapp-q1009.nci.nih.gov:8080/ctom-wsrf/services/cagrid/LabLoader";
	//String serviceUrl = "http://localhost:8080/wsrf/services/cagrid/LabLoader";
	//String serviceUrl = "http://cbiovqa5010.nci.nih.gov:28080/wsrf/services/cagrid/LabLoader";
	//String serviceUrl="http://cbvapp-d1017.nci.nih.gov:28080/ctom-wsrf/services/cagrid/LabLoader";
	// String serviceUrl= "https://cbvapp-d1017.nci.nih.gov:28443/ctom-wsrf/services/cagrid/LabLoader";
	//String serviceUrl= "http://cbvapp-t1017.nci.nih.gov:8080/ctom-wsrf/services/cagrid/LabLoader";
	//String sampleFile = "C:/Documents and Settings/asharma/Desktop/Temp/processedFolder/TER_2008_04_28_13_23_00_V2TOV3_v2-18-hl7v3.xml";
	//String sampleFile="D:/Development/LabLoader/src/TER_2008_07_03_09_27_20_V2TOV3_v2-1-hl7v3.xml";
	 String sampleFile="D:/Development/LabLoader-1.2-cvs/src/HL7V3-1a.xml";
	String proxyFile ="D:/proxy";
	private Logger logger = Logger.getLogger(getClass());
	/**
	 * @param args 
	 */
	public static void main(String[] args)
	{
		LabLoaderTest tester = new LabLoaderTest();
		tester.test();
	}
	
	/**
	 * test method performs the test by creating the registration object and calling
	 * the service
	 */
	public void test()
	{
		System.out.println("Beginning test to call Lab Loader at " + serviceUrl);
		logger.debug("Beginning test to call Lab Loader at " + serviceUrl);
		try
		{
			// Setup the credentials
			GlobusCredential gb =this.obtainCredentials();//new GlobusCredential(proxyFile);//
						 
			// Create the client
			LabLoaderClient client = new LabLoaderClient(this.serviceUrl,gb);
			
			// Read the sample HL7v3 message to send
			//URL fileLoc = getClass().getResource(sampleFile);
			
			// Read the file from disk
			//for(int i=1;i<42;i++)
			//{
			//String sampleFile="D:/DEMOTEST/mrn-2/HL7v3-"+i+".xml";	
			System.out.println(sampleFile);
			FileInputStream fis = new FileInputStream(sampleFile);
			int x= fis.available();
			byte b[]= new byte[x];
			fis.read(b);
			String content = new String(b);
			
			// Call the service
			client.loadLab(content);
			
			System.out.println("Returned from calling service");
			//}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Obtains grid credentials directly from Dorian.
	 * @return GlobusCredential
	 */
	private GlobusCredential obtainCredentials(){
		
		GlobusCredential proxy =null;
		logger.debug("Obtaining Globus Proxy");
		try{
			   
			   //Create credential		

			   Credential cred = new Credential();
			   BasicAuthenticationCredential bac = new BasicAuthenticationCredential();
			   bac.setUserId("ccts@nih.gov");
			   bac.setPassword("!Ccts@nih.gov1");
			   cred.setBasicAuthenticationCredential(bac);
					
			   //Authenticate to the IdP (DorianIdP) using credential
			   AuthenticationClient authClient = new AuthenticationClient("https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian",cred);
			   SAMLAssertion saml = authClient.authenticate();
					

			   //Requested Grid Credential lifetime (12 hours)
					    
			   ProxyLifetime lifetime = new ProxyLifetime();
			   lifetime.setHours(12);

			   //Delegation Path Length
					
			   int delegationLifetime = 0;

			   //Request Grid Credential

			    IFSUserClient dorian = new IFSUserClient("https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian");
			    proxy = dorian.createProxy(saml, lifetime,delegationLifetime);
					
			 }catch (Exception e) {
			   e.printStackTrace();
			 }
			 return proxy;

	}
}
