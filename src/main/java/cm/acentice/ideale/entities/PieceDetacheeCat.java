package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "CATEGORY_PIECE_DETACHEE")
public class PieceDetacheeCat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categorie", fetch = FetchType.LAZY)
    private List<PieceDetachee> piecedetachees;
}
