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
    private final HistoriqueStatutCommandeRepos HistorisueStatutCommandeRepos;
    private final ProduitRepos produitRepos;

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


        List<LigneDeCommande> ligneDeCommandes = commandeDto.getLigneDeCommandes();
        double prixTotale = 0;
        for (LigneDeCommande ligneDeCommande : ligneDeCommandes) {
            String idProduit = ligneDeCommande.getRefProduit();
            Optional<Produit> produit = produitRepos.findById(idProduit);
            if(!produit.isPresent()){
                throw new ResourceNotFoundException("Produit non trouvé");
            }
            StockProduitFinis stockProduitFinis = stockProduitFinisRepos.findByProduit(produit.get());
            ligneDeCommande.setQuantity(ligneDeCommande.getQuantity());
            ligneDeCommande.setPrixUnitaire(ligneDeCommande.getPrixUnitaire());
            ligneDeCommande.setRefProduit(produit.get().getId());
            ligneDeCommande.setCommande(commande);
            prixTotale += ligneDeCommande.getPrixUnitaire() * ligneDeCommande.getQuantity();
            commande.setPrixTotale(prixTotale);
            int quantiteCommande = ligneDeCommandes.stream().map(lc -> lc.getQuantity())
                    .reduce(0,Integer :: sum);
            commande.setQuantiteGlobale(quantiteCommande);
            /*int quantiteEnStock = stockProduitFinis.getQuantite();
            HistorisueStatutCommande statutCmd = new HistorisueStatutCommande();
            if(commande.getQuantiteGlobale() > quantiteEnStock){
                statutCmd.setCommandeId(commande.getIdCommande());
                statutCmd.setCommandeStatut(CommandeStatut.STOCK_NON_VALIDEE);
                statutCmd.setDateChangementStatut(LocalDateTime.now());
                HistorisueStatutCommandeRepos.save(statutCmd);
            }else if(quantiteCommande <= quantiteEnStock){
                statutCmd.setCommandeStatut(CommandeStatut.STOCK_VALIDEE);
            }*/

              commande = commandeRepository.save(commande);
              commandeDto = dataPMapper.mapFromCmdToCommandeDto(commande);
            }


        return commandeDto;
    }


}
