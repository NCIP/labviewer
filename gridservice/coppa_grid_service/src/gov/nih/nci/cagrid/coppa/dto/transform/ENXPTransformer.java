package gov.nih.nci.cagrid.coppa.dto.transform;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import gov.nih.nci.coppa.iso.Enxp;

public class ENXPTransformer implements Transformer<gov.nih.nci.cagrid.coppa.iso.Enxp,gov.nih.nci.coppa.iso.Enxp> {
	 protected static Logger logger = LogManager.getLogger(ENXPTransformer.class);
	 
	 public  String escape(String input)throws DtoTransformException {
		  return input.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	 }
	
	public Enxp transform(gov.nih.nci.cagrid.coppa.iso.Enxp input) throws DtoTransformException {
		if (input == null) return null;
		Enxp  res = new Enxp(new EntityNamePartTypeTransformer().transform(input.getType()));
		res = transform(input,res);
		return res;
	}

	
	public Enxp transform(gov.nih.nci.cagrid.coppa.iso.Enxp input, Enxp res) throws DtoTransformException {
		if (input == null) return null;
		if ((res==null)&&(input.getType()!=null)) {
			res = new Enxp(new EntityNamePartTypeTransformer().transform(input.getType()));
		}
		res.setCode(input.getCode());
		res.setCodeSystem(input.getCodeSystem());
		res.setCodeSystemVersion(input.getCodeSystemVersion());
        EntityNamePartQualifierTransformer enpqt = new  EntityNamePartQualifierTransformer();
        res.setQualifier(enpqt.transform(input.getQualifier()));
        res.setValue(escape(input.getValue()));
		return res;
	}

	public gov.nih.nci.cagrid.coppa.iso.Enxp transform(Enxp input) throws DtoTransformException {
		gov.nih.nci.cagrid.coppa.iso.Enxp res = new gov.nih.nci.cagrid.coppa.iso.Enxp();
		res = transform(input,res);
		return res;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.Enxp transform(Enxp input, gov.nih.nci.cagrid.coppa.iso.Enxp res) throws DtoTransformException {
		if (input == null) return null;
		logger.debug("ENXP transformed:"+input.getValue());
		res.setCode(input.getCode());
		res.setCodeSystem(input.getCodeSystem());
		res.setCodeSystemVersion(input.getCodeSystemVersion());
        EntityNamePartQualifierTransformer enpqt = new  EntityNamePartQualifierTransformer();
        res.setQualifier(enpqt.transform(input.getQualifier()));
        res.setType(new EntityNamePartTypeTransformer().transform(input.getType()));
        res.setValue(input.getValue());
		return res;
	}	
	

}
