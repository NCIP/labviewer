package gov.nih.nci.cagrid.coppa.dto.transform;

import java.io.StringWriter;

import javax.xml.namespace.QName;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.coppa.iso.Any;
import gov.nih.nci.cagrid.coppa.iso.DSet;
import gov.nih.nci.cagrid.coppa.iso.EnPn;
import gov.nih.nci.cagrid.person.service.Person;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.services.person.PersonDTO;

public class PersonTransformer implements Transformer<Person, PersonDTO> {
	 protected static Logger logger = LogManager.getLogger(PersonTransformer.class);


	
	public PersonDTO transform(Person input) throws DtoTransformException {
		PersonDTO res = new PersonDTO();
		res = transform(input,res);
		return res;
	}

	
	public PersonDTO transform(Person input, PersonDTO res)
			throws DtoTransformException {
		if (input == null) return null;
		res.setIdentifier(new IITransformer().transform(input.getIdentifier()));
        res.setName(new ENPNTransformer().transform(input.getName()));
        res.setPostalAddress(new ADTransformer().transform(input.getPostalAddress()));
        //res.setSexCode(new CDTransformer().transform(input.getSexCode()));
        res.setStatusCode(new CDTransformer().transform(input.getStatusCode()));
        DSETTransformer<Tel> dsetTransformer = new DSETTransformer<Tel>();
        gov.nih.nci.coppa.iso.DSet<Tel> telAddress = dsetTransformer.transform(input.getTelecomAddress());
        res.setTelecomAddress(telAddress);
		return res;
	}

	public Person transform(PersonDTO input) throws DtoTransformException {
		Person res = new Person();
		res = transform(input,res);
		return res;
	}

	
	public Person transform(PersonDTO input, Person res) throws DtoTransformException {
		if (input == null) return null;
		res.setIdentifier(new IITransformer().transform(input.getIdentifier()));
		res.setName(new ENPNTransformer().transform(input.getName()));
		res.setPostalAddress(new ADTransformer().transform(input.getPostalAddress()));
		//res.setSexCode(new CDTransformer().transform(input.getSexCode()));
        res.setStatusCode(new CDTransformer().transform(input.getStatusCode()));
        DSETTransformer<Tel> dsetTransformer = new DSETTransformer<Tel>();
        DSet telAddress = dsetTransformer.transform(input.getTelecomAddress());
        res.setTelecomAddress(telAddress);

		return res;
	}
}
