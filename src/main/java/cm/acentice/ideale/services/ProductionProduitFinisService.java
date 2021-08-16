package cm.acentice.ideale.services;

import cm.acentice.ideale.constants.TypeMovementStock;
import cm.acentice.ideale.dto.ProductionProduitFinisDto;
import cm.acentice.ideale.entities.HistoriqueStockApprovMPPD;
import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.ProductionProduitFinis;
import cm.acentice.ideale.entities.StockMatierePremiere;
import cm.acentice.ideale.repositories.HistoriqueStockApproMPPDRepos;
import cm.acentice.ideale.repositories.MatierePremiereRepository;
import cm.acentice.ideale.repositories.ProductionProduitFinisRepos;
import cm.acentice.ideale.repositories.StockMatierePremiereRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProductionProduitFinisService {

    private final MatierePremiereRepository matierePremiereRepository;
    private final ProductionProduitFinisRepos productionProduitFinisRepos;
    private final StockMatierePremiereRep stockMatierePremiereRep;
    private final ApprovisionnementMPService approvisionnementMPService;
    private final HistoriqueStockApproMPPDRepos historiqueStockApproMPPDRepos;

    @Autowired
    public ProductionProduitFinisService(MatierePremiereRepository matierePremiereRepository, ProductionProduitFinisRepos productionProduitFinisRepos, StockMatierePremiereRep stockMatierePremiereRep, ApprovisionnementMPService approvisionnementMPService, HistoriqueStockApproMPPDRepos historiqueStockApproMPPDRepos) {
        this.matierePremiereRepository = matierePremiereRepository;
        this.productionProduitFinisRepos = productionProduitFinisRepos;
        this.stockMatierePremiereRep = stockMatierePremiereRep;
        this.approvisionnementMPService = approvisionnementMPService;
        this.historiqueStockApproMPPDRepos = historiqueStockApproMPPDRepos;
    }
    @Transactional
    public ProductionProduitFinis create(ProductionProduitFinisDto produitFinisDto){

        ProductionProduitFinis produitFinis = new ProductionProduitFinis();
        matierePremiereRepository.findAll().forEach(mp ->{
            String refMP = mp.getReference();
            String refMPPF = produitFinisDto.getRefMatierePremiere();
            if(refMP.equals(refMPPF)){
               createtHistoriqueStock(produitFinisDto,mp);
            }
        });
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
        produitFinis.setRefProduitsFinis(produitFinisDto.getRefProduitsFinis());
        produitFinis.setSiteDeProduction(produitFinisDto.getSiteDeProduction());

        return productionProduitFinisRepos.save(produitFinis);
    }
    public List<ProductionProduitFinis>getAllProduitFinis(){

        return productionProduitFinisRepos.findAll();
    }

    private HistoriqueStockApprovMPPD createtHistoriqueStock(ProductionProduitFinisDto produitFinisDto,MatierePremiere matierePremiere) {
        StockMatierePremiere stockMatiereP = stockMatierePremiereRep.findByMatierePremiere(matierePremiere);
        HistoriqueStockApprovMPPD histApprovMP = new HistoriqueStockApprovMPPD();
        String histRefArticle = stockMatiereP.getMatierePremiere().getReference();
        int quantiteStockMP = stockMatiereP.getQuantite();
        int quantiteUtilise = produitFinisDto.getQuantitéMatPremiereUtilisée();
        int nouvelleValeur = quantiteStockMP - quantiteUtilise;
        stockMatiereP.setQuantite(nouvelleValeur);

        histApprovMP.setTypeMouvement(TypeMovementStock.SORTIE.name());
        histApprovMP.setAncienneValeurStock(quantiteStockMP);
        histApprovMP.setNouvelleValeurStock(nouvelleValeur);
        histApprovMP.setDateMAJ(new Date());
        histApprovMP.setQuantitéModifiee(quantiteUtilise);
        histApprovMP.setRefArticle(histRefArticle);
        return historiqueStockApproMPPDRepos.save(histApprovMP);

    }
}
