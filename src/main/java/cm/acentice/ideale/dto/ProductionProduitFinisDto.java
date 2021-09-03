package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.SiteDeProduction;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ProductionProduitFinisDto {

    private Long id;
   // private Long refProduitsFinis;
    private String refProduitsFinis;
    private LocalDateTime dateHeureDebutProduction;
    private LocalDateTime dateHeureFinProduction;
    private String refMatierePremiere;
    private int quantitéMatPremiereUtilisée;
    private double poidsBobine;
    private int quantitéProduitsFinis;
    private String refProduitsFinisDefectueux;
    private int quantitéProduitsFinisDefectueux;
    private  String commentaires;
    private String releveCompteurDebutKwh;
    private String releveCompteurFinKwh;
    private Long idAgentProduction;
    private Long idSiteDeProduction;
}
