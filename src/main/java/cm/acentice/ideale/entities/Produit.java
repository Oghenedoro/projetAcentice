package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Produit {
    @Id
    private String id;
    private double poids;
    private String description;
    private String couleur;
    private Date datePeremption;

    @JsonIgnore
    @OneToMany(mappedBy = "produit",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<StockProduitFinis> stockProduitFinisList;

    @JsonIgnore
    @OneToMany(mappedBy = "produit",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ApprovissionnementPFHasProduit>approvissionnementPFHasProduits;

    @JsonIgnore
    @OneToMany(mappedBy = "produit",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProduitHasMatierePremier>produitHasMatierePremiers;
    @Transient
    private int quantiteFabrique;

   /* @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idSiteDeVente")
    private SiteDeVente siteDeVente;*/

}
