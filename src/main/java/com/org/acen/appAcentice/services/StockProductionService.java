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


   public PieceDetachee createPieceDetachee(PieceDetachee detachee, Long catId) throws ParseException {

       PieceDetacheeCat cat = detacheeCatRepository.findById(catId).get();

       detachee.setDateDachat(new Date());
       detachee.setCategorie(cat);

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
