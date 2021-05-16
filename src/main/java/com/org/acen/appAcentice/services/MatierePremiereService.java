package com.org.acen.appAcentice.services;

import com.org.acen.appAcentice.entities.MatierePremiere;
import com.org.acen.appAcentice.entities.MatierePremiereCat;
import com.org.acen.appAcentice.entities.PieceDetachee;
import com.org.acen.appAcentice.entities.PieceDetacheeCat;
import com.org.acen.appAcentice.models.MatierePremDto;
import com.org.acen.appAcentice.repositories.MatierePremiereCatRepository;
import com.org.acen.appAcentice.repositories.MatierePremiereRepository;
import com.org.acen.appAcentice.repositories.PieceDetacheeCatRepository;
import com.org.acen.appAcentice.repositories.PieceDetacheeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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

    public List<MatierePremiere>getAllMatierePremiere(){
        return matierePremiereRepository.findAll();
    }

    public MatierePremiere getMatPremByReference(String ref){
        return matierePremiereRepository.findById(ref).get();
    }


    public boolean supprimerMatierePremiere(String idMatPremier){

        MatierePremiere matierePremiere = matierePremiereRepository.findById(idMatPremier).get();
        if(matierePremiere == null){
            throw new RuntimeException("MatierePremiere not found !");
        }
        matierePremiereRepository.delete(matierePremiere);
        return true;
    }


    public MatierePremiere upDateMatPremier(MatierePremDto matierePremDto, String ref) throws ParseException {

        MatierePremiere matierePremiere = matierePremiereRepository.findById(ref).get();
        matierePremiere.setReference(ref);
        matierePremiere.setLibelle(matierePremDto.getLibelle());
        matierePremiere.setCategorie(matierePremiere.getCategorie());
        matierePremiere.setDateDachat(matierePremiere.getDateDachat());
        matierePremiere.setDiscription(matierePremDto.getDiscription());
        matierePremiere.setExpirationDate(matierePremDto.getExpirationDate());
        matierePremiere.setPrixDachat(matierePremDto.getPrixDachat());

        return matierePremiereRepository.save(matierePremiere);
    }
}
