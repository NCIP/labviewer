package gov.nih.nci.cagrid.coppa.dto.transform;

import gov.nih.nci.coppa.iso.En;
import gov.nih.nci.coppa.iso.Enxp;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ENTransformer implements Transformer<gov.nih.nci.cagrid.coppa.iso.En,gov.nih.nci.coppa.iso.En> {
	 protected static Logger logger = LogManager.getLogger(ENTransformer.class);
	
	public En transform(gov.nih.nci.cagrid.coppa.iso.En input) throws DtoTransformException {
		En res = new En();
		res = transform(input,res);
 		return res;
	}

	
	public En transform(gov.nih.nci.cagrid.coppa.iso.En input, En res) throws DtoTransformException {
		if (input == null) return null;
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		gov.nih.nci.cagrid.coppa.iso.Enxp[] part = input.getPart();
		if (part==null)return res;
    	List<Enxp> part_iso = res.getPart();
		ENXPTransformer transformer = new ENXPTransformer();
		for (gov.nih.nci.cagrid.coppa.iso.Enxp enxp : part) {
			Enxp enxp_iso = transformer.transform(enxp);
			if (enxp_iso!=null)part_iso.add(enxp_iso);
        }
		return res;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.En transform(gov.nih.nci.coppa.iso.En input) throws DtoTransformException {
		gov.nih.nci.cagrid.coppa.iso.En res = new gov.nih.nci.cagrid.coppa.iso.En();
		res = transform(input,res);
 		return res;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.En transform(gov.nih.nci.coppa.iso.En input, gov.nih.nci.cagrid.coppa.iso.En res) throws DtoTransformException {
		if (input == null) return null;
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));

		List<Enxp> part_iso = input.getPart();
		if (part_iso==null) return res;
        res.setPart(new gov.nih.nci.cagrid.coppa.iso.Enxp[part_iso.size()]);
		ENXPTransformer transformer = new ENXPTransformer();
		int i = 0;
		for (gov.nih.nci.coppa.iso.Enxp enxp_iso : part_iso) {
			logger.debug("Found part");
			gov.nih.nci.cagrid.coppa.iso.Enxp enxp = transformer
					.transform(enxp_iso);
			res.setPart(i++, enxp);
		}
   		return res;
	}


}
