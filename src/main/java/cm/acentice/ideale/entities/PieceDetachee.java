package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "PIECE_DETACHEE")
public class PieceDetachee {

    @Id
    @Column(name = "reference",length = 20)
    @GenericGenerator(name="GeneratorSequenceId", strategy = "cm.acentice.ideale.utils.GeneratorIdPieceDetachee")
    @GeneratedValue(generator = "GeneratorSequenceId")
    private String reference;

    @Column(name = "libelle",length = 45)
    private String libelle;

    @Column(name = "description",length = 500)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name="date")
    private Date dateAchat;

    @Column(name="duree_de_vie")
    private int dureeDeVie;

    @Column(name="prix_achat")
    private double prixAchat;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    private PieceDetacheeCat categorie;

}
