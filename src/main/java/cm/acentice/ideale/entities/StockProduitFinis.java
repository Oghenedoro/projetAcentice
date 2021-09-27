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
    private int quantite;
    private int quantite_Min;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
    @Column(name = "Date_derniere_MAJ")
    private LocalDate dateDerniereMaj;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "refProduit",referencedColumnName = "id")
    private Produit produit;

   /* @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idSiteDeVente")
    private SiteDeVente siteDeVente;*/

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idSiteDeProduction")
    private SiteDeProduction siteDeProduction;
}
