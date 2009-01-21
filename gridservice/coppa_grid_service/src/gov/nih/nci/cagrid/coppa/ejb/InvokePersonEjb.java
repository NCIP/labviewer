package gov.nih.nci.cagrid.coppa.ejb;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class InvokePersonEjb {
	
	static Logger logger = LogManager.getLogger(InvokePersonEjb.class); 
    JNDIUtil jndiUtil = JNDIUtil.getInstance();
	
	public List<PersonDTO> search(PersonDTO person) throws InvokeCoppaServiceException {
		try {
			List<PersonDTO> persons = jndiUtil.getPersonService().search(person);
			return persons;
		}catch(Exception exception) {
			logger.error("Error searching persons.",exception);
			throw new InvokeCoppaServiceException("Error searching persons.",exception);
		}
	}
	
	public PersonDTO getPerson(Ii ii) throws NullifiedEntityException,InvokeCoppaServiceException {
		try {
			PersonDTO person = jndiUtil.getPersonService().getPerson(ii);
			return person;
		}catch(NullifiedEntityException nee) {
			logger.error("Nullified entity exception getting persons.",nee);
			throw nee;
		}catch(Exception exception) {
			logger.error("Error getting persons.",exception);
			throw new InvokeCoppaServiceException("Error getting persons.",exception);
		}
	}	
	
	public Ii createPerson(PersonDTO person) throws EntityValidationException,InvokeCoppaServiceException {
		try {
			Ii ii = jndiUtil.getPersonService().createPerson(person);
			return ii;
		}
		catch(EntityValidationException eve) {
			logger.error("Entity validation exception creating persons.",eve);
			throw eve;
		}
		catch(Exception exception) {
			logger.error("Error creating persons.",exception);
			throw new InvokeCoppaServiceException("Error creating persons.",exception);
		}
	}	
	
	public void updatePerson(PersonDTO person) throws EntityValidationException,InvokeCoppaServiceException {
		try {
			jndiUtil.getPersonService().updatePerson(person);
			return;
		}catch(EntityValidationException eve) {
			logger.error("Entity validation exception updating persons.",eve);
			throw eve;
		}catch(Exception exception) {
			logger.error("Error updating persons.",exception);
			throw new InvokeCoppaServiceException("Error updating persons.",exception);
		}
	}
	
	public void updatePersonStatus(Ii ii,Cd cd) throws EntityValidationException,InvokeCoppaServiceException {
		try {
			jndiUtil.getPersonService().updatePersonStatus(ii,cd);
			return;
		}catch(EntityValidationException eve) {
			logger.error("Entity validation exception updating person status.",eve);
			throw eve;
		}catch(Exception exception) {
			logger.error("Error updating person status.",exception);
			throw new InvokeCoppaServiceException("Error updating person status.",exception);
		}
	}	
	
	public Map<String,String[]> validate(PersonDTO person) throws InvokeCoppaServiceException {
		try {
			Map results = jndiUtil.getPersonService().validate(person);
			return results;
		}catch(Exception exception) {
			logger.error("Error getting persons.",exception);
			throw new InvokeCoppaServiceException("Error getting persons.",exception);
		}
	}		

}
