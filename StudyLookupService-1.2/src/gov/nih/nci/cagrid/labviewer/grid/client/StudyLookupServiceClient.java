package gov.nih.nci.cagrid.labviewer.grid.client;

import java.io.InputStream;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.gsi.GlobusCredential;

import gov.nih.nci.cagrid.labviewer.grid.stubs.StudyLookupServicePortType;
import gov.nih.nci.cagrid.labviewer.grid.stubs.service.StudyLookupServiceAddressingLocator;
import gov.nih.nci.cagrid.labviewer.grid.common.StudyLookupServiceI;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.1
 */
public class StudyLookupServiceClient extends ServiceSecurityClient implements StudyLookupServiceI {	
	protected StudyLookupServicePortType portType;
	private Object portTypeMutex;

	public StudyLookupServiceClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public StudyLookupServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	   	initialize();
	}
	
	public StudyLookupServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public StudyLookupServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
		initialize();
	}
	
	private void initialize() throws RemoteException {
	    this.portTypeMutex = new Object();
		this.portType = createPortType();
	}

	private StudyLookupServicePortType createPortType() throws RemoteException {

		StudyLookupServiceAddressingLocator locator = new StudyLookupServiceAddressingLocator();
		// attempt to load our context sensitive wsdd file
		InputStream resourceAsStream = getClass().getResourceAsStream("client-config.wsdd");
		if (resourceAsStream != null) {
			// we found it, so tell axis to configure an engine to use it
			EngineConfiguration engineConfig = new FileProvider(resourceAsStream);
			// set the engine of the locator
			locator.setEngine(new AxisClient(engineConfig));
		}
		StudyLookupServicePortType port = null;
		try {
			port = locator.getStudyLookupServicePortTypePort(getEndpointReference());
		} catch (Exception e) {
			throw new RemoteException("Unable to locate portType:" + e.getMessage(), e);
		}

		return port;
	}
	

	public static void usage(){
		System.out.println(StudyLookupServiceClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  StudyLookupServiceClient client = new StudyLookupServiceClient(args[1]);
			  // place client calls here if you want to use this main as a
			  // test....
			} else {
				usage();
				System.exit(1);
			}
		} else {
			usage();
			System.exit(1);
		}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

  public gov.nih.nci.ccts.grid.Study getStudy(gov.nih.nci.ccts.grid.Registration registration) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getStudy");
    gov.nih.nci.cagrid.labviewer.grid.stubs.GetStudyRequest params = new gov.nih.nci.cagrid.labviewer.grid.stubs.GetStudyRequest();
    gov.nih.nci.cagrid.labviewer.grid.stubs.GetStudyRequestRegistration registrationContainer = new gov.nih.nci.cagrid.labviewer.grid.stubs.GetStudyRequestRegistration();
    registrationContainer.setRegistration(registration);
    params.setRegistration(registrationContainer);
    gov.nih.nci.cagrid.labviewer.grid.stubs.GetStudyResponse boxedResult = portType.getStudy(params);
    return boxedResult.getStudy();
    }
  }

}
