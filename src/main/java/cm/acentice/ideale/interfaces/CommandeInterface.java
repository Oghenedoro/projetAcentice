package cm.acentice.ideale.interfaces;

import cm.acentice.ideale.dto.CommandeDto;
import cm.acentice.ideale.entities.Commande;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.exceptions.StockInsufficantException;

public interface CommandeInterface {
    public CommandeDto create(CommandeDto commandeDto) throws ResourceNotFoundException, StockInsufficantException;
}
