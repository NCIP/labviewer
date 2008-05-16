package gov.nih.nci.caXchange.oubound.operations;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;

import javax.xml.namespace.QName;

import org.xml.sax.InputSource;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.ccts.grid.HealthcareSiteType;
import gov.nih.nci.ccts.grid.IdentifierType;
import gov.nih.nci.ccts.grid.ParticipantType;
import gov.nih.nci.ccts.grid.Registration;
import gov.nih.nci.ccts.grid.ScheduledEpochType;
import gov.nih.nci.ccts.grid.StudyRefType;
import gov.nih.nci.ccts.grid.StudySiteType;
import gov.nih.nci.ccts.grid.SystemAssignedIdentifierType;
import gov.nih.nci.ccts.grid.client.RegistrationConsumerClient;
import junit.framework.TestCase;

public class RegistrationInvocationTest extends TestCase {

	/**
	 * This methods test the RegistrationConsumer Grid service
	 * @param
	 * @return
	 * @throws Exception
	 */
	public void testClient() throws Exception {
		System.out
				.println("Running the RegistrationConsumer Grid Service Client");
		try {

			String url = "http://cbvapp-d1017.nci.nih.gov:18080/caaers-wsrf/services/cagrid/RegistrationConsumer";
			RegistrationConsumerClient client = new RegistrationConsumerClient(
					url);

			System.out
					.println("Call the RegistrationConsumer.Register operation");
			Registration reg = client.register(getPopulatedRegistration());
			System.out
					.println("Got a Registration Response back from Registration Consumer Grid Service..");
			System.out.println(reg.toString());

			InputStream resourceAsStream = Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(
							"gov/nih/nci/ccts/grid/client/client-config.wsdd");

			StringWriter writer = new StringWriter();
			Utils.serializeObject(reg, new QName(
					"http://ccts.nci.nih.gov/RegistrationConsumer",
					"RegisterResponse"), writer, resourceAsStream);

			System.out.println("HERE: " + writer.getBuffer().toString());

			System.out
					.println("Call the RegistrationConsumer.commit operation");
			client.commit(getPopulatedRegistration());
			System.out
					.println("commit operation successful from Registration Consumer Grid Service..");

			System.out
					.println("Call the RegistrationConsumer.rollback operation");
			client.rollback(getPopulatedRegistration());
			System.out
					.println("Rollback operation successful from Registration Consumer Grid Service..");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method returns registration for the the given study and identifier
	 * @param
	 * @return registration
	 * @throws
	 */
	private Registration getPopulatedRegistration() {

		Registration registration = new Registration();
		StudyRefType sr = new StudyRefType();
		SystemAssignedIdentifierType sait = new SystemAssignedIdentifierType();
		sait.setPrimaryIndicator(true);
		sait.setValue("C3PR_00C0092");

		IdentifierType[] idType = new IdentifierType[1];
		idType[0] = sait;
		sr.setIdentifier(idType);
		registration.setStudyRef(sr);

		StudySiteType ss = new StudySiteType();
		HealthcareSiteType[] hcsts = new HealthcareSiteType[1];
		HealthcareSiteType hcst = new HealthcareSiteType();
		hcst.setNciInstituteCode("SITE_ON");
		hcsts[0] = hcst;
		ss.setHealthcareSite(hcsts);
		ss.setStartDate(new Date());
		ss.setIrbApprovalDate(new Date());

		registration.setStudySite(ss);
		StringWriter writer = new StringWriter();
		try {
			Utils.serializeObject(registration, new QName("registration"),
					writer);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		System.out.println(writer.getBuffer().toString());
		return registration;
	}
}
