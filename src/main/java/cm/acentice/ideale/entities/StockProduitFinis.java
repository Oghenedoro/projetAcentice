package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "STOCK_PRODUITS_FINIS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockProduitFinis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Reference_Produit")
    private Long refProduit;
    private String libellé;
    private String description;
    private int quantite;
    private int quantite_Min;
    @Column(name = "Date_derniere_MAJ")
    private Date dateDerniereMaj;
    @OneToMany(mappedBy = "stockProduitFinis", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Produit> produits;
    @OneToMany(mappedBy = "stockProduitFinis", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SiteDeVente>siteDeVentes;
}
