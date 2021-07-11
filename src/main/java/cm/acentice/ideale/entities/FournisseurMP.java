package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Table(name="FOURNISSEUR_MP")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class FournisseurMP extends Fournisseur {

    @Column(name = "idFMP", length = 45)
    @GenericGenerator(name = "FMatierePremierId", strategy = "cm.acentice.ideale.utils.GeneratorIdFournisseurMP")
    @GeneratedValue(generator = "FMatierePremierId")
    @Id
    private String id;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fournisseurMP")
    private Collection<Contact> contacts;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fournisseurMP", cascade = CascadeType.ALL)
    private Collection<MatierePremFournisseurMP> matierePremFournisseurMPS;

}
