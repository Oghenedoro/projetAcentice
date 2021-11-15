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
import java.time.LocalDate;
import java.util.*;

@Service
public class ApprovisionnementMPService {

    private final ApprovisionnementMatieresPremieresRep approvisionnementMatieresPremieresRep;
    private final StockMatierePremiereRep stockMatierePremiereRep;
    private final MatierePremiereRepository matierePremiereRepository;
    private final SiteDeProductionRepos siteDeProductionRepos;
    private final HistoriqueStockApproMPPDRepos historiqueStockApproMPPDRepos;
    private final ApprovHasMatierePremiereRep approvHasMatierePremiereRep;

    @Autowired
    public ApprovisionnementMPService(ApprovisionnementMatieresPremieresRep approvisionnementMatieresPremieresRep, StockMatierePremiereRep stockMatierePremiereRep, MatierePremiereRepository matierePremiereRepository, SiteDeProductionRepos siteDeProductionRepos, HistoriqueStockApproMPPDRepos historiqueStockApproMPPDRepos, ApprovHasMatierePremiereRep approvHasMatierePremiereRep) {
        this.approvisionnementMatieresPremieresRep = approvisionnementMatieresPremieresRep;
        this.stockMatierePremiereRep = stockMatierePremiereRep;
        this.matierePremiereRepository = matierePremiereRepository;
        this.siteDeProductionRepos = siteDeProductionRepos;
        this.historiqueStockApproMPPDRepos = historiqueStockApproMPPDRepos;
        this.approvHasMatierePremiereRep = approvHasMatierePremiereRep;
    }

    @Transactional
    public void create(ApprovisionnementMatieresPremieresDto appMatieresPremieresDto) throws ParseException, ResourceNotFoundException {
        ApprovisionnementMatieresPremieres matieresPremieres = new ApprovisionnementMatieresPremieres();
        String datePartern = "yyyy-MM-dd:HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(datePartern);
        String dateAppr = sdf.format(new Date());
        matieresPremieres.setDateApprovisionnement(sdf.parse(dateAppr));
        matieresPremieres.setDevise(appMatieresPremieresDto.getDevise());

        Long id = appMatieresPremieresDto.getIdSiteDeProduction();
        Optional<SiteDeProduction> siteDeProduction = siteDeProductionRepos.findById(id);
        if (!siteDeProduction.isPresent()){
            throw new ResourceNotFoundException("SiteDeProduction non trouvé !");
        }
        matieresPremieres.setSiteDeProduction(siteDeProduction.get());
        List<ApprovHasMatierePremiere> premiereList = appMatieresPremieresDto.getApprovHasMatiereList();
        matieresPremieres.setApprovHasMatierePremieres(premiereList);
        double tauxTva = appMatieresPremieresDto.getTauxTVA() / 100 + 1;

          premiereList.forEach(aprovHasMP ->{
          MatierePremiere matierePMSaisi = aprovHasMP.getMatierePremiere();
          MatierePremiere matierePremiere = matierePremiereRepository.findById(matierePMSaisi.getReference()).get();

          aprovHasMP.setMatierePremiere(matierePremiere);
          aprovHasMP.setApprovisionnementMatieresPremiere(matieresPremieres);
          int quantite = matierePMSaisi.getQuantiteMP();
          double prixUnitaireHT = matierePremiere.getPrixAchat();
          double prixUnitaireTTC = tauxTva * prixUnitaireHT;
          double montantTVA = prixUnitaireTTC * quantite;
          double montantTTC = prixUnitaireHT * quantite;
          aprovHasMP.setMontantTTC(montantTTC);
          aprovHasMP.setQuantite(quantite);
          aprovHasMP.setTauxTVA(tauxTva);
          aprovHasMP.setPrixUnitaireHT(prixUnitaireHT);
          aprovHasMP.setPrixUitaireTTC(prixUnitaireTTC);
          aprovHasMP.setMontantTVA(montantTVA);

          if (stockMatierePremiereRep.findByRefMP(matierePremiere.getReference()) == null) {
              createStockMatierePremiere(matierePMSaisi,matieresPremieres);
              }else if (stockMatierePremiereRep.findByRefMP(matierePremiere.getReference()) != null){
              try {
                  updateStockMatierePremiere(matierePMSaisi,matieresPremieres);
              } catch (ResourceNotFoundException e) {
                  e.getMessage();
              }
             }
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

        private void createStockMatierePremiere(MatierePremiere matierePremiere,ApprovisionnementMatieresPremieres matieresPremieres){
            StockMatierePremiere stockMatierePremiere = new StockMatierePremiere();
            stockMatierePremiere.setQuantite(matierePremiere.getQuantiteMP());
            stockMatierePremiere.setRefMP(matierePremiere.getReference());
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
            createtHistoriqueStock(stockMatierePremiere,matierePremiere,matieresPremieres);
        }

    private void updateStockMatierePremiere(MatierePremiere matierePremiere,ApprovisionnementMatieresPremieres matieresPremieres) throws ResourceNotFoundException {

          StockMatierePremiere stockMP = stockMatierePremiereRep.findByRefMP(matierePremiere.getReference());
          stockMP.setIdStockMP(stockMP.getIdStockMP());
          int quantiteStock = stockMP.getQuantite();
          int quantiteApprovisionner = matierePremiere.getQuantiteMP();
          stockMP.setQuantite(quantiteApprovisionner + quantiteStock);
          createtHistoriqueStock(stockMP,matierePremiere,matieresPremieres);
          stockMatierePremiereRep.save(stockMP);
     }

    private HistoriqueStockApprovMPPD createtHistoriqueStock(StockMatierePremiere stockMatierePremiere, MatierePremiere matierePremiere,
                                                             ApprovisionnementMatieresPremieres matieresPremieres){
         HistoriqueStockApprovMPPD historiqueStockApproMPPD = new HistoriqueStockApprovMPPD();
         historiqueStockApproMPPD.setTypeMouvement(TypeMovementStock.ENTREE.name());
         historiqueStockApproMPPD.setDateMAJ(LocalDate.now());
         historiqueStockApproMPPD.setRefArticle(matierePremiere.getReference());
         int nouvelleValeur = stockMatierePremiere.getQuantite();
         int quantiteApprovissionnee = matierePremiere.getQuantiteMP();
         historiqueStockApproMPPD.setNouvelleValeurStock(nouvelleValeur);
         int ancienValeur = nouvelleValeur - quantiteApprovissionnee;
         historiqueStockApproMPPD.setAncienneValeurStock(ancienValeur);
         historiqueStockApproMPPD.setQuantitéModifiee(quantiteApprovissionnee);

        SiteDeProduction siteDeProduction = matieresPremieres.getSiteDeProduction();
        historiqueStockApproMPPD.setIdSiteDeProduction(siteDeProduction.getId());

         return historiqueStockApproMPPDRepos.save(historiqueStockApproMPPD);

     }

     public List<HistoriqueStockApprovMPPD> getAllHistorique(){
         return historiqueStockApproMPPDRepos.findAll();
     }
    }
