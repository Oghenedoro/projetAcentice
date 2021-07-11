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
import java.util.Set;

@Service
public class PieceDetacheeService {

    private final PieceDetacheeCatRepository detacheeCatRepository;
    private final PieceDetacheeRepository pieceDetacheeRepository;

    public PieceDetacheeService(PieceDetacheeCatRepository detacheeCatRepository, PieceDetacheeRepository pieceDetacheeRepository) {
        this.detacheeCatRepository = detacheeCatRepository;
        this.pieceDetacheeRepository = pieceDetacheeRepository;
    }

    public PieceDetachee create(PieceDetachee pieceDetachee) {
        Long id = pieceDetachee.getIdPDCat();
        pieceDetachee.setIdPDCat(id);
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
    public PieceDetachee updatepiecedetachee(PieceDetacheeDTO pieceDetacheeDTO,String id) throws ParseException {
       PieceDetachee pieceDetachee = pieceDetacheeRepository.findById(id).get();
       if(pieceDetachee != null) pieceDetachee.setReference(pieceDetachee.getReference());
       pieceDetachee.setIdPDCat(pieceDetacheeDTO.getIdPDCat());
       pieceDetachee.setDateAchat(pieceDetacheeDTO.getDateAchat());
       pieceDetachee.setDescription(pieceDetacheeDTO.getDescription());
       pieceDetachee.setLibelle(pieceDetacheeDTO.getLibelle());
       pieceDetachee.setDureeDeVie(pieceDetacheeDTO.getDureeDeVie());
       pieceDetachee.setPrixAchat(pieceDetacheeDTO.getPrixAchat());
        return pieceDetacheeRepository.save(pieceDetachee);
    }

    //FIXME Create a method getPieceDetacheeByCategory in the PieceDetacheeRepository
    public Set<PieceDetachee> getPieceDetacheeByCat(Long catId) {
        PieceDetacheeCat pieceDetacheeCat = detacheeCatRepository.findById(catId).get();
        Set<PieceDetachee> pieceDetachees = pieceDetacheeCat.getPiecedetachees();
        return pieceDetachees;
    }

    public List<PieceDetacheeCat> getAllCat() {
        return detacheeCatRepository.findAll();
    }

}
