package cm.acentice.ideale.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockMatierePremiere {

    @Column(name = "idStockMP",length = 45)
    @GenericGenerator(name="stockmpId", strategy = "cm.acentice.ideale.utils.GeneratorIdStockMatierePremiere")
    @GeneratedValue(generator = "stockmpId")
    @Id
    private String idStockMP;
    private int quantite;
    private int quantité_Min;
    @Column(name = "Date_derniere_MAJ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDerniereMaj;
    private String refMP;

   /* @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "idMP",referencedColumnName = "reference")*/
   /* @Transient
    private MatierePremiere matierePremiere;
*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockMatierePremiere)) return false;
        StockMatierePremiere that = (StockMatierePremiere) o;
        return quantite == that.quantite && quantité_Min == that.quantité_Min && Objects.equals(idStockMP, that.idStockMP) && Objects.equals(dateDerniereMaj, that.dateDerniereMaj) && Objects.equals(refMP, that.refMP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStockMP, quantite, quantité_Min, dateDerniereMaj, refMP);
    }
}
