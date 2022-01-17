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
import java.util.*;
import java.util.stream.Collectors;

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

        List<Integer> QteLivreeList = new ArrayList<>();
        List<Integer> qteCmdList = new ArrayList<>();

        int totalQteLivree = 0;

        for (Livraison livraison : livraisons) {
        List<Livraison> livraisons2 = livraisonRepos.findByRefProduitAndCommande(livraison.getRefProduit(),commande.get());

        for (Livraison livraison1: livraisons2){
            totalQteLivree = livraison1.getQuantiteLivre();
        }

        int totalQteLivrees =  totalQteLivree + livraison.getQuantiteLivre();
        QteLivreeList.add(totalQteLivrees);

        Optional<Produit> produit = produitRepos.findById(livraison.getRefProduit());
        if (!produit.isPresent()) {
            throw new ResourceNotFoundException("produit non trouvé !");
        }
        List<LigneDeCommande> ligneDeCommandes = ligneDeCommandeRepos.findByCommandeAndRefProduit(commande.get(),produit.get().getId());
        updateStockProduitFinis(produit.get(),livraison );

        StockProduitFinis stockProduitFinis = stockProduitFinisRepos.findByRefProduit(produit.get().getId());
        livraison.setCommande(commande.get());

       int quantiteCommandee = 0;
       for (LigneDeCommande ligneDeCommande : ligneDeCommandes) {
           quantiteCommandee = ligneDeCommande.getQuantity();
           updateLivraison(livraison,ligneDeCommande,commande.get());
        }
       qteCmdList.add(quantiteCommandee);

         updateCommandeStatut(commande, QteLivreeList, qteCmdList);
         updateHistoriqueStockProduitFinis(stockProduitFinis, user.get(), livraison);
        }
        CommandeLivraison commandeLivraison = dataPMapper.mapFromCommandeLivraisonDto_toCommandeLivraison(commandeLivraisonDto);
        commandeLivraison.setDateLivraison(LocalDateTime.now());
        commandeLivraison = commandeLivraisonRepos.save(commandeLivraison);
        createHistorisueStatutCommande(commande.get());
        commandeLivraisonDto = dataPMapper.mapFromCommandeLivraison_toCommandeLivraisonDto(commandeLivraison);

        return commandeLivraisonDto;
    }

    private void updateCommandeStatut(Optional<Commande> commande, List<Integer> QteLivreeList, List<Integer> qteCmdList) {
        int totalQteCmd = qteCmdList.stream().reduce(0,(Integer::sum));
        int totalQteLivre = QteLivreeList.stream().reduce(0,(Integer::sum));

        if (totalQteCmd == 0 || totalQteLivre == 0 ){
            commande.get().setCommandeStatut(CommandeStatut.LIVRAISON_PARTIEL);
        }

        if (totalQteCmd > totalQteLivre ){
            commande.get().setCommandeStatut(CommandeStatut.LIVRAISON_PARTIEL);
        }

        else if (totalQteCmd == totalQteLivre){
            commande.get().setCommandeStatut(CommandeStatut.LIVRAISON_COMPLET);
        }
    }

    private void createHistorisueStatutCommande(Commande commande) {
        HistorisueStatutCommande historisueStatutCommande = new HistorisueStatutCommande();
        historisueStatutCommande.setCommandeStatut(commande.getCommandeStatut());
        historisueStatutCommande.setCommandeId(commande.getIdCommande());
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
        StockProduitFinis stockProduitFinis = stockProduitFinisRepos.findByRefProduit(produit.getId());
        stockProduitFinis.setDateDerniereMaj(LocalDateTime.now());
        int qttLivPourLivre = livraison.getQuantiteLivre();
        int quantiteEnStock = stockProduitFinis.getQuantite();
        stockProduitFinis.setQuantite(quantiteEnStock - qttLivPourLivre);

        stockProduitFinisRepos.save(stockProduitFinis);

        }

    public void updateLivraison(Livraison livraison,LigneDeCommande ligneDeCommande,Commande commande) throws LivraisonImpossibleException {

        LinkedList<Livraison> livraisons = livraisonRepos.findByRefProduitAndCommande(livraison.getRefProduit(),commande);
            int quantiteLivre = livraison.getQuantiteLivre();
             int quantiteCommandee = ligneDeCommande.getQuantity();
            if (livraisons.size() == 0) {
                livraison.setQuantiteLivre(quantiteLivre);
                int qteRestante = quantiteCommandee - quantiteLivre;
                livraison.setId(livraison.getId());
                livraison.setCommande(livraison.getCommande());
                livraison.setRefProduit(livraison.getRefProduit());
                livraison.setQuantiteCommandee(quantiteCommandee);
                livraison.setQuantiteRestante(qteRestante);
            } else if (livraisons.size() > 0) {
                int quantitePrecedente = livraisons.getLast().getQuantiteLivre();
                quantiteLivre += quantitePrecedente;
                int qteRestante = quantiteCommandee - quantiteLivre;

                livraison.setQuantiteCommandee(quantiteCommandee);
                livraison.setQuantiteLivre(quantiteLivre);
                livraison.setQuantiteRestante(qteRestante);
                if (quantiteLivre > quantiteCommandee) {
                    throw new LivraisonImpossibleException("On ne peut pas livrer plus que commandé !");
                }
                livraisonRepos.save(livraison);
            }
        }
}