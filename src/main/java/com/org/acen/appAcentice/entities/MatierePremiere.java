package com.org.acen.appAcentice.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "MATIERE_PREMIERE")
public class MatierePremiere {

    @Id
    @Column(name = "REFERENCEMP",length = 45)
    @GenericGenerator(name="matierPremierId", strategy = "com.org.acen.appAcentice.utils.GeneratorIdMatierePremier")
    @GeneratedValue(generator = "matierPremierId")
    private String reference;

    @Column(name = "LIBELLE",length = 45)
    private String libelle;

    @Column(name="PRIXDACHAT")
    private double prixDachat;

    @Temporal(TemporalType.DATE)
    @Column(name="DATEDACHAT")
    private Date dateDachat;

    @Temporal(TemporalType.DATE)
    @Column(name="DATEDEXPIRATION")
    private Date expirationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private MatierePremiereCat categorie;

    @Column(name = "DESCRIPTION",length = 500)
    private String discription;

}
