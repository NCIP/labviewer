package gov.nih.nci.cagrid.coppa.dto.transform;


import gov.nih.nci.coppa.iso.Cd;

public class CDTransformer implements Transformer<gov.nih.nci.cagrid.coppa.iso.Cd,gov.nih.nci.coppa.iso.Cd> {

	
	public Cd transform(gov.nih.nci.cagrid.coppa.iso.Cd input) throws DtoTransformException {
        Cd res = new Cd();
        res= transform(input,res);
        
		return res;
	}

	
	public Cd transform(gov.nih.nci.cagrid.coppa.iso.Cd  input, Cd res) throws DtoTransformException {
		if (input == null) return null;
        res.setCode(input.getCode());
        res.setCodeSystem(input.getCodeSystem());
        res.setCodeSystemName(input.getCodeSystemName());
        res.setCodeSystemVersion(input.getCodeSystemVersion());
        res.setDisplayName(new STTransformer().transform(input.getDisplayName()));
        res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
        res.setOriginalText(new EDTEXTTransformer().transform(input.getOriginalText()));
        
		return res;
	}
	
	public gov.nih.nci.cagrid.coppa.iso.Cd transform(gov.nih.nci.coppa.iso.Cd input) throws DtoTransformException {
		gov.nih.nci.cagrid.coppa.iso.Cd res = new gov.nih.nci.cagrid.coppa.iso.Cd();
        res= transform(input,res);
        
		return res;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.Cd transform(gov.nih.nci.coppa.iso.Cd  input, gov.nih.nci.cagrid.coppa.iso.Cd res) throws DtoTransformException {
		if (input == null) return null;
        res.setCode(input.getCode());
        res.setCodeSystem(input.getCodeSystem());
        res.setCodeSystemName(input.getCodeSystemName());
        res.setCodeSystemVersion(input.getCodeSystemVersion());
        res.setDisplayName(new STTransformer().transform(input.getDisplayName()));
        res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
        res.setOriginalText(new EDTEXTTransformer().transform(input.getOriginalText()));
        
		return res;
	}	

}
