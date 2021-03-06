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
public class SiteDeProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String adresse;
    private String ville;
    private String quartier;
    @Column(name = "CODE_POSTALE")
    private String codePostal;
    private String pays;
    private String type;
    private String statut;
    private String telephone;
    private String telephone2;

    @JsonIgnore
    @OneToMany(mappedBy = "siteDeProduction",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <ApprovisionnementMatieresPremieres> approvisionnementMatieresPremieres;

    @JsonIgnore
    @OneToMany(mappedBy = "siteDeProduction",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <StockProduitFinis> stockProduitFinis;

}
