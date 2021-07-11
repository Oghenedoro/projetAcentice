package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.FournisseurMP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FournisseurMPRepository extends JpaRepository<FournisseurMP, String> {
}
