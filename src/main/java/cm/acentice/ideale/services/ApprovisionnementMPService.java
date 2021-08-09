package cm.acentice.ideale.services;

import cm.acentice.ideale.dto.ApprovisionnementMatieresPremieresDto;
import cm.acentice.ideale.dto.DateDto;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApprovisionnementMPService {

    private ApprovisionnementMatieresPremieres matieresPremieres = new ApprovisionnementMatieresPremieres();
    private final ApprovisionnementMatieresPremieresRep approvisionnementMatieresPremieresRep;
    private final StockMatierePremiereRep stockMatierePremiereRep;
    private final ApprovHasMatierePremiereRep approvHasMatierePremiereRep;

    @Autowired
    public ApprovisionnementMPService(ApprovisionnementMatieresPremieresRep approvisionnementMatieresPremieresRep, StockMatierePremiereRep stockMatierePremiereRep, MatierePremiereRepository matierePremiereRepository, ApprovHasMatierePremiereRep approvHasMatierePremiereRep, ApprovHasMatierePremiereRep approvHasMatierePremiereRep1) {
        this.approvisionnementMatieresPremieresRep = approvisionnementMatieresPremieresRep;
        this.stockMatierePremiereRep = stockMatierePremiereRep;
        this.approvHasMatierePremiereRep = approvHasMatierePremiereRep;
    }

    @Transactional
    public void create(ApprovisionnementMatieresPremieresDto appMatieresPremieresDto) throws ParseException {

        matieresPremieres = new ApprovisionnementMatieresPremieres();
        String datePartern = "yyyy-MM-dd:HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(datePartern);
        String dateAppr = sdf.format(new Date());
        matieresPremieres.setDateApprovisionnement(sdf.parse(dateAppr));
        matieresPremieres.setDescription(appMatieresPremieresDto.getDescription());
        matieresPremieres.setDevise(appMatieresPremieresDto.getDevise());
        matieresPremieres.setSiteDeProduction(appMatieresPremieresDto.getSiteDeProduction());
       // matieresPremieres.setUser(appMatieresPremieresDto.getUser());
        matieresPremieres.setSiteDeProduction(appMatieresPremieresDto.getSiteDeProduction());
        matieresPremieres.setApprovHasMatierePremieres(appMatieresPremieresDto.getApprovHasMatierePremieres());
        matieresPremieres.setLibelle(appMatieresPremieresDto.getLibelle());
        List<ApprovHasMatierePremiere> premiereList = appMatieresPremieresDto.getApprovHasMatierePremieres();

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
        List<MatierePremiere> matierePremieres = new ArrayList<>();
        Collection<Integer> quantityList = new ArrayList<>();

        for (ApprovHasMatierePremiere approvHasMP : premieres) {
            MatierePremiere matierePremiere = approvHasMP.getMatierePremiere();
            int qty = approvHasMP.getQuantitéMPApprove();
           if (qty != 0) {
                quantityList.add(qty);
            }
            if (matierePremiere != null) {
                matierePremieres.add(matierePremiere);

                if (stockMatierePremiereRep.findByMatierePremiere(matierePremiere) == null) {
                    StockMatierePremiere stockMatierePremiere = new StockMatierePremiere();
                    stockMatierePremiere.setMatierePremiere(matierePremiere);
                    for (Integer qt : quantityList) {
                        int qq = qt;

                    }
                    if(qty != 0) {
                        stockMatierePremiere.setQuantite(qty);
                    }
                    stockMatierePremiere.setMatierePremiere(matierePremiere);
                    stockMatierePremiere.setDescription(appMatieresPremieresDto.getDescription());
                    String pattern = "yyyy-MM-dd:HH:mm";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    String date = simpleDateFormat.format(new Date());
                    stockMatierePremiere.setDateDerniereMaj(simpleDateFormat.parse(date));
                    stockMatierePremiere.setQuantité_Min(10);
                    stockMatierePremiere.setLibelle(appMatieresPremieresDto.getLibelle());
                    stockMatierePremiereRep.save(stockMatierePremiere);
                } else if (stockMatierePremiereRep.findByMatierePremiere(matierePremiere) != null) {

                    StockMatierePremiere stockMP = stockMatierePremiereRep.findByMatierePremiere(matierePremiere);
                    updateQuantity(matierePremiere, appMatieresPremieresDto);

                }

            }
        }
    }

    private void updateQuantity(MatierePremiere mp, ApprovisionnementMatieresPremieresDto appMatieresPremieresDto) {
        List<Integer> quantityList = new ArrayList<>();
        int i = 0;
        int siz = quantityList.size();

        int allqtt = 0;
        Map<Integer, StockMatierePremiere> listSMP = new HashMap<>();
        StockMatierePremiere stockMP = null;


            for (ApprovHasMatierePremiere appHMP : appMatieresPremieresDto.getApprovHasMatierePremieres()) {
                int qqt = appHMP.getQuantitéMPApprove();
                if (qqt > 0) {
                    quantityList.add(i, qqt);
                    i++;
                }

                for (StockMatierePremiere smp : stockMatierePremiereRep.findAll()) {
                    String refSmp = smp.getMatierePremiere().getReference();
                    String refmp = appHMP.getMatierePremiere().getReference();
                    if (refSmp.equals(refmp)) {

                        stockMP = stockMatierePremiereRep.findById(smp.getIdStockMP()).get();
                        listSMP.put(i, stockMP);

                        for (StockMatierePremiere premiere : listSMP.values()) {
                            premiere.setIdStockMP(premiere.getIdStockMP());

                            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                            System.out.println(premiere.getIdStockMP());
                            System.out.println(premiere.getQuantite());
                            int sqtt = premiere.getQuantite();
                            allqtt = qqt + sqtt;
                          //  premiere.setQuantite(allqtt);
                            System.out.println(allqtt);
                            break;
                            // System.out.println(allqtt);
                            //  stockMP.setQuantite(allqtt);

                        }

                    }
                }}}

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
