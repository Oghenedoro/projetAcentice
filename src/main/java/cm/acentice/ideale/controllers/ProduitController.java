package cm.acentice.ideale.controllers;

import cm.acentice.ideale.entities.Produit;
import cm.acentice.ideale.repositories.ProduitRepos;
import cm.acentice.ideale.services.ProduitService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/v1")
@RestController
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitRepos produitRepos, ProduitService produitService) {
        this.produitService = produitService;
    }
    @RequestMapping(value = "/produits", method = RequestMethod.POST)
    public Produit create(@RequestBody Produit produit){
        return produitService.create(produit);
    }

    @RequestMapping(value = "/produits", method = RequestMethod.GET)
    List<Produit> getAllProduits(){
        return produitService.getAllProduits();
    }
}
