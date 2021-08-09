package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.Produit;
import cm.acentice.ideale.entities.StockProduitFinis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockProduitFinisRepos extends JpaRepository<StockProduitFinis, Long> {

    StockProduitFinis findByProduit(Produit produit);
}
