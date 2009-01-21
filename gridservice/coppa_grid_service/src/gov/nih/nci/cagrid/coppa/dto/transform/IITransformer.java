package gov.nih.nci.cagrid.coppa.dto.transform;

import gov.nih.nci.cagrid.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;

public class IITransformer implements Transformer<gov.nih.nci.cagrid.coppa.iso.Ii,Ii> {

	
	public Ii transform(gov.nih.nci.cagrid.coppa.iso.Ii input) throws DtoTransformException {
        Ii ii = new Ii();
        ii = transform(input,ii);
		return ii;
	}

	
	public Ii transform(gov.nih.nci.cagrid.coppa.iso.Ii input, Ii res) throws DtoTransformException {
		if (input == null) return null;
        Ii ii = res;
        ii.setExtension(input.getExtension());
        ii.setDisplayable(input.getDisplayable());
        ii.setIdentifierName(input.getIdentifierName());
        if (input.getReliability()!=null){
           ii.setReliability(IdentifierReliability.valueOf(input.getReliability().getValue()));
        }
        if (input.getScope()!=null){
           ii.setScope(IdentifierScope.valueOf(input.getScope().getValue()));
        }
        ii.setRoot(input.getRoot());
        ii.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		return ii;
	}
	
	public gov.nih.nci.cagrid.coppa.iso.Ii transform(Ii input) throws DtoTransformException {
		gov.nih.nci.cagrid.coppa.iso.Ii res = new gov.nih.nci.cagrid.coppa.iso.Ii();
        res = transform(input,res);
		return res;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.Ii transform(Ii input, gov.nih.nci.cagrid.coppa.iso.Ii res) throws DtoTransformException {
		if (input == null) return null;	
		res.setDisplayable(input.getDisplayable());
		res.setExtension(input.getExtension());
		res.setIdentifierName(input.getIdentifierName());
		res.setRoot(input.getRoot());
		res.setReliability(gov.nih.nci.cagrid.coppa.iso.IdentifierReliability.fromValue(input.getReliability().name()));
		res.setScope(gov.nih.nci.cagrid.coppa.iso.IdentifierScope.fromValue(input.getScope().name()));
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		return res;
	}
}
