/**
 * 
 */
package gov.nih.nci.cagrid.labviewer.grid.test;


import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.bean.Credential;
import gov.nih.nci.cagrid.authentication.client.AuthenticationClient;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.dorian.client.IFSUserClient;
import gov.nih.nci.cagrid.dorian.ifs.bean.ProxyLifetime;
import gov.nih.nci.cagrid.labviewer.grid.client.StudyLookupServiceClient;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;
import gov.nih.nci.ccts.grid.Registration;
import gov.nih.nci.ccts.grid.Study;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.globus.gsi.GlobusCredential;
import org.globus.wsrf.encoding.DeserializationException;
import org.xml.sax.SAXException;

/**
 * @author asharma
 *
 */
public class CaXchangeStudyConsumerTest {

	
		 //String serviceUrl="https://cbvapp-d1017.nci.nih.gov:28443/ctom-wsrf/services/cagrid/StudyLookupService";
		 String serviceUrl = "https://localhost:8443/ctom-wsrf/services/cagrid/StudyLookupService";
		 String sampleFile = "/SampleRegistrationMessage.xml";
		
		/**
		 * main method just creates this class and calls the test method which performs all
		 * the work
		 * <P>
		 * @param args not needed
		 */
		public static void main(String[] args)
		{
			CaXchangeStudyConsumerTest tester = new CaXchangeStudyConsumerTest();
			tester.test();
		}
		
		/**
		 * test method performs the test by creating the registration object and calling
		 * the service
		 */
		public void test()
		{
			try
			{
				System.out.println("Calling Study Look up service at " + serviceUrl);
				// Setup the credentials
				 GlobusCredential gb =this.obtainCredentials();
				
				// Create the client
				StudyLookupServiceClient client = new StudyLookupServiceClient(this.serviceUrl,gb);
				
				// Create the Registration object
				Registration reg = getRegistration();
				
				// Call the service
				  Study study = client.getStudy(reg);
				 System.out.println("Study title"+ study.getLongTitleText());  
				System.out.println("Returned from calling service");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		/**
		 * getRegistration reads the XML file and creates a registration object from it for testing
		 * <P>
		 * @return
		 * @throws DeserializationException
		 * @throws SAXException
		 */
		private Registration getRegistration() throws DeserializationException, SAXException
		{
	    	InputStream sampleIs = getClass().getResourceAsStream(sampleFile);
	        InputStreamReader reader = new InputStreamReader(sampleIs);
	        InputStream wsddIs = getClass().getResourceAsStream("/gov/nih/nci/cagrid/labviewer/grid/client/client-config.wsdd");

	        Registration reg = (gov.nih.nci.ccts.grid.Registration)
	        	Utils.deserializeObject(reader, gov.nih.nci.ccts.grid.Registration.class, wsddIs);
	        
	        return reg;
	    }
		/**
		 * Obtains grid credentials directly from Dorian.
		 * @return GlobusCredential
		 */
		private GlobusCredential obtainCredentials(){
			
			GlobusCredential proxy =null;
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
