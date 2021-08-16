package cm.acentice.ideale.services;

import cm.acentice.ideale.constants.TypeMovementStock;
import cm.acentice.ideale.dto.ApprovisionnementProduitFinisDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.ApprovisionnementProduitFinisRepos;
import cm.acentice.ideale.repositories.HistoriqueStockProduitsFinisRepos;
import cm.acentice.ideale.repositories.SiteDeVenteRepos;
import cm.acentice.ideale.repositories.StockProduitFinisRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ApprovisionnementPFService {

    private final ApprovisionnementProduitFinisRepos approvisionnementProduitFinisRepos;
    private final StockProduitFinisRepos stockProduitFinisRepos;
    private final SiteDeVenteRepos siteDeVenteRepos;
    private final HistoriqueStockProduitsFinisRepos historiqueStockProduitsFinisRepos;

    @Autowired
    public ApprovisionnementPFService(ApprovisionnementProduitFinisRepos approvisionnementProduitFinisRepos, StockProduitFinisRepos stockProduitFinisRepos, SiteDeVenteRepos siteDeVenteRepos, HistoriqueStockProduitsFinisRepos historiqueStockProduitsFinisRepos) {
        this.approvisionnementProduitFinisRepos = approvisionnementProduitFinisRepos;
        this.stockProduitFinisRepos = stockProduitFinisRepos;
        this.siteDeVenteRepos = siteDeVenteRepos;
        this.historiqueStockProduitsFinisRepos = historiqueStockProduitsFinisRepos;
    }

    public void create(ApprovisionnementProduitFinisDto approvisionnementPFDto,Long idSiteDeVente) throws ResourceNotFoundException {
        ApprovisionnementProduitFinis approvisionnementProduitFinis = new ApprovisionnementProduitFinis();

        Optional<SiteDeVente> siteDeVente = siteDeVenteRepos.findById(idSiteDeVente);
        if(!siteDeVente.isPresent()){
            throw new ResourceNotFoundException("Site de vente non trouvé");
        }
        approvisionnementProduitFinis.setDateApprovisionnement(LocalDate.now());
        approvisionnementProduitFinis.setSiteDeVente(siteDeVente.get());
        approvisionnementProduitFinis.setSiteDeProduction(approvisionnementPFDto.getSiteDeProduction());
        approvisionnementProduitFinis.setUser(approvisionnementPFDto.getUser());
        approvisionnementProduitFinis.setReceptionist(approvisionnementPFDto.getReceptionist());
        approvisionnementProduitFinis.setApprovissionnementPFHasProduits(approvisionnementPFDto.getApprovissionnementPFHasProduits());
        List<ApprovissionnementPFHasProduit> approvPFHasProduits = approvisionnementPFDto.getApprovissionnementPFHasProduits();

        approvPFHasProduits.forEach(pf->{
           pf.setApprovisionnementProduitFinis(approvisionnementProduitFinis);
           Produit produit = pf.getProduit();
           int quantite = produit.getQuantiteFabrique();
           pf.setQuantiteFabrique(quantite);

        approvisionnementProduitFinis.setApprovissionnementPFHasProduits(approvPFHasProduits);
        approvisionnementProduitFinisRepos.save(approvisionnementProduitFinis);

        if (stockProduitFinisRepos.findByProduit(produit) == null) {
            createStockProduitFinis(produit);
        } else if(stockProduitFinisRepos.findByProduit(produit) != null) {
            updateStockProduitFinis(produit);
        }
      });
    }

    public void createStockProduitFinis(Produit produit){
        StockProduitFinis stockProduitFinis = new StockProduitFinis();
        stockProduitFinis.setProduit(produit);
        stockProduitFinis.setQuantite_Min(10);
        stockProduitFinis.setDateDerniereMaj(LocalDate.now());
        int quantiteFabrique = produit.getQuantiteFabrique();
        stockProduitFinis.setQuantite(quantiteFabrique);
        createtHistoriqueStockPF(stockProduitFinis,produit);
        stockProduitFinisRepos.save(stockProduitFinis);
    }

    public void updateStockProduitFinis(Produit produit){
        StockProduitFinis stockPF = stockProduitFinisRepos.findByProduit(produit);
        stockPF.setId(stockPF.getId());
        int quantiteFabrique = produit.getQuantiteFabrique();
        stockPF.setQuantite(quantiteFabrique + stockPF.getQuantite());
        createtHistoriqueStockPF(stockPF,produit);
        stockProduitFinisRepos.save(stockPF);
    }

    public HistoriqueStockProduitsFinis createtHistoriqueStockPF(StockProduitFinis stockProduitFinis, Produit produit){
        HistoriqueStockProduitsFinis historiqueStockProduitsFinis = new HistoriqueStockProduitsFinis();
        historiqueStockProduitsFinis.setTypeMouvement(TypeMovementStock.ENTREE.name());
        historiqueStockProduitsFinis.setDateMAJ(new Date());
        historiqueStockProduitsFinis.setRefArticle(produit.getId());
        int nouvelleValeur = stockProduitFinis.getQuantite();
        int quantiteFabrique = produit.getQuantiteFabrique();
        historiqueStockProduitsFinis.setNouvelleValeurStock(nouvelleValeur);
        historiqueStockProduitsFinis.setAncienneValeurStock(nouvelleValeur - quantiteFabrique);
        historiqueStockProduitsFinis.setQuantiteModifiee(quantiteFabrique);

        return historiqueStockProduitsFinisRepos.save(historiqueStockProduitsFinis);
    }
}

