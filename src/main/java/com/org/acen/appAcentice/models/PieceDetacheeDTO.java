package com.org.acen.appAcentice.models;

import com.org.acen.appAcentice.entities.PieceDetacheeCat;
import lombok.Data;

import java.util.Date;

@Data
public class PieceDetacheeDTO {

    private String reference;
    private String libelle;
    private String discription;
    private Date dateDachat;
    private String dureeDeVie;
    private PieceDetacheeCat categorie;
    private double prixDachat;
}
