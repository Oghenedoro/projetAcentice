package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Table(name="FOURNISSEUR_has_PIECE_DETACHEE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FournisseurPD extends Fournisseur{

    @Column(name = "ID",length = 45)
    @GenericGenerator(name="fournisseurPD", strategy = "com.org.acen.appAcentice.utils.GeneratorIdFournisseurPD")
    @GeneratedValue(generator = "fournisseurPD")
    private String id;

    @Column(name = "PIECEDETACHEE_reference", length = 45)
    private String pieceDetacheeRef;
}
