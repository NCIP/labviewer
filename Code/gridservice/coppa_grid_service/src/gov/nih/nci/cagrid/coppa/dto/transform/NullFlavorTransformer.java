package gov.nih.nci.cagrid.coppa.dto.transform;

import gov.nih.nci.coppa.iso.NullFlavor;

public class NullFlavorTransformer implements Transformer<gov.nih.nci.cagrid.coppa.iso.NullFlavor,NullFlavor> {

	
	public NullFlavor transform(gov.nih.nci.cagrid.coppa.iso.NullFlavor input) throws DtoTransformException {
		if (input == null) return null;
		  NullFlavor nullFlavor = NullFlavor.valueOf(input.getValue());
 		return nullFlavor;
	}

	
	public NullFlavor transform(gov.nih.nci.cagrid.coppa.iso.NullFlavor input,
			NullFlavor res) throws DtoTransformException {
		if (input == null) return null;
		  res = NullFlavor.valueOf(input.getValue());
		return res;
	}

	public gov.nih.nci.cagrid.coppa.iso.NullFlavor transform(gov.nih.nci.coppa.iso.NullFlavor input) throws DtoTransformException {
		if (input == null) return null;
		gov.nih.nci.cagrid.coppa.iso.NullFlavor nullFlavor = gov.nih.nci.cagrid.coppa.iso.NullFlavor.fromValue(input.name());
 		return nullFlavor;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.NullFlavor transform(gov.nih.nci.coppa.iso.NullFlavor input,
			gov.nih.nci.cagrid.coppa.iso.NullFlavor res) throws DtoTransformException {
		if (input == null) return null;
		  res = gov.nih.nci.cagrid.coppa.iso.NullFlavor.fromValue(input.name());
		return res;
	}	
	
}
