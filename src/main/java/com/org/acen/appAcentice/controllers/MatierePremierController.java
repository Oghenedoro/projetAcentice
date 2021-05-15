package com.org.acen.appAcentice.controllers;

import com.org.acen.appAcentice.entities.MatierePremiere;
import com.org.acen.appAcentice.entities.MatierePremiereCat;
import com.org.acen.appAcentice.services.MatierePremiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RequestMapping("/api")
@RestController
public class MatierePremierController {

    @Autowired
    MatierePremiereService matierePremiereService;

    @RequestMapping("/v1/matieres-premieres/{type}")
    public MatierePremiere createMatPremier(@RequestBody MatierePremiere matierePremier, @PathVariable String type) throws ParseException {
        return matierePremiereService.createMatirePrem(matierePremier, type);
    }

    @RequestMapping("/v1/piece-categoriemp")
    public MatierePremiereCat createMatierePremiereCat(@RequestBody MatierePremiereCat matierePremiereCat) throws ParseException {
        return matierePremiereService.createMatierePremiereCat(matierePremiereCat);
    }

}
