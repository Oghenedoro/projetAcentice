package cm.acentice.ideale.controllers;

import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.StockMatierePremiere;
import cm.acentice.ideale.services.StockMatierePremiereService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/v1")
@RestController
public class StockMatierePremiereCtlr {

    private final StockMatierePremiereService stockMatierePremiereService;

    public StockMatierePremiereCtlr(StockMatierePremiereService stockMatierePremiereService) {
        this.stockMatierePremiereService = stockMatierePremiereService;
    }

    /*@RequestMapping(value = "/stockmatierepremiers", method = RequestMethod.GET)
    List<StockMatierePremiere> consulterTousStockMP() {
        return approvisionnementMPService.consulterTousStockMP();
    }*/
    @RequestMapping(value = "/stockmatierepremiers", method = RequestMethod.GET)
    public List<StockMatierePremiere> getAllMatierPremier(){
            return stockMatierePremiereService.getAll();
        }
    }
