package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String matricule;
    private String Nom;
    private String prénon;
    private String sexe;
    @Column(name = "DATE_DE_NAISSANCE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateDeNaissance;
    @Column(name = "LIEU_DE_NAISSANCE")
    private String lieuDeNaissance;
    @Column(name = "NUMERO_CNI")
    private String numéroCNI;
    @Column(name = "DATE_EXPIRATION_CNI")
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH")
    private LocalDate dateExpirationCNI;
    private String téléphone;
    private String adresse;
    private String ville;
    @Column(name = "DATE_RECRUTEMENT")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd") //DateTimeFormatter.BASIC_ISO_DATE
    private LocalDate dateDeRecrutement;
    @Column(name = "NUMERO_PERMIS_CONDUIRE")
    private String numeroPermisConduire;
    private String qualification;
    private String grade;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistoriqueStockProduitsFinis> historiqueStockProduitsFinis;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ApprovisionnementProduitFinis>listApprovisionnementProduitFinis;
}


// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
//    @Column(name = "DATE_APPROVISIONNEMENT")
//    private LocalDateTime dateApprovisionnement;