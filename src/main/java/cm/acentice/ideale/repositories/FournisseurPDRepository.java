package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.FournisseurPD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FournisseurPDRepository extends JpaRepository<FournisseurPD, String> {
}
