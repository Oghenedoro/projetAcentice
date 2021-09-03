package cm.acentice.ideale.services;

import cm.acentice.ideale.entities.ProductionProduitFinis;
import cm.acentice.ideale.entities.Produit;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.ProductionProduitFinisRepos;
import cm.acentice.ideale.repositories.ProduitRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    private final ProduitRepos produitRepos;
    private final ProductionProduitFinisRepos productionProduitFinisRepos;

   // @Autowired
    public ProduitService(ProduitRepos produitRepos, ProductionProduitFinisRepos productionProduitFinisRepos) {
        this.produitRepos = produitRepos;
        this.productionProduitFinisRepos = productionProduitFinisRepos;
    }

    public Produit create(Produit produit, Long idProdProduitFinis) throws ResourceNotFoundException {
        Optional<ProductionProduitFinis> produitFinis = productionProduitFinisRepos.findById(idProdProduitFinis);
        if(!produitFinis.isPresent()){
            throw new ResourceNotFoundException("ProductionProduitFinis not found !");
        }
        produit.setId(produitFinis.get().getRefProduitsFinis());
        produit.setQuantiteFabrique(produitFinis.get().getQuantitéProduitsFinis());
        return produitRepos.save(produit);
    }

    public List<Produit> getAllProduits(){
        return produitRepos.findAll();
    }
}
