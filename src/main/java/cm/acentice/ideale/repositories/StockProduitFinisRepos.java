package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.Produit;
import cm.acentice.ideale.entities.StockProduitFinis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockProduitFinisRepos extends JpaRepository<StockProduitFinis, Long> {

    StockProduitFinis findByProduit(Produit produit);

    @Query("Select s from StockProduitFinis s where s.produit.id = 1 ")
    List<StockProduitFinis>getStockProdByProduit(String idproduit);
}
