package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "Approvisionnement_Matieres_Premieres")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ApprovisionnementMatieresPremieres {

    @GenericGenerator(name="apprmpId", strategy = "cm.acentice.ideale.utils.GeneratorIdApprovisionnementMP")
    @GeneratedValue(generator = "apprmpId")
    @Id
    private String id;
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date  dateApprovisionnement;
    private String devise;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Id_Site_production")
    private SiteDeProduction siteDeProduction;

   @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "approvisionnementMatieresPremiere")
    private List<ApprovHasMatierePremiere> approvHasMatierePremieres;

}
