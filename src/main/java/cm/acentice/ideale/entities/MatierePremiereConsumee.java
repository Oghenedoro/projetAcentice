package cm.acentice.ideale.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "MATIERE_PREMIERE_CONSUMEE")
public class MatierePremiereConsumee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idProductionResult;
    private String refMatierePremiere;
    private int quantiteMPUtilisee;
}
