package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
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
    private Long id;
   /* @Column(name = "LIBELLE",length = 45)
    private String libellé;
    @Column(name = "DESCRIPTION",length = 45)
    private String description;*/
    private int quantite;
    private int quantite_Min;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
    @Column(name = "Date_derniere_MAJ")
    private LocalDate dateDerniereMaj;

    @ManyToOne
    @JoinColumn(name = "refProduit",referencedColumnName = "id")
    private Produit produit;

   /* @OneToMany(mappedBy = "stockProduitFinis", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SiteDeVente>siteDeVentes;*/
}
