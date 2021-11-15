package cm.acentice.ideale.entities;

import cm.acentice.ideale.constants.CommandeStatut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockVirtuel {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idCommande;
    private int quantiteCommandee;
    private int quantiteEnStockDisponible;
    private int quantiteLivre;
    private int quantiteStockRestanteAlivrer;
    @Enumerated(EnumType.STRING)
    private CommandeStatut commandeStatut;
    private LocalDateTime date;
}
