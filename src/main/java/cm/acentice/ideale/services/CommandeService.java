package cm.acentice.ideale.services;

import cm.acentice.ideale.constants.CommandeStatut;
import cm.acentice.ideale.dto.CommandeDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.exceptions.StockInsufficantException;
import cm.acentice.ideale.interfaces.CommandeInterface;
import cm.acentice.ideale.interfaces.DataPMapper;
import cm.acentice.ideale.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommandeService implements CommandeInterface {

    private final DataPMapper dataPMapper;
    private final CommandeRepository commandeRepository;
    private final SiteDeVenteRepos siteDeVenteRepos;
    private final StockProduitFinisRepos stockProduitFinisRepos;
    private final UserRepos userRepos;
    private final HistoriqueStockProduitsFinisRepos historiqueStockProduitsFinisRepos;
    private final ClientRepository clientRepository;
    private final HistoriqueStatutCommandeRepos historiqueStatutCommandeRepos;
    private final ProduitRepos produitRepos;
    private final LigneDeCommandeRepos ligneDeCommandeRepos;

    @Override
    public CommandeDto create(CommandeDto commandeDto) throws ResourceNotFoundException, StockInsufficantException {

        String idSiteDeVente = commandeDto.getIdSiteDeVente();
        Optional<SiteDeVente> siteDeVente = siteDeVenteRepos.findById(idSiteDeVente);
        if(!siteDeVente.isPresent()){
            throw new ResourceNotFoundException("Site de vente non trouvé !");
        }
        commandeDto.setIdSiteDeVente(siteDeVente.get().getId());

        Long clientid = commandeDto.getIdClient();
        Optional<Client> client = clientRepository.findById(clientid);

        if(!client.isPresent()){
            throw new ResourceNotFoundException("Client non trouvé !");
        }
        commandeDto.setIdClient(clientid);
        Commande commande = dataPMapper.mapFromCmdDtoToCommande(commandeDto);
        commande.setDateDeCommande(LocalDateTime.now());
        commande.setCommandeStatut(CommandeStatut.COMMANDE_ENREGISTREE);
        commande = commandeRepository.save(commande);
        getHistorisueStatutCommande(commande);
        commandeDto = dataPMapper.mapFromCmdToCommandeDto(commande);
        List<LigneDeCommande> ligneDeCommandes = commandeDto.getLigneDeCommandes();

        double prixTotale = 0;
        for (LigneDeCommande ligneDeCommande : ligneDeCommandes) {
            int quantite = ligneDeCommande.getQuantity();
            double prixUnitaire = ligneDeCommande.getPrixUnitaire();
            ligneDeCommande.setPrixUnitaire(prixUnitaire);
            double prix = prixUnitaire * quantite;
            prixTotale += prix;
            commande.setPrixTotale(prixTotale);
            ligneDeCommande.setQuantity(quantite);
            createLigneDeCommande( ligneDeCommande, commandeDto, commande);
        }
        return commandeDto;
    }

    private void getHistorisueStatutCommande(Commande commande){
        HistorisueStatutCommande statutCmd = new HistorisueStatutCommande();
        statutCmd.setDateChangementStatut(LocalDateTime.now());
        statutCmd.setCommandeStatut(commande.getCommandeStatut());
        statutCmd.setCommandeId(commande.getIdCommande());
        historiqueStatutCommandeRepos.save(statutCmd);
    }
    private void createLigneDeCommande(LigneDeCommande ligneDeCommande, CommandeDto commandeDto,Commande commande){

        List<LigneDeCommande> ligneDeCommandes = commandeDto.getLigneDeCommandes();
        ligneDeCommande.setCommande(commande);
        ligneDeCommande.setRefProduit(ligneDeCommande.getRefProduit());
        ligneDeCommande.setCommande(commande);
        ligneDeCommandeRepos.save(ligneDeCommande);
      }
    }
