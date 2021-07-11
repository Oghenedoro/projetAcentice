package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.FournisseurDto;
import cm.acentice.ideale.entities.*;
//import cm.acentice.ideale.entities.FournisseurPD;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.services.FournisseurService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RequestMapping(value = "/v1")
@RestController
public class FournisseurController {

    final FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    @RequestMapping(value = "/fournisseurs/pieces-detachees", method = RequestMethod.GET)
    public List<FournisseurPD> getFournisseursPieceDetachees() {
        return fournisseurService.getAllPieceDetaches();
    }
    @RequestMapping(value = "/fournisseurs/pieces-detachees/{ref}", method = RequestMethod.GET)
    public FournisseurPD getFournisseurPDByReference(@PathVariable String ref) {
        return fournisseurService.getFournisseurPDByReference(ref);
    }


    @RequestMapping(value = "/fournisseurs/pieces-detachees", method = RequestMethod.POST)
    public FournisseurPD createFournisseurPD(@RequestBody FournisseurDto fournisseurDto){
        return fournisseurService.createFournisseurPD(fournisseurDto);
    }

    @RequestMapping(value = "/fournisseurs/pieces-detachees/{id}", method = RequestMethod.PUT)
    public FournisseurPD updateFournisseurPD(@RequestBody FournisseurDto fournisseurDto,@PathVariable String id){
        return fournisseurService.updateFournisseurPD(fournisseurDto,id);
    }

    @RequestMapping(value = "/fournisseurs/pieces-detachees/{id}", method = RequestMethod.DELETE)
    public boolean supprimerFournisseurPD(@PathVariable String id) {
        return fournisseurService.supprimerFournisseurPD(id);
    }


    @RequestMapping(value = "/fournisseurs/matieres-premieres", method = RequestMethod.POST)
    public FournisseurMP createFournisseurMP(@RequestBody FournisseurDto fournisseurDto){
        return fournisseurService.createFournisseurMP(fournisseurDto);
    }

    @RequestMapping(value = "/fournisseurs/matieres-premieres/{id}", method = RequestMethod.PUT)
    public FournisseurMP updateFournisseurMP(@RequestBody FournisseurDto FournisseurDto, @PathVariable String id){
        return fournisseurService.updateFournisseurMP(FournisseurDto,id);
    }

    @RequestMapping(value = "/fournisseurs/matieres-premieres", method = RequestMethod.GET)
    public List<FournisseurMP> getAllMatierePremieres() {
        return fournisseurService.getAllMatierePremieres();
    }

    @RequestMapping(value = "/fournisseurs/matieres-premieres/{ref}", method = RequestMethod.GET)
    public Collection<FournisseurMP> getFournisseurMPByReference(@PathVariable String ref) throws ResourceNotFoundException {
        return fournisseurService.getFournisseurMPByMatPremReference(ref);
    }
    @RequestMapping(value = "/fournisseurs/matieres-premieres/{id}", method = RequestMethod.DELETE)
    public boolean supprimerFournisseurMP(@PathVariable String id) {
        return fournisseurService.supprimerFournisseurMP(id);
    }
    @RequestMapping(value = "/fournisseurs/matieres-premieres/mp/{id}", method = RequestMethod.GET)
    public FournisseurMP getFournisseurMP(@PathVariable String id) {
        return fournisseurService.getFournisseurMP(id);
    }

    @RequestMapping(value = "/fournisseurs/matieres-premieres/fournisseurs/{id}", method = RequestMethod.GET)
    List<MatierePremiere> findByFounisseurMatPremiereId (@PathVariable String id){
        return fournisseurService.getMatPremiereByFournisseurMPId(id);
    }
}
