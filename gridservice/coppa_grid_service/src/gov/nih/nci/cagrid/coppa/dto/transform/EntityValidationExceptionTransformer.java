package gov.nih.nci.cagrid.coppa.dto.transform;

import java.util.Map;
import java.util.Set;

import gov.nih.nci.cagrid.coppa.iso.MapItem;
import gov.nih.nci.cagrid.coppa.person.service.stubs.types.EntityValidationFault;
import gov.nih.nci.po.service.EntityValidationException;

public class EntityValidationExceptionTransformer implements Transformer< EntityValidationException,EntityValidationFault> {

	public EntityValidationFault transform(EntityValidationException input)
			throws DtoTransformException {
		EntityValidationFault res = new EntityValidationFault();
		res = transform(input,res);
		return res;
	}

	public EntityValidationFault transform(EntityValidationException input,
			EntityValidationFault res) throws DtoTransformException {
		Map<String, String[]> errors = input.getErrors();
		if (errors == null){
			res.setErrors(null);
			return res;
		}
		Set<String> keys = errors.keySet();
		gov.nih.nci.cagrid.coppa.iso.Map map_iso = new gov.nih.nci.cagrid.coppa.iso.Map();
		map_iso.setItem(new MapItem[keys.size()]);
		int i=0;
		for (String key:keys) {
			MapItem item = new MapItem();
			item.setKey(key);
			item.setValue(errors.get(key));
			map_iso.setItem(i++, item);
		}
		res.setErrors(map_iso);
		return res;
	}





}
