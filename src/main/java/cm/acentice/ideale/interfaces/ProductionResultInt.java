package cm.acentice.ideale.interfaces;

import cm.acentice.ideale.dto.ProductionResultDto;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;

public interface ProductionResultInt {

    public ProductionResultDto create(ProductionResultDto productionResultDto) throws ResourceNotFoundException;

}
