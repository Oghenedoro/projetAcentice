package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
public class ApprovisionnementMatieresPremieresDto {

    private String id;
    private Date  dateApprovisionnement;
    private String description;
    private double prixUnitaireHT;
    private double prixUnitaireTTC;
    private double montantTVA;
    private double tauxTVA;
    private String devise;
    private String libelle;
    //private MatierePremiere matierePremiere;
    //private User user;
    private SiteDeProduction siteDeProduction;
    private List<ApprovHasMatierePremiere> approvHasMatierePremieres;

}
