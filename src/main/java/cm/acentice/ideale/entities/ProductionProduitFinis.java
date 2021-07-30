package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductionProduitFinis {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long refProduitsFinis;
   private Date dateHeureDebutProduction;
   private Date dateHeureFinProduction;
   private String refMatierePremiere;
   private int quantitéMatPremiereUtilisée;
   private double poidsBobine;
   @Column(name = "QUANTITE_PRODUIT_FINIS")
   private int quantitéProduitsFinis;
   @Column(name = "REFERENCE_PRODUIT_FINIS_DEFECTUEUX")
   private String refProduitsFinisDefectueux;
   @Column(name = "QUANTITE_PRODUIT_FINIS_DEFECTUEUX")
   private int quantitéProduitsFinisDefectueux;
   private  String commentaires;
   @Column(name = "RELEVE_COMPTEUR_DEBUT_KWH")
   private String releveCompteurDebutKwh;
   @Column(name = "RELEVE_COMPTEUR_FIN_KWH")
   private String releveCompteurFinKwh;
   @Column(name = "ID_AGENT_PRODUCTION")
   private Long idAgentProduction;

   @ManyToOne
   @JoinColumn(name = "idSiteProduction")
   private SiteDeProduction siteDeProduction;
}
