package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "matiere_premiere_fournisseursMP")
@Entity
public class MatierePremFournisseurMP {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JsonIgnore
    //@JsonManagedReference
    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "matiere_premiere_reference")
    private MatierePremiere matierePremiere;

   // @JsonIgnore
  // @JsonBackReference
   // @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "fournisseursMP_id",referencedColumnName = "id")
    private FournisseurMP fournisseurMP;
}
