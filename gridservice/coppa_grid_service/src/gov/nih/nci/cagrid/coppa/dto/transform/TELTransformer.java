package gov.nih.nci.cagrid.coppa.dto.transform;

import java.net.URI;
import java.net.URISyntaxException;

import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException;

import gov.nih.nci.cagrid.coppa.iso.TelEmail;
import gov.nih.nci.cagrid.coppa.iso.TelPerson;
import gov.nih.nci.cagrid.coppa.iso.TelPhone;
import gov.nih.nci.cagrid.coppa.iso.TelUrl;
import gov.nih.nci.coppa.iso.Tel;

public class TELTransformer implements Transformer<gov.nih.nci.cagrid.coppa.iso.Tel,gov.nih.nci.coppa.iso.Tel> {

	
	
	public Tel transform(gov.nih.nci.cagrid.coppa.iso.Tel input) throws DtoTransformException {
	    Tel res = null;
		if (input instanceof TelEmail){
		    res = new gov.nih.nci.coppa.iso.TelEmail();
		}else if (input instanceof TelPhone) {
			res = new gov.nih.nci.coppa.iso.TelPhone();
		}else if (input instanceof TelPerson) {
			res = new gov.nih.nci.coppa.iso.TelPerson();
		}else if (input instanceof TelUrl) {
			res = new gov.nih.nci.coppa.iso.TelUrl();
		}else {
			res = new gov.nih.nci.coppa.iso.Tel();
		}
		res = transform(input,res);
		return res;
	}

	
	public Tel transform(gov.nih.nci.cagrid.coppa.iso.Tel input, Tel res) throws DtoTransformException {
		if (input == null) return null;
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		if (input.getValue()!=null) {
		   try {	
             URI uri = new URI(input.getValue().toString());
             res.setValue(uri);
		   }catch(URISyntaxException se){
		   }
		}
   
		return res;
	}

	public gov.nih.nci.cagrid.coppa.iso.Tel transform(gov.nih.nci.coppa.iso.Tel input) throws DtoTransformException {
		gov.nih.nci.cagrid.coppa.iso.Tel res = new gov.nih.nci.cagrid.coppa.iso.Tel();
		if (input instanceof gov.nih.nci.coppa.iso.TelEmail){
		    res = new TelEmail();
		}else if (input instanceof gov.nih.nci.coppa.iso.TelPhone) {
			res = new TelPhone();
		}else if (input instanceof gov.nih.nci.coppa.iso.TelPerson) {
			res = new TelPerson();
		}else if (input instanceof gov.nih.nci.coppa.iso.TelUrl) {
			res = new TelUrl();
		}else {
			res = new gov.nih.nci.cagrid.coppa.iso.Tel();
		}
		res = transform(input,res);
		return res;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.Tel transform(gov.nih.nci.coppa.iso.Tel input, gov.nih.nci.cagrid.coppa.iso.Tel res) throws DtoTransformException {
		if (input == null) return null;
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		if (input.getValue()!=null) {
		   try {	
             org.apache.axis.types.URI uri = new org.apache.axis.types.URI(input.getValue().toString());
             res.setValue(uri);
		   }catch(org.apache.axis.types.URI.MalformedURIException se){
		   }
		}

		return res;
	}

}
