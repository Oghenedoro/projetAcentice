package com.org.acen.appAcentice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class PieceDetacheeCat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE", length = 45)
    private String type;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categorie", fetch = FetchType.LAZY)
    private List<PieceDetachee> piecedetachees;
}
