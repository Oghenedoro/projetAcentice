package com.org.acen.appAcentice.controllers;

import com.org.acen.appAcentice.entities.MatierePremiere;
import com.org.acen.appAcentice.services.StockProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RequestMapping("/api")
@RestController
public class MatierePremierController {

    @Autowired
    StockProductionService productionService;

    @RequestMapping("/v1/matieres-premieres")
    public MatierePremiere createMatPremier(@RequestBody MatierePremiere matierePremier) throws ParseException {
        return productionService.createMatirePrem(matierePremier);
    }
}
