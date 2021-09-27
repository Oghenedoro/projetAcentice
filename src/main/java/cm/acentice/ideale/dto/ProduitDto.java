package cm.acentice.ideale.dto;

import cm.acentice.ideale.entities.ApprovissionnementPFHasProduit;
import cm.acentice.ideale.entities.StockProduitFinis;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Data
public class ProduitDto {

    private String id;
    private double poids;
    private String description;
    private String couleur;
    private Date datePeremption;
    private int quantiteFabrique;
    private List<StockProduitFinis> stockProduitFinisList;
    private List<ApprovissionnementPFHasProduit>approvissionnementPFHasProduits;
}
