package cm.acentice.ideale.services;

import cm.acentice.ideale.constants.TypeMovementStock;
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
    private final MatierePremiereRepository matierePremiereRepository;
    private final SiteDeProductionRepos siteDeProductionRepos;
    private final HistoriqueStockApproMPPDRepos historiqueStockApproMPPDRepos;

    @Autowired
    public ApprovisionnementMPService(ApprovisionnementMatieresPremieresRep approvisionnementMatieresPremieresRep, StockMatierePremiereRep stockMatierePremiereRep, MatierePremiereRepository matierePremiereRepository, SiteDeProductionRepos siteDeProductionRepos, HistoriqueStockApproMPPDRepos historiqueStockApproMPPDRepos) {
        this.approvisionnementMatieresPremieresRep = approvisionnementMatieresPremieresRep;
        this.stockMatierePremiereRep = stockMatierePremiereRep;
        this.matierePremiereRepository = matierePremiereRepository;
        this.siteDeProductionRepos = siteDeProductionRepos;
        this.historiqueStockApproMPPDRepos = historiqueStockApproMPPDRepos;
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

        matierePremiereRepository.findAll().forEach(matierepremiere -> {
          premiereList.forEach(aprovHasMP ->{
          MatierePremiere matierePMSaisi = aprovHasMP.getMatierePremiere();

      if(matierepremiere.getReference().equals(matierePMSaisi.getReference())) {
          aprovHasMP.setMatierePremiere(matierepremiere);
          aprovHasMP.setApprovisionnementMatieresPremiere(matieresPremieres);
          int quantite = matierePMSaisi.getQuantiteMP();
          double prixUnitaireHT = matierepremiere.getPrixAchat();
          double prixUnitaireTTC = tauxTva * prixUnitaireHT;
          double montantTVA = prixUnitaireTTC * quantite;
          double montantTTC = prixUnitaireHT * quantite;
          aprovHasMP.setMontantTTC(montantTTC);
          aprovHasMP.setQuantite(matierePMSaisi.getQuantiteMP());
          aprovHasMP.setTauxTVA(tauxTva);
          aprovHasMP.setPrixUnitaireHT(prixUnitaireHT);
          aprovHasMP.setPrixUitaireTTC(prixUnitaireTTC);
          aprovHasMP.setMontantTVA(montantTVA);

          if (stockMatierePremiereRep.findByMatierePremiere(matierePMSaisi) == null) {
              createStockMatierePremiere(matierePMSaisi);
              }else if (stockMatierePremiereRep.findByMatierePremiere(matierePMSaisi) != null){
              updateStockMatierePremiere(matierePMSaisi);
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

        public void createStockMatierePremiere(MatierePremiere matierePremiere){
            StockMatierePremiere stockMatierePremiere = new StockMatierePremiere();
            stockMatierePremiere.setQuantite(matierePremiere.getQuantiteMP());
            stockMatierePremiere.setMatierePremiere(matierePremiere);
            String pattern = "yyyy-MM-dd:HH:mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            try {
                stockMatierePremiere.setDateDerniereMaj(simpleDateFormat.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            stockMatierePremiere.setQuantité_Min(10);
            createtHistoriqueStock(stockMatierePremiere,matierePremiere);
            stockMatierePremiereRep.save(stockMatierePremiere);
        }

      public void updateStockMatierePremiere(MatierePremiere matierePremiere){
          StockMatierePremiere stockMP = stockMatierePremiereRep.findByMatierePremiere(matierePremiere);
          stockMP.setIdStockMP(stockMP.getIdStockMP());
          int quantiteStock = stockMP.getQuantite();
          int quantiteApprovisionner = matierePremiere.getQuantiteMP();
          stockMP.setQuantite(quantiteApprovisionner + quantiteStock);
          createtHistoriqueStock(stockMP,matierePremiere);
          stockMatierePremiereRep.save(stockMP);
     }

     public HistoriqueStockApprovMPPD createtHistoriqueStock(StockMatierePremiere stockMatierePremiere, MatierePremiere matierePremiere){
         HistoriqueStockApprovMPPD historiqueStockApproMPPD = new HistoriqueStockApprovMPPD();
         historiqueStockApproMPPD.setTypeMouvement(TypeMovementStock.ENTREE.name());
         historiqueStockApproMPPD.setDateMAJ(new Date());
         historiqueStockApproMPPD.setRefArticle(matierePremiere.getReference());
         int nouvelleValeur = stockMatierePremiere.getQuantite();
         int quantiteApprovissionnee = matierePremiere.getQuantiteMP();
         historiqueStockApproMPPD.setNouvelleValeurStock(nouvelleValeur);
         int ancienValeur = nouvelleValeur - quantiteApprovissionnee;
         historiqueStockApproMPPD.setAncienneValeurStock(ancienValeur);
         historiqueStockApproMPPD.setQuantitéModifiee(quantiteApprovissionnee);
         return historiqueStockApproMPPDRepos.save(historiqueStockApproMPPD);

     }

     public List<HistoriqueStockApprovMPPD> getAllHistorique(){
         return historiqueStockApproMPPDRepos.findAll();
     }
    }
