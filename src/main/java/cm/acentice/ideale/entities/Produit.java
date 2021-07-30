package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private Long refProduit;
    @ManyToOne
    @JoinColumn(name = "refProduit")
    private StockProduitFinis stockProduitFinis;
    private double poids;
    private String description;
    private String couleur;
    private Date datePeremption;
}
