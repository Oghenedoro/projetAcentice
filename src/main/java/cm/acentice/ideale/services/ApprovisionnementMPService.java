package cm.acentice.ideale.services;

import cm.acentice.ideale.dto.ApprovisionnementMatieresPremieresDto;
import cm.acentice.ideale.entities.ApprovHasMatierePremiere;
import cm.acentice.ideale.entities.ApprovisionnementMatieresPremieres;
import cm.acentice.ideale.entities.MatierePremiere;
import cm.acentice.ideale.entities.StockMatierePremiere;
import cm.acentice.ideale.repositories.ApprovHasMatierePremiereRep;
import cm.acentice.ideale.repositories.ApprovisionnementMatieresPremieresRep;
import cm.acentice.ideale.repositories.MatierePremiereRepository;
import cm.acentice.ideale.repositories.StockMatierePremiereRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ApprovisionnementMPService {

    private ApprovisionnementMatieresPremieres matieresPremieres = new ApprovisionnementMatieresPremieres();
    private final ApprovisionnementMatieresPremieresRep approvisionnementMatieresPremieresRep;
    private final StockMatierePremiereRep stockMatierePremiereRep;
    private final MatierePremiereRepository matierePremiereRepository;
    private final ApprovHasMatierePremiereRep approvHasMatierePremiereRep;

    @Autowired
    public ApprovisionnementMPService(ApprovisionnementMatieresPremieresRep approvisionnementMatieresPremieresRep, StockMatierePremiereRep stockMatierePremiereRep, MatierePremiereRepository matierePremiereRepository, ApprovHasMatierePremiereRep approvHasMatierePremiereRep) {
        this.approvisionnementMatieresPremieresRep = approvisionnementMatieresPremieresRep;
        this.stockMatierePremiereRep = stockMatierePremiereRep;
        this.matierePremiereRepository = matierePremiereRepository;
        this.approvHasMatierePremiereRep = approvHasMatierePremiereRep;
    }


    @Transactional
    public void create(ApprovisionnementMatieresPremieresDto appMatieresPremieresDto) {
        matieresPremieres = new ApprovisionnementMatieresPremieres();
        matieresPremieres.setDateApprovisionnement(new Date());
        matieresPremieres.setDescription(appMatieresPremieresDto.getDescription());
        matieresPremieres.setDevise(appMatieresPremieresDto.getDevise());
        matieresPremieres.setSiteDeProduction(appMatieresPremieresDto.getSiteDeProduction());
        matieresPremieres.setUser(appMatieresPremieresDto.getUser());
        matieresPremieres.setSiteDeProduction(appMatieresPremieresDto.getSiteDeProduction());
        matieresPremieres.setApprovHasMatierePremieres(appMatieresPremieresDto.getApprovHasMatierePremieres());
        matieresPremieres.setLibelle(appMatieresPremieresDto.getLibelle());

        MatierePremiere matierePremiere = appMatieresPremieresDto.getMatierePremiere();
        matieresPremieres.setMatierePremiere(matierePremiere);

        List<ApprovHasMatierePremiere> premiereList = new ArrayList<>();
        for (ApprovHasMatierePremiere appHMP : appMatieresPremieresDto.getApprovHasMatierePremieres()) {
            premiereList.add(appHMP);
            appHMP.setApprovisionnementMatieresPremiere(matieresPremieres);
            appHMP.setMatierePremiere(matierePremiere);
        }
        matierePremiere.setApprovHasMatierePremieres(premiereList);
        matieresPremieres.setApprovHasMatierePremieres(premiereList);
        double tauxTva = appMatieresPremieresDto.getTauxTVA() / 100 + 1;
        double prixUnitaireHT = appMatieresPremieresDto.getPrixUnitaireHT();
        double prixUnitaireTTC = tauxTva * prixUnitaireHT;
        double montantTVA = prixUnitaireTTC - prixUnitaireHT;

        matieresPremieres.setTauxTVA(tauxTva);
        matieresPremieres.setPrixUnitaireHT(prixUnitaireHT);
        matieresPremieres.setMontantTVA(montantTVA);
        matieresPremieres.setPrixUnitaireTTC(prixUnitaireTTC);
        approvisionnementMatieresPremieresRep.save(matieresPremieres);

        List<ApprovHasMatierePremiere> premieres = appMatieresPremieresDto.getApprovHasMatierePremieres();
        if (stockMatierePremiereRep.findByMatierePremiere(appMatieresPremieresDto.getMatierePremiere()) == null) {
            StockMatierePremiere stockMatierePremiere = new StockMatierePremiere();
            stockMatierePremiere.setMatierePremiere(appMatieresPremieresDto.getMatierePremiere());
            stockMatierePremiere.setDescription(appMatieresPremieresDto.getDescription());
            stockMatierePremiere.setDateDerniereMaj(new Date());
            stockMatierePremiere.setQuantité_Min(10);
            stockMatierePremiere.setLibelle(appMatieresPremieresDto.getLibelle());
            for (ApprovHasMatierePremiere appHMP : premieres) {
                stockMatierePremiere.setQuantite(appHMP.getQuantitéMPApprove());
            }
            stockMatierePremiereRep.save(stockMatierePremiere);
        } else {
            StockMatierePremiere stockMatierePremiere = stockMatierePremiereRep.findByMatierePremiere(appMatieresPremieresDto.getMatierePremiere());
            stockMatierePremiereRep.save(stockMatierePremiere);
            updateQuantity(stockMatierePremiere.getIdStockMP(), appMatieresPremieresDto);
        }
    }

        private void updateQuantity (String id, ApprovisionnementMatieresPremieresDto appMatieresPremieresDto){

            StockMatierePremiere stockM = stockMatierePremiereRep.findById(id).get();

            stockM.setMatierePremiere(stockM.getMatierePremiere());
            for (ApprovHasMatierePremiere appHMP : appMatieresPremieresDto.getApprovHasMatierePremieres()) {
                stockM.setQuantite(appHMP.getQuantitéMPApprove() + stockM.getQuantite());
            }
    }

        public List<ApprovisionnementMatieresPremieres> getAll () {
            return approvisionnementMatieresPremieresRep.findAll();
        }


    }
