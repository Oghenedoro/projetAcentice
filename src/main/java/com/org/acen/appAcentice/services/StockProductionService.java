package com.org.acen.appAcentice.services;

import com.org.acen.appAcentice.entities.MatierePremiere;
import com.org.acen.appAcentice.entities.PieceDetachee;
import com.org.acen.appAcentice.entities.PieceDetacheeCat;
import com.org.acen.appAcentice.repositories.MatierePremiereRepository;
import com.org.acen.appAcentice.repositories.PieceDetacheeCatRepository;
import com.org.acen.appAcentice.repositories.PieceDetacheeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class StockProductionService {

    @Autowired
    private PieceDetacheeRepository pieceDetacheeRepository;
    @Autowired
    private MatierePremiereRepository matierePremiereRepository;
    @Autowired
    private PieceDetacheeCatRepository detacheeCatRepository;


   public PieceDetachee createPieceDetachee(PieceDetachee detachee, String type) throws ParseException {

       PieceDetacheeCat cat = detacheeCatRepository.findByType(type);

       detachee.setDateDachat(new Date());
       detachee.setCategorie(cat);
       detachee.setDureeDeVie(detachee.getDureeDeVie()+ " mois");

       return pieceDetacheeRepository.save(detachee);
   }

    public MatierePremiere createMatirePrem(MatierePremiere matierePremier) throws ParseException {
        matierePremier.setDateDachat(new Date());
        return matierePremiereRepository.save(matierePremier);
    }

    public PieceDetacheeCat create(PieceDetacheeCat pieceDetacheeCat){
        return detacheeCatRepository.save(pieceDetacheeCat);
    }

}
