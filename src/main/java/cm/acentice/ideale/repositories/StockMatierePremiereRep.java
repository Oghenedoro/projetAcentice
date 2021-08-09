package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.StockMatierePremiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMatierePremiereRep extends JpaRepository<StockMatierePremiere, String> {

      public StockMatierePremiere findByMatierePremiere(MatierePremiere matierePremiere);
}
