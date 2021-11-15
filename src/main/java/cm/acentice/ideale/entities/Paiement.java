package cm.acentice.ideale.entities;

import cm.acentice.ideale.constants.MoyenPaiement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime datePaiement;
    private double montantPaye;
    private double montantDeLaCommande;
    private double montantRestant;
    @Enumerated(EnumType.STRING)
    private MoyenPaiement moyenPaiement;
    private Long idCommande;
}
