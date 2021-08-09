package cm.acentice.ideale.services;

import cm.acentice.ideale.entities.ProductionProduitFinis;
import cm.acentice.ideale.repositories.ProductionProduitFinisRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionProduitFinisService {

    private final ProductionProduitFinisRepos productionProduitFinisRepos;

    @Autowired
    public ProductionProduitFinisService(ProductionProduitFinisRepos productionProduitFinisRepos) {
        this.productionProduitFinisRepos = productionProduitFinisRepos;
    }

    public ProductionProduitFinis create(ProductionProduitFinis produitFinis){
        return productionProduitFinisRepos.save(produitFinis);
    }
    public List<ProductionProduitFinis>getAllProduitFinis(){
        return productionProduitFinisRepos.findAll();
    }
}
