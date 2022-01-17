package cm.acentice.ideale.dto;

import lombok.Data;

@Data
public class MatierePremiereConsumeeDto {

    private Long id;
    private Long idProductionResult;
    private String refMatierePremiere;
    private int quantiteMPUtilisee;
}
