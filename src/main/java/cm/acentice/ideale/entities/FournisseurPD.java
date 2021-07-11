package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Table(name = "FOURNISSEUR_PD")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class FournisseurPD extends Fournisseur {

    @Column(name = "idFPD",unique = true, length = 45)
    @GenericGenerator(name = "FPieceDetacheerId", strategy = "cm.acentice.ideale.utils.GeneratorIdFournisseurPD")
    @GeneratedValue(generator = "FPieceDetacheerId")

    private String id;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "fournisseurPD")
    private List<Contact> contacts;

    @JsonIgnore
    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "piece_detachee_fournisseursPD",
            joinColumns = @JoinColumn(name = "fournisseursPD_id"),
            inverseJoinColumns = @JoinColumn(name = "piece_detachee_reference"))
    private List<PieceDetachee> pieceDetachees;
}
