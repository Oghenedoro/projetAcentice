package cm.acentice.ideale.controllers;

import cm.acentice.ideale.entities.PieceDetachee;
import cm.acentice.ideale.entities.PieceDetacheeCat;
import cm.acentice.ideale.dto.PieceDetacheeCatDto;
import cm.acentice.ideale.dto.PieceDetacheeDTO;
import cm.acentice.ideale.services.PieceDetacheeService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

@RequestMapping("/v1")
@RestController
public class PieceDetacheeController {

    final
    PieceDetacheeService pieceDetacheeService;

    public PieceDetacheeController(PieceDetacheeService pieceDetacheeService) {
        this.pieceDetacheeService = pieceDetacheeService;
    }

    @PostMapping("/piece-detachees")
    public PieceDetachee createPieceDetachee(@RequestBody PieceDetachee pieceDetachee) throws ParseException {
        return pieceDetacheeService.create(pieceDetachee);
    }

    @RequestMapping(value = "/piece-detacheescat/{catId}", method = RequestMethod.GET)
    public Set<PieceDetachee> getPieceDetacheeByCat(@PathVariable Long catId){
        return pieceDetacheeService.getPieceDetacheeByCat(catId);
    }

    @PutMapping("/piece-detachees/{id}")
    public PieceDetachee upDatePieceDetachee(@RequestBody PieceDetacheeDTO pieceDetacheeDTO,@PathVariable String id) throws ParseException {
        return pieceDetacheeService.updatepiecedetachee(pieceDetacheeDTO,id);
    }

    @GetMapping("/piece-detachees/{ref}")
    public PieceDetachee getPieceDetacheeByRef(@PathVariable String ref){
        return pieceDetacheeService.getPieceDetacheeByReference(ref);

    }

    @GetMapping("/piece-detachees")
    List<PieceDetachee> getAllList(){
        return pieceDetacheeService.getAllPieceDetachee();
    }
    @DeleteMapping("/piece-detachees/{idMatPremier}")
    public boolean supprimerPieceDetachee(@PathVariable String idMatPremier){
        pieceDetacheeService.supprimerPieceDetachee(idMatPremier);
        return true;
    }


}
