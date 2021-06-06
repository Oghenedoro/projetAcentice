package cm.acentice.ideale.services;


import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.repositories.FournisseurMPRepository;
import cm.acentice.ideale.repositories.FournisseurPDRepository;
import cm.acentice.ideale.repositories.MatierePremiereRepository;
import cm.acentice.ideale.repositories.PieceDetacheeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static cm.acentice.ideale.constants.TypeFournisseur.PARTICULIER;
import static cm.acentice.ideale.constants.TypeFournisseur.SOCIETE;

@Service
public class FournisseurService {

    private final FournisseurMPRepository fournisseurMPRepository;
    private final FournisseurPDRepository fournisseurPDRepository;
    private final PieceDetacheeRepository pieceDetacheeRepository;
    private final MatierePremiereRepository matierePremiereRepository;

    @Autowired
    public FournisseurService(FournisseurMPRepository fournisseurMPRepository, FournisseurPDRepository fournisseurPDRepository, PieceDetacheeRepository pieceDetacheeRepository, MatierePremiereRepository matierePremiereRepository) {
        this.fournisseurMPRepository = fournisseurMPRepository;
        this.fournisseurPDRepository = fournisseurPDRepository;
        this.pieceDetacheeRepository = pieceDetacheeRepository;
        this.matierePremiereRepository = matierePremiereRepository;
    }

    public FournisseurPD createFournisseurPieceDetachee(FournisseurPD fournisseurPD) {
        return fournisseurPDRepository.save(fournisseurPD);
    }

    public FournisseurMP createFournisseurMatierePremiere(FournisseurMP fournisseurMP) {
        return fournisseurMPRepository.save(fournisseurMP);
    }

    public List<FournisseurMP> getAllMatierePremieres() {
        return this.fournisseurMPRepository.findAll();
    }

    public List<FournisseurPD> getAllPieceDetaches() {
        return this.fournisseurPDRepository.findAll();
    }
}
