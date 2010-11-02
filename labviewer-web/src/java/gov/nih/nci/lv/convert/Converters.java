package gov.nih.nci.lv.convert;

/**
 * 
 * @author NAmiruddin
 *
 */
public class Converters {

    private static HealthcareSiteConverter hcs = new HealthcareSiteConverter();
    private static InvestigatorConverter inv = new InvestigatorConverter();
    
    /**
     * @param clazz class
     * @param <TYPE> the converter type to get
     * @return converter
     * @throws Exception exception
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    public static <TYPE extends AbstractConverter> TYPE get(Class<TYPE> clazz)  throws Exception {
        if (clazz.equals(HealthcareSiteConverter.class)) {
            return (TYPE) hcs;
        }
        if (clazz.equals(InvestigatorConverter.class)) {
            return (TYPE) inv;
        }        
        throw new Exception("Converter needs to be added to gov.nih.nci.lv.convert.Converters.  ");
    }
    
}
