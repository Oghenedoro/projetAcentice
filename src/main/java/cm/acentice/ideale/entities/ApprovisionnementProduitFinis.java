package cm.acentice.ideale.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
    @Column(name = "DATE_APPROVISIONNEMENT")
    private LocalDate dateApprovisionnement;

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
