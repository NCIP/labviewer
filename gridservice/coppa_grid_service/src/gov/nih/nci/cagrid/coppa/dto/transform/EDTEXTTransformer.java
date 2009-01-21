package gov.nih.nci.cagrid.coppa.dto.transform;

import gov.nih.nci.cagrid.coppa.iso.EdText;


public class EDTEXTTransformer  implements Transformer<gov.nih.nci.cagrid.coppa.iso.EdText,gov.nih.nci.coppa.iso.EdText> {

	
	public gov.nih.nci.coppa.iso.EdText transform(gov.nih.nci.cagrid.coppa.iso.EdText input) throws DtoTransformException {
		gov.nih.nci.coppa.iso.EdText res = new gov.nih.nci.coppa.iso.EdText();
        res = transform(input,res);
		return res;
	}

	
	public gov.nih.nci.coppa.iso.EdText transform(gov.nih.nci.cagrid.coppa.iso.EdText input, gov.nih.nci.coppa.iso.EdText res)
			throws DtoTransformException {
		if (input == null) return null;
        res = (gov.nih.nci.coppa.iso.EdText)new EDTransformer().transform(input, res);
		return res;
	}
	
	public gov.nih.nci.cagrid.coppa.iso.EdText transform(gov.nih.nci.coppa.iso.EdText input) throws DtoTransformException {
		gov.nih.nci.cagrid.coppa.iso.EdText res = new gov.nih.nci.cagrid.coppa.iso.EdText();
        res = transform(input,res);
		return res;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.EdText transform(gov.nih.nci.coppa.iso.EdText input, gov.nih.nci.cagrid.coppa.iso.EdText res)
			throws DtoTransformException {
		if (input == null) return null;
        res = (gov.nih.nci.cagrid.coppa.iso.EdText)new EDTransformer().transform(input, res);
		return res;
	}	

}
