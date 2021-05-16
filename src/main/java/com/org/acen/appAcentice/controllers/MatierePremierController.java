package com.org.acen.appAcentice.controllers;

import com.org.acen.appAcentice.entities.MatierePremiere;
import com.org.acen.appAcentice.entities.MatierePremiereCat;
import com.org.acen.appAcentice.models.MatierePremDto;
import com.org.acen.appAcentice.services.MatierePremiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequestMapping("/api")
@RestController
public class MatierePremierController {

    @Autowired
    MatierePremiereService matierePremiereService;

    @PostMapping("/v1/matieres-premieres/{type}")
    public MatierePremiere createMatPremier(@RequestBody MatierePremiere matierePremier, @PathVariable String type) throws ParseException {
        return matierePremiereService.createMatirePrem(matierePremier, type);
    }

    @PostMapping("/v1/piece-categoriemp")
    public MatierePremiereCat createMatierePremiereCat(@RequestBody MatierePremiereCat matierePremiereCat) throws ParseException {
        return matierePremiereService.createMatierePremiereCat(matierePremiereCat);
    }

    @GetMapping("/v1/matieres-premieres")
    List<MatierePremiere> getAllList(){
        return matierePremiereService.getAllMatierePremiere();
    }

    @DeleteMapping("/v1/matieres-premieres/{idMatPremier}")
    public boolean supprimerMatierePremiere(@PathVariable String idMatPremier){

        matierePremiereService.supprimerMatierePremiere(idMatPremier);
        return true;
    }

    @GetMapping("/v1/matieres-premieres/{idMatPremier}")
    public MatierePremiere getMatremiereByRef(@PathVariable String idMatPremier){

        return matierePremiereService.getMatPremByReference(idMatPremier);

    }

    @PutMapping("/v1/matieres-premieres/{ref}")
    public MatierePremiere upDateMatPremier(@RequestBody MatierePremDto matierePremDto, @PathVariable String ref) throws ParseException {

        return matierePremiereService.upDateMatPremier(matierePremDto, ref);
    }


}
