package gov.nih.nci.lv.convert;

/**
 * 
 * @author NAmiruddin
 *
 */
public class Converters {

    private static HealthcareSiteConverter hcs = new HealthcareSiteConverter();
    
    /**
     * @param clazz class
     * @param <TYPE> the converter type to get
     * @return converter
     * @throws Exception exception
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    public static <TYPE extends AbstractConverter> TYPE get(Class<TYPE> clazz)  throws Exception {
        // TODO - replace this lookup tree with a Map

        if (clazz.equals(HealthcareSiteConverter.class)) {
            return (TYPE) hcs;
        }
        throw new Exception("Converter needs to be added to gov.nih.nci.pa.iso.convert.Converters.  ");
    }
    
}
