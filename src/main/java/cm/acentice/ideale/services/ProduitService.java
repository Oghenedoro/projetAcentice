package cm.acentice.ideale.services;

import cm.acentice.ideale.entities.Produit;
import cm.acentice.ideale.repositories.ProduitRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {

    private final ProduitRepos produitRepos;

    @Autowired
    public ProduitService(ProduitRepos produitRepos) {
        this.produitRepos = produitRepos;
    }

    public Produit create(Produit produit){
        return produitRepos.save(produit);
    }

    public List<Produit> getAllProduits(){
        return produitRepos.findAll();
    }
}
