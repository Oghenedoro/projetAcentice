package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class ProduitHasMatierePremier {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProdHasMP;
    private int quantitéProduitsFinis;
    private int quantitéMPUtilise;
    private String refMP;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "idProdHasMP")
    private List<MatierePremiere> matierePremieres;
    @ManyToOne
    @JoinColumn(name = "idProduit",referencedColumnName = "id")
    private Produit produit;
    @ManyToOne
    @JoinColumn(name = "idProdProdFini")
    private ProductionProduitFinis productionProduitFinis;
}
