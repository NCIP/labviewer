package gov.nih.nci.cagrid.coppa.dto.transform;

import gov.nih.nci.coppa.iso.AddressPartType;

public class AddressPartTypeTransformer implements Transformer<gov.nih.nci.cagrid.coppa.iso.AddressPartType, AddressPartType> {


	public AddressPartType transform(
			gov.nih.nci.cagrid.coppa.iso.AddressPartType input)
			throws DtoTransformException {
		if (input==null) return null;
		AddressPartType res = AddressPartType.valueOf(input.getValue());
 		return res;
	}


	public AddressPartType transform(
			gov.nih.nci.cagrid.coppa.iso.AddressPartType input,
			AddressPartType res) throws DtoTransformException {
		if (input==null) return null;
		 res = AddressPartType.valueOf(input.getValue());
 		return res;
	}

	public gov.nih.nci.cagrid.coppa.iso.AddressPartType transform(
			gov.nih.nci.coppa.iso.AddressPartType input)
			throws DtoTransformException {
		if (input==null) return null;
		gov.nih.nci.cagrid.coppa.iso.AddressPartType res = gov.nih.nci.cagrid.coppa.iso.AddressPartType.fromString(input.name());
 		return res;
	}


	public gov.nih.nci.cagrid.coppa.iso.AddressPartType transform(
			gov.nih.nci.coppa.iso.AddressPartType input,
			gov.nih.nci.cagrid.coppa.iso.AddressPartType res) throws DtoTransformException {
		if (input==null) return null;
		res = gov.nih.nci.cagrid.coppa.iso.AddressPartType.fromString(input.name());
 		return res;
	}

}
