package gov.nih.nci.caxchange.ctom.viewer.viewobjects;

import java.util.Comparator;

public class LabActivityResultComparator implements Comparator {

	private String order = null;
	public static final String ASCENDING = "ascending";
	public static final String DESCENDING = "descending";
	
	public int compare(Object arg0, Object arg1) {
		
		LabActivityResult lar1 = (LabActivityResult)arg0;
		LabActivityResult lar2 = (LabActivityResult)arg1;
		
		if (ASCENDING.equalsIgnoreCase(order)) {
			if(lar1.getActualDate().before(lar2.getActualDate()))
				return -1;
			else if(lar1.getActualDate().after(lar2.getActualDate()))
				return 1;
		} else if (DESCENDING.equalsIgnoreCase(order)){
			if(lar1.getActualDate().before(lar2.getActualDate()))
				return 1;
			else if(lar1.getActualDate().after(lar2.getActualDate()))
				return -1;			
		}
		//default
		if(lar1.getActualDate().before(lar2.getActualDate()))
			return -1;
		else if(lar1.getActualDate().after(lar2.getActualDate()))
			return 1;
		
		
		return 0;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
