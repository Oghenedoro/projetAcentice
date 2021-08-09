package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class ApprovisionnementProduitFinisDto {

    private Long id;
    private String receptionist;
    private LocalDate dateApprovisionnement;
    private SiteDeVente siteDeVente;
    private SiteDeProduction siteDeProduction;
    private Produit produit;
    private List<ApprovissionnementPFHasProduit>approvissionnementPFHasProduits;
    private User user;

}
