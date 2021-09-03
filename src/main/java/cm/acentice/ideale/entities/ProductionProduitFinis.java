package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductionProduitFinis {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String refProduitsFinis;

   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
   private LocalDateTime dateHeureDebutProduction;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
   private LocalDateTime dateHeureFinProduction;

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

