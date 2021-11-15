package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.Commande;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class LigneDeCommandeDto {

    private Long id;
    private Long idcommande;
    private String refProduit;
    private double prixUnitaire;
    private int quantity;
}
