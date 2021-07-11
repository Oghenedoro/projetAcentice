package cm.acentice.ideale.services;

import cm.acentice.ideale.dto.PieceDetacheeCatDto;
import cm.acentice.ideale.entities.PieceDetacheeCat;
import cm.acentice.ideale.repositories.PieceDetacheeCatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class PieceDetacheeCatService {

    private final PieceDetacheeCatRepository pieceDetacheeCatRepository;

    @Autowired
    public PieceDetacheeCatService(PieceDetacheeCatRepository pieceDetacheeCatRepository) {
        this.pieceDetacheeCatRepository = pieceDetacheeCatRepository;
    }

    public List<PieceDetacheeCat> getAll(){
        return pieceDetacheeCatRepository.findAll();
    }

    public PieceDetacheeCat upDatePieceDetacheeCat(PieceDetacheeCatDto pieceDetacheeCatDto) throws ParseException {
        PieceDetacheeCat pieceDetacheeCat = new PieceDetacheeCat();
        pieceDetacheeCat.setIdPDCat(pieceDetacheeCatDto.getIdPDCat());
        pieceDetacheeCat.setName(pieceDetacheeCatDto.getName());

        return pieceDetacheeCatRepository.save(pieceDetacheeCat);
    }


    public PieceDetacheeCat createPieceDetacheeCat(PieceDetacheeCat pieceDetacheeCat) {
        return pieceDetacheeCatRepository.save(pieceDetacheeCat);
    }

}
