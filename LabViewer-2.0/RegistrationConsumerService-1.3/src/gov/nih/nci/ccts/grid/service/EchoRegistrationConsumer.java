package gov.nih.nci.ccts.grid.service;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse;
import org.oasis.wsrf.properties.GetMultipleResourceProperties_Element;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.QueryResourcePropertiesResponse;
import org.oasis.wsrf.properties.QueryResourceProperties_Element;

import gov.nih.nci.cabig.ccts.domain.Registration;
import gov.nih.nci.ccts.grid.common.RegistrationConsumerI;
import gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException;
import gov.nih.nci.ccts.grid.stubs.types.RegistrationConsumptionException;

public class EchoRegistrationConsumer implements RegistrationConsumerI {

	public void commit(Registration registration) throws RemoteException,
			InvalidRegistrationException {
		System.out.println("commit API invoked");

	}

	public GetMultipleResourcePropertiesResponse getMultipleResourceProperties(
			GetMultipleResourceProperties_Element params)
			throws RemoteException {
		System.out.println("getMultipleResourceProperties API invoked");
		return null;
	}

	public GetResourcePropertyResponse getResourceProperty(QName params)
			throws RemoteException {
		System.out.println("getMultipleResourceProperties API invoked");
		return null;
	}

	public QueryResourcePropertiesResponse queryResourceProperties(
			QueryResourceProperties_Element params) throws RemoteException {
		System.out.println("getResourceProperty API invoked");
		return null;
	}

	public Registration register(Registration registration)
			throws RemoteException, InvalidRegistrationException,
			RegistrationConsumptionException {
		System.out.println("register API invoked");
		return null;
	}

	public void rollback(Registration registration) throws RemoteException,
			InvalidRegistrationException {
		System.out.println("rollback API invoked");

	}

}
