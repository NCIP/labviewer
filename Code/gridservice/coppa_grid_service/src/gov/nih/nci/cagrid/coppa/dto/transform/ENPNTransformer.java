package gov.nih.nci.cagrid.coppa.dto.transform;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import gov.nih.nci.coppa.iso.EnPn;

public class ENPNTransformer implements Transformer<gov.nih.nci.cagrid.coppa.iso.EnPn, EnPn> {
    protected static Logger logger = LogManager.getLogger(ENPNTransformer.class);

	
	public EnPn transform(gov.nih.nci.cagrid.coppa.iso.EnPn input) throws DtoTransformException {
		EnPn res = new EnPn();
		res = transform(input,res);
 		return res;
	}

	
	public EnPn transform(gov.nih.nci.cagrid.coppa.iso.EnPn input, EnPn res) throws DtoTransformException {
		if (input == null) return null;
        res = (EnPn)new ENTransformer().transform(input, res);
		return res;
	}

	public gov.nih.nci.cagrid.coppa.iso.EnPn transform(EnPn input) throws DtoTransformException {
		gov.nih.nci.cagrid.coppa.iso.EnPn res = new gov.nih.nci.cagrid.coppa.iso.EnPn();
		res = transform(input,res);
 		return res;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.EnPn transform(EnPn input, gov.nih.nci.cagrid.coppa.iso.EnPn res) throws DtoTransformException {
		if (input == null) return null;
        res = (gov.nih.nci.cagrid.coppa.iso.EnPn)new ENTransformer().transform(input, res);
		return res;
	}

}
