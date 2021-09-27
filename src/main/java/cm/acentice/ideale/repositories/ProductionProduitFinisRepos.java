package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.ProductionProduitFinis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionProduitFinisRepos extends JpaRepository<ProductionProduitFinis, Long> {

    /*public ProductionProduitFinis findByRefMatierePremiere(String mpRef);
    public ProductionProduitFinis findByRefProduitsFinis(String idProduitFini);*/

}
