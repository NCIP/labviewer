package gov.nih.nci.ccts.grid.service.globus.resource;

import gov.nih.nci.ccts.grid.common.RegistrationConsumerConstants;
import gov.nih.nci.ccts.grid.stubs.RegistrationConsumerResourceProperties;

import org.apache.axis.components.uuid.UUIDGen;
import org.apache.axis.components.uuid.UUIDGenFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.wsrf.InvalidResourceKeyException;
import org.globus.wsrf.PersistenceCallback;
import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.ResourceContext;
import gov.nih.nci.cagrid.introduce.servicetools.SingletonResourceHomeImpl;
import org.globus.wsrf.jndi.Initializable;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements the resource home for the resource type represented
 * by this service.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class RegistrationConsumerResourceHome extends SingletonResourceHomeImpl implements Initializable {

	static final Log logger = LogFactory.getLog(RegistrationConsumerResourceHome.class);
    private static final UUIDGen UUIDGEN = UUIDGenFactory.getUUIDGen();

	public Resource createSingleton() {
		logger.info("Creating a single resource.");
		try {
		    RegistrationConsumerResourceProperties props = new RegistrationConsumerResourceProperties();
			RegistrationConsumerResource resource = new RegistrationConsumerResource();
			if (resource instanceof PersistenceCallback) {
			      //try to load the resource if it was persisted
                  try{
                    ((PersistenceCallback) resource).load(null);
			      } catch (InvalidResourceKeyException ex){
			      	  //persisted singleton resource was not found so we will just create a new one
			          resource.initialize(props, RegistrationConsumerConstants.RESOURCE_PROPERTY_SET, UUIDGEN.nextUUID());
			      }
            } else {
                    resource.initialize(props, RegistrationConsumerConstants.RESOURCE_PROPERTY_SET, UUIDGEN.nextUUID());
            }
			
			return resource;
		} catch (Exception e) {
			logger.error("Exception when creating the resource",e);
			return null;
		}
	}


	public Resource find(ResourceKey key) throws ResourceException {
		RegistrationConsumerResource resource = (RegistrationConsumerResource) super.find(key);
		return resource;
	}


	/**
	 * Initialze the singleton resource, when the home is initialized.
	 */
	public void initialize() throws Exception {
		logger.info("Attempting to initialize resource.");
		Resource resource = find(null);
		if (resource == null) {
			logger.error("Unable to initialize resource!");
		} else {
			logger.info("Successfully initialized resource.");
		}
	}
	
    /**
     * Get the resouce that is being addressed in this current context
     */
    public RegistrationConsumerResource getAddressedResource() throws Exception {
        RegistrationConsumerResource thisResource;
        thisResource = (RegistrationConsumerResource) ResourceContext.getResourceContext().getResource();
        return thisResource;
    }
}