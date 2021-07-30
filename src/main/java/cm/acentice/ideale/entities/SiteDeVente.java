package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SiteDeVente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String telephone;
    private String adresse;
    private String vile;
    private String quartier;
    private String type;
    private String statut;
    private String compteMatricule;

    @ManyToOne
    @JoinColumn(name = "idStockProduitFinis")
    private StockProduitFinis stockProduitFinis;

}
