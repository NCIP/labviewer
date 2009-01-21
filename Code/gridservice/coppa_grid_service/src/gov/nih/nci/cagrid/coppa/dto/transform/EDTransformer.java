package gov.nih.nci.cagrid.coppa.dto.transform;


import gov.nih.nci.coppa.iso.Ed;

public class EDTransformer implements Transformer<gov.nih.nci.cagrid.coppa.iso.Ed,gov.nih.nci.coppa.iso.Ed> {

	
	public gov.nih.nci.coppa.iso.Ed transform(gov.nih.nci.cagrid.coppa.iso.Ed input) throws DtoTransformException {
        gov.nih.nci.coppa.iso.Ed res = new gov.nih.nci.coppa.iso.Ed();
        res = transform(input,res);
		return res;
	}

	
	public Ed transform(gov.nih.nci.cagrid.coppa.iso.Ed input, Ed res) throws DtoTransformException {
		if (input == null) return null;
        res.setCharset(input.getCharset());
        res.setCompression(new CompressionTransformer().transform(input.getCompression()));
        if (input.getData()!=null){
           res.setData(input.getData().getBytes());
        }
        res.setDescription(new STTransformer().transform(input.getDescription()));
		if (input.getIntegrityCheck()!=null) {
		    res.setIntegrityCheck(input.getIntegrityCheck().getBytes());
		}
		res.setMediaType(input.getMediaType());
		res.setValue(input.getValue());
		res.setXml(input.getXml());
		res.setIntegrityCheckAlgorithm(new IntegrityCheckAlgorithmTransformer().transform(input.getIntegrityCheckAlgorithm()));
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		res.setThumbnail(new EDTransformer().transform(input.getThumbnail()));
        res.setReference(new TELURLTransformer().transform(input.getReference()));
		return res;
	}
	
	public gov.nih.nci.cagrid.coppa.iso.Ed transform(gov.nih.nci.coppa.iso.Ed input) throws DtoTransformException {
		gov.nih.nci.cagrid.coppa.iso.Ed res = new gov.nih.nci.cagrid.coppa.iso.Ed();
        res = transform(input,res);
		return res;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.Ed transform(gov.nih.nci.coppa.iso.Ed input, gov.nih.nci.cagrid.coppa.iso.Ed res) throws DtoTransformException {
		if (input == null) return null;
        res.setCharset(input.getCharset());
        res.setCompression(new CompressionTransformer().transform(input.getCompression()));
        if (input.getData()!=null){
           res.setData(new String(input.getData()));
        }
        res.setDescription(new STTransformer().transform(input.getDescription()));
		if (input.getIntegrityCheck()!=null) {
		    res.setIntegrityCheck(new String(input.getIntegrityCheck()));
		}
		res.setMediaType(input.getMediaType());
		res.setValue(input.getValue());
		res.setXml(input.getXml());
		res.setIntegrityCheckAlgorithm(new IntegrityCheckAlgorithmTransformer().transform(input.getIntegrityCheckAlgorithm()));
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		res.setThumbnail(new EDTransformer().transform(input.getThumbnail()));
        res.setReference(new TELURLTransformer().transform(input.getReference()));
		return res;
	}	

}
