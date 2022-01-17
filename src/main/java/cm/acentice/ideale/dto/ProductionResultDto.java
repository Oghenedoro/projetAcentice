package cm.acentice.ideale.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductionResultDto {

    private Long id;
    private Long idProduction;
    private String refProduit;
    private int quantiteFabriquee;
    private double poidsBobine;
    private int numeroSerie;
    private int numeroLot;
    private Long idSiteDeProduction;
    List<MatierePremiereConsumeeDto> matierePremiereConsumeesDtos;
}
