package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Table(name="FOURNISSEUR_has_MATIERE_PREMIERE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FournisseurMP extends Fournisseur{

    @Column(name = "ID",length = 45)
    @GenericGenerator(name="fournisseurMP", strategy = "cm.acentice.ideale.utils.GeneratorIdFournisseurMP")
    @GeneratedValue(generator = "fournisseurMP")
    private String id;

    @Column(name = "MATIERE_PREMIERE_reference", length = 45)
    private String matierePremierRef;
}
