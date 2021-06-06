package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.MatierePremiereCat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatierePremiereCatRepository extends JpaRepository<MatierePremiereCat, Long> {
    MatierePremiereCat findByName(String name);
}
