package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.Livraison;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
public class CommandeLivraisonDto {

    private Long id;
    private Long idCommande;
    private LocalDateTime dateLivraison;
    private Long idClient;
    private Long idUser;
    List<Livraison> livraisons;
}
