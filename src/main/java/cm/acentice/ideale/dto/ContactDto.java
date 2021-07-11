package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.FournisseurMP;
import cm.acentice.ideale.entities.FournisseurPD;
import lombok.Data;

@Data
public class ContactDto {

    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String ville;
    private String codePostale;
    private String quartier;
    private String pays;
    private String telephone1;
    private String telephone2;
    private String fonction;
    private FournisseurMP fournisseurMP;
    private FournisseurPD fournisseurPD;

}
