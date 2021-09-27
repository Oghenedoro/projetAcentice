package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "Approvisionnement_Produit_Finis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ApprovisionnementProduitFinis {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "RECEPTIONNIST",length = 45)
    private String receptionist;

    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "DATE_APPROVISIONNEMENT")
    private LocalDateTime dateApprovisionnement;

    @ManyToOne
    @JoinColumn(name = "idSiteDeVente")
    private SiteDeVente siteDeVente;

    @ManyToOne
    @JoinColumn(name = "idSiteDeProduction")
    private SiteDeProduction siteDeProduction;

    @OneToMany(mappedBy = "approvisionnementProduitFinis",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ApprovissionnementPFHasProduit>approvissionnementPFHasProduits;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;
}
