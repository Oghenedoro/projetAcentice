package cm.acentice.ideale.services;

import cm.acentice.ideale.constants.CommandeStatut;
import cm.acentice.ideale.constants.TypeMovementStock;
import cm.acentice.ideale.dto.CommandeLivraisonDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.exceptions.LivraisonImpossibleException;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.interfaces.CommandeLivraisonInt;
import cm.acentice.ideale.interfaces.DataPMapper;
import cm.acentice.ideale.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommandeLivraisonService implements CommandeLivraisonInt {

    private final DataPMapper dataPMapper;
    private final LivraisonRepos livraisonRepos;
    private final ClientRepository clientRepository;
    private final CommandeRepository commandeRepository;
    private final UserRepos userRepos;
    private final HistoriqueStatutCommandeRepos HistorisueStatutCommandeRepos;
    private final HistoriqueStockProduitsFinisRepos historiqueStockProduitsFinisRepos;
    private final StockProduitFinisRepos stockProduitFinisRepos;
    private final ProduitRepos produitRepos;
    private final LigneDeCommandeRepos ligneDeCommandeRepos;
    private final CommandeLivraisonRepos commandeLivraisonRepos;

    @Transactional
    @Override
    public CommandeLivraisonDto create(CommandeLivraisonDto commandeLivraisonDto) throws ResourceNotFoundException, LivraisonImpossibleException {

        Long idCommande = commandeLivraisonDto.getIdCommande();
        Optional<Commande> commande = commandeRepository.findById(idCommande);
        if (!commande.isPresent()) {
            throw new ResourceNotFoundException("Commande non trouvé !");
        }
        commandeLivraisonDto.setIdCommande(commande.get().getIdCommande());

        Long idClient = commandeLivraisonDto.getIdClient();
        Optional<Client> client = clientRepository.findById(idClient);
        if (!client.isPresent()) {
            throw new ResourceNotFoundException("Client non trouvé !");
        }
        commandeLivraisonDto.setIdClient(client.get().getIdClient());

        Optional<User> user = userRepos.findById(commandeLivraisonDto.getIdUser());
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User non trouvé !");
        }
        commandeLivraisonDto.setIdUser(user.get().getUserId());

        List<Livraison> livraisons = commandeLivraisonDto.getLivraisons();
        for (Livraison livraison : livraisons) {
            LinkedList<LigneDeCommande> ligneDeCommandes = ligneDeCommandeRepos.findByRefProduit(livraison.getRefProduit());

            Optional<Produit> produit = produitRepos.findById(livraison.getRefProduit());
            if (!produit.isPresent()) {
                throw new ResourceNotFoundException("produit non trouvé !");
            }
            updateStockProduitFinis(produit.get(),livraison );

            StockProduitFinis stockProduitFinis = stockProduitFinisRepos.findByProduit(produit.get());
            livraison.setCommande(commande.get());

           for (LigneDeCommande ligneDeCommande : ligneDeCommandes) {
               updateLivraison(livraison,ligneDeCommande);
            }

            createHistorisueStatutCommande(stockProduitFinis,livraison,commande.get());
            updateHistoriqueStockProduitFinis(stockProduitFinis, user.get(), livraison);
        }

        CommandeLivraison commandeLivraison = dataPMapper.mapFromCommandeLivraisonDto_toCommandeLivraison(commandeLivraisonDto);
        commandeLivraison.setDateLivraison(LocalDateTime.now());
        commandeLivraison = commandeLivraisonRepos.save(commandeLivraison);
        commandeLivraisonDto = dataPMapper.mapFromCommandeLivraison_toCommandeLivraisonDto(commandeLivraison);

        return commandeLivraisonDto;
    }

    private void createHistorisueStatutCommande(StockProduitFinis stockProduitFinis,Livraison livraison,Commande commande) throws LivraisonImpossibleException {
        HistorisueStatutCommande historisueStatutCommande = new HistorisueStatutCommande();
        int qtyCommande = livraison.getQuantiteCommandee();
        int qtyRestante = livraison.getQuantiteRestante();

        if (stockProduitFinis.getQuantite() < commande.getQuantiteGlobale()) {
            historisueStatutCommande.setCommandeStatut(CommandeStatut.STOCK_NON_VALIDEE);
        } else if (stockProduitFinis.getQuantite() >= commande.getQuantiteGlobale()) {
            historisueStatutCommande.setCommandeStatut(CommandeStatut.STOCK_VALIDEE);
        }

        livraison.setQuantiteCommandee(qtyCommande);
        livraison.setQuantiteRestante(qtyRestante);
        historisueStatutCommande.setCommandeId(livraison.getCommande().getIdCommande());
        historisueStatutCommande.setDateChangementStatut(LocalDateTime.now());

        HistorisueStatutCommandeRepos.save(historisueStatutCommande);
    }

    private void updateHistoriqueStockProduitFinis(StockProduitFinis stockProduitFinis, User user, Livraison livraison) throws ResourceNotFoundException {
        HistoriqueStockProduitsFinis finis = new HistoriqueStockProduitsFinis();
        finis.setTypeMouvement(TypeMovementStock.SORTIE.name());
        finis.setDateMAJ(LocalDateTime.now());
        finis.setRefArticle(stockProduitFinis.getProduit().getId());

        int qtyLivree = livraison.getQuantiteLivre();
        int qtyStock = stockProduitFinis.getQuantite();

        finis.setNouvelleValeurStock(qtyStock);
        finis.setAncienneValeurStock(qtyStock + qtyLivree);

        finis.setQuantiteModifiee(qtyLivree);
        finis.setIdSiteDeProduction(finis.getIdSiteDeProduction());
        finis.setUser(user);
        finis.setIdSiteDeProduction(stockProduitFinis.getSiteDeProduction().getId());
        historiqueStockProduitsFinisRepos.save(finis);
    }

    private void updateStockProduitFinis(Produit produit, Livraison livraison) {
        StockProduitFinis stockProduitFinis = stockProduitFinisRepos.findByProduit(produit);
        stockProduitFinis.setDateDerniereMaj(LocalDateTime.now());
        int qttLivPourLivre = livraison.getQuantiteLivre();
        int quantiteEnStock = stockProduitFinis.getQuantite();
        stockProduitFinis.setQuantite(quantiteEnStock - qttLivPourLivre);

        stockProduitFinisRepos.save(stockProduitFinis);

        }

    public void updateLivraison(Livraison livraison,LigneDeCommande ligneDeCommande) throws LivraisonImpossibleException {

        LinkedList<Livraison> livraisons = livraisonRepos.findByRefProduit(livraison.getRefProduit());
        int quantiteCommandee = ligneDeCommande.getQuantity();
        if(livraisons.size() == 0){
            int quantiteLivre = livraison.getQuantiteLivre();
            int qteRestante = quantiteCommandee - quantiteLivre;
            livraison.setId(livraison.getId());
            livraison.setCommande(livraison.getCommande());
            livraison.setRefProduit(livraison.getRefProduit());
            livraison.setQuantiteCommandee(quantiteCommandee);
            livraison.setQuantiteRestante(qteRestante);
            if(quantiteLivre > quantiteCommandee ){
                throw new LivraisonImpossibleException("On ne peut pas livrer plus que commandé !");
            }
        }else if(livraisons.size() > 0){
        int quantitePrecedente = livraisons.getLast().getQuantiteLivre();
        int totalQteLivre = quantitePrecedente + livraison.getQuantiteLivre();
        int qteRestante = quantiteCommandee - totalQteLivre;
        livraison.setQuantiteCommandee(quantiteCommandee);
        livraison.setQuantiteLivre(totalQteLivre);
        livraison.setQuantiteRestante(qteRestante);
            if(totalQteLivre > quantiteCommandee ){
                throw new LivraisonImpossibleException("On ne peut pas livrer plus que commandé !");
            }
        livraisonRepos.save(livraison);
    }
}}