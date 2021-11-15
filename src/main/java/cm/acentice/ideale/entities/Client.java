package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;
    private String denomination;
    private String email;
    private String adresse;
    private String ville;
    private String quartier;
    private String secteur;
    private String type;
    private String codePromo;
    private String telephone1;
    private String telephone2;
    private String contactId;
   /* @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private List<Commande> commandes;
*/
}
