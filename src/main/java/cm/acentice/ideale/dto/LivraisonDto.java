package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.Commande;
import lombok.Data;
import java.time.LocalDateTime;


@Data
public class LivraisonDto {
    private LocalDateTime dateLivraison;
    private Long idClient;
    private Long idUser;
    private int quantiteCommandee;
    private int quantiteLivre;
    private int quantiteResatante;
    private Commande commande;
}
