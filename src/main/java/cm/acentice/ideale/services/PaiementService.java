package cm.acentice.ideale.services;

import cm.acentice.ideale.constants.CommandeStatut;
import cm.acentice.ideale.constants.MoyenPaiement;
import cm.acentice.ideale.dto.PaiementDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.exceptions.ExcessPaiementException;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.interfaces.DataPMapper;
import cm.acentice.ideale.interfaces.PaimentInterface;
import cm.acentice.ideale.repositories.CommandeRepository;
import cm.acentice.ideale.repositories.HistoriqueStatutCommandeRepos;
import cm.acentice.ideale.repositories.PaiementRepos;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Builder
@RequiredArgsConstructor
@Service
public class PaiementService implements PaimentInterface {

    private final PaiementRepos paiementRepos;
    private final DataPMapper dataPMapper;
    private final CommandeRepository commandeRepository;
    private final HistoriqueStatutCommandeRepos statutCommandeRepos;

    @Override
    public PaiementDto create(PaiementDto paiementDto) throws ResourceNotFoundException, ExcessPaiementException {
        paiementDto.setMontantPaye(paiementDto.getMontantPaye());

        Optional<Commande> commande = commandeRepository.findById(paiementDto.getIdCommande());
        if(!commande.isPresent()){
            throw new ResourceNotFoundException("Commande non trouvé !");
        }
        List<Paiement> paiements = paiementRepos.findByIdCommande(paiementDto.getIdCommande());
        double montantPaye = paiementDto.getMontantPaye();
        double montantDeCommande = commande.get().getPrixTotale();

        Paiement paiement = dataPMapper.mapFromPaimentDto_toPaiment(paiementDto);
        paiement.setIdCommande(commande.get().getIdCommande());
        paiement.setDatePaiement(LocalDateTime.now());
        if(paiements.size()==0){
            paiement = new Paiement();
            paiement.setDatePaiement(LocalDateTime.now());
            paiement.setMontantPaye(montantPaye);
            paiement.setIdCommande(paiement.getIdCommande());
            paiement.setMontantDeLaCommande(montantDeCommande);
            paiement.setMontantRestant(montantDeCommande - montantPaye);
            paiement.setIdCommande(commande.get().getIdCommande());
        }else if(paiements.size()>0){
            double montant = paiements.get(paiements.size()-1).getMontantPaye();
            montantPaye = montant + paiement.getMontantPaye();
            paiement.setMontantPaye(montantPaye);
            double montantRestant = montantDeCommande - montantPaye;
            paiement.setMontantRestant(montantRestant);
            paiement.setMontantDeLaCommande(montantDeCommande);
        }
        createCommandeStatut(commande.get(), paiement);

        if(montantPaye > montantDeCommande ){
            throw new ExcessPaiementException("On ne peut pas payer plus que le montant de la commande !");
        }
        paiement.setMoyenPaiement(MoyenPaiement.CB);
        paiement = paiementRepos.save(paiement);
        paiementDto = dataPMapper.mapFromPaiment_toPaimentDto(paiement);
        return paiementDto;
    }

    private void createCommandeStatut(Commande commande,Paiement paiement){
        HistorisueStatutCommande statutCommande = new HistorisueStatutCommande();
        statutCommande.setCommandeId(commande.getIdCommande());
        statutCommande.setDateChangementStatut(LocalDateTime.now());
        double montantPaye = 0;
        montantPaye = paiement.getMontantPaye();
        double montantCommande = commande.getPrixTotale();

        if(montantPaye < montantCommande){
            statutCommande.setCommandeStatut(CommandeStatut.PAIEMENTPARTIEL);
            commande.setCommandeStatut(CommandeStatut.PAIEMENTPARTIEL);
        }
        else if(montantPaye == commande.getPrixTotale()){
            statutCommande.setCommandeStatut(CommandeStatut.PAIEMENT_COMPLET);
            commande.setCommandeStatut(CommandeStatut.PAIEMENT_COMPLET);
        }
        if(montantPaye > montantCommande){
            statutCommande.setCommandeStatut(CommandeStatut.PAIEMENT_DEPASSE);
            commande.setCommandeStatut(CommandeStatut.PAIEMENT_DEPASSE);
        }

        statutCommandeRepos.save(statutCommande);
    }

    public static int sum(List<Double> list)
    {
        // iterator for accessing the elements
        Iterator<Double> it = list.iterator();

        int res = 0;
        while (it.hasNext()) {
            double num = it.next();
                res += num;
            }
        return res;
    }
}

