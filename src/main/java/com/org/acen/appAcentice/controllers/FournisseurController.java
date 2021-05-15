package com.org.acen.appAcentice.controllers;

import com.org.acen.appAcentice.entities.Fournisseur;
import com.org.acen.appAcentice.entities.FournisseurMP;
import com.org.acen.appAcentice.entities.FournisseurPD;
import com.org.acen.appAcentice.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api")
@RestController
public class FournisseurController {

    @Autowired
    FournisseurService fournisseurService;

    @RequestMapping("/v1/fournisseur/piecedetachee/{idPD}")
    public Fournisseur createFournisseurPD(@RequestBody FournisseurPD fournisseurPD, @PathVariable String idPD){

         return fournisseurService.createFournisseurPD(fournisseurPD,idPD);
    }

    @RequestMapping("/v1/fournisseur/matierepm/{idMP}")
    public Fournisseur createFournisseurMP(@RequestBody FournisseurMP fournisseurMP, @PathVariable String idMP){

        return fournisseurService.createFournisseurMP(fournisseurMP,idMP);
    }

}
