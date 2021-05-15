package com.org.acen.appAcentice.controllers;

import com.org.acen.appAcentice.entities.PieceDetachee;
import com.org.acen.appAcentice.entities.PieceDetacheeCat;
import com.org.acen.appAcentice.services.MatierePremiereService;
import com.org.acen.appAcentice.services.PieceDetacheeService;
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
    PieceDetacheeService pieceDetacheeService;

    @RequestMapping("/v1/piece-detachee/{type}")
    public PieceDetachee createPieceDetachee(@RequestBody PieceDetachee pieceDetachee, @PathVariable String type) throws ParseException {
        return pieceDetacheeService.createPieceDetachee(pieceDetachee, type);
    }

    @RequestMapping("/v1/piece-categoriepd")
    public PieceDetacheeCat createPieceDetacheeCat(@RequestBody PieceDetacheeCat pieceDetacheeCat) throws ParseException {
        return pieceDetacheeService.createPieceDetacheeCat(pieceDetacheeCat);
    }

}
