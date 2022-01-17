package cm.acentice.ideale.services;

import cm.acentice.ideale.constants.TypeFournisseur;
import cm.acentice.ideale.dto.FournisseurDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class FournisseurService {

    private final MatierePremFournisseurMPRepository matierePremFournisseurMPRepository;
    private final FournisseurMPRepository fournisseurMPRepository;
    private final FournisseurPDRepository fournisseurPDRepository;
    private final PieceDetacheeRepository pieceDetacheeRepository;
    private final MatierePremiereRepository matierePremiereRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public FournisseurService(MatierePremiereService matierePremiereService, MatierePremFournisseurMPRepository matierePremFournisseurMPRepository, FournisseurMPRepository fournisseurMPRepository, FournisseurPDRepository fournisseurPDRepository, PieceDetacheeRepository pieceDetacheeRepository, MatierePremiereRepository matierePremiereRepository, ContactRepository contactRepository) {
        this.matierePremFournisseurMPRepository = matierePremFournisseurMPRepository;
        this.fournisseurMPRepository = fournisseurMPRepository;
        this.fournisseurPDRepository = fournisseurPDRepository;
        this.pieceDetacheeRepository = pieceDetacheeRepository;
        this.matierePremiereRepository = matierePremiereRepository;
        this.contactRepository = contactRepository;
    }

    public List<FournisseurPD> getAllPieceDetaches() {
        return this.fournisseurPDRepository.findAll();
    }

    public FournisseurPD getFournisseurPDByReference(String ref) {
        return fournisseurPDRepository.findById(ref).get();
    }


    public FournisseurPD createFournisseurPD(FournisseurDto fournisseurDto) {
        FournisseurPD fournisseurPD = new FournisseurPD();
        fournisseurPD.setDenomination(fournisseurDto.getDenomination());
        fournisseurPD.setAdresse(fournisseurDto.getAdresse());
        fournisseurPD.setEmail(fournisseurDto.getEmail());
        fournisseurPD.setPays(fournisseurDto.getPays());
        fournisseurPD.setQuartier(fournisseurDto.getQuartier());
        fournisseurPD.setType(fournisseurDto.getType());
        fournisseurPD.setTelephone(fournisseurDto.getTelephone());
        fournisseurPD.setSecteurActivite(fournisseurDto.getSecteurActivite());
        fournisseurPD.setVille(fournisseurDto.getVille());
        fournisseurPD.setContacts(fournisseurDto.getContacts());
        fournisseurPD.setPieceDetachees(fournisseurDto.getPieceDetachees());
        return fournisseurPDRepository.save(fournisseurPD);

    }

    public FournisseurPD updateFournisseurPD(FournisseurDto fournisseurDto, String id) {

        FournisseurPD fournisseurPD = fournisseurPDRepository.findById(id).get();
        fournisseurPD.setId(fournisseurDto.getId());
        fournisseurPD.setDenomination(fournisseurDto.getDenomination());
        fournisseurPD.setAdresse(fournisseurDto.getAdresse());
        fournisseurPD.setEmail(fournisseurDto.getEmail());
        fournisseurPD.setPays(fournisseurDto.getPays());
        fournisseurPD.setQuartier(fournisseurDto.getQuartier());
        fournisseurPD.setType(TypeFournisseur.PARTICULIER);
        fournisseurPD.setTelephone(fournisseurDto.getTelephone());
        fournisseurPD.setSecteurActivite(fournisseurDto.getSecteurActivite());
        fournisseurPD.setVille(fournisseurDto.getVille());
        return fournisseurPDRepository.save(fournisseurPD);

    }

    public boolean supprimerFournisseurPD(String idMatPremier) {
        Optional<FournisseurPD> pieceDetachee = fournisseurPDRepository.findById(idMatPremier);
        if (pieceDetachee.isEmpty()) {
            throw new RuntimeException("FournisseurPD not found !");
        }
        fournisseurPDRepository.delete(pieceDetachee.get());
        return true;
    }

   @Transactional
    public FournisseurMP createFournisseurMP(FournisseurDto fournisseurDto) {
        FournisseurMP fournisseurMP = new FournisseurMP();
        fournisseurMP.setVille(fournisseurDto.getVille());
        fournisseurMP.setQuartier(fournisseurDto.getQuartier());
        fournisseurMP.setVille(fournisseurDto.getVille());
        fournisseurMP.setType(TypeFournisseur.SOCIETE);
        fournisseurMP.setSecteurActivite(fournisseurDto.getSecteurActivite());
        fournisseurMP.setEmail(fournisseurDto.getEmail());
        fournisseurMP.setAdresse(fournisseurDto.getAdresse());
        fournisseurMP.setPays(fournisseurDto.getPays());
        fournisseurMP.setDenomination(fournisseurDto.getDenomination());
        fournisseurMP.setTelephone(fournisseurDto.getTelephone());

        for (MatierePremFournisseurMP mpf : fournisseurDto.getMatierePremFournisseurMPS()) {
            mpf.setFournisseurMP(fournisseurMP);
        }

        fournisseurMP.setMatierePremFournisseurMPS(fournisseurDto.getMatierePremFournisseurMPS());
        fournisseurMP.setContacts(fournisseurDto.getContacts());
        return fournisseurMPRepository.save(fournisseurMP);
    }

    public MatierePremFournisseurMP createFMP(MatierePremFournisseurMP matierePremFournisseurMP) {
        return matierePremFournisseurMPRepository.save(matierePremFournisseurMP);
    }

    public List<MatierePremFournisseurMP> findAll() {
        return matierePremFournisseurMPRepository.findAll();
    }

    public FournisseurMP updateFournisseurMP(FournisseurDto fournisseurDto, String id) {
        FournisseurMP fournisseurMP = fournisseurMPRepository.findById(id).get();
        fournisseurMP.setId(fournisseurMP.getId());
        fournisseurMP.setDenomination(fournisseurDto.getDenomination());
        fournisseurMP.setAdresse(fournisseurDto.getAdresse());
        fournisseurMP.setEmail(fournisseurDto.getEmail());
        fournisseurMP.setPays(fournisseurDto.getPays());
        fournisseurMP.setQuartier(fournisseurDto.getQuartier());
        fournisseurMP.setType(fournisseurDto.getType());
        fournisseurMP.setTelephone(fournisseurDto.getTelephone());
        fournisseurMP.setSecteurActivite(fournisseurDto.getSecteurActivite());
        fournisseurMP.setVille(fournisseurDto.getVille());

        return fournisseurMPRepository.save(fournisseurMP);
    }

    public boolean supprimerFournisseurMP(String id) {
        Optional<FournisseurMP> fournisseurMP = fournisseurMPRepository.findById(id);
        if (fournisseurMP.isEmpty()) {
            throw new RuntimeException("FournisseurPD not found !");
        }
        fournisseurMPRepository.delete(fournisseurMP.get());
        return true;
    }

    public List<FournisseurMP> getAllMatierePremieres() {
        return this.fournisseurMPRepository.findAll();
    }

    public Collection<FournisseurMP> getFournisseurMPByMatPremReference(String ref) throws ResourceNotFoundException {
    MatierePremiere matierePremiere = matierePremiereRepository.findById(ref).get();
      List<FournisseurMP>pList = new ArrayList<>();
      Collection<MatierePremFournisseurMP> mpList =  matierePremiere.getMatierePremFournisseurMPS();
        for(MatierePremFournisseurMP mp: mpList){
           FournisseurMP fournisseurMP =  mp.getFournisseurMP();
           pList.add(fournisseurMP);
        }
        return pList;
    }


    public List<MatierePremiere> getMatPremiereByFournisseurMPId (String id){
        List<MatierePremiere>matierePremieres = new ArrayList<>();
        FournisseurMP fournisseurMP = fournisseurMPRepository.findById(id).get();
        Collection<MatierePremFournisseurMP> mpList = fournisseurMP.getMatierePremFournisseurMPS();
        for(MatierePremFournisseurMP mp: mpList){
            MatierePremiere matierePremiere = mp.getMatierePremiere();
            matierePremieres.add(matierePremiere);
        }
        return matierePremieres;
    }

    public FournisseurMP getFournisseurMP(String id) {
        Optional<FournisseurMP> fournisseurMP = fournisseurMPRepository.findById(id);
        if (!fournisseurMP.isPresent()) {
            throw new RuntimeException("FournisseurMP not found !");
        }
        return fournisseurMP.get();
    }

}