package cm.acentice.ideale.dto;

import cm.acentice.ideale.constants.CommandeStatut;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockVirtuelDto {

    private Long id;
    private Long idCommande;
    private int quantiteCommandee;
    private int quantiteEnStockDisponible;
    private int quantiteLivre;
    private int quantiteStockRestanteALivrer;
    private CommandeStatut commandeStatut;
    private LocalDateTime date;
}
