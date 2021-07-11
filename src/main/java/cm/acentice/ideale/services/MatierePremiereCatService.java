package cm.acentice.ideale.services;

import cm.acentice.ideale.dto.MatierePremCatDto;
import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.MatierePremiereCat;
import cm.acentice.ideale.repositories.MatierePremiereCatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class MatierePremiereCatService {


    private final MatierePremiereCatRepository matierePremiereCatRepository;

    @Autowired
    public MatierePremiereCatService(MatierePremiereCatRepository matierePremiereCatRepository) {
        this.matierePremiereCatRepository = matierePremiereCatRepository;
    }

    public MatierePremiereCat createMatierePremiereCat(MatierePremiereCat matierePremiereCat) {
        return matierePremiereCatRepository.save(matierePremiereCat);
    }

    public List<MatierePremiereCat> getAllCat() {
        return matierePremiereCatRepository.findAll();
    }

    public MatierePremiereCat upDateMatPremierCat(MatierePremCatDto matierePremCatDto) throws ParseException {
        MatierePremiereCat matierePremiereCat = new MatierePremiereCat();
        matierePremiereCat.setIdMPCat(matierePremCatDto.getId());
        matierePremiereCat.setName(matierePremCatDto.getName());

        return matierePremiereCatRepository.save(matierePremiereCat);
    }

}