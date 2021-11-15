package cm.acentice.ideale.interfaces;

import cm.acentice.ideale.dto.CommandeLivraisonDto;
import cm.acentice.ideale.entities.CommandeLivraison;
import cm.acentice.ideale.exceptions.LivraisonImpossibleException;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;

public interface CommandeLivraisonInt {

    CommandeLivraisonDto create(CommandeLivraisonDto commdlivraisonDto) throws ResourceNotFoundException, LivraisonImpossibleException;
}
