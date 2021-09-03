package cm.acentice.ideale.services;

import cm.acentice.ideale.constants.TypeMovementStock;
import cm.acentice.ideale.dto.ApprovisionnementProduitFinisDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ApprovisionnementPFService {

    private final ApprovisionnementProduitFinisRepos approvisionnementProduitFinisRepos;
    private final StockProduitFinisRepos stockProduitFinisRepos;
    private final SiteDeVenteRepos siteDeVenteRepos;
    private final SiteDeProductionRepos siteDeProductionRepos;
    private final HistoriqueStockProduitsFinisRepos historiqueStockProduitsFinisRepos;
    private final ProduitRepos produitRepos;

    @Autowired
    public ApprovisionnementPFService(ApprovisionnementProduitFinisRepos approvisionnementProduitFinisRepos, StockProduitFinisRepos stockProduitFinisRepos, SiteDeVenteRepos siteDeVenteRepos, SiteDeProductionRepos siteDeProductionRepos, HistoriqueStockProduitsFinisRepos historiqueStockProduitsFinisRepos, ProduitRepos produitRepos) {
        this.approvisionnementProduitFinisRepos = approvisionnementProduitFinisRepos;
        this.stockProduitFinisRepos = stockProduitFinisRepos;
        this.siteDeVenteRepos = siteDeVenteRepos;
        this.siteDeProductionRepos = siteDeProductionRepos;
        this.historiqueStockProduitsFinisRepos = historiqueStockProduitsFinisRepos;
        this.produitRepos = produitRepos;
    }

    public void create(ApprovisionnementProduitFinisDto approvisionnementPFDto) throws ResourceNotFoundException {
        ApprovisionnementProduitFinis approvisionnementProduitFinis = new ApprovisionnementProduitFinis();

        approvisionnementProduitFinis.setDateApprovisionnement(LocalDateTime.now());
        approvisionnementProduitFinis.setUser(approvisionnementPFDto.getUser());
        approvisionnementProduitFinis.setReceptionist(approvisionnementPFDto.getReceptionist());
        approvisionnementProduitFinis.setApprovissionnementPFHasProduits(approvisionnementPFDto.getApprovissionnementPFHasProduits());
        List<ApprovissionnementPFHasProduit> approvPFHasProduits = approvisionnementPFDto.getApprovissionnementPFHasProduits();

        Long idSiteDeProd = approvisionnementPFDto.getIdSiteDeProduction();
        Long idSiteDeVente = approvisionnementPFDto.getIdFiteDeVente();

        Optional<SiteDeProduction> siteDeProduction = siteDeProductionRepos.findById(idSiteDeProd);
        Optional<SiteDeVente> siteDeVente = siteDeVenteRepos.findById(idSiteDeVente);

        if(!siteDeProduction.isPresent()){
            throw new ResourceNotFoundException("Site de Production non trouvé");
        }
        if(!siteDeVente.isPresent()){
            throw new ResourceNotFoundException("Site de Vente non trouvé");
        }
        approvisionnementProduitFinis.setSiteDeProduction(siteDeProduction.get());
        approvisionnementProduitFinis.setSiteDeVente(siteDeVente.get());
        approvPFHasProduits.forEach(pf->{
           pf.setApprovisionnementProduitFinis(approvisionnementProduitFinis);
           Produit produit = pf.getProduit();
           Produit produit1 = produitRepos.findById(produit.getId()).get();
           int quantite = produit1.getQuantiteFabrique();
           pf.setQuantiteFabrique(quantite);

        approvisionnementProduitFinis.setApprovissionnementPFHasProduits(approvPFHasProduits);
        approvisionnementProduitFinisRepos.save(approvisionnementProduitFinis);
          //  createtHistoriqueStockPF(produit,approvisionnementPF);

        if (stockProduitFinisRepos.findByProduit(produit1) == null) {
            createStockProduitFinis(produit1,approvisionnementPFDto);
        } else if(stockProduitFinisRepos.findByProduit(produit1) != null) {
            updateStockProduitFinis(produit1,approvisionnementPFDto);
        }
      });
    }

    public void createStockProduitFinis(Produit produit,ApprovisionnementProduitFinisDto approvisionnementPF){
        StockProduitFinis stockProduitFinis = new StockProduitFinis();
        stockProduitFinis.setProduit(produit);
        stockProduitFinis.setQuantite_Min(10);
        stockProduitFinis.setDateDerniereMaj(LocalDate.now());
        int quantiteFabrique = produit.getQuantiteFabrique();
        stockProduitFinis.setQuantite(quantiteFabrique);
        stockProduitFinisRepos.save(stockProduitFinis);
        createtHistoriqueStockPF(produit,approvisionnementPF);
    }

    private void updateStockProduitFinis(Produit produit,ApprovisionnementProduitFinisDto approvisionnementPF){
        StockProduitFinis stockPF = stockProduitFinisRepos.findByProduit(produit);
        stockPF.setId(stockPF.getId());
        int quantiteFabrique = produit.getQuantiteFabrique();
        stockPF.setQuantite(quantiteFabrique);
        stockProduitFinisRepos.save(stockPF);
        createtHistoriqueStockPF(produit,approvisionnementPF);
    }

    private HistoriqueStockProduitsFinis createtHistoriqueStockPF( Produit produit,
        ApprovisionnementProduitFinisDto approvisionnementPF){
        HistoriqueStockProduitsFinis historiqueStockProduitsFinis = new HistoriqueStockProduitsFinis();
        historiqueStockProduitsFinis.setTypeMouvement(TypeMovementStock.ENTREE.name());
        historiqueStockProduitsFinis.setDateMAJ(new Date());
        historiqueStockProduitsFinis.setRefArticle(produit.getId());
        int quantiteFabrique = produit.getQuantiteFabrique();
        historiqueStockProduitsFinis.setNouvelleValeurStock(quantiteFabrique);
        historiqueStockProduitsFinis.setAncienneValeurStock(0);
        historiqueStockProduitsFinis.setQuantiteModifiee(quantiteFabrique);
        historiqueStockProduitsFinis.setIdSiteDeProduction(approvisionnementPF.getIdSiteDeProduction());
        historiqueStockProduitsFinis.setIdSiteVente(approvisionnementPF.getIdFiteDeVente());
        return historiqueStockProduitsFinisRepos.save(historiqueStockProduitsFinis);
    }
}

