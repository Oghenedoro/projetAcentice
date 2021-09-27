package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.ProduitHasMatierePremier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitHasMatierePremierRepos extends JpaRepository<ProduitHasMatierePremier, String> {
    ProduitHasMatierePremier findByProduit(String refProduit);
}
