package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HistoriqueStockProduitsFinis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refArticle;
    private String typeMouvement;
    private int ancienneValeurStock;
    private int quantiteModifiee;
    private int nouvelleValeurStock;
    private LocalDate dateMAJ;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

  /*  @ManyToOne
    @JoinColumn(name = "idSitePrroduction")*/
    private Long idSiteDeProduction;
   // private String idSiteVente;
}
