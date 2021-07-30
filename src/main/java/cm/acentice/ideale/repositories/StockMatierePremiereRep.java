package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.StockMatierePremiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockMatierePremiereRep extends JpaRepository<StockMatierePremiere, String> {

   // @Query("Select smp from StockMatierePremiere smp where matierePremiere = ?1")
   // @Modifying
//    @Query("UPDATE StockMatierePremiere s set s.idStockMP = :idstockmp where matierePremiere = :matierePremiere")
    public StockMatierePremiere findByMatierePremiere(MatierePremiere matierePremiere);
}
