package gov.nih.nci.cagrid.coppa.dto.transform;

import gov.nih.nci.cagrid.coppa.iso.EntityNamePartQualifier;

import java.util.HashSet;
import java.util.Set;

public class EntityNamePartQualifierTransformer implements Transformer<EntityNamePartQualifier,  gov.nih.nci.coppa.iso.EntityNamePartQualifier> {

	
	public gov.nih.nci.coppa.iso.EntityNamePartQualifier transform(
			EntityNamePartQualifier input) throws DtoTransformException {
		if (input==null) return null;
 		return gov.nih.nci.coppa.iso.EntityNamePartQualifier.valueOf(input.getValue());
	}

	
	public gov.nih.nci.coppa.iso.EntityNamePartQualifier transform(
			EntityNamePartQualifier input,
			gov.nih.nci.coppa.iso.EntityNamePartQualifier res)
			throws DtoTransformException {
		// TODO Auto-generated method stub
		if (input==null) return null;
		return gov.nih.nci.coppa.iso.EntityNamePartQualifier.valueOf(input.getValue());
	}
	
	public Set<gov.nih.nci.coppa.iso.EntityNamePartQualifier> transform(EntityNamePartQualifier[] input) throws DtoTransformException {
		if (input==null)return null;
		try {
		Set<gov.nih.nci.coppa.iso.EntityNamePartQualifier> res = new HashSet<gov.nih.nci.coppa.iso.EntityNamePartQualifier>();
	    	EntityNamePartQualifier[] els = input;
			for(EntityNamePartQualifier el:els) {
				gov.nih.nci.coppa.iso.EntityNamePartQualifier enpq_iso = transform(el);
				res.add(enpq_iso);
			}
		return res;
		}
		catch(Exception e) {
		}
		return null;

	}
	
	public gov.nih.nci.cagrid.coppa.iso.EntityNamePartQualifier transform(
			gov.nih.nci.coppa.iso.EntityNamePartQualifier input) throws DtoTransformException {
		if (input==null) return null;
 		return gov.nih.nci.cagrid.coppa.iso.EntityNamePartQualifier.fromValue(input.name());
	}

	
	public gov.nih.nci.cagrid.coppa.iso.EntityNamePartQualifier transform(
			gov.nih.nci.coppa.iso.EntityNamePartQualifier input,
			gov.nih.nci.cagrid.coppa.iso.EntityNamePartQualifier res)
			throws DtoTransformException {
		// TODO Auto-generated method stub
		if (input==null) return null;
		return gov.nih.nci.cagrid.coppa.iso.EntityNamePartQualifier.fromValue(input.name());
	}	

	public EntityNamePartQualifier[]  transform(Set<gov.nih.nci.coppa.iso.EntityNamePartQualifier> input) throws DtoTransformException {
		if (input==null)return null;
		try {
			EntityNamePartQualifier[] res = new EntityNamePartQualifier[input.size()];
			int i=0;
			for(gov.nih.nci.coppa.iso.EntityNamePartQualifier el:input){
				res[i++] = transform(el);
			}
		    return res;
		}
		catch(Exception e) {
		}
		return null;

	}

}
