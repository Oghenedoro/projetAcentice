package com.org.acen.appAcentice.models;

import com.org.acen.appAcentice.entities.MatierePremiereCat;
import lombok.Data;

import java.util.Date;

@Data
public class MatierePremDto {

    private String reference;
    private String libelle;
    private double prixDachat;
    private Date dateDachat;
    private Date expirationDate;
    private MatierePremiereCat categorie;
    private String discription;

}
