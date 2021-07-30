package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="APPROV_HAS_MATIEREPREMIERE")
public class ApprovHasMatierePremiere {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantité_MP_Approv")
    private int quantitéMPApprove;

    @ManyToOne
    @JoinColumn(name = "id_MP",referencedColumnName = "reference")
    private MatierePremiere matierePremiere;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_APPROV_MP",referencedColumnName = "id")
    private ApprovisionnementMatieresPremieres approvisionnementMatieresPremiere;

}
