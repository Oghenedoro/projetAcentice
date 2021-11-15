package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneDeCommande {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refProduit;
    private double prixUnitaire;
    private int quantity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCommande")
    private Commande commande;

}
