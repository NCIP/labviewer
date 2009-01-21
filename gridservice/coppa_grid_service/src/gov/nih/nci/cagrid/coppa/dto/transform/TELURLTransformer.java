package gov.nih.nci.cagrid.coppa.dto.transform;

import gov.nih.nci.coppa.iso.TelUrl;

public class TELURLTransformer implements Transformer<gov.nih.nci.cagrid.coppa.iso.TelUrl,gov.nih.nci.coppa.iso.TelUrl> {

	
	public TelUrl transform(gov.nih.nci.cagrid.coppa.iso.TelUrl input) throws DtoTransformException {
		TelUrl res = new TelUrl();
        res = transform(input,res);
		return res;
	}

	
	public TelUrl transform(gov.nih.nci.cagrid.coppa.iso.TelUrl input, TelUrl res)
			throws DtoTransformException {
		if (input == null) return null;
        res = (TelUrl)new TELTransformer().transform(input, res);
		return res;
	}

	public gov.nih.nci.cagrid.coppa.iso.TelUrl transform(gov.nih.nci.coppa.iso.TelUrl input) throws DtoTransformException {
		gov.nih.nci.cagrid.coppa.iso.TelUrl res = new gov.nih.nci.cagrid.coppa.iso.TelUrl();
        res = transform(input,res);
		return res;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.TelUrl transform(gov.nih.nci.coppa.iso.TelUrl input, gov.nih.nci.cagrid.coppa.iso.TelUrl res)
			throws DtoTransformException {
		if (input == null) return null;
        res = (gov.nih.nci.cagrid.coppa.iso.TelUrl)new TELTransformer().transform(input, res);
		return res;
	}

}
