package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.HistoriqueStockProduitsFinis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventaireProduitsFinisRepos extends JpaRepository<HistoriqueStockProduitsFinis, Long> {
}
