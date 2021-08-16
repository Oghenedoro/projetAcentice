package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ApprovissionnementPFHasProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantiteFabrique;

    @ManyToOne
    @JoinColumn(name = "idAprovisionnment")
    private ApprovisionnementProduitFinis approvisionnementProduitFinis;

    @ManyToOne
    @JoinColumn(name = "idProduit")
    private Produit produit;

}
