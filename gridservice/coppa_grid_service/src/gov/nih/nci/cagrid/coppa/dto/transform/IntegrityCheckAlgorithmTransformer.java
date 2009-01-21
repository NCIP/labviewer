package gov.nih.nci.cagrid.coppa.dto.transform;

import gov.nih.nci.cagrid.coppa.iso.IntegrityCheckAlgorithm;

public class IntegrityCheckAlgorithmTransformer implements Transformer<IntegrityCheckAlgorithm, gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm> {

	
	public gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm transform(IntegrityCheckAlgorithm input) throws DtoTransformException {
		if (input == null) return null;
		gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm ica = gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm.valueOf(input.getValue());
		return ica;
	}

	
	public gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm transform(
			IntegrityCheckAlgorithm input,
			gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm res)
			throws DtoTransformException {
		if (input == null) return null;
		res = gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm.valueOf(input.getValue());
		return res;
	}
	
	public IntegrityCheckAlgorithm transform(gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm input) throws DtoTransformException {
		if (input == null) return null;
		IntegrityCheckAlgorithm ica = IntegrityCheckAlgorithm.fromString(input.toString());
		return ica;
	}

	
	public IntegrityCheckAlgorithm transform(
			gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm input,
			IntegrityCheckAlgorithm res)
			throws DtoTransformException {
		if (input == null) return null;
		IntegrityCheckAlgorithm ica = IntegrityCheckAlgorithm.fromString(input.toString());
		return res;
	}	

}
