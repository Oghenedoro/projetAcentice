package cm.acentice.ideale.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductionDto {

    private Long id;
    private LocalDateTime dateHeureDebutProduction;
    private LocalDateTime dateHeureFinProduction;
    private double poidsBobine;
    private double indexCompteurDebut;
    private double indexCompteurFin;
    private Long idUser;
}
