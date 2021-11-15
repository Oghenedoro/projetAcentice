package cm.acentice.ideale.services;

import cm.acentice.ideale.constants.TypeMovementStock;
import cm.acentice.ideale.dto.ApprovisionnementProduitFinisDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.exceptions.ResourceExisteDejaException;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final UserRepos userRepos;

    @Autowired
    public ApprovisionnementPFService(ApprovisionnementProduitFinisRepos approvisionnementProduitFinisRepos, StockProduitFinisRepos stockProduitFinisRepos, SiteDeVenteRepos siteDeVenteRepos, SiteDeProductionRepos siteDeProductionRepos, HistoriqueStockProduitsFinisRepos historiqueStockProduitsFinisRepos, ProduitRepos produitRepos, UserRepos userRepos) {
        this.approvisionnementProduitFinisRepos = approvisionnementProduitFinisRepos;
        this.stockProduitFinisRepos = stockProduitFinisRepos;
        this.siteDeVenteRepos = siteDeVenteRepos;
        this.siteDeProductionRepos = siteDeProductionRepos;
        this.historiqueStockProduitsFinisRepos = historiqueStockProduitsFinisRepos;
        this.produitRepos = produitRepos;
        this.userRepos = userRepos;
    }
    @Transactional
    public void create(ApprovisionnementProduitFinisDto approvisionnementPFDto) throws ResourceNotFoundException,ResourceExisteDejaException {
        ApprovisionnementProduitFinis approvisionnementProduitFinis = new ApprovisionnementProduitFinis();

        approvisionnementProduitFinis.setDateApprovisionnement(LocalDateTime.now());
        Long id = approvisionnementPFDto.getUserId();
        Optional<User> user = userRepos.findById(id);
        if(!user.isPresent()){
            throw new ResourceNotFoundException("User non trouvé");
        }
        approvisionnementProduitFinis.setUser(user.get());

        Long idSiteDeProd = approvisionnementPFDto.getIdSiteDeProduction();
        Optional<SiteDeProduction> siteDeProduction = siteDeProductionRepos.findById(idSiteDeProd);
        if(!siteDeProduction.isPresent()){
            throw new ResourceNotFoundException("Site de Production non trouvé");
        }
        approvisionnementProduitFinis.setSiteDeProduction(siteDeProduction.get());

        String idSiteDeVente = approvisionnementPFDto.getIdSiteDeVente();
        Optional<SiteDeVente> siteDeVente = siteDeVenteRepos.findById(idSiteDeVente);
        if(!siteDeVente.isPresent()){
            throw new ResourceNotFoundException("Site de Vente non trouvé");
        }
        approvisionnementProduitFinis.setSiteDeVente(siteDeVente.get());

        approvisionnementProduitFinis.setReceptionist(approvisionnementPFDto.getReceptionist());
        approvisionnementProduitFinis.setApprovissionnementPFHasProduits(approvisionnementPFDto.getApprovissionnementPFHasProduits());
        List<ApprovissionnementPFHasProduit> approvPFHasProduits = approvisionnementPFDto.getApprovissionnementPFHasProduits();

        approvPFHasProduits.forEach(pf->{
           pf.setApprovisionnementProduitFinis(approvisionnementProduitFinis);
           Produit produit = pf.getProduit();
           Produit produit1 = produitRepos.findById(produit.getId()).get();
           int quantite = produit1.getQuantiteFabrique();
           pf.setQuantiteFabrique(quantite);
        approvisionnementProduitFinis.setApprovissionnementPFHasProduits(approvPFHasProduits);

        if (stockProduitFinisRepos.findByProduit(produit1) == null) {
            createStockProduitFinis(produit1,approvisionnementPFDto);
            approvisionnementProduitFinisRepos.save(approvisionnementProduitFinis);
        } else if(stockProduitFinisRepos.findByProduit(produit1) != null) {
            try {
                throw new ResourceExisteDejaException("Ce produit a été deja approvisionné !");
            } catch (ResourceExisteDejaException e) {
                e.printStackTrace();
            }
            return;
        }

      });
    }

    public void createStockProduitFinis(Produit produit,ApprovisionnementProduitFinisDto approvisionnementPF){
        StockProduitFinis stockProduitFinis = new StockProduitFinis();
        stockProduitFinis.setProduit(produit);
        stockProduitFinis.setQuantite_Min(10);
        stockProduitFinis.setDateDerniereMaj(LocalDateTime.now());
        String id = approvisionnementPF.getIdSiteDeVente();
        SiteDeVente siteDeVente = siteDeVenteRepos.findById(id).get();
       // stockProduitFinis.setSiteDeVente(siteDeVente);
        int quantiteFabrique = produit.getQuantiteFabrique();
        stockProduitFinis.setQuantite(quantiteFabrique);
        stockProduitFinisRepos.save(stockProduitFinis);
        createtHistoriqueStockPF(produit,approvisionnementPF);
    }
    private HistoriqueStockProduitsFinis createtHistoriqueStockPF( Produit produit,
        ApprovisionnementProduitFinisDto approvisionnementPF){
        HistoriqueStockProduitsFinis historiqueStockProduitsFinis = new HistoriqueStockProduitsFinis();
        historiqueStockProduitsFinis.setTypeMouvement(TypeMovementStock.ENTREE.name());
        historiqueStockProduitsFinis.setDateMAJ(LocalDateTime.now());
        historiqueStockProduitsFinis.setRefArticle(produit.getId());
        int quantiteFabrique = produit.getQuantiteFabrique();
        int ancientQty = historiqueStockProduitsFinis.getAncienneValeurStock();
        historiqueStockProduitsFinis.setNouvelleValeurStock(ancientQty + quantiteFabrique);
        historiqueStockProduitsFinis.setQuantiteModifiee(quantiteFabrique);
        historiqueStockProduitsFinis.setIdSiteDeProduction(approvisionnementPF.getIdSiteDeProduction());
        //historiqueStockProduitsFinis.setIdSiteVente(approvisionnementPF.getIdSiteDeVente());
        Long id = approvisionnementPF.getUserId();
        User user = userRepos.findById(id).get();
        historiqueStockProduitsFinis.setUser(user);
        return historiqueStockProduitsFinisRepos.save(historiqueStockProduitsFinis);
    }
}

