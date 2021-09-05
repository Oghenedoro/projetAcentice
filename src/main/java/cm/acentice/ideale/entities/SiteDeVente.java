package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SiteDeVente {

    @Id
    private String id;
    @Column(name = "NOM",length = 45)
    private String nom;
    @Column(name = "TELEPHONE",length = 45)
    private String telephone;
    @Column(name = "ADRESS",length = 45)
    private String adresse;
    @Column(name = "VILLE",length = 45)
    private String vile;
    @Column(name = "QUARTIER",length = 45)
    private String quartier;
    @Column(name = "TYPE",length = 45)
    private String type;
    @Column(name = "STAUS",length = 45)
    private String statut;
    @Column(name = "COMPTE_MATRICULE",length = 45)
    private String compteMatricule;


    @OneToMany(mappedBy = "siteDeVente",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List <StockProduitFinis> stockProduitFinis;

    @OneToMany(mappedBy = "siteDeVente",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ApprovisionnementProduitFinis>approvisionnementProduitFinisList;

   /* @OneToMany
    @JoinColumn(name = "id", referencedColumnName = "id")
    private List<Produit> produits;*/
}
