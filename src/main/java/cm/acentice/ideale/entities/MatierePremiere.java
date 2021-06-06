package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "MATIERE_PREMIERE")
public class MatierePremiere {

    @Id
    @Column(name = "REFERENCE", length = 45)
    @GenericGenerator(name = "matierPremierId", strategy = "cm.acentice.ideale.utils.GeneratorIdMatierePremier")
    @GeneratedValue(generator = "matierPremierId")
    private String reference;

    @Column(name = "libelle", length = 45)
    private String libelle;

    @Column(name = "description", length = 500)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date dateAchat;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_expiration")
    private Date expirationDate;

    @Column(name = "prix_achat")
    private double prixAchat;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    private MatierePremiereCat categorie;

}
