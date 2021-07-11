package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "PIECE_DETACHEE")
public class PieceDetachee {

    @GenericGenerator(name="GeneratorSequenceId", strategy = "cm.acentice.ideale.utils.GeneratorIdPieceDetachee")
    @GeneratedValue(generator = "GeneratorSequenceId")
    @Id
    @Column(name = "reference",length = 20)
    private String reference;

    @Column(name = "idPDCat")
    private Long idPDCat;

    @Column(name = "libelle",length = 45)
    private String libelle;

    @Column(name = "description",length = 1000)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name="date")
    private Date dateAchat;

    @Column(name="duree_de_vie")
    private int dureeDeVie;

    @Column(name="prix_achat")
    private double prixAchat;

    @JsonIgnore
    @ManyToMany(mappedBy = "pieceDetachees")
    private Set<FournisseurPD> fournisseurPD;

}
