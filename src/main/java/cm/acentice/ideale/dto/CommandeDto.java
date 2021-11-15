package cm.acentice.ideale.dto;

import cm.acentice.ideale.constants.TypeMovementStock;
import cm.acentice.ideale.entities.LigneDeCommande;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommandeDto {

    private Long idCommande;
    private String idSiteDeVente;
    private Long idUser;;
    private LocalDateTime dateDeCommande;
    private String canal;
    private int quantiteGlobale;
    private TypeMovementStock typeMovementStock;
    private String commentaires;
    private Long idClient;
    private List<LigneDeCommande> ligneDeCommandes;
}
