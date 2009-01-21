package gov.nih.nci.cagrid.coppa.dto.transform;

import gov.nih.nci.cagrid.coppa.iso.Compression;

public class CompressionTransformer implements Transformer<gov.nih.nci.cagrid.coppa.iso.Compression, gov.nih.nci.coppa.iso.Compression> {

	
	public gov.nih.nci.coppa.iso.Compression transform(gov.nih.nci.cagrid.coppa.iso.Compression input) throws DtoTransformException {
		if (input==null)return null;
		gov.nih.nci.coppa.iso.Compression compression = gov.nih.nci.coppa.iso.Compression.valueOf(input.getValue());
		
		return compression;
	}

	
	public gov.nih.nci.coppa.iso.Compression transform(gov.nih.nci.cagrid.coppa.iso.Compression input,
			gov.nih.nci.coppa.iso.Compression res) throws DtoTransformException {
		if (input==null)return null;
		res = gov.nih.nci.coppa.iso.Compression.valueOf(input.getValue());
		
		return res;
	}
	
	public gov.nih.nci.cagrid.coppa.iso.Compression transform(gov.nih.nci.coppa.iso.Compression input) throws DtoTransformException {
		if (input==null)return null;
		gov.nih.nci.cagrid.coppa.iso.Compression compression = gov.nih.nci.cagrid.coppa.iso.Compression.fromString(input.toString());
		
		return compression;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.Compression transform(gov.nih.nci.coppa.iso.Compression input,
			gov.nih.nci.cagrid.coppa.iso.Compression res) throws DtoTransformException {
		if (input==null)return null;
		res = gov.nih.nci.cagrid.coppa.iso.Compression.fromString(input.toString());
		
		return res;
	}	

}
