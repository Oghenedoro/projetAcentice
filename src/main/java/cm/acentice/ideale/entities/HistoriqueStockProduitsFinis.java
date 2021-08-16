package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HistoriqueStockProduitsFinis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long refArticle;
    private String typeMouvement;
    private int ancienneValeurStock;
    private int quantiteModifiee;
    private int nouvelleValeurStock;
    private Date dateMAJ;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idSitePrroduction")
    private SiteDeProduction siteDeProduction;

    private Long idSiteVente;
}
