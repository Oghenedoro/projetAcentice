package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(name = "HISTORIQUE_APPROV_MP_PD")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HistoriqueStockApprovMPPD {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;
    @Column(name="REF_ARTICLE",length = 25)
    private String refArticle;
    @Column(name="TYPE_MOVEMENT", length = 25)
    private String TypeMouvement;
    private int ancienneValeurStock;
    private int quantitéModifiee;
    private int nouvelleValeurStock;
    private Date dateMAJ;
   // private Long idUser;
}
