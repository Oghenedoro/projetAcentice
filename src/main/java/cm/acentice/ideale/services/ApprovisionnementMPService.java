package cm.acentice.ideale.services;

import cm.acentice.ideale.dto.ApprovisionnementMatieresPremieresDto;
import cm.acentice.ideale.dto.DateDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ApprovisionnementMPService {

    private final ApprovisionnementMatieresPremieresRep approvisionnementMatieresPremieresRep;
    private final StockMatierePremiereRep stockMatierePremiereRep;
    private final ApprovHasMatierePremiereRep approvHasMatierePremiereRep;
    private final MatierePremiereRepository matierePremiereRepository;
    private final SiteDeProductionRepos siteDeProductionRepos;

    @Autowired
    public ApprovisionnementMPService(ApprovisionnementMatieresPremieresRep approvisionnementMatieresPremieresRep, StockMatierePremiereRep stockMatierePremiereRep, MatierePremiereRepository matierePremiereRepository, ApprovHasMatierePremiereRep approvHasMatierePremiereRep, ApprovHasMatierePremiereRep approvHasMatierePremiereRep1, MatierePremiereRepository matierePremiereRepository1, SiteDeProductionRepos siteDeProductionRepos) {
        this.approvisionnementMatieresPremieresRep = approvisionnementMatieresPremieresRep;
        this.stockMatierePremiereRep = stockMatierePremiereRep;
        this.approvHasMatierePremiereRep = approvHasMatierePremiereRep;
        this.matierePremiereRepository = matierePremiereRepository;
        this.siteDeProductionRepos = siteDeProductionRepos;
    }

    @Transactional
    public void create(ApprovisionnementMatieresPremieresDto appMatieresPremieresDto, Long idSiteProd) throws ParseException, ResourceNotFoundException {
        ApprovisionnementMatieresPremieres matieresPremieres = new ApprovisionnementMatieresPremieres();
        Optional<SiteDeProduction> siteDeProduction = siteDeProductionRepos.findById(idSiteProd);
        if (!siteDeProduction.isPresent()){
            throw new ResourceNotFoundException("SiteDeProduction non trouvé !");
        }
        String datePartern = "yyyy-MM-dd:HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(datePartern);
        String dateAppr = sdf.format(new Date());
        matieresPremieres.setDateApprovisionnement(sdf.parse(dateAppr));
        matieresPremieres.setDevise(appMatieresPremieresDto.getDevise());
        matieresPremieres.setSiteDeProduction(appMatieresPremieresDto.getSiteDeProduction());
        matieresPremieres.setApprovHasMatierePremieres(appMatieresPremieresDto.getApprovHasMatiereList());
        List<ApprovHasMatierePremiere> premiereList = appMatieresPremieresDto.getApprovHasMatiereList();
        matieresPremieres.setSiteDeProduction(siteDeProduction.get());
        matieresPremieres.setApprovHasMatierePremieres(premiereList);
        double tauxTva = appMatieresPremieresDto.getTauxTVA() / 100 + 1;
        matierePremiereRepository.findAll().forEach(mp -> {
            premiereList.forEach(aprovHasMP ->{
                MatierePremiere premiere = aprovHasMP.getMatierePremiere();

          if(mp.getReference().equals(premiere.getReference())) {
              aprovHasMP.setMatierePremiere(mp);
              aprovHasMP.setApprovisionnementMatieresPremiere(matieresPremieres);
              int quantite = premiere.getQuantiteMP();
              double prixUnitaireHT = mp.getPrixAchat();
              double prixUnitaireTTC = tauxTva * prixUnitaireHT;
              double montantTVA = prixUnitaireTTC * quantite;
              double montantTTC = prixUnitaireHT * quantite;
              aprovHasMP.setMontantTTC(montantTTC);
              aprovHasMP.setQuantite(premiere.getQuantiteMP());
              aprovHasMP.setTauxTVA(tauxTva);
              aprovHasMP.setPrixUnitaireHT(prixUnitaireHT);
              aprovHasMP.setPrixUitaireTTC(prixUnitaireTTC);
              aprovHasMP.setMontantTVA(montantTVA);
              StockMatierePremiere stockMatierePremiere = null;
              if (stockMatierePremiereRep.findByMatierePremiere(premiere) == null) {
                  stockMatierePremiere = new StockMatierePremiere();
                  stockMatierePremiere.setQuantite(premiere.getQuantiteMP());
                  stockMatierePremiere.setMatierePremiere(premiere);
                  String pattern = "yyyy-MM-dd:HH:mm";
                  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                  String date = simpleDateFormat.format(new Date());
                  try {
                      stockMatierePremiere.setDateDerniereMaj(simpleDateFormat.parse(date));
                  } catch (ParseException e) {
                      e.printStackTrace();
                  }
                  stockMatierePremiere.setQuantité_Min(10);
                  stockMatierePremiereRep.save(stockMatierePremiere);

              }else if (stockMatierePremiereRep.findByMatierePremiere(premiere) != null){
                  StockMatierePremiere stockMP = stockMatierePremiereRep.findByMatierePremiere(premiere);
                  stockMP.setIdStockMP(stockMP.getIdStockMP());
                  int quantiteStock = stockMP.getQuantite();
                  stockMP.setQuantite(quantite + quantiteStock);
              }
           }
         });
      });
        approvisionnementMatieresPremieresRep.save(matieresPremieres);
     }

        public List<ApprovisionnementMatieresPremieres> getAll () {
            return approvisionnementMatieresPremieresRep.findAll();
        }

        public List<ApprovisionnementMatieresPremieres> getDataBetweenDates (Date startDate, Date endDate){
            DateDto dateDto = new DateDto();
            startDate = dateDto.getStartDate();
            endDate = dateDto.getEndDate();
            List<ApprovisionnementMatieresPremieres> listData = approvisionnementMatieresPremieresRep.getListApprovMPBetweenDates(startDate, endDate);
            return listData;
        }
    }
