package cm.acentice.ideale.controllers;

import cm.acentice.ideale.entities.Produit;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.ProduitRepos;
import cm.acentice.ideale.services.ProduitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/v1")
@RestController
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitRepos produitRepos, ProduitService produitService) {
        this.produitService = produitService;
    }
    @RequestMapping(value = "/produits/{idProdProduitFinis}", method = RequestMethod.POST)
    public Produit create(@RequestBody Produit produit, @PathVariable Long idProdProduitFinis) throws ResourceNotFoundException {
        return produitService.create(produit,idProdProduitFinis);
    }

    @RequestMapping(value = "/produits", method = RequestMethod.GET)
    List<Produit> getAllProduits(){
        return produitService.getAllProduits();
    }
}
