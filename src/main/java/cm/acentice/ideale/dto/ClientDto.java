package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.Commande;
import lombok.Data;
import java.util.List;

@Data
public class ClientDto {

    private Long idClient;
    private String denomination;
    private String email;
    private String adresse;
    private String ville;
    private String quartier;
    private String secteur;
    private String type;
    private String codePromo;
    private String telephone1;
    private String telephone2;
    private String contactId;
    private List<Commande> commandes;
}
