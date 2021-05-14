package com.org.acen.appAcentice.services;

import com.org.acen.appAcentice.appConstants.ConstantElements;
import com.org.acen.appAcentice.entities.*;
import com.org.acen.appAcentice.repositories.FournisseurMPRepository;
import com.org.acen.appAcentice.repositories.FournisseurPDRepository;
import com.org.acen.appAcentice.repositories.MatierePremiereRepository;
import com.org.acen.appAcentice.repositories.PieceDetacheeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FournisseurService {

    @Autowired
    private FournisseurMPRepository fournisseurMPRepository;
    @Autowired
    private FournisseurPDRepository fournisseurPDRepository;
    @Autowired
    private PieceDetacheeRepository pieceDetacheeRepository;
    @Autowired
    private MatierePremiereRepository matierePremiereRepository;

    public Fournisseur createFournisseurPD(FournisseurPD fournisseurPD, String idPD) {

        Fournisseur fournisseur = new FournisseurPD();
        PieceDetachee pieceDetachee = pieceDetacheeRepository.findById(idPD).get();
        if (fournisseur instanceof FournisseurPD) {
            fournisseur.setDenomination(fournisseurPD.getDenomination());
            fournisseur.setAdresse(fournisseurPD.getAdresse());
            fournisseur.setQuartier(fournisseurPD.getQuartier());
            fournisseur.setVille(fournisseurPD.getVille());
            fournisseur.setEmail(fournisseurPD.getEmail());
            fournisseur.setType(ConstantElements.SOCIETE);
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
            fournisseur.setType(ConstantElements.PARTICULIER);
            fournisseur.setPays("Cameroun");
            fournisseur.setTelephone(fournisseurMP.getTelephone());
            fournisseur.setSecteurActivite(fournisseurMP.getSecteurActivite());
            ((FournisseurMP) fournisseur).setMatierePremierRef(matierePremier.getReference());

        }
        return fournisseurMPRepository.save(fournisseur);

    }
}