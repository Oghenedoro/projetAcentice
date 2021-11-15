package cm.acentice.ideale.interfaces;

import cm.acentice.ideale.dto.StockVirtuelDto;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;

public interface StockVirtuelInt {

    public StockVirtuelDto create(StockVirtuelDto stockVirtuelDto) throws ResourceNotFoundException;
}
