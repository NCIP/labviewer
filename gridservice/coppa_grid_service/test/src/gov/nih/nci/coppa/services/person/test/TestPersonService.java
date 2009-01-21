package gov.nih.nci.coppa.services.person.test;





import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.coppa.dto.transform.PersonTransformer;
import gov.nih.nci.cagrid.coppa.iso.Cd;
import gov.nih.nci.cagrid.coppa.iso.EntityNamePartType;
import gov.nih.nci.cagrid.coppa.iso.Enxp;
import gov.nih.nci.cagrid.coppa.iso.Ii;
import gov.nih.nci.cagrid.coppa.iso.Map;
import gov.nih.nci.cagrid.coppa.iso.NullFlavor;
import gov.nih.nci.cagrid.coppa.person.service.client.CoppaPersonServiceClient;
import gov.nih.nci.cagrid.person.service.Person;

import java.io.StringWriter;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TestPersonService extends TestCase {
    private static Logger logger = LogManager.getLogger(TestPersonService.class);
	private gov.nih.nci.cagrid.coppa.person.service.client.CoppaPersonServiceClient coppaPersonServiceClient = null;
	static protected Properties coppaTestProperties = null;
	static {
		coppaTestProperties = new Properties();
		try {
		  coppaTestProperties.load(TestPersonService.class.getClassLoader().getResourceAsStream("coppatest.properties"));
		  logger.debug("url is:"+coppaTestProperties);
		}catch(Exception e){
			logger.error("Error loading coppa test properties.",e);
		}
	}
	
	public void setUp() {
	  try {
		logger.debug("url is:"+coppaTestProperties.getProperty("coppa.services.url"));
		coppaPersonServiceClient = new CoppaPersonServiceClient(coppaTestProperties.getProperty("coppa.services.url"));
	  }catch(Exception e){
		  throw new RuntimeException("Error initiating the coppa services client.",e);
	  }
	}
	
	public void testSearchPerson() {
		gov.nih.nci.cagrid.coppa.iso.Ii ii = new gov.nih.nci.cagrid.coppa.iso.Ii();
		ii.setNullFlavor(NullFlavor.NI);
		gov.nih.nci.cagrid.person.service.Person person = new gov.nih.nci.cagrid.person.service.Person();
		person.setIdentifier(ii);
		gov.nih.nci.cagrid.coppa.iso.EnPn enpn = new gov.nih.nci.cagrid.coppa.iso.EnPn();
		enpn.setPart(new gov.nih.nci.cagrid.coppa.iso.Enxp[2]);
		gov.nih.nci.cagrid.coppa.iso.Enxp enxp  = new gov.nih.nci.cagrid.coppa.iso.Enxp();
		enxp.setType(EntityNamePartType.GIV);
		enxp.setValue("%");
		enpn.setPart(0,enxp);
		enxp  = new gov.nih.nci.cagrid.coppa.iso.Enxp();
		enxp.setType(EntityNamePartType.FAM);
		enxp.setValue("%");
		enpn.setPart(1,enxp);
		person.setName(enpn);
		  try {
			Person[] results = coppaPersonServiceClient.search(person);
			if (results!=null){
			   logger.debug("Persons returned:"+results.length);
			   for (Person res:results){
				   StringWriter sw = new StringWriter();
				   Utils.serializeObject(res, new javax.xml.namespace.QName("http://isodatatypes.coppa.nci.nih.gov","Person"), sw);
				   logger.debug(sw);
			   }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Coppa Person service fault occurred.", e);
			fail();
 		} 

	}
	
	
	public void testUpdatePersonStatus() {
		gov.nih.nci.cagrid.coppa.iso.Ii ii = new gov.nih.nci.cagrid.coppa.iso.Ii();
		ii.setExtension("8116");
		ii.setRoot("UID.for.nci.entity.person");
		Cd status = new Cd();
		status.setCode("ACTIVE");
		  try {
			Person result =   coppaPersonServiceClient.getPerson(ii);
			if ("ACTIVE".equals(result.getStatusCode().getCode())){
				status.setCode("PENDING");
			}
			coppaPersonServiceClient.updatePersonStatus(ii, status);
			logger.debug("Status updated to:"+status.getCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Coppa Person service fault occurred.", e);
			fail();
 		} 

	}	
	
	
	public void testUpdatePerson() {
		gov.nih.nci.cagrid.coppa.iso.Ii ii = new gov.nih.nci.cagrid.coppa.iso.Ii();
		ii.setExtension("6980");
		ii.setRoot("UID.for.nci.entity.person");
		Cd status = new Cd();
		status.setCode("ACTIVE");
		  try {
			Person result =   coppaPersonServiceClient.getPerson(ii);
			StringWriter sw1 = new StringWriter();
 		    Utils.serializeObject(result, new javax.xml.namespace.QName("http://isodatatypes.coppa.nci.nih.gov","Person"), sw1);			
			logger.debug("Queried Person:"+sw1);			
            Enxp[] enxp =result.getName().getPart();
            boolean foundPrefix=false;
            for(Enxp en: enxp){
            	if ("PFX".equals(en.getType().getValue())) {
            		foundPrefix = true;
            		if ("Mr.".equals(en.getValue())){
            		    en.setValue("Ms.");	
            		}else {
            			en.setValue("Mr.");
            		}
            	}
            }
            Enxp[] newPart = new Enxp[enxp.length+1];
            for (int i=0;i<enxp.length;i++)newPart[i]=enxp[i];
            if (!foundPrefix) {
            	Enxp en = new Enxp();
            	en.setType(EntityNamePartType.PFX);
            	en.setValue("Mr.");
                newPart[enxp.length]=en;
            }
            result.getName().setPart(newPart);
			coppaPersonServiceClient.updatePerson(result);
			Person updatedPerson =   coppaPersonServiceClient.getPerson(ii);
			StringWriter sw = new StringWriter();
 		    Utils.serializeObject(updatedPerson, new javax.xml.namespace.QName("http://isodatatypes.coppa.nci.nih.gov","Person"), sw);			
			logger.debug("Person updated to:"+sw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Coppa Person service fault occurred.", e);
			fail();
 		} 

	}	
	
	public void testGetPerson() {
		gov.nih.nci.cagrid.coppa.iso.Ii ii = new gov.nih.nci.cagrid.coppa.iso.Ii();
		ii.setExtension("8116");
		ii.setRoot("UID.for.nci.entity.person");
		  try {
			Person result = coppaPersonServiceClient.getPerson(ii);
			if (result!=null){
				   StringWriter sw = new StringWriter();
				   Utils.serializeObject(result, new javax.xml.namespace.QName("http://isodatatypes.coppa.nci.nih.gov","Person"), sw);
				   logger.debug(sw);
			}
			PersonTransformer trans = new PersonTransformer();
			result = trans.transform(trans.transform(result));
			if (result!=null){
				   StringWriter sw = new StringWriter();
				   Utils.serializeObject(result, new javax.xml.namespace.QName("http://isodatatypes.coppa.nci.nih.gov","Person"), sw);
				   logger.debug(sw);
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Coppa Person service fault occurred.", e);
			fail();
 		} 

	}	
	
	public void testCreatePerson() {
		gov.nih.nci.cagrid.coppa.iso.Ii ii = new gov.nih.nci.cagrid.coppa.iso.Ii();
		ii.setExtension("8116");
		ii.setRoot("UID.for.nci.entity.person");
		  try {
			Person result = coppaPersonServiceClient.getPerson(ii);
			gov.nih.nci.cagrid.coppa.iso.Ii ii_new = new Ii();
			ii_new.setNullFlavor(NullFlavor.NI);
			result.setIdentifier(ii_new);
			Ii ii_ret = coppaPersonServiceClient.createPerson(result);
			if (ii_ret!=null){
				   StringWriter sw = new StringWriter();
				   Utils.serializeObject(ii_ret, new javax.xml.namespace.QName("http://isodatatypes.coppa.nci.nih.gov", "Ii"), sw);
				   logger.debug(sw);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Coppa Person service fault occurred.", e);
			fail();
 		} 

	}	
	
	public void testValidate() {
		gov.nih.nci.cagrid.coppa.iso.Ii ii = new gov.nih.nci.cagrid.coppa.iso.Ii();
		ii.setExtension("8116");
		ii.setRoot("UID.for.nci.entity.person");
		  try {
			Person result = coppaPersonServiceClient.getPerson(ii);
			gov.nih.nci.cagrid.coppa.iso.Ii ii_new = new Ii();
			ii_new.setNullFlavor(NullFlavor.NI);
			result.setTelecomAddress(null);
			result.setIdentifier(ii_new);
			Map results = coppaPersonServiceClient.validate(result);
			if (results!=null){
				   StringWriter sw = new StringWriter();
				   Utils.serializeObject(results, new javax.xml.namespace.QName("http://isodatatypes.coppa.nci.nih.gov", "Map"), sw);
				   logger.debug(sw);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Coppa Person service fault occurred.", e);
			fail();
 		} 

	}	
	  public TestPersonService() {
		super();
		// TODO Auto-generated constructor stub
	   }

	public TestPersonService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public static Test suite() {
	       TestSuite suite = new TestSuite();
	       suite.addTest(new TestPersonService("testCreatePerson"));
	       return suite;
	    }

	
}
