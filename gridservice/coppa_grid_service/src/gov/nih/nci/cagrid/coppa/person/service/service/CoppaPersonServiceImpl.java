package gov.nih.nci.cagrid.coppa.person.service.service;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.coppa.dto.transform.CDTransformer;
import gov.nih.nci.cagrid.coppa.dto.transform.EntityValidationExceptionTransformer;
import gov.nih.nci.cagrid.coppa.dto.transform.IITransformer;
import gov.nih.nci.cagrid.coppa.dto.transform.PersonTransformer;
import gov.nih.nci.cagrid.coppa.ejb.InvokeCoppaServiceException;
import gov.nih.nci.cagrid.coppa.ejb.InvokePersonEjb;
import gov.nih.nci.cagrid.coppa.iso.MapItem;
import gov.nih.nci.cagrid.coppa.person.service.stubs.types.CoppaPersonServiceFault;
import gov.nih.nci.cagrid.coppa.person.service.stubs.types.EntityValidationFault;
import gov.nih.nci.cagrid.coppa.person.service.stubs.types.NullifiedEntityFault;
import gov.nih.nci.cagrid.person.service.Person;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;

import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class CoppaPersonServiceImpl extends CoppaPersonServiceImplBase {

	private static org.apache.log4j.Logger logger = LogManager.getLogger(CoppaPersonServiceImpl.class);
	private InvokePersonEjb personService = new InvokePersonEjb();
	
	public CoppaPersonServiceImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.cagrid.person.service.Person[] search(gov.nih.nci.cagrid.person.service.Person person) throws RemoteException, gov.nih.nci.cagrid.coppa.person.service.stubs.types.CoppaPersonServiceFault {
	try {  
	      if (logger.isDebugEnabled()){
		     StringWriter sw = new StringWriter();
		     Utils.serializeObject(person, new javax.xml.namespace.QName("http://isodatatypes.coppa.nci.nih.gov","Person"), sw);
		     logger.debug("Search person invoked with :"+sw);
	      }
	      PersonDTO person_iso = new PersonTransformer().transform(person);
          List<PersonDTO> results = personService.search(person_iso);
          if (results==null) return null;
          logger.debug("Persons searched from COPPA:"+results.size());
          PersonTransformer transformer = new PersonTransformer();
          Person[] returnResults = new Person[results.size()];
          int i=0;
          for (PersonDTO person_res:results){
        	  gov.nih.nci.cagrid.person.service.Person person_res_tr = transformer.transform(person_res);
        	  returnResults[i++]=person_res_tr;
          }
          return returnResults;
	}catch(Exception e){
		logger.error("Error in searching persons.",e);
		CoppaPersonServiceFault coppaFault = new CoppaPersonServiceFault();
		coppaFault.setFaultDetailString("Error in searching persons."+e.getMessage());
		throw coppaFault;
	}
  }

  public gov.nih.nci.cagrid.person.service.Person getPerson(gov.nih.nci.cagrid.coppa.iso.Ii ii) throws RemoteException, gov.nih.nci.cagrid.coppa.person.service.stubs.types.CoppaPersonServiceFault, gov.nih.nci.cagrid.coppa.person.service.stubs.types.NullifiedEntityFault {
	 try {
	    Ii ii_iso = new IITransformer().transform(ii); 
	    PersonDTO person_iso = personService.getPerson(ii_iso);
	    Person person = new PersonTransformer().transform(person_iso);
	    return person;
	 }catch (NullifiedEntityException nee){
		 //Transform nee to Nullified entity fault
		 NullifiedEntityFault nef = new NullifiedEntityFault();
		 nef.setFaultDetailString("Error getting person"+nee.getMessage());
		 throw nef;
     }catch(Exception e){
			logger.error("Error in getting persons.",e);
			CoppaPersonServiceFault coppaFault = new CoppaPersonServiceFault();
			coppaFault.setFaultDetailString("Error in getting persons."+e.getMessage());
			throw coppaFault;
		}
  }

  public gov.nih.nci.cagrid.coppa.iso.Ii createPerson(gov.nih.nci.cagrid.person.service.Person person) throws RemoteException, gov.nih.nci.cagrid.coppa.person.service.stubs.types.CoppaPersonServiceFault, gov.nih.nci.cagrid.coppa.person.service.stubs.types.EntityValidationFault {
		 try {
			    PersonDTO person_iso = new PersonTransformer().transform(person);
			    Ii ii_iso = personService.createPerson(person_iso);
			    gov.nih.nci.cagrid.coppa.iso.Ii ii = new IITransformer().transform(ii_iso); 
			    return ii;
	     }catch(EntityValidationException eve){
	    	 //Transform eve to Entity validation fault
	    	 EntityValidationFault evf = new EntityValidationFault();
	    	 evf.setFaultDetailString("Error creating person."+eve.getErrorMessages());
	    	 try {
	    	  	   evf = new EntityValidationExceptionTransformer().transform(eve, evf);
	    	  	 }catch(Exception e) {}	    	 
	    	 throw evf;
         }catch(Exception e){
					logger.error("Error in getting persons.",e);
					CoppaPersonServiceFault coppaFault = new CoppaPersonServiceFault();
					coppaFault.setFaultDetailString("Error in getting persons."+e.getMessage());
					throw coppaFault;
		}
  }

  public void updatePerson(gov.nih.nci.cagrid.person.service.Person person) throws RemoteException, gov.nih.nci.cagrid.coppa.person.service.stubs.types.CoppaPersonServiceFault, gov.nih.nci.cagrid.coppa.person.service.stubs.types.EntityValidationFault {
    try {
    	  PersonDTO person_iso = new PersonTransformer().transform(person);
    	  personService.updatePerson(person_iso);
    	  return;
    }catch(EntityValidationException eve){
   	 //Transform eve to Entity validation fault
   	 EntityValidationFault evf = new EntityValidationFault();
   	 evf.setFaultDetailString("Error updating person."+eve.getMessage());
	 try {
  	   evf = new EntityValidationExceptionTransformer().transform(eve, evf);
  	 }catch(Exception e) {}
  	 throw evf;   	 
    }catch(Exception e){
		logger.error("Error in updating persons.",e);
		CoppaPersonServiceFault coppaFault = new CoppaPersonServiceFault();
		coppaFault.setFaultDetailString("Error in updating persons."+e.getMessage());
		throw coppaFault;
     }
  }

  public void updatePersonStatus(gov.nih.nci.cagrid.coppa.iso.Ii ii,gov.nih.nci.cagrid.coppa.iso.Cd cd) throws RemoteException, gov.nih.nci.cagrid.coppa.person.service.stubs.types.EntityValidationFault {
	    try {
	    	  Ii ii_iso = new IITransformer().transform(ii);
	    	  Cd cd_iso = new CDTransformer().transform(cd);
	    	  personService.updatePersonStatus(ii_iso, cd_iso);
	    	  return;
	    }catch(EntityValidationException eve){
	    	 //Transform eve to Entity validation fault
	    	 EntityValidationFault evf = new EntityValidationFault();
	    	 evf.setFaultDetailString("Error updating person status."+eve.getMessage());
	    	 try {
	    	   evf = new EntityValidationExceptionTransformer().transform(eve, evf);
	    	 }catch(Exception e) {}
	    	 throw evf;
        }catch(Exception e){
			logger.error("Error in updating person status.",e);
			CoppaPersonServiceFault coppaFault = new CoppaPersonServiceFault();
			coppaFault.setFaultDetailString("Error in updating person status."+e.getMessage());
			throw coppaFault;
	     }
  }

  public gov.nih.nci.cagrid.coppa.iso.Map validate(gov.nih.nci.cagrid.person.service.Person person) throws RemoteException, gov.nih.nci.cagrid.coppa.person.service.stubs.types.CoppaPersonServiceFault {
	    try {
	    	  PersonDTO person_iso = new PersonTransformer().transform(person);
	    	  Map<String,String[]> validationResults = personService.validate(person_iso);
	    	  gov.nih.nci.cagrid.coppa.iso.Map map_coppa = new gov.nih.nci.cagrid.coppa.iso.Map();
	    	  if (validationResults == null) return null;
	    	  Set<String> keySet = validationResults.keySet();
	    	  MapItem[] items = new MapItem[keySet.size()];
	    	  int i=0;
	    	  for (String key:keySet) {
	    		  MapItem item = new MapItem();
	    		  item.setKey(key);
	    		  item.setValue(validationResults.get(key));
	    		  items[i++]=item;
	    	  }
	    	  map_coppa.setItem(items);
	    	  return map_coppa;
	    }catch(Exception e){
			logger.error("Error in validating persons.",e);
			CoppaPersonServiceFault coppaFault = new CoppaPersonServiceFault();
			coppaFault.setFaultDetailString("Error in validating persons."+e.getMessage());
			throw coppaFault;
	     }
  }

}

