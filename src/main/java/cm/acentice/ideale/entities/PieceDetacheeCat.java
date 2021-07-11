package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "CATEGORY_PIECE_DETACHEE")
public class PieceDetacheeCat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idPDCat")
    private Long idPDCat;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

   // @JsonManagedReference   @JoinColumn(name = "idMPCat", referencedColumnName = "idMPCat")
    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "idPDCat", referencedColumnName = "idPDCat")
    private Set<PieceDetachee> piecedetachees;
}
