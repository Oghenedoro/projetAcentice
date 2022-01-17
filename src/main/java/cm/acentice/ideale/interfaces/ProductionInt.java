package cm.acentice.ideale.interfaces;

import cm.acentice.ideale.dto.ProductionDto;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;

public interface ProductionInt {

    public ProductionDto create(ProductionDto productionDto) throws ResourceNotFoundException;
}
