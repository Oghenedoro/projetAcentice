package cm.acentice.ideale.controllers;

import cm.acentice.ideale.entities.PieceDetachee;
import cm.acentice.ideale.entities.PieceDetacheeCat;
import cm.acentice.ideale.dto.PieceDetacheeCatDto;
import cm.acentice.ideale.dto.PieceDetacheeDTO;
import cm.acentice.ideale.services.PieceDetacheeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequestMapping("/api")
@RestController
public class PieceDetacheeController {

    @Autowired
    PieceDetacheeService pieceDetacheeService;

    @PostMapping("/v1/piece-detachees/{type}")
    public PieceDetachee createPieceDetachee(@RequestBody PieceDetachee pieceDetachee, @PathVariable String type) throws ParseException {
        return pieceDetacheeService.createPieceDetachee(pieceDetachee, type);
    }

    @GetMapping("/v1/piece-detacheess/{catId}")
    public List<PieceDetachee> getPieceDetacheeByCat(@PathVariable Long catId){
        return pieceDetacheeService.getPieceDetacheeByCat(catId);
    }

    @PutMapping("/v1/piece-detachees/{ref}")
    public PieceDetachee upDatePieceDetachee(@RequestBody PieceDetacheeDTO pieceDetacheeDTO, @PathVariable String ref) throws ParseException {

        return pieceDetacheeService.upDatePieceDetachee(pieceDetacheeDTO, ref);
    }

    @GetMapping("/v1/piece-detachees/{ref}")
    public PieceDetachee getPieceDetacheeByRef(@PathVariable String ref){

        return pieceDetacheeService.getPieceDetacheeByReference(ref);

    }

    @GetMapping("/v1/piece-detachees")
    List<PieceDetachee> getAllList(){
        return pieceDetacheeService.getAllPieceDetachee();
    }

    @DeleteMapping("/v1/piece-detachees/{idMatPremier}")
    public boolean supprimerPieceDetachee(@PathVariable String idMatPremier){

        pieceDetacheeService.supprimerPieceDetachee(idMatPremier);
        return true;
    }

    @PostMapping("/v1/piece-categoriepd")
    public PieceDetacheeCat createPieceDetacheeCat(@RequestBody PieceDetacheeCat pieceDetacheeCat) throws ParseException {
        return pieceDetacheeService.createPieceDetacheeCat(pieceDetacheeCat);
    }

    @GetMapping("/v1/piece-categoriepd")
    List<PieceDetacheeCat> getAllCat(){
        return pieceDetacheeService.getAllCat();
    }

    @PutMapping("/v1/piece-categoriepd/{id}")
    public PieceDetacheeCat update(@RequestBody PieceDetacheeCatDto pieceDetacheeCatDto, @PathVariable Long id) throws ParseException {
        return pieceDetacheeService.upDatePieceDetacheeCat(pieceDetacheeCatDto, id);
    }
}
