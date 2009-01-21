package gov.nih.nci.cagrid.coppa.dto.transform;

import gov.nih.nci.coppa.iso.Ad;

public class ADTransformer implements Transformer<gov.nih.nci.cagrid.coppa.iso.Ad,Ad> {

	
	public gov.nih.nci.coppa.iso.Ad transform(gov.nih.nci.cagrid.coppa.iso.Ad input) throws DtoTransformException {
        Ad res = new Ad();
        res = transform(input,res);
		return res;
	}

	
	public Ad transform(gov.nih.nci.cagrid.coppa.iso.Ad input, Ad res) throws DtoTransformException {
		if (input == null) return null;
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		if (input.getPart()!=null) {
		   ADXPTransformer transformer = new ADXPTransformer();
		   res.setPart(transformer.transform(input.getPart())); 
		}
		return res;
	}

	public gov.nih.nci.cagrid.coppa.iso.Ad transform(gov.nih.nci.coppa.iso.Ad input) throws DtoTransformException {
		gov.nih.nci.cagrid.coppa.iso.Ad res = new gov.nih.nci.cagrid.coppa.iso.Ad();
        res = transform(input,res);
		return res;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.Ad transform(gov.nih.nci.coppa.iso.Ad input, gov.nih.nci.cagrid.coppa.iso.Ad res) throws DtoTransformException {
		if (input == null) return null;
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		if (input.getPart()!=null) {
		   ADXPTransformer transformer = new ADXPTransformer();
		   res.setPart(transformer.transform(input.getPart())); 
		}
		return res;
	}
}
