package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(name = "Inventaire_Stocks_Appro_MPPD")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HistoriqueStockApproMPPD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refArticle;
    private String TypeMouvement;
    private int ancienneValeurStock;
    private int quantitéModifiee;
    private int nouvelleValeurStock;
    private Date dateMAJ;
    private Long idUser;
}
