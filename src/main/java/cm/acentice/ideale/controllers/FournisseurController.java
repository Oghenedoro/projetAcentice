package cm.acentice.ideale.controllers;

import cm.acentice.ideale.entities.FournisseurMP;
import cm.acentice.ideale.entities.FournisseurPD;
import cm.acentice.ideale.services.FournisseurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/v1")
@RestController
public class FournisseurController {

    final FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    @PostMapping(value = "/fournisseurs/pieces-detachees", consumes = {"application/json"})
    public FournisseurPD createFournisseurPD(@RequestBody FournisseurPD fournisseurPD){
         return fournisseurService.createFournisseurPieceDetachee(fournisseurPD);
    }

    @PostMapping("/fournisseurs/matieres-premieres")
    public FournisseurMP createFournisseurMP(@RequestBody FournisseurMP fournisseurMP){
        return fournisseurService.createFournisseurMatierePremiere(fournisseurMP);
    }

    @GetMapping("/fournisseurs/matieres-premieres")
    public List<FournisseurMP> getFournisseursMatierePremieres() {
        return fournisseurService.getAllMatierePremieres();
    }

    @GetMapping("/fournisseurs/pieces-detachees")
    public List<FournisseurPD> getFournisseursPieceDetachees() {
        return fournisseurService.getAllPieceDetaches();
    }


}
