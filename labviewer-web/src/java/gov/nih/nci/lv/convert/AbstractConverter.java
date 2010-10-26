package gov.nih.nci.lv.convert;

import java.util.ArrayList;
import java.util.List;

/**
 * converter class.
 * @author NAmiruddin
 *
 * @param <DTO> dto
 * @param <BO> bo
 */
public abstract class AbstractConverter<DTO, BO>  {
    

    
    /**
     * 
     * @param bo bo
     * @return dto
     */
    public abstract DTO  convertToDTO(BO bo);


    /**
     * 
     * @param dto dto
     * @return dto
     */
    public abstract BO  convertToBO(DTO dto);    
    

    /**
     * 
     * @param bos domain objects
     * @return list of dtos
     */
    public  List<DTO>  convertToDTOs(List<BO> bos) {
        List<DTO> dtos = new ArrayList<DTO>();
        if (bos == null) {
            return dtos;
        }
        for (BO bo : bos) {
            dtos.add(convertToDTO(bo));
        }
        return dtos;
        
    }

    /**
     * 
     * @param dtos dto objects
     * @return list of bos
     */
    public  List<BO>  convertToBOs(List<DTO> dtos) {
        List<BO> bos = new ArrayList<BO>();
        if (dtos == null) {
            return bos;
        }
        for (DTO dto : dtos) {
            bos.add(convertToBO(dto));
        }
        return bos;
    }

    

}
