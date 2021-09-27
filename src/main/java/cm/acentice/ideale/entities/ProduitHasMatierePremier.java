package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class ProduitHasMatierePremier {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProdHasMP;
    private int quantitéProduitsFinis;
    private int quantitéMPUtilise;
    @ManyToOne
    @JoinColumn(name = "refMP",referencedColumnName = "reference")
    private MatierePremiere matierePremiere;
    @ManyToOne
    @JoinColumn(name = "idProduit",referencedColumnName = "id")
    private Produit produit;
    @ManyToOne
    @JoinColumn(name = "idProdProdFini")
    private ProductionProduitFinis productionProduitFinis;
}
