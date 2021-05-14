package com.org.acen.appAcentice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "FOURNISSEUR")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fournisseur {

    @Id
    @Column(name = "ID",length = 45)
    @GenericGenerator(name="fournisseurId", strategy = "com.org.acen.appAcentice.utils.GeneratorIdFournisseur")
    @GeneratedValue(generator = "fournisseurId")
    private String id;
    @Column(name = "DENOMINATION", length = 125)
    private String denomination;

    @Column(name = "EMAIL", length = 45)
    private String email;

    @Column(name = "ADRESSE", length = 45)
    private String adresse;

    @Column(name = "VILLE", length = 42)
    private String ville;

    @Column(name = "QUARTIER", length = 45)
    private String quartier;

    @Column(name = "SECTEUR_ACTIVITE", length = 45)
    private String secteurActivite;

    @Column(name = "TYPE", length = 45)
    private String type;

    @Column(name = "PAYS", length = 45)
    private String pays;

    @Column(name="TELEPHONE")
    private String telephone;
}


