package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateDerniereMaj;

    @Column(name = "refProduit", nullable = false)
    private String refProduit;

    @Column(name = "idSiteDeProduction", nullable = false)
    private Long idSiteDeProduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refProduit",insertable = false, updatable = false)
    private Produit produit;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idSiteDeProduction", insertable = false, updatable = false)
    private SiteDeProduction siteDeProduction;
}
