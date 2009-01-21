package gov.nih.nci.cagrid.coppa.dto.transform;

import gov.nih.nci.cagrid.coppa.iso.EntityNamePartType;

public class EntityNamePartTypeTransformer implements Transformer<EntityNamePartType, gov.nih.nci.coppa.iso.EntityNamePartType> {

	
	public gov.nih.nci.coppa.iso.EntityNamePartType transform(
			EntityNamePartType input) throws DtoTransformException {
		// TODO Auto-generated method stub
		if (input==null) return null;
 		return gov.nih.nci.coppa.iso.EntityNamePartType.valueOf(input.getValue());
	}

	
	public gov.nih.nci.coppa.iso.EntityNamePartType transform(
			EntityNamePartType input,
			gov.nih.nci.coppa.iso.EntityNamePartType res)
			throws DtoTransformException {
		if (input==null) return null;
 		return gov.nih.nci.coppa.iso.EntityNamePartType.valueOf(input.getValue());
	}

	public EntityNamePartType transform(
			gov.nih.nci.coppa.iso.EntityNamePartType input) throws DtoTransformException {
		// TODO Auto-generated method stub
		if (input==null) return null;
 		return gov.nih.nci.cagrid.coppa.iso.EntityNamePartType.fromValue(input.name());
	}

	
	public EntityNamePartType transform(
			gov.nih.nci.coppa.iso.EntityNamePartType input,
			EntityNamePartType res)
			throws DtoTransformException {
		if (input==null) return null;
 		return gov.nih.nci.cagrid.coppa.iso.EntityNamePartType.fromValue(input.name());
	}
}
