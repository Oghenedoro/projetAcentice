package cm.acentice.ideale.controllers;

import cm.acentice.ideale.entities.ProductionProduitFinis;
import cm.acentice.ideale.services.ProductionProduitFinisService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/v1")
@RestController
public class ProductionProduitFinisCtrl {

    private final ProductionProduitFinisService productionProduitFinisService;

    public ProductionProduitFinisCtrl(ProductionProduitFinisService productionProduitFinisService) {
        this.productionProduitFinisService = productionProduitFinisService;
    }

    @RequestMapping(value = "/produitfinis", method = RequestMethod.POST)
    public ProductionProduitFinis create(@RequestBody ProductionProduitFinis produitFinis){
        return productionProduitFinisService.create(produitFinis);
    }
    @RequestMapping(value = "/produitfinis", method = RequestMethod.GET)
    List<ProductionProduitFinis> getAllProduitFinis(){
        return productionProduitFinisService.getAllProduitFinis();
    }
}
