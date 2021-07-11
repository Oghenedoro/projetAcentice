package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.MatierePremCatDto;
import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.MatierePremiereCat;
import cm.acentice.ideale.services.MatierePremiereCatService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequestMapping("/v1")
@RestController
public class MatierePremiereCatController {

    private final MatierePremiereCatService matierePremiereCatService;

    public MatierePremiereCatController(MatierePremiereCatService matierePremiereCatService) {
        this.matierePremiereCatService = matierePremiereCatService;
    }

    @GetMapping("/matieres-categories")
    List<MatierePremiereCat> getAllCat() {
        return matierePremiereCatService.getAllCat();
    }

    @PostMapping("/matieres-categories")
    public MatierePremiereCat createMatierePremiereCat(@RequestBody MatierePremiereCat matierePremiereCat) throws ParseException {
        return matierePremiereCatService.createMatierePremiereCat(matierePremiereCat);
    }

     @PutMapping("/matieres-categories")
    public MatierePremiereCat update(@RequestBody MatierePremCatDto matierePremCatDto) throws ParseException {
        return matierePremiereCatService.upDateMatPremierCat(matierePremCatDto);
    }
}
