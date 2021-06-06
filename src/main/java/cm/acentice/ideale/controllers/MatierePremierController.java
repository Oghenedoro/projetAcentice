package cm.acentice.ideale.controllers;

import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.MatierePremiereCat;
import cm.acentice.ideale.dto.MatierePremCatDto;
import cm.acentice.ideale.dto.MatierePremDto;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.services.MatierePremiereService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequestMapping("/v1")
@RestController
public class MatierePremierController {

    final MatierePremiereService matierePremiereService;

    public MatierePremierController(MatierePremiereService matierePremiereService) {
        this.matierePremiereService = matierePremiereService;
    }

    @PostMapping("/matieres-premieres")
    public MatierePremiere createMatPremier(@RequestBody MatierePremiere matierePremiere) throws ParseException {
        return matierePremiereService.create(matierePremiere);
    }

    @GetMapping("/matieres-premieres")
    List<MatierePremiere> getAllList() {
        return matierePremiereService.getAllMatierePremiere();
    }

    @DeleteMapping("/matieres-premieres/{reference}")
    public ResponseEntity<Void> supprimerMatierePremiere(@PathVariable String reference) throws ResourceNotFoundException {
        matierePremiereService.supprimerMatierePremiere(reference);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/matieres-premieres/{reference}")
    public MatierePremiere getMatremiereByRef(@PathVariable String reference) throws ResourceNotFoundException {
        return matierePremiereService.getMatierePremiereByReference(reference);
    }

    @GetMapping("matieres-premieress/{catId}")
    public List<MatierePremiere> getMatierePremierByCat(@PathVariable Long catId) {
        return matierePremiereService.getMatirePremiereByCat(catId);
    }

    @PutMapping("/matieres-premieres/{reference}")
    public MatierePremiere upDateMatPremier(@RequestBody MatierePremDto matierePremDto, @PathVariable String reference) throws ParseException {
        return matierePremiereService.update(matierePremDto, reference);
    }


    @PostMapping("/matieres-categoriemp")
    public MatierePremiereCat createMatierePremiereCat(@RequestBody MatierePremiereCat matierePremiereCat) throws ParseException {
        return matierePremiereService.createMatierePremiereCat(matierePremiereCat);
    }

    @GetMapping("/matieres-categoriemp")
    List<MatierePremiereCat> getAllCat() {
        return matierePremiereService.getAllCat();
    }

    @PutMapping("/matieres-categoriemp/{id}")
    public MatierePremiereCat update(@RequestBody MatierePremCatDto matierePremCatDto, @PathVariable Long id) throws ParseException {
        return matierePremiereService.upDateMatPremierCat(matierePremCatDto, id);
    }
}
