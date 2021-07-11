package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contact{

    @Column(name = "id",length = 45)
    @GenericGenerator(name="contactId", strategy = "cm.acentice.ideale.utils.GeneratorIdContact")
    @GeneratedValue(generator = "contactId")
    @Id
    private String id;

    @Column(name = "NOM", length = 45)
    private String nom;

    @Column(name = "PRENOM", length = 45)
    private String prenom;
    @Column(name = "EMAIL", length = 45)
    private String email;

    @Column(name = "ADRESSE", length = 45)
    private String adresse;

    @Column(name = "VILLE", length = 42)
    private String ville;

    @Column(name = "CODE_POSTALE", length = 42)
    private String codePostale;

    @Column(name = "QUARTIER", length = 45)
    private String quartier;

    @Column(name = "PAYS", length = 45)
    private String pays;

    @Column(name="TELEPHONE1")
    private String telephone1;

    @Column(name="TELEPHONE2")
    private String telephone2;

    @Column(name="FONCTION")
    private String fonction;

   // @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idFMP",referencedColumnName = "id")
    private FournisseurMP fournisseurMP;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE,targetEntity =FournisseurPD.class )
    @JoinColumn(name = "idFPD",referencedColumnName = "id")
    private FournisseurPD fournisseurPD;

}
