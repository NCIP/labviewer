package gov.nih.nci.cagrid.caxchange.context.service.globus.resource;

import gov.nih.nci.cagrid.advertisement.AdvertisementClient;
import gov.nih.nci.cagrid.advertisement.exceptions.UnregistrationException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.axis.MessageContext;
import org.apache.axis.components.uuid.UUIDGen;
import org.apache.axis.components.uuid.UUIDGenFactory;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.mds.aggregator.types.AggregatorConfig;
import org.globus.mds.aggregator.types.AggregatorContent;
import org.globus.mds.aggregator.types.GetMultipleResourcePropertiesPollType;
import org.globus.mds.servicegroup.client.ServiceGroupRegistrationParameters;
import org.globus.wsrf.Constants;
import org.globus.wsrf.RemoveCallback;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.ResourceContextException;
import org.globus.wsrf.ResourceIdentifier;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.ResourceLifetime;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.config.ContainerConfig;
import org.globus.wsrf.container.ServiceHost;
import org.globus.wsrf.encoding.DeserializationException;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.impl.ReflectionResourceProperty;
import org.globus.wsrf.impl.SimpleResourceProperty;
import org.globus.wsrf.impl.SimpleResourcePropertyMetaData;
import org.globus.wsrf.impl.SimpleResourcePropertySet;
import org.globus.wsrf.impl.security.descriptor.ResourceSecurityDescriptor;
import org.globus.wsrf.impl.servicegroup.client.ServiceGroupRegistrationClient;
import org.globus.wsrf.jndi.Initializable;
import org.globus.wsrf.security.SecureResource;
import org.globus.wsrf.utils.AddressingUtils;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class is the base class of the resource type created for this service.
 * It contains accessor and utility methods for managing any resource properties
 * of these resource as well as code for registering any properties selected
 * to the index service.
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public abstract class BaseResourceBase implements SecureResource, RemoveCallback, ResourceIdentifier, ResourceLifetime, ResourceProperties, Initializable {

	static final Log logger = LogFactory.getLog(BaseResourceBase.class);
	/** the identifier of this resource... should be unique in the service */
	private Object id;
	private static final UUIDGen UUIDGEN = UUIDGenFactory.getUUIDGen();
	private ResourcePropertySet propSet;
	private ResourceSecurityDescriptor desc;
	private ResourceConfiguration configuration;
	private ResourceKey key;
	
	// this can be used to cancel the registration renewal
    private AdvertisementClient registrationClient;
    
    private URL baseURL;
    
    private java.util.Calendar terminationTime;
    

	//Define the metadata resource properties
	private ResourceProperty caXchangeResponseMessageRP;
	private gov.nih.nci.caxchange.ResponseMessage caXchangeResponseMessageValue;
	


	/**
	 * @see org.globus.wsrf.ResourceIdentifier#getID()
	 */
	public Object getID() {
		return this.id;
	}


	/**
	 * @see org.globus.wsrf.jndi.Initializable#initialize()
	 */
	public void initialize() throws Exception {
		this.id = UUIDGEN.nextUUID();
		this.desc = null;

		this.propSet = new SimpleResourcePropertySet(ResourceConstants.RESOURCE_PROPERTY_SET);

		// these are the RPs necessary for resource lifetime management
		ResourceProperty prop = new ReflectionResourceProperty(SimpleResourcePropertyMetaData.TERMINATION_TIME, this);
		this.propSet.add(prop); // this property exposes the currenttime, as
		// believed by the local system
		prop = new ReflectionResourceProperty(SimpleResourcePropertyMetaData.CURRENT_TIME, this);
		this.propSet.add(prop);
		
		// now add the metadata as resource properties		//init the rp
		this.caXchangeResponseMessageRP = new SimpleResourceProperty(ResourceConstants.CAXCHANGERESPONSEMESSAGE_Value_RP);
		//add the value to the rp
		this.caXchangeResponseMessageRP.add(this.caXchangeResponseMessageValue);
		//add the rp to the prop set
		this.propSet.add(this.caXchangeResponseMessageRP);
	

	}
	
	
	/**
	 * 
	 * @see org.globus.wsrf.ResourceLifetime#setTerminationTime(java.util.Calendar)
	 */
	public void setTerminationTime(Calendar time) {
		this.terminationTime = time;
	}


	/**
	 * 
	 * 
	 * @see org.globus.wsrf.ResourceLifetime#getTerminationTime()
	 */
	public Calendar getTerminationTime() {
		return this.terminationTime;
	}


	/**
	 * 
	 * @see org.globus.wsrf.ResourceLifetime#getCurrentTime()
	 */
	public Calendar getCurrentTime() {
		return Calendar.getInstance();
	}
	


	//Getters/Setters for ResourceProperties
	
	
	protected ResourceProperty getCaXchangeResponseMessageRP(){
		return this.caXchangeResponseMessageRP;
	}
	
	
	public gov.nih.nci.caxchange.ResponseMessage getCaXchangeResponseMessageValue(){
		return this.caXchangeResponseMessageValue;
	}
	
	public void setCaXchangeResponseMessageValue(gov.nih.nci.caxchange.ResponseMessage caXchangeResponseMessage ){
		this.caXchangeResponseMessageValue=caXchangeResponseMessage;
		getCaXchangeResponseMessageRP().set(0,caXchangeResponseMessage);
	}
	



	/**
	 * @see org.globus.wsrf.ResourceProperties#getResourcePropertySet()
	 */
	public ResourcePropertySet getResourcePropertySet() {
		return propSet;
	}
	
    /**
     * Sets the security descriptor for this resource.  The default resource
     * security will be null so it will fall back to method level then service
     * level security.  If you want to protect this particular instance of this
     * resource then provide a resource security descriptor to this resource
     * through this method.
     */
	public void setSecurityDescriptor(ResourceSecurityDescriptor desc) {
		this.desc = desc;
	}
	
	
	public ResourceSecurityDescriptor getSecurityDescriptor() {
		return this.desc;
	}
	
	public ResourceConfiguration getConfiguration() {
		if (this.configuration != null) {
			return this.configuration;
		}
		MessageContext ctx = MessageContext.getCurrentContext();

		String servicePath = ctx.getTargetService();
		servicePath = servicePath.substring(0,servicePath.lastIndexOf("/"));
		servicePath+="/CaXchangeResponseService";

		String jndiName = Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/configuration";
		logger.debug("Will read configuration from jndi name: " + jndiName);
		try {
			Context initialContext = new InitialContext();
			this.configuration = (ResourceConfiguration) initialContext.lookup(jndiName);
		} catch (Exception e) {
			logger.error("when performing JNDI lookup for " + jndiName + ": " + e, e);
		}

		return this.configuration;
	}


    /**
     * This checks the configuration file, and attempts to register to the
     * IndexService if shouldPerformRegistration==true. It will first read the
     * current container URL, and compare it against the saved value. If the
     * value exists, it will only try to reregister if the values are different.
     * This exists to handle fixing the registration URL which may be incorrect
     * during initialization, then later corrected during invocation. The
     * existence of baseURL does not imply successful registration (a non-null
     * registrationClient does). We will only attempt to reregister when the URL
     * changes (to prevent attempting registration with each invocation if there
     * is a configuration problem).
     */
    public void refreshRegistration(boolean forceRefresh) {
        if (getConfiguration().shouldPerformRegistration()) {

            // first check to see if there are any resource properties that
            // require registration
            ResourceContext ctx;
            try {
                MessageContext msgContext = MessageContext.getCurrentContext();
                if (msgContext == null) {
                    logger.error("Unable to determine message context!");
                    return;
                }

                ctx = ResourceContext.getResourceContext(msgContext);
            } catch (ResourceContextException e) {
                logger.error("Could not get ResourceContext: " + e, e);
                return;
            }
            EndpointReferenceType epr;
            try {
               String transportURL = (String) ctx.getProperty(org.apache.axis.MessageContext.TRANS_URL);
	           transportURL = transportURL.substring(0,transportURL.lastIndexOf('/') +1 );
	           transportURL += "CaXchangeResponseService";
			   epr = AddressingUtils.createEndpointReference(transportURL, getResourceKey());
            } catch (Exception e) {
                logger.error("Could not form EPR: " + e, e);
                return;
            }

            ServiceGroupRegistrationParameters params = null;

            File registrationFile = new File(ContainerConfig.getBaseDirectory() + File.separator
                + getConfiguration().getRegistrationTemplateFile());

            if (registrationFile.exists() && registrationFile.canRead()) {
                logger.debug("Loading registration argumentsrmation from:" + registrationFile);

                try {
                    params = ServiceGroupRegistrationClient.readParams(registrationFile.getAbsolutePath());
                } catch (Exception e) {
                    logger.error("Unable to read registration file:" + registrationFile, e);
                }

                // set our service's EPR as the registrant, or use the specified
                // value
                EndpointReferenceType registrantEpr = params.getRegistrantEPR();
                if (registrantEpr == null) {
                    params.setRegistrantEPR(epr);
                }

            } else {
                logger.error("Unable to read registration file:" + registrationFile);
            }

            if (params != null) {

                AggregatorContent content = (AggregatorContent) params.getContent();
                AggregatorConfig config = content.getAggregatorConfig();
                MessageElement[] elements = config.get_any();
                GetMultipleResourcePropertiesPollType pollType = null;
                try {
                    pollType = (GetMultipleResourcePropertiesPollType) ObjectDeserializer.toObject(elements[0],
                        GetMultipleResourcePropertiesPollType.class);
                } catch (DeserializationException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

 
                if (pollType != null) {
                    
                    // if there are properties names that need to be registered then
                    // register them to the index service
                    if (pollType.getResourcePropertyNames()!=null && pollType.getResourcePropertyNames().length != 0) {

                        URL currentContainerURL = null;
                        try {
                            currentContainerURL = ServiceHost.getBaseURL();
                        } catch (IOException e) {
                            logger.error("Unable to determine container's URL!  Skipping registration.", e);
                            return;
                        }

                        if (this.baseURL != null) {
                            // we've tried to register before (or we are being
                            // forced to
                            // retry)
                            // do a string comparison as we don't want to do DNS
                            // lookups
                            // for comparison
                            if (forceRefresh || !this.baseURL.equals(currentContainerURL)) {
                                // we've tried to register before, and we have a
                                // different
                                // URL now.. so cancel the old registration (if
                                // it
                                // exists),
                                // and try to redo it.
                                if (registrationClient != null) {
                                    try {
                                        this.registrationClient.unregister();
                                    } catch (UnregistrationException e) {
                                        logger
                                            .error("Problem unregistering existing registration:" + e.getMessage(), e);
                                    }
                                }

                                // save the new value
                                this.baseURL = currentContainerURL;
                                logger.info("Refreshing existing registration [container URL=" + this.baseURL + "].");
                            } else {
                                // URLs are the same (and we weren't forced), so
                                // don't
                                // try
                                // to reregister
                                return;
                            }

                        } else {
                            // we've never saved the baseURL (and therefore
                            // haven't
                            // tried to
                            // register)
                            this.baseURL = currentContainerURL;
                            logger.info("Attempting registration for the first time[container URL=" + this.baseURL
                                + "].");
                        }

                        try {
                            // perform the registration for this service
                            this.registrationClient = new AdvertisementClient(params);
                            this.registrationClient.register();

                        } catch (Exception e) {
                            logger.error("Exception when trying to register service (" + epr + "): " + e, e);
                        }
                    } else {
                        logger.info("No resource properties to register for service (" + epr + ")");
                    }
                } else {
                    logger.warn("Registration file deserialized with no poll type (" + epr + ")");
                }
            } else {
                logger.warn("Registration file deserialized with returned null SeviceGroupParams");
            }
        } else {
            logger.info("Skipping registration.");
        }
    }


	public ResourceKey getResourceKey(){
	    return this.key;
	}
	
	protected void setResourceKey(ResourceKey key){
	    this.key = key;
	    
	}
	
	
}
