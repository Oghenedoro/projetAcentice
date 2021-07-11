package cm.acentice.ideale.entities;

import cm.acentice.ideale.constants.TypeFournisseur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@MappedSuperclass
@Data
public abstract class  Fournisseur implements Serializable {

    @Id
    @Column(name = "ID",length = 45)
    @GenericGenerator(name="fournisseurId", strategy = "cm.acentice.ideale.utils.GeneratorIdFournisseur")
    @GeneratedValue(generator = "fournisseurId")
    private String id;

    @Column(name = "DENOMINATION", length = 125)
    protected String denomination;

    @Column(name = "EMAIL", length = 45)
    protected String email;

    @Column(name = "ADRESSE", length = 45)
    protected String adresse;

    @Column(name = "VILLE", length = 42)
    protected String ville;

    @Column(name = "QUARTIER", length = 45)
    protected String quartier;

    @Column(name = "SECTEUR_ACTIVITE", length = 45)
    protected String secteurActivite;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 45)
    protected TypeFournisseur type;

    @Column(name = "PAYS", length = 45)
    protected String pays;

    @Column(name="TELEPHONE")
    protected String telephone;

}


