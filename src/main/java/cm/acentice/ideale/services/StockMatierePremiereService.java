package cm.acentice.ideale.services;

import cm.acentice.ideale.entities.StockMatierePremiere;
import cm.acentice.ideale.repositories.StockMatierePremiereRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StockMatierePremiereService {

    private final StockMatierePremiereRep stockMatierePremiereRep;

    @Autowired
    public StockMatierePremiereService(StockMatierePremiereRep stockMatierePremiereRep) {
        this.stockMatierePremiereRep = stockMatierePremiereRep;
    }

    public List<StockMatierePremiere> getAll(){
        return stockMatierePremiereRep.findAll();
    }
}
