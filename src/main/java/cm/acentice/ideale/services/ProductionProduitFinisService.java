package cm.acentice.ideale.services;

import cm.acentice.ideale.constants.TypeMovementStock;
import cm.acentice.ideale.dto.ProductionProduitFinisDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.exceptions.ResourceExisteDejaException;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductionProduitFinisService {

    private final SiteDeProductionRepos siteDeProductionRepos;
    private final MatierePremiereRepository matierePremiereRepository;
    private final ProductionProduitFinisRepos productionProduitFinisRepos;
    private final StockMatierePremiereRep stockMatierePremiereRep;
    private final ApprovisionnementMPService approvisionnementMPService;
    private final HistoriqueStockApproMPPDRepos historiqueStockApproMPPDRepos;
    private final StockProduitFinisRepos stockProduitFinisRepos;
    private final ProduitRepos produitRepos;
    private final UserRepos userRepos;
    private final HistoriqueStockProduitsFinisRepos historiqueStockProduitsFinisRepos;
    private final ProduitHasMatierePremierRepos produitHasMatierePremierRepos;
    private String idProduit = null;
    private String refMP = null;

    @Autowired
    public ProductionProduitFinisService(SiteDeProductionRepos siteDeProductionRepos, MatierePremiereRepository matierePremiereRepository, ProductionProduitFinisRepos productionProduitFinisRepos, StockMatierePremiereRep stockMatierePremiereRep, ApprovisionnementMPService approvisionnementMPService, HistoriqueStockApproMPPDRepos historiqueStockApproMPPDRepos, StockProduitFinisRepos stockProduitFinisRepos, ProduitRepos produitRepos, UserRepos userRepos, HistoriqueStockProduitsFinisRepos historiqueStockProduitsFinisRepos, ProduitHasMatierePremierRepos produitHasMatierePremierRepos) {
        this.siteDeProductionRepos = siteDeProductionRepos;
        this.matierePremiereRepository = matierePremiereRepository;
        this.productionProduitFinisRepos = productionProduitFinisRepos;
        this.stockMatierePremiereRep = stockMatierePremiereRep;
        this.approvisionnementMPService = approvisionnementMPService;
        this.historiqueStockApproMPPDRepos = historiqueStockApproMPPDRepos;
        this.stockProduitFinisRepos = stockProduitFinisRepos;
        this.produitRepos = produitRepos;
        this.userRepos = userRepos;
        this.historiqueStockProduitsFinisRepos = historiqueStockProduitsFinisRepos;
        this.produitHasMatierePremierRepos = produitHasMatierePremierRepos;
    }
    @Transactional
    public ProductionProduitFinis create(ProductionProduitFinisDto produitFinisDto) throws ResourceNotFoundException {

        ProductionProduitFinis produitFinis = new ProductionProduitFinis();
        Long id = produitFinisDto.getIdSiteDeProduction();
        Optional<SiteDeProduction> siteDeProduction = siteDeProductionRepos.findById(id);
        if (!siteDeProduction.isPresent()){
            throw new ResourceNotFoundException("SiteDeProduction not found !");
        }
        produitFinis.setSiteDeProduction(siteDeProduction.get());
        produitFinis.setQuantitéProduitsFinisDefectueux(produitFinisDto.getQuantitéProduitsFinisDefectueux());
        produitFinis.setIdAgentProduction(produitFinisDto.getIdAgentProduction());
        produitFinis.setPoidsBobine(produitFinisDto.getPoidsBobine());
        produitFinis.setDateHeureDebutProduction(produitFinisDto.getDateHeureDebutProduction());
        produitFinis.setDateHeureFinProduction(produitFinisDto.getDateHeureFinProduction());

        produitFinis.setRefProduitsFinisDefectueux(produitFinisDto.getRefProduitsFinisDefectueux());
        produitFinis.setCommentaires(produitFinisDto.getCommentaires());
        produitFinis.setReleveCompteurDebutKwh(produitFinisDto.getReleveCompteurDebutKwh());
        List<ProduitHasMatierePremier>produitHasMatierePremiers = produitFinisDto.getProduitHasMatierePremiers();
        produitFinis.setProduitHasMatierePremiers(produitHasMatierePremiers);
        produitFinis.setReleveCompteurFinKwh(produitFinisDto.getReleveCompteurFinKwh());

        ProductionProduitFinis fProduitFinis = produitFinis;
        produitHasMatierePremiers.forEach(phasMp ->{
            phasMp.setProductionProduitFinis(fProduitFinis);
            MatierePremiere matierePremiere = phasMp.getMatierePremiere();
            Produit produit = phasMp.getProduit();

            refMP = matierePremiere.getReference();
            if(refMP == null){
                try {
                    throw new ResourceNotFoundException("MatierePremiere reference non trouvé !");
                } catch (ResourceNotFoundException e) {
                    e.getMessage();
                }
                return;
            }
            idProduit = produit.getId();
            if(idProduit == null){
                try {
                    throw new ResourceNotFoundException("Produit reference non trouvé !");
                } catch (ResourceNotFoundException e) {
                    e.getMessage();
                }
            }
            int quantiteMPUtilise = matierePremiere.getQuantiteMP();
            int quantiteProduitsFabrique = produit.getQuantiteFabrique();

            phasMp.setMatierePremiere(matierePremiere);
            phasMp.setProduit(produit);
            phasMp.setQuantitéMPUtilise(quantiteMPUtilise);
            phasMp.setQuantitéProduitsFinis(quantiteProduitsFabrique);

            if (stockProduitFinisRepos.findByProduit(produit) == null){
                createStockProduitFinis(produit,produitFinisDto);
            }else{
                try {
                    throw new ResourceExisteDejaException("Ce produit reference existe deja !");
                } catch (ResourceExisteDejaException e) {
                    e.getMessage();
                }
                return;
            }

          });
        produitFinis = productionProduitFinisRepos.save(fProduitFinis);
        createHistoriqueStockProduitFini(produitFinisDto);
        createtHistoriqueStockMP(produitFinisDto);
        return produitFinis;
    }

    private void createtHistoriqueStockMP(ProductionProduitFinisDto produitFinisDto) throws ResourceNotFoundException {
        produitFinisDto.getProduitHasMatierePremiers().forEach(prodHasMP -> {
            HistoriqueStockApprovMPPD histApprovMP = new HistoriqueStockApprovMPPD();
            String refMatPrem = prodHasMP.getMatierePremiere().getReference();
            if (stockMatierePremiereRep.findByRefMP(refMatPrem) == null) {
                try {
                    throw new ResourceNotFoundException("Aucun stock associé à cette matiere Premiere !");
                } catch (ResourceNotFoundException e) {
                    e.getMessage();
                }
            }
            Long id = produitFinisDto.getIdSiteDeProduction();
            Optional<SiteDeProduction> siteDeProduction = siteDeProductionRepos.findById(id);
            if(!siteDeProduction.isPresent()){
                try {
                    throw new ResourceNotFoundException("Site de production non trouvé !");
                } catch (ResourceNotFoundException e) {
                    e.getMessage();
                }
            }
            histApprovMP.setIdSiteDeProduction(id);
            StockMatierePremiere stockMatiereP = stockMatierePremiereRep.findByRefMP(refMatPrem);
            int quantiteStockMP = stockMatiereP.getQuantite();
            int quantiteMPUtilise = prodHasMP.getMatierePremiere().getQuantiteMP();
            int nouvelleValeur = quantiteStockMP - quantiteMPUtilise;
            stockMatiereP.setQuantite(nouvelleValeur);
            histApprovMP.setNouvelleValeurStock(nouvelleValeur);
            histApprovMP.setQuantitéModifiee(quantiteMPUtilise);

            histApprovMP.setTypeMouvement(TypeMovementStock.SORTIE.name());
            histApprovMP.setAncienneValeurStock(quantiteStockMP);
            histApprovMP.setDateMAJ(LocalDate.now());
            histApprovMP.setRefArticle(stockMatiereP.getRefMP());
            historiqueStockApproMPPDRepos.save(histApprovMP);
        });

      }

    public void  createHistoriqueStockProduitFini(ProductionProduitFinisDto produitFinisDto) throws ResourceNotFoundException {
        Optional<User> user = userRepos.findById(produitFinisDto.getIdAgentProduction());
        if(!user.isPresent()){
            throw new ResourceNotFoundException("Agent de production non trouvé !");
        }
        Optional<SiteDeProduction> siteDeProduction = siteDeProductionRepos.findById(produitFinisDto.getIdSiteDeProduction());
        if(!siteDeProduction.isPresent()){
            throw new ResourceNotFoundException("Site de producrion non trouvé !");
        }
        produitFinisDto.getProduitHasMatierePremiers().forEach(prodHasMP ->{
            HistoriqueStockProduitsFinis historiqueStockProduitsFinis = new HistoriqueStockProduitsFinis();
            historiqueStockProduitsFinis.setUser(user.get());
            Produit produit = prodHasMP.getProduit();
            historiqueStockProduitsFinis.setQuantiteModifiee(produit.getQuantiteFabrique());
            historiqueStockProduitsFinis.setNouvelleValeurStock(produit.getQuantiteFabrique()+historiqueStockProduitsFinis.getNouvelleValeurStock());
            historiqueStockProduitsFinis.setRefArticle(produit.getId());
            historiqueStockProduitsFinis.setIdSiteDeProduction(siteDeProduction.get().getId());
            historiqueStockProduitsFinis.setTypeMouvement(TypeMovementStock.ENTREE.name());
            historiqueStockProduitsFinis.setAncienneValeurStock(historiqueStockProduitsFinis.getAncienneValeurStock());

            historiqueStockProduitsFinis.setDateMAJ(LocalDate.now());
            historiqueStockProduitsFinisRepos.save(historiqueStockProduitsFinis);
        });

    }

    public void createStockProduitFinis(Produit produit,ProductionProduitFinisDto produitFinisDto){
        StockProduitFinis stockProduitFinis = new StockProduitFinis();
        stockProduitFinis.setProduit(produit);
        stockProduitFinis.setQuantite_Min(10);
        stockProduitFinis.setDateDerniereMaj(LocalDate.now());
        Long id = produitFinisDto.getIdSiteDeProduction();
        SiteDeProduction siteDeProduction = siteDeProductionRepos.findById(id).get();
        stockProduitFinis.setSiteDeProduction(siteDeProduction);
        int quantiteFabrique = produit.getQuantiteFabrique();
        stockProduitFinis.setQuantite(quantiteFabrique);
        stockProduitFinisRepos.save(stockProduitFinis);
    }

    public List<ProductionProduitFinis>getAllProduitFinis(){

        return productionProduitFinisRepos.findAll();
    }

}


