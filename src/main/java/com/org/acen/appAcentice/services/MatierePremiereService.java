package com.org.acen.appAcentice.services;

import com.org.acen.appAcentice.entities.MatierePremiere;
import com.org.acen.appAcentice.entities.MatierePremiereCat;
import com.org.acen.appAcentice.entities.PieceDetachee;
import com.org.acen.appAcentice.entities.PieceDetacheeCat;
import com.org.acen.appAcentice.repositories.MatierePremiereCatRepository;
import com.org.acen.appAcentice.repositories.MatierePremiereRepository;
import com.org.acen.appAcentice.repositories.PieceDetacheeCatRepository;
import com.org.acen.appAcentice.repositories.PieceDetacheeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class MatierePremiereService {

    @Autowired
    private MatierePremiereRepository matierePremiereRepository;
    @Autowired
    private MatierePremiereCatRepository matierePremiereCatRepository;



    public MatierePremiere createMatirePrem(MatierePremiere matierePremier, String type) throws ParseException {
        matierePremier.setDateDachat(new Date());
        MatierePremiereCat matierePremiereCat = matierePremiereCatRepository.findByType(type);
        matierePremier.setCategorie(matierePremiereCat);
        return matierePremiereRepository.save(matierePremier);
    }

    public MatierePremiereCat createMatierePremiereCat(MatierePremiereCat matierePremiereCat){
        return matierePremiereCatRepository.save(matierePremiereCat);
    }
}
