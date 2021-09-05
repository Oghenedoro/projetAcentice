package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.ApprovisionnementProduitFinis;
import cm.acentice.ideale.entities.HistoriqueStockProduitsFinis;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String matricule;
    private String Nom;
    private String prénon;
    private String sexe;
    private LocalDate dateDeNaissance;
    private String lieuDeNaissance;
    private String numéroCNI;
    private LocalDate dateExpirationCNI;
    private String téléphone;
    private String adresse;
    private String ville;
    private LocalDate dateDeRecrutement;
    private String numeroPermisConduire;
    private String qualification;
    private String grade;
    private List<HistoriqueStockProduitsFinis> historiqueStockProduitsFinis;
    private List<ApprovisionnementProduitFinis>listApprovisionnementProduitFinis;
}
