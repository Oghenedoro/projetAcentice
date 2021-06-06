package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Table(name = "FOURNISSEUR_PD")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class FournisseurPD extends AbstractFournisseur implements Fournisseur  {


    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "piece_detachee_fournisseurs",
            joinColumns = @JoinColumn(name = "fournisseurs_id"),
            inverseJoinColumns = @JoinColumn(name = "piece_detachee_reference"))
    private Set<PieceDetachee> pieceDetachees;

    @Override
    public String getId() {
        return this.id;
    }
}
