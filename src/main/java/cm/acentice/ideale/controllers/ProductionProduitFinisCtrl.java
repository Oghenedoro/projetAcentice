package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.ProductionProduitFinisDto;
import cm.acentice.ideale.entities.ProductionProduitFinis;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.services.ProductionProduitFinisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/v1")
@RestController
public class ProductionProduitFinisCtrl {

    private final ProductionProduitFinisService productionProduitFinisService;

    public ProductionProduitFinisCtrl(ProductionProduitFinisService productionProduitFinisService) {
        this.productionProduitFinisService = productionProduitFinisService;
    }

    @RequestMapping(value = "/productionproduitfinis", method = RequestMethod.POST)
    public ProductionProduitFinis create(@RequestBody ProductionProduitFinisDto produitFinisDto)throws ResourceNotFoundException {
        return productionProduitFinisService.create(produitFinisDto);
    }
    @RequestMapping(value = "/productionproduitfinis", method = RequestMethod.GET)
    List<ProductionProduitFinis> getAllProduitFinis(){
        return productionProduitFinisService.getAllProduitFinis();
    }
}
