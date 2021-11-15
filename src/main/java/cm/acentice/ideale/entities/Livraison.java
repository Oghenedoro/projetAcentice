package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Livraison {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantiteCommandee;
    private int quantiteLivre;
    private int quantiteRestante;
    private String refProduit;
    @ManyToOne
    @JoinColumn(name = "idCommande")
    private Commande commande;

}
