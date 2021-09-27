package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.ProduitDto;
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
    @RequestMapping(value = "/produits", method = RequestMethod.POST)
    public ProduitDto create(@RequestBody ProduitDto produitDto) throws ResourceNotFoundException {
        return produitService.create(produitDto);
    }

    @RequestMapping(value = "/produits", method = RequestMethod.GET)
    List<Produit> getAllProduits(){
        return produitService.getAllProduits();
    }
}
