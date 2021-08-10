package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.FournisseurMP;
import cm.acentice.ideale.entities.MatierePremiereCat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
public class MatierePremDto {
    private String reference;
    private Long idMPCat;
    private String libelle;
    private String description;
    private Date dateAchat;
    private Date expirationDate;
    private double prixAchat;
    private int quantiteMP;
    private List<FournisseurMP> fournisseurMPs;

}
