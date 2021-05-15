package com.org.acen.appAcentice.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "PIECE_DETACHEE")
public class PieceDetachee {

    @Id
    @Column(name = "REFERENCE",length = 20)
    @GenericGenerator(name="GeneratorSequenceId", strategy = "com.org.acen.appAcentice.utils.GeneratorIdPieceDetachee")
    @GeneratedValue(generator = "GeneratorSequenceId")
    private String reference;

    @Column(name = "LIBELLE",length = 45)
    private String libelle;

    @Column(name = "DESCRIPTION",length = 500)
    private String discription;

    @Temporal(TemporalType.DATE)
    @Column(name="DATE")
    private Date dateDachat;

    @Column(name="DUREEDEVIE")
    private String dureeDeVie;

    @ManyToOne(cascade = CascadeType.ALL)
    private PieceDetacheeCat categorie;

    @Column(name="PRIX_DACHAT")
    private double prixDachat;
}
