package cm.acentice.ideale.services;

import cm.acentice.ideale.entities.PieceDetacheeCat;
import cm.acentice.ideale.entities.PieceDetachee;
import cm.acentice.ideale.dto.PieceDetacheeCatDto;
import cm.acentice.ideale.dto.PieceDetacheeDTO;
import cm.acentice.ideale.repositories.PieceDetacheeCatRepository;
import cm.acentice.ideale.repositories.PieceDetacheeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PieceDetacheeService {

    private final PieceDetacheeCatRepository detacheeCatRepository;
    private final PieceDetacheeRepository pieceDetacheeRepository;

    public PieceDetacheeService(PieceDetacheeCatRepository detacheeCatRepository, PieceDetacheeRepository pieceDetacheeRepository) {
        this.detacheeCatRepository = detacheeCatRepository;
        this.pieceDetacheeRepository = pieceDetacheeRepository;
    }

    public PieceDetachee create(PieceDetachee pieceDetachee) {
        return pieceDetacheeRepository.save(pieceDetachee);
    }

    public List<PieceDetachee> getAllPieceDetachee() {
        return pieceDetacheeRepository.findAll();
    }

    public PieceDetachee getPieceDetacheeByReference(String ref) {
        return pieceDetacheeRepository.findById(ref).get();
    }


    public void supprimerPieceDetachee(String idMatPremier) {

        Optional<PieceDetachee> pieceDetachee = pieceDetacheeRepository.findById(idMatPremier);
        if (pieceDetachee.isEmpty()) {
            throw new RuntimeException("PieceDetachee not found !");
        }
        pieceDetacheeRepository.delete(pieceDetachee.get());
    }

    //FIXME Service layer should handle business entities not Dto
    public PieceDetachee updatepiecedetachee(PieceDetacheeDTO pieceDetacheeDTO, String ref) throws ParseException {

        PieceDetachee pieceDetachee = pieceDetacheeRepository.findById(ref).get();
        pieceDetachee.setReference(ref);
        pieceDetachee.setLibelle(pieceDetacheeDTO.getLibelle());
        pieceDetachee.setCategorie(pieceDetachee.getCategorie());
        pieceDetachee.setDateAchat(pieceDetachee.getDateAchat());
        pieceDetachee.setDescription(pieceDetacheeDTO.getDiscription());
        pieceDetachee.setDureeDeVie(pieceDetacheeDTO.getDureeDeVie());
        pieceDetachee.setPrixAchat(pieceDetacheeDTO.getPrixDachat());

        return pieceDetacheeRepository.save(pieceDetachee);
    }

    //FIXME Create a method getPieceDetacheeByCategory in the PieceDetacheeRepository
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
        pieceDetacheeCat.setName(pieceDetacheeCatDto.getType());

        return detacheeCatRepository.save(pieceDetacheeCat);
    }
}
