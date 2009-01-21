package gov.nih.nci.cagrid.coppa.dto.transform;



public interface Transformer<T,S> {
	  public S transform(T input) throws DtoTransformException;
	  public S transform(T input,S res) throws DtoTransformException;
	  

}
