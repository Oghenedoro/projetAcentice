package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.LigneDeCommande;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaiementDto {

    private Long id;
    private double montantPaye;
    private Long idCommande;
//    private List<LigneDeCommande>ligneDeCommandes;
}
