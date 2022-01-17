package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.StockMatierePremiere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockMatierePremiereRep extends JpaRepository<StockMatierePremiere, String> {

      StockMatierePremiere findByRefMP(String refMP);
}
