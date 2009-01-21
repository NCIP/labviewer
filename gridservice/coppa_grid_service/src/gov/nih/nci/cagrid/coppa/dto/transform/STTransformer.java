package gov.nih.nci.cagrid.coppa.dto.transform;

import gov.nih.nci.coppa.iso.St;

public class STTransformer implements Transformer<gov.nih.nci.cagrid.coppa.iso.St,gov.nih.nci.coppa.iso.St> {

	
	public St transform(gov.nih.nci.cagrid.coppa.iso.St input) throws DtoTransformException {
		St res = new St();
		res = transform(input,res);
		
		return res;
	}

	
	public St transform(gov.nih.nci.cagrid.coppa.iso.St input, St res) throws DtoTransformException {
		if (input == null) return null;
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		res.setValue(input.getValue());
		
		return res;
	}
	
	public gov.nih.nci.cagrid.coppa.iso.St transform(gov.nih.nci.coppa.iso.St input) throws DtoTransformException {
		gov.nih.nci.cagrid.coppa.iso.St res = new gov.nih.nci.cagrid.coppa.iso.St();
		res = transform(input,res);
		
		return res;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.St transform(gov.nih.nci.coppa.iso.St input, gov.nih.nci.cagrid.coppa.iso.St res) throws DtoTransformException {
		if (input == null) return null;
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		res.setValue(input.getValue());
		
		return res;
	}	

}
