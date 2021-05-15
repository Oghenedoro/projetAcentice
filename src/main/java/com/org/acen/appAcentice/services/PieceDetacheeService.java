package com.org.acen.appAcentice.services;

import com.org.acen.appAcentice.entities.PieceDetachee;
import com.org.acen.appAcentice.entities.PieceDetacheeCat;
import com.org.acen.appAcentice.repositories.PieceDetacheeCatRepository;
import com.org.acen.appAcentice.repositories.PieceDetacheeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class PieceDetacheeService {

    @Autowired
    private PieceDetacheeCatRepository detacheeCatRepository;
    @Autowired
    private PieceDetacheeRepository pieceDetacheeRepository;

    public PieceDetachee createPieceDetachee(PieceDetachee detachee, String type) throws ParseException {

        PieceDetacheeCat cat = detacheeCatRepository.findByType(type);

        detachee.setDateDachat(new Date());
        detachee.setCategorie(cat);
        detachee.setDureeDeVie(detachee.getDureeDeVie()+ " mois");

        return pieceDetacheeRepository.save(detachee);
    }

    public PieceDetacheeCat createPieceDetacheeCat(PieceDetacheeCat pieceDetacheeCat){
        return detacheeCatRepository.save(pieceDetacheeCat);
    }
}
