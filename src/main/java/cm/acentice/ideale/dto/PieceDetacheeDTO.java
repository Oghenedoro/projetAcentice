package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.PieceDetacheeCat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
public class PieceDetacheeDTO {

    private String reference;
    private String libelle;
    private String description;
    private Date dateAchat;
    private int dureeDeVie;
    private double prixAchat;
    private PieceDetacheeCat categorie;
    private String idFournisseurPD;
    private Long idPDCat;
}
