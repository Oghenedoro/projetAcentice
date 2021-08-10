package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matricule;
    private String Nom;
    private String prénon;
    private String sexe;
    @Column(name = "DATE_DE_NAISSANCE")
    private Date dateDeNaissance;
    @Column(name = "LIEU_DE_NAISSANCE")
    private String lieuDeNaissance;
    @Column(name = "NUMERO_CNI")
    private String numéroCNI;
    @Column(name = "DATE_EXPIRATION_CNI")
    private Date dateExpirationCNI;
    private String téléphone;
    private String adresse;
    private String ville;
    @Column(name = "DATE_RECRUTEMENT")
    private Date dateDeRecrutement;
    @Column(name = "NUMERO_PERMIS_CONDUIRE")
    private String numeroPermisConduire;
    private String qualification;
    private String grade;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistoriqueStockProduitsFinis> inventaireProduitsFinis;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ApprovisionnementProduitFinis>approvisionnementProduitFinisList;
}
