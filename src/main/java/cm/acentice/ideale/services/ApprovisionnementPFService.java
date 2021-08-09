package cm.acentice.ideale.services;

import cm.acentice.ideale.dto.ApprovisionnementMatieresPremieresDto;
import cm.acentice.ideale.dto.ApprovisionnementProduitFinisDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.repositories.ApprovisionnementProduitFinisRepos;
import cm.acentice.ideale.repositories.StockProduitFinisRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApprovisionnementPFService {

    private final ApprovisionnementProduitFinisRepos approvisionnementProduitFinisRepos;
    private final StockProduitFinisRepos stockProduitFinisRepos;

    @Autowired
    public ApprovisionnementPFService(ApprovisionnementProduitFinisRepos approvisionnementProduitFinisRepos, StockProduitFinisRepos stockProduitFinisRepos) {
        this.approvisionnementProduitFinisRepos = approvisionnementProduitFinisRepos;
        this.stockProduitFinisRepos = stockProduitFinisRepos;
    }

    public void create(ApprovisionnementProduitFinisDto approvisionnementPFDto) {

        ApprovisionnementProduitFinis approvisionnementProduitFinis = new ApprovisionnementProduitFinis();
        approvisionnementProduitFinis.setDateApprovisionnement(LocalDate.now());
        approvisionnementProduitFinis.setSiteDeVente(approvisionnementPFDto.getSiteDeVente());
        approvisionnementProduitFinis.setSiteDeProduction(approvisionnementPFDto.getSiteDeProduction());
        approvisionnementProduitFinis.setUser(approvisionnementPFDto.getUser());
        approvisionnementProduitFinis.setReceptionist(approvisionnementPFDto.getReceptionist());
        approvisionnementProduitFinis.setApprovissionnementPFHasProduits(approvisionnementPFDto.getApprovissionnementPFHasProduits());
        approvisionnementProduitFinis.setProduit(approvisionnementPFDto.getProduit());
        List<ApprovissionnementPFHasProduit> approvPFHasProduits = approvisionnementPFDto.getApprovissionnementPFHasProduits();
        approvisionnementProduitFinis.setApprovissionnementPFHasProduits(approvPFHasProduits);

        /*for (ApprovissionnementPFHasProduit appHP : approvPFHasProduits) {
            appHP.setApprovisionnementProduitFinis(approvisionnementProduitFinis);
            approvisionnementProduitFinis.getApprovissionnementPFHasProduits().add(appHP);
        }*/
        approvisionnementProduitFinisRepos.save(approvisionnementProduitFinis);

        List<ApprovissionnementPFHasProduit> produitFinis = approvisionnementPFDto.getApprovissionnementPFHasProduits();

        if (stockProduitFinisRepos.findByProduit(approvisionnementPFDto.getProduit()) == null) {
            StockProduitFinis stockProduitFinis = new StockProduitFinis();
            stockProduitFinis.setProduit(approvisionnementPFDto.getProduit());
            stockProduitFinis.setQuantite_Min(10);
            stockProduitFinis.setDateDerniereMaj(LocalDate.now());
            for (ApprovissionnementPFHasProduit approvProduit : produitFinis) {
                stockProduitFinis.setQuantite(approvProduit.getQuantiteApprouve());
            }
            stockProduitFinisRepos.save(stockProduitFinis);
            StockProduitFinis stockProduitF = stockProduitFinisRepos.findByProduit(approvisionnementPFDto.getProduit());
            System.out.println("ggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");
            System.out.println(stockProduitF.getProduit().getId());
        } else {

            StockProduitFinis stockProduitFinis = stockProduitFinisRepos.findByProduit(approvisionnementPFDto.getProduit());
             updateQuantity(stockProduitFinis.getId(),approvisionnementPFDto);
        }
    }

    private void updateQuantity(Long idSPF, ApprovisionnementProduitFinisDto dto) {
        StockProduitFinis stockPF = stockProduitFinisRepos.findById(idSPF).get();
        stockPF.setProduit(stockPF.getProduit());
        for (ApprovissionnementPFHasProduit approvPF : dto.getApprovissionnementPFHasProduits()) {
            stockPF.setQuantite(approvPF.getQuantiteApprouve() + stockPF.getQuantite());
        }
    }
}

