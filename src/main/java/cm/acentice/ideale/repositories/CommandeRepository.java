package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.Commande;
import cm.acentice.ideale.entities.HistorisueStatutCommande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande,Long> {
}
