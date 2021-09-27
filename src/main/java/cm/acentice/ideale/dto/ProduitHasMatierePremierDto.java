package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.Produit;
import lombok.Data;

import javax.persistence.*;

@Data
public class ProduitHasMatierePremierDto {


    private Long idProdHasMP;
    private int quantitéProduitsFinis;
    private int quantitéMPUtilise;
    private MatierePremiere matierePremiere;
    private Produit produit;
}
