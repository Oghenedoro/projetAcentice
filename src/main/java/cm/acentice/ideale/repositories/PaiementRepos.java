package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementRepos extends JpaRepository<Paiement, Long> {
   // List<Paiement> findByIdCommande(Long idCommande);
    List<Paiement> findByIdCommande(Long idCommande);
}
