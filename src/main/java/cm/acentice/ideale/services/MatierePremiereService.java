package cm.acentice.ideale.services;

import cm.acentice.ideale.dto.MatierePremDto;
import cm.acentice.ideale.entities.FournisseurMP;
import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.MatierePremiereCat;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.MatierePremiereCatRepository;
import cm.acentice.ideale.repositories.MatierePremiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;


@Service
public class MatierePremiereService {

    private final MatierePremiereCatRepository matierePremiereCatRepository;
    private final MatierePremiereRepository matierePremiereRepository;

    @Autowired
    public MatierePremiereService(MatierePremiereCatRepository matierePremiereCatRepository, MatierePremiereRepository matierePremiereRepository) {
        this.matierePremiereCatRepository = matierePremiereCatRepository;
        this.matierePremiereRepository = matierePremiereRepository;
    }


    public MatierePremiere create(MatierePremiere matierePremiere) throws ParseException {
        Long id = matierePremiere.getIdMPCat();
        matierePremiere.setIdMPCat(id);
        matierePremiere.setMatierePremFournisseurMPS(matierePremiere.getMatierePremFournisseurMPS());
        return matierePremiereRepository.save(matierePremiere);
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


    public MatierePremiere update(MatierePremDto matierePremDto, String id) throws ParseException {
        MatierePremiere matierePremiere = matierePremiereRepository.findById(id).get();
        if(matierePremiere != null)
        matierePremiere.setReference(matierePremiere.getReference());
        matierePremiere.setIdMPCat(matierePremDto.getIdMPCat());
        matierePremiere.setDateAchat(matierePremDto.getDateAchat());
        matierePremiere.setExpirationDate(matierePremDto.getExpirationDate());
        matierePremiere.setDescription(matierePremDto.getDescription());
        matierePremiere.setPrixAchat(matierePremDto.getPrixAchat());
        matierePremiere.setLibelle(matierePremDto.getLibelle());
        return matierePremiereRepository.save(matierePremiere);

    }

    public List<MatierePremiere> getMatirePremiereByCat(Long catId) {
        MatierePremiereCat matierePremiereCat = matierePremiereCatRepository.findById(catId).get();
        List<MatierePremiere> matierePremieres = matierePremiereCat.getMatierePremieres();
        return matierePremieres;
    }


}
