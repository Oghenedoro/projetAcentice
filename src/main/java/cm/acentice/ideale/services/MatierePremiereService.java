package cm.acentice.ideale.services;

import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.MatierePremiereCat;
import cm.acentice.ideale.dto.MatierePremCatDto;
import cm.acentice.ideale.dto.MatierePremDto;
import cm.acentice.ideale.repositories.MatierePremiereCatRepository;
import cm.acentice.ideale.repositories.MatierePremiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public MatierePremiereCat createMatierePremiereCat(MatierePremiereCat matierePremiereCat) {
        return matierePremiereCatRepository.save(matierePremiereCat);
    }

    public List<MatierePremiere> getAllMatierePremiere() {
        return matierePremiereRepository.findAll();
    }

    public MatierePremiere getMatPremByReference(String ref) {
        return matierePremiereRepository.findById(ref).get();
    }


    public boolean supprimerMatierePremiere(String idMatPremier) {

        MatierePremiere matierePremiere = matierePremiereRepository.findById(idMatPremier).get();
        if (matierePremiere == null) {
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

    public List<MatierePremiere> getMatirePremiereByCat(Long catId) {

        MatierePremiereCat matierePremiereCat = matierePremiereCatRepository.findById(catId).get();

        List<MatierePremiere> matierePremieres = matierePremiereCat.getMatierePremieres();
        return matierePremieres;
    }

    public List<MatierePremiereCat> getAllCat() {
        return matierePremiereCatRepository.findAll();
    }

    public MatierePremiereCat upDateMatPremierCat(MatierePremCatDto matierePremCatDto, Long id) throws ParseException {

        MatierePremiereCat matierePremiereCat = matierePremiereCatRepository.findById(id).get();
        matierePremiereCat.setId(matierePremiereCat.getId());
        matierePremiereCat.setType(matierePremCatDto.getType());

        return matierePremiereCatRepository.save(matierePremiereCat);
    }
}
