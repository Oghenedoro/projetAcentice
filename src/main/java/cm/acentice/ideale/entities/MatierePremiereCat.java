package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "CATEGORY_MATIERE_PREMIERE")
public class MatierePremiereCat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idMPCat")
    private Long idMPCat;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    //@JsonManagedReference
    //@JsonIgnore
    @OneToMany
    @JoinColumn(name = "idMPCat", referencedColumnName = "idMPCat")
    private List<MatierePremiere> matierePremieres;
}
