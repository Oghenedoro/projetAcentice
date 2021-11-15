package cm.acentice.ideale.entities;


import cm.acentice.ideale.constants.CommandeStatut;
import cm.acentice.ideale.constants.MoyenPaiement;
import cm.acentice.ideale.constants.TypeMovementStock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Commande {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommande;
    private String idSiteDeVente;
    private Long idUser;
    private LocalDateTime dateDeCommande;
    private String canal;
    private int quantiteGlobale;
    private String commentaires;
    private Long idClient;
    private double prixTotale;
    @OneToMany(cascade = CascadeType.ALL)
    private List<LigneDeCommande>ligneDeCommandes;

}
