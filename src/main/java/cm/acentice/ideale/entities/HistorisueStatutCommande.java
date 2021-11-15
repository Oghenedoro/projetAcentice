package cm.acentice.ideale.entities;

import cm.acentice.ideale.constants.CommandeStatut;
import cm.acentice.ideale.constants.TypeMovementStock;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HistorisueStatutCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long commandeId;
    @Enumerated(EnumType.STRING)
    private CommandeStatut commandeStatut;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateChangementStatut;
}
