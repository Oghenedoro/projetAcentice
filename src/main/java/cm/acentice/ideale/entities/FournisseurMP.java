package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Table(name="FOURNISSEUR_MP")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Data
public class FournisseurMP extends AbstractFournisseur implements Fournisseur  {

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "matiere_premiere_fournisseurs",
            joinColumns = @JoinColumn(name = "fournisseurs_id"),
            inverseJoinColumns = @JoinColumn(name = "matiere_premiere_reference"))
    private Set<MatierePremiere> matierePremieres;

    @Override
    public String getId() {
        return this.id;
    }
}
