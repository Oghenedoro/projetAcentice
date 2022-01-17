package cm.acentice.ideale.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "PRODUCTION_RESULT")
public class ProductionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idProduction;
    private String refProduit;
    private int quantiteFabriquee;
    private double poidsBobine;
    private int numeroSerie;
    private int numeroLot;
    private Long idSiteDeProduction;

    @OneToMany(cascade = CascadeType.ALL)
    List<MatierePremiereConsumee> matierePremiereConsumees;


}
