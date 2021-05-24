package cm.acentice.ideale.services;


import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.repositories.FournisseurMPRepository;
import cm.acentice.ideale.repositories.FournisseurPDRepository;
import cm.acentice.ideale.repositories.MatierePremiereRepository;
import cm.acentice.ideale.repositories.PieceDetacheeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Fournisseur createFournisseurPD(FournisseurPD fournisseurPD, String idPD) {

        Fournisseur fournisseur = new FournisseurPD();
        PieceDetachee pieceDetachee = pieceDetacheeRepository.findById(idPD).get();
        if (fournisseur instanceof FournisseurPD) {
            fournisseur.setDenomination(fournisseurPD.getDenomination());
            fournisseur.setAdresse(fournisseurPD.getAdresse());
            fournisseur.setQuartier(fournisseurPD.getQuartier());
            fournisseur.setVille(fournisseurPD.getVille());
            fournisseur.setEmail(fournisseurPD.getEmail());
            fournisseur.setType(SOCIETE);
            fournisseur.setPays(fournisseurPD.getPays());
            fournisseur.setPays("Cameroun");
            fournisseur.setTelephone(fournisseurPD.getTelephone());
            fournisseur.setSecteurActivite(fournisseurPD.getSecteurActivite());
            ((FournisseurPD) fournisseur).setPieceDetacheeRef(pieceDetachee.getReference());

        }
        return fournisseurPDRepository.save(fournisseur);

    }

    public Fournisseur createFournisseurMP(FournisseurMP fournisseurMP, String idMP) {

        Fournisseur fournisseur = new FournisseurMP();
        MatierePremiere matierePremier = matierePremiereRepository.findById(idMP).get();
        if (fournisseur instanceof FournisseurMP) {
            fournisseur.setDenomination(fournisseurMP.getDenomination());
            fournisseur.setAdresse(fournisseurMP.getAdresse());
            fournisseur.setQuartier(fournisseurMP.getQuartier());
            fournisseur.setVille(fournisseurMP.getVille());
            fournisseur.setEmail(fournisseurMP.getEmail());
            fournisseur.setType(PARTICULIER);
            fournisseur.setPays("Cameroun");
            fournisseur.setTelephone(fournisseurMP.getTelephone());
            fournisseur.setSecteurActivite(fournisseurMP.getSecteurActivite());
            ((FournisseurMP) fournisseur).setMatierePremierRef(matierePremier.getReference());

        }
        return fournisseurMPRepository.save(fournisseur);

    }
}
