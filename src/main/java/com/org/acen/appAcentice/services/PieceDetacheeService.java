package com.org.acen.appAcentice.services;

import com.org.acen.appAcentice.entities.MatierePremiere;
import com.org.acen.appAcentice.entities.MatierePremiereCat;
import com.org.acen.appAcentice.entities.PieceDetachee;
import com.org.acen.appAcentice.entities.PieceDetacheeCat;
import com.org.acen.appAcentice.models.MatierePremDto;
import com.org.acen.appAcentice.models.PieceDetacheeCatDto;
import com.org.acen.appAcentice.models.PieceDetacheeDTO;
import com.org.acen.appAcentice.repositories.PieceDetacheeCatRepository;
import com.org.acen.appAcentice.repositories.PieceDetacheeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
        detachee.setDureeDeVie(detachee.getDureeDeVie() + " mois");

        return pieceDetacheeRepository.save(detachee);
    }

    public List<PieceDetachee> getAllPieceDetachee() {
        return pieceDetacheeRepository.findAll();
    }

    public PieceDetachee getPieceDetacheeByReference(String ref) {
        return pieceDetacheeRepository.findById(ref).get();
    }


    public boolean supprimerPieceDetachee(String idMatPremier) {

        PieceDetachee pieceDetachee = pieceDetacheeRepository.findById(idMatPremier).get();
        if (pieceDetachee == null) {
            throw new RuntimeException("PieceDetachee not found !");
        }
        pieceDetacheeRepository.delete(pieceDetachee);
        return true;
    }

    public PieceDetachee upDatePieceDetachee(PieceDetacheeDTO pieceDetacheeDTO, String ref) throws ParseException {

        PieceDetachee pieceDetachee = pieceDetacheeRepository.findById(ref).get();
        pieceDetachee.setReference(ref);
        pieceDetachee.setLibelle(pieceDetacheeDTO.getLibelle());
        pieceDetachee.setCategorie(pieceDetachee.getCategorie());
        pieceDetachee.setDateDachat(pieceDetachee.getDateDachat());
        pieceDetachee.setDiscription(pieceDetacheeDTO.getDiscription());
        pieceDetachee.setDureeDeVie(pieceDetacheeDTO.getDureeDeVie() + " mois");
        pieceDetachee.setPrixDachat(pieceDetacheeDTO.getPrixDachat());

        return pieceDetacheeRepository.save(pieceDetachee);
    }

    public List<PieceDetachee> getPieceDetacheeByCat(Long catId) {

        PieceDetacheeCat pieceDetacheeCat = detacheeCatRepository.findById(catId).get();
        List<PieceDetachee> pieceDetachees = pieceDetacheeCat.getPiecedetachees();
        return pieceDetachees;
    }

    //Lagestion des catégories
    public PieceDetacheeCat createPieceDetacheeCat(PieceDetacheeCat pieceDetacheeCat) {
        return detacheeCatRepository.save(pieceDetacheeCat);
    }

    public List<PieceDetacheeCat> getAllCat() {
        return detacheeCatRepository.findAll();
    }

    public PieceDetacheeCat upDatePieceDetacheeCat(PieceDetacheeCatDto pieceDetacheeCatDto, Long id) throws ParseException {

        PieceDetacheeCat pieceDetacheeCat = detacheeCatRepository.findById(id).get();
        pieceDetacheeCat.setId(pieceDetacheeCat.getId());
        pieceDetacheeCat.setType(pieceDetacheeCatDto.getType());

        return detacheeCatRepository.save(pieceDetacheeCat);
    }
}