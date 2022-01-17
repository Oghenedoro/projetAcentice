package cm.acentice.ideale.services;

import cm.acentice.ideale.constants.TypeMovementStock;
import cm.acentice.ideale.dto.MatierePremiereConsumeeDto;
import cm.acentice.ideale.dto.ProductionResultDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.interfaces.DataPMapper;
import cm.acentice.ideale.interfaces.ProductionResultInt;
import cm.acentice.ideale.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class ProductionResultService implements ProductionResultInt {

    private final ProductionResultRepos productionResultRepos;
    private final DataPMapper dataPMapper;
    private final StockProduitFinisRepos stockProduitFinisRepos;
    private final MatierePremiereConsumeeRepos matierePremiereConsumeeRepos;
    private final ProduitRepos produitRepos;
    private final ProductionRepos productionRepos;
    private final HistoriqueStockProduitsFinisRepos historiqueStockProduitsFinisRepos;
    private final StockMatierePremiereRep stockMatierePremiereRep;
    private final MatierePremiereRepository matierePremiereRepository;
    private final HistoriqueStockApproMPPDRepos historiqueStockApproMPPDRepos;
    private final UserRepos userRepos;

    @Override
    public ProductionResultDto create(ProductionResultDto productionResultDto) throws ResourceNotFoundException {

        List<MatierePremiereConsumeeDto> matierePremiereConsumeesDtos = productionResultDto.getMatierePremiereConsumeesDtos();

        for (MatierePremiereConsumeeDto consumeeDto : matierePremiereConsumeesDtos) {
            consumeeDto.setRefMatierePremiere(consumeeDto.getRefMatierePremiere());
            consumeeDto.setQuantiteMPUtilisee(consumeeDto.getQuantiteMPUtilisee());
            consumeeDto.setIdProductionResult(productionResultDto.getIdProduction());
            updateStockMatierePremiere(consumeeDto,productionResultDto);
            matierePremiereConsumeeRepos.save(dataPMapper.mapFromMatierePremiereConsumeeDto_toMatierePremiereConsumee(consumeeDto));
        }
        ProductionResult productionResult = dataPMapper.mapFromProductionResultDto_toProductionResult(productionResultDto);
        productionResult = productionResultRepos.save(productionResult);
        productionResultDto = dataPMapper.mapFromProductionResult_toProductionResultDto(productionResult);
        approvisionnerStockProduitFinis(productionResultDto);
        return productionResultDto;
    }

    public void approvisionnerStockProduitFinis(ProductionResultDto productionResultDto) throws ResourceNotFoundException {

        Optional<Produit> produit = produitRepos.findById(productionResultDto.getRefProduit());
        if (!produit.isPresent()) {
            throw new ResourceNotFoundException("Ce reference de produit est introuvable !");
        }

        if (stockProduitFinisRepos.findByRefProduit(produit.get().getId()) == null) {
            StockProduitFinis stockProduitFinis = new StockProduitFinis();
            stockProduitFinis.setRefProduit(productionResultDto.getRefProduit());
            stockProduitFinis.setQuantite_Min(10);
            stockProduitFinis.setDateDerniereMaj(LocalDateTime.now());
            Long id = productionResultDto.getIdSiteDeProduction();
            stockProduitFinis.setQuantite(productionResultDto.getQuantiteFabriquee());
            stockProduitFinis.setIdSiteDeProduction(id);
            stockProduitFinisRepos.save(stockProduitFinis);
            createtHistoriqueStockPF(productionResultDto,stockProduitFinis);

        } else if (stockProduitFinisRepos.findByRefProduit(productionResultDto.getRefProduit()) != null) {
            StockProduitFinis stockProduitF = stockProduitFinisRepos.findByRefProduit(productionResultDto.getRefProduit());
            stockProduitF.setQuantite(stockProduitF.getQuantite() + productionResultDto.getQuantiteFabriquee());
            stockProduitFinisRepos.save(stockProduitF);
            createtHistoriqueStockPF(productionResultDto,stockProduitF);
        }
    }

    private HistoriqueStockProduitsFinis createtHistoriqueStockPF(ProductionResultDto productionResultDto,StockProduitFinis stockProduitFinis) throws ResourceNotFoundException {

        Optional<Produit> produit = produitRepos.findById(productionResultDto.getRefProduit());
        if (!produit.isPresent()) {
            throw new ResourceNotFoundException("Ce reference de produit est introuvable !");
        }
        HistoriqueStockProduitsFinis historiqueStockProduitsFinis = new HistoriqueStockProduitsFinis();
        historiqueStockProduitsFinis.setTypeMouvement(TypeMovementStock.ENTREE.name());
        historiqueStockProduitsFinis.setDateMAJ(LocalDateTime.now());
        historiqueStockProduitsFinis.setRefArticle(produit.get().getId());
        int quantiteFabrique = productionResultDto.getQuantiteFabriquee();
        int nouvelleQtyEnStock = stockProduitFinis.getQuantite();
        historiqueStockProduitsFinis.setAncienneValeurStock(nouvelleQtyEnStock - productionResultDto.getQuantiteFabriquee());
        historiqueStockProduitsFinis.setNouvelleValeurStock(nouvelleQtyEnStock);
        historiqueStockProduitsFinis.setQuantiteModifiee(quantiteFabrique);
        historiqueStockProduitsFinis.setIdSiteDeProduction(productionResultDto.getIdSiteDeProduction());
        Optional<Production> production = productionRepos.findById(productionResultDto.getIdProduction());
        if (!production.isPresent()) {
            throw new ResourceNotFoundException("Reference de Production non trouvé !");
        }
        Optional<User> user = userRepos.findById(production.get().getId());
        if(!user.isPresent()){
            throw new ResourceNotFoundException("User non trouve !");
        }
        historiqueStockProduitsFinis.setUser(user.get());
        return historiqueStockProduitsFinisRepos.save(historiqueStockProduitsFinis);
    }

    private void updateStockMatierePremiere(MatierePremiereConsumeeDto consumeeDto,ProductionResultDto productionResultDto){
        StockMatierePremiere stockMatierePremiere = stockMatierePremiereRep.findByRefMP(consumeeDto.getRefMatierePremiere());
        int qteMpEnStock = stockMatierePremiere.getQuantite();
        int qteMatierePremierUtilisee = consumeeDto.getQuantiteMPUtilisee();
        stockMatierePremiere.setQuantite(qteMpEnStock - qteMatierePremierUtilisee);
        updateHistoriqueMP(productionResultDto,stockMatierePremiere,consumeeDto);
    }

    private void updateHistoriqueMP(ProductionResultDto productionResultDto,StockMatierePremiere stockMatierePremiere,MatierePremiereConsumeeDto consumeeDto){
        HistoriqueStockApprovMPPD historiqueStockApprovMPPD = new HistoriqueStockApprovMPPD();
        historiqueStockApprovMPPD.setTypeMouvement(TypeMovementStock.SORTIE.name());
        historiqueStockApprovMPPD.setRefArticle(productionResultDto.getRefProduit());
        historiqueStockApprovMPPD.setIdSiteDeProduction(productionResultDto.getIdSiteDeProduction());

        int nouvelleValeur = stockMatierePremiere.getQuantite();
        int mpUtilisee = consumeeDto.getQuantiteMPUtilisee();

        historiqueStockApprovMPPD.setNouvelleValeurStock(nouvelleValeur);;
        historiqueStockApprovMPPD.setAncienneValeurStock(nouvelleValeur + mpUtilisee);

        historiqueStockApprovMPPD.setQuantitéModifiee(mpUtilisee);
        historiqueStockApprovMPPD.setDateMAJ(LocalDate.now());
        historiqueStockApproMPPDRepos.save(historiqueStockApprovMPPD);
    }
}
