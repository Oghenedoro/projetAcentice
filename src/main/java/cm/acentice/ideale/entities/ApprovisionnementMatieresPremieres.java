package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "Approvisionnement_Matieres_Premieres")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ApprovisionnementMatieresPremieres {

    @GenericGenerator(name="apprmpId", strategy = "cm.acentice.ideale.utils.GeneratorIdApprovisionnementMP")
    @GeneratedValue(generator = "apprmpId")
    @Id
    private String id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date  dateApprovisionnement;
    private String description;
    @Column(name = "Prix_Unitaire_HT")
    private double prixUnitaireHT;
    @Column(name = "Prix_Unitaire_TTC")
    private double prixUnitaireTTC;
    @Column(name = "MONTANT_TVA")
    private double montantTVA;
    private double tauxTVA;
    private String devise;
    private String libelle;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Utilisateur")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Id_Site_production")
    private SiteDeProduction siteDeProduction;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "approvisionnementMatieresPremiere")
    private List<ApprovHasMatierePremiere> approvHasMatierePremieres;

    @ManyToOne
    @JoinColumn(name = "refMP")
    private MatierePremiere matierePremiere;
}
