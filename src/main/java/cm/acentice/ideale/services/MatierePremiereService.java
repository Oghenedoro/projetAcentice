package cm.acentice.ideale.services;

import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.MatierePremiereCat;
import cm.acentice.ideale.dto.MatierePremCatDto;
import cm.acentice.ideale.dto.MatierePremDto;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.MatierePremiereCatRepository;
import cm.acentice.ideale.repositories.MatierePremiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MatierePremiereService {

    @Autowired
    private MatierePremiereRepository matierePremiereRepository;
    @Autowired
    private MatierePremiereCatRepository matierePremiereCatRepository;

    public MatierePremiere create(MatierePremiere matierePremiere) throws ParseException {
        return matierePremiereRepository.save(matierePremiere);
    }

    public MatierePremiereCat createMatierePremiereCat(MatierePremiereCat matierePremiereCat) {
        return matierePremiereCatRepository.save(matierePremiereCat);
    }

    public List<MatierePremiere> getAllMatierePremiere() {
        return matierePremiereRepository.findAll();
    }

    public MatierePremiere getMatierePremiereByReference(String reference) throws ResourceNotFoundException {
        Optional<MatierePremiere> matierePremiere = matierePremiereRepository.findById(reference);

        if (matierePremiere.isEmpty()) {
            throw new ResourceNotFoundException( "MatierePremiere not found !");
        }
        return matierePremiere.get();
    }


    public boolean supprimerMatierePremiere(String reference) throws ResourceNotFoundException {

        Optional<MatierePremiere> matierePremiere = matierePremiereRepository.findById(reference);

        if (matierePremiere.isEmpty()) {
            throw new ResourceNotFoundException( "MatierePremiere not found !");
        }
        matierePremiereRepository.delete(matierePremiere.get());
        return true;
    }


    public MatierePremiere update(MatierePremDto matierePremDto, String ref) throws ParseException {

        MatierePremiere matierePremiere = matierePremiereRepository.findById(ref).get();
        matierePremiere.setReference(ref);
        matierePremiere.setLibelle(matierePremDto.getLibelle());
        matierePremiere.setCategorie(matierePremiere.getCategorie());
        matierePremiere.setDateAchat(matierePremiere.getDateAchat());
        matierePremiere.setDescription(matierePremDto.getDiscription());
        matierePremiere.setExpirationDate(matierePremDto.getExpirationDate());
        matierePremiere.setPrixAchat(matierePremDto.getPrixDachat());

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
        matierePremiereCat.setName(matierePremCatDto.getName());

        return matierePremiereCatRepository.save(matierePremiereCat);
    }
}
