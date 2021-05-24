package cm.acentice.ideale.controllers;

import cm.acentice.ideale.entities.Fournisseur;
import cm.acentice.ideale.entities.FournisseurMP;
import cm.acentice.ideale.entities.FournisseurPD;
import cm.acentice.ideale.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api")
@RestController
public class FournisseurController {

    @Autowired
    FournisseurService fournisseurService;

    @PostMapping("/v1/fournisseur/piecedetachee/{idPD}")
    public Fournisseur createFournisseurPD(@RequestBody FournisseurPD fournisseurPD, @PathVariable String idPD){

         return fournisseurService.createFournisseurPD(fournisseurPD,idPD);
    }

    @PostMapping("/v1/fournisseur/matierepm/{idMP}")
    public Fournisseur createFournisseurMP(@RequestBody FournisseurMP fournisseurMP, @PathVariable String idMP){

        return fournisseurService.createFournisseurMP(fournisseurMP,idMP);
    }


}
