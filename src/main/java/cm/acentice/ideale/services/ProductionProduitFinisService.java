package cm.acentice.ideale.services;

import cm.acentice.ideale.constants.TypeMovementStock;
import cm.acentice.ideale.dto.ProductionProduitFinisDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductionProduitFinisService {

    private final SiteDeProductionRepos siteDeProductionRepos;
    private final MatierePremiereRepository matierePremiereRepository;
    private final ProductionProduitFinisRepos productionProduitFinisRepos;
    private final StockMatierePremiereRep stockMatierePremiereRep;
    private final ApprovisionnementMPService approvisionnementMPService;
    private final HistoriqueStockApproMPPDRepos historiqueStockApproMPPDRepos;

    @Autowired
    public ProductionProduitFinisService(SiteDeProductionRepos siteDeProductionRepos, MatierePremiereRepository matierePremiereRepository, ProductionProduitFinisRepos productionProduitFinisRepos, StockMatierePremiereRep stockMatierePremiereRep, ApprovisionnementMPService approvisionnementMPService, HistoriqueStockApproMPPDRepos historiqueStockApproMPPDRepos) {
        this.siteDeProductionRepos = siteDeProductionRepos;
        this.matierePremiereRepository = matierePremiereRepository;
        this.productionProduitFinisRepos = productionProduitFinisRepos;
        this.stockMatierePremiereRep = stockMatierePremiereRep;
        this.approvisionnementMPService = approvisionnementMPService;
        this.historiqueStockApproMPPDRepos = historiqueStockApproMPPDRepos;
    }
    @Transactional
    public ProductionProduitFinis create(ProductionProduitFinisDto produitFinisDto) throws ResourceNotFoundException {

        Long id = produitFinisDto.getIdSiteDeProduction();
        Optional<SiteDeProduction> siteDeProduction = siteDeProductionRepos.findById(id);
        if (!siteDeProduction.isPresent()){
            throw new ResourceNotFoundException("SiteDeProduction not found !");
        }
        ProductionProduitFinis produitFinis = new ProductionProduitFinis();
        produitFinis.setQuantitéProduitsFinis(produitFinisDto.getQuantitéProduitsFinis());
        produitFinis.setQuantitéMatPremiereUtilisée(produitFinisDto.getQuantitéMatPremiereUtilisée());
        produitFinis.setRefMatierePremiere(produitFinisDto.getRefMatierePremiere());
        produitFinis.setQuantitéProduitsFinisDefectueux(produitFinisDto.getQuantitéProduitsFinisDefectueux());
        produitFinis.setIdAgentProduction(produitFinisDto.getIdAgentProduction());
        produitFinis.setPoidsBobine(produitFinisDto.getPoidsBobine());
        produitFinis.setDateHeureDebutProduction(produitFinisDto.getDateHeureDebutProduction());
        produitFinis.setDateHeureFinProduction(produitFinisDto.getDateHeureFinProduction());

        produitFinis.setRefProduitsFinisDefectueux(produitFinisDto.getRefProduitsFinisDefectueux());
        produitFinis.setCommentaires(produitFinisDto.getCommentaires());
        produitFinis.setReleveCompteurDebutKwh(produitFinisDto.getReleveCompteurDebutKwh());
        produitFinis.setReleveCompteurFinKwh(produitFinisDto.getReleveCompteurFinKwh());
        System.out.println("ggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");
        System.out.println(UUID.randomUUID().toString());
        produitFinis.setRefProduitsFinis(UUID.randomUUID().toString());
        produitFinis.setSiteDeProduction(siteDeProduction.get());

        MatierePremiere matierePremiere = matierePremiereRepository.findById(produitFinisDto.getRefMatierePremiere()).get();
        createtHistoriqueStock(produitFinisDto,matierePremiere.getReference(),produitFinis);
        return productionProduitFinisRepos.save(produitFinis);
    }
    public List<ProductionProduitFinis>getAllProduitFinis(){

        return productionProduitFinisRepos.findAll();
    }

    private HistoriqueStockApprovMPPD createtHistoriqueStock(ProductionProduitFinisDto produitFinisDto,String mpRef,ProductionProduitFinis produitFinis) throws ResourceNotFoundException {
        HistoriqueStockApprovMPPD histApprovMP = new HistoriqueStockApprovMPPD();
        Long id = produitFinisDto.getIdSiteDeProduction();
        histApprovMP.setIdSiteDeProduction(id);
       if(stockMatierePremiereRep.findByRefMP(mpRef) == null){
           throw new ResourceNotFoundException("Aucun stock associé à cette matiere Premiere !");
        }
        StockMatierePremiere stockMatiereP = stockMatierePremiereRep.findByRefMP(mpRef);
        int quantiteStockMP = stockMatiereP.getQuantite();
        int quantiteUtilise = produitFinisDto.getQuantitéMatPremiereUtilisée();

        int nouvelleValeur = quantiteStockMP - quantiteUtilise;
        stockMatiereP.setQuantite(nouvelleValeur);

        histApprovMP.setTypeMouvement(TypeMovementStock.SORTIE.name());
        histApprovMP.setAncienneValeurStock(quantiteStockMP);
        histApprovMP.setNouvelleValeurStock(nouvelleValeur);
        histApprovMP.setDateMAJ(LocalDate.now());
        histApprovMP.setQuantitéModifiee(quantiteUtilise);
        histApprovMP.setRefArticle(stockMatiereP.getRefMP());

        return historiqueStockApproMPPDRepos.save(histApprovMP);

    }
}
