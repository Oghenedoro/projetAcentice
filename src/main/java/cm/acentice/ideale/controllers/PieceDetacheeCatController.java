package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.PieceDetacheeCatDto;
import cm.acentice.ideale.entities.PieceDetacheeCat;
import cm.acentice.ideale.services.PieceDetacheeCatService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequestMapping("/v1")
@RestController
public class PieceDetacheeCatController {

    private final PieceDetacheeCatService pieceDetacheeCatService;

    public PieceDetacheeCatController(PieceDetacheeCatService pieceDetacheeCatService) {
        this.pieceDetacheeCatService = pieceDetacheeCatService;
    }


    //@PostMapping("/piece-categoriepd")
    @RequestMapping(value = "/pieces-categories", method = RequestMethod.POST)
    public PieceDetacheeCat createPieceDetacheeCat(@RequestBody PieceDetacheeCat pieceDetacheeCat) throws ParseException {
        return pieceDetacheeCatService.createPieceDetacheeCat(pieceDetacheeCat);
    }

    @GetMapping("/pieces-categories")
    List<PieceDetacheeCat> getAllCat(){
        return pieceDetacheeCatService.getAll();
    }

   @PutMapping("/pieces-categories")
    public PieceDetacheeCat update(@RequestBody PieceDetacheeCatDto pieceDetacheeCatDto) throws ParseException {
        return pieceDetacheeCatService.upDatePieceDetacheeCat(pieceDetacheeCatDto);
    }
}
