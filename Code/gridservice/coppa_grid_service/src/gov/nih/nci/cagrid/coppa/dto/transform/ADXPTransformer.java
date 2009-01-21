package gov.nih.nci.cagrid.coppa.dto.transform;

import gov.nih.nci.coppa.iso.Adxp;

import java.util.ArrayList;
import java.util.List;

public class ADXPTransformer implements Transformer<Adxp,gov.nih.nci.cagrid.coppa.iso.Adxp> {

	
	public Adxp transform(gov.nih.nci.cagrid.coppa.iso.Adxp input) throws DtoTransformException {
		if (input == null) return null;
		Adxp res = Adxp.createAddressPart(new AddressPartTypeTransformer().transform(input.getType()));
		res = transform(input,res);
		return res;
	}

	
	public Adxp transform(gov.nih.nci.cagrid.coppa.iso.Adxp input, Adxp res) throws DtoTransformException {
		if (input == null) return res;
		if (res == null)  res = Adxp.createAddressPart(new AddressPartTypeTransformer().transform(input.getType()));
		res.setCode(input.getCode());
		res.setValue(input.getValue());
		return res;
	}
	/*
	public List<Adxp> transform(SetOfADXP input)throws DtoTransformException {
		if (input == null) return null;
		List<Adxp> adxps_iso = new ArrayList<Adxp>();
		gov.nih.nci.cagrid.coppa.iso.Adxp[] adxps =input.getAdxp();
		if (adxps!=null){
			for (gov.nih.nci.cagrid.coppa.iso.Adxp adxp:adxps) {
				Adxp adxp_iso = transform(adxp);
				adxps_iso.add(adxp_iso);
			}
		}
		return adxps_iso;
	}*/
	
	public List<Adxp> transform(gov.nih.nci.cagrid.coppa.iso.Adxp[] input)throws DtoTransformException {
		if (input == null) return null;
		List<Adxp> adxps_iso = new ArrayList<Adxp>(input.length);
		gov.nih.nci.cagrid.coppa.iso.Adxp[] adxps =input;
		if (adxps!=null){
			for (gov.nih.nci.cagrid.coppa.iso.Adxp adxp:adxps) {
				Adxp adxp_iso = transform(adxp);
				adxps_iso.add(adxp_iso);
			}
		}
		return adxps_iso;
	}	
	
	public gov.nih.nci.cagrid.coppa.iso.Adxp transform(Adxp input) throws DtoTransformException {
		gov.nih.nci.cagrid.coppa.iso.Adxp res = new gov.nih.nci.cagrid.coppa.iso.Adxp();
		res = transform(input,res);
		return res;
	}

	
	public gov.nih.nci.cagrid.coppa.iso.Adxp transform(Adxp input, gov.nih.nci.cagrid.coppa.iso.Adxp res) throws DtoTransformException {
		if (input == null) return null;
		res.setCode(input.getCode());
		res.setValue(input.getValue());
		res.setType(new AddressPartTypeTransformer().transform(input.getType()));
		return res;
	}	
	/*
	public SetOfADXP  transform(List<Adxp> input)throws DtoTransformException {
		if (input == null) return null;
		    SetOfADXP part = new SetOfADXP();
		    part.setAdxp(new gov.nih.nci.cagrid.coppa.iso.Adxp[input.size()] );
		    int i=0;
			for (gov.nih.nci.coppa.iso.Adxp adxp_iso:input) {
				gov.nih.nci.cagrid.coppa.iso.Adxp adxp = transform(adxp_iso);
				part.setAdxp(i++,adxp);
			}
		return part;
	}*/

	public gov.nih.nci.cagrid.coppa.iso.Adxp[]  transform(List<Adxp> input)throws DtoTransformException {
		if (input == null) return null;
		    gov.nih.nci.cagrid.coppa.iso.Adxp[] part = new gov.nih.nci.cagrid.coppa.iso.Adxp[input.size()];
		    int i=0;
			for (gov.nih.nci.coppa.iso.Adxp adxp_iso:input) {
				gov.nih.nci.cagrid.coppa.iso.Adxp adxp = transform(adxp_iso);
				part[i++]=adxp;
			}
		return part;
	}
}
