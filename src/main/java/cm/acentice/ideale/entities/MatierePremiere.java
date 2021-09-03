package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "MATIERE_PREMIERE")
public class MatierePremiere {

    @Id
    @Column(name = "REFERENCE", length = 45)
    @GenericGenerator(name = "matierPremierId", strategy = "cm.acentice.ideale.utils.GeneratorIdMatierePremier")
    @GeneratedValue(generator = "matierPremierId")
    private String reference;

    @Column(name = "idMPCat")
    private Long idMPCat;

    @Column(name = "libelle", length = 45)
    private String libelle;

    @Column(name = "description", length = 1000)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date dateAchat;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_expiration")
    private Date expirationDate;

    @Column(name = "prix_achat")
    private double prixAchat;

    @Transient
    private int quantiteMP;

    @JsonIgnore
    @Column
    @OneToMany(mappedBy = "matierePremiere", cascade = CascadeType.MERGE)
    private Collection<MatierePremFournisseurMP> matierePremFournisseurMPS;

    @OneToMany(mappedBy = "matierePremiere",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ApprovHasMatierePremiere>approvHasMatierePremieres;

   /* @OneToMany(mappedBy = "matierePremiere",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<StockMatierePremiere> stockMatierePremieres;
*/
    /*@OneToMany(mappedBy = "matierePremiere",cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    private Collection<ApprovisionnementMatieresPremieres> approvisionnementMatieresPremieres;*/
}
