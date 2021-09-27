package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.ProduitHasMatierePremier;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductionProduitFinisDto {

    private Long id;
    private String refProduitsFinis;
    private LocalDateTime dateHeureDebutProduction;
    private LocalDateTime dateHeureFinProduction;
    private String refMatierePremiere;
    private double poidsBobine;
    private String refProduitsFinisDefectueux;
    private int quantitéProduitsFinisDefectueux;
    private  String commentaires;
    private String releveCompteurDebutKwh;
    private String releveCompteurFinKwh;
    private Long idAgentProduction;
    private Long idSiteDeProduction;
    List<ProduitHasMatierePremier> produitHasMatierePremiers;

}
