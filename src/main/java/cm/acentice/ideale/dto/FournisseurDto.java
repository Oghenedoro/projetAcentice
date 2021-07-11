package cm.acentice.ideale.dto;

import cm.acentice.ideale.constants.TypeFournisseur;
import cm.acentice.ideale.entities.Contact;
import cm.acentice.ideale.entities.MatierePremFournisseurMP;
import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.PieceDetachee;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
public class FournisseurDto {

    private String id;
    protected String denomination;
    protected String email;
    protected String adresse;
    protected String ville;
    protected String quartier;
    protected String secteurActivite;
    protected TypeFournisseur type;
    protected String pays;
    protected String telephone;
    private List<Contact> contacts;
    private List<MatierePremiere> matierePremieres;
    private List<PieceDetachee> pieceDetachees;
    private List<MatierePremFournisseurMP> matierePremFournisseurMPS;

}
