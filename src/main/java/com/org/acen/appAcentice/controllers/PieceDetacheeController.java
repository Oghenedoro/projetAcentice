package com.org.acen.appAcentice.controllers;

import com.org.acen.appAcentice.entities.PieceDetachee;
import com.org.acen.appAcentice.entities.PieceDetacheeCat;
import com.org.acen.appAcentice.services.StockProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RequestMapping("/api")
@RestController
public class PieceDetacheeController {

    @Autowired
    StockProductionService productionService;

    @RequestMapping("/v1/piece-detachee/{idCat}")
    public PieceDetachee createPieceDetachee(@RequestBody PieceDetachee pieceDetachee, @PathVariable Long idCat) throws ParseException {
        return productionService.createPieceDetachee(pieceDetachee, idCat);
    }

    @RequestMapping("/v1/piece-categorie")
    public PieceDetacheeCat createPieceDetacheeCat(@RequestBody PieceDetacheeCat pieceDetacheeCat) throws ParseException {
        return productionService.create(pieceDetacheeCat);
    }

}
