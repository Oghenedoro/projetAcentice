package cm.acentice.ideale.interfaces;

import cm.acentice.ideale.dto.PaiementDto;
import cm.acentice.ideale.exceptions.ExcessPaiementException;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;

import java.util.List;

public interface PaimentInterface {
    PaiementDto create(PaiementDto paiementDto) throws ResourceNotFoundException, ExcessPaiementException;
}
