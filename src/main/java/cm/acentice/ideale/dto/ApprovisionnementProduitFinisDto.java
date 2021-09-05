package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ApprovisionnementProduitFinisDto {

    private Long id;
    private String receptionist;
    private LocalDate dateApprovisionnement;
    private String idSiteDeVente;
    private Long idSiteDeProduction;
    private Produit produit;
    private List<ApprovissionnementPFHasProduit>approvissionnementPFHasProduits;
    private Long  userId;

}
