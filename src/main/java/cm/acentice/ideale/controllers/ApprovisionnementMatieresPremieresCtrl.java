package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.ApprovisionnementMatieresPremieresDto;
import cm.acentice.ideale.dto.DateDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.services.ApprovisionnementMPService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequestMapping(value = "/v1")
@RestController
public class ApprovisionnementMatieresPremieresCtrl {

    private final ApprovisionnementMPService approvisionnementMPService;

    public ApprovisionnementMatieresPremieresCtrl(ApprovisionnementMPService approvisionnementMPService) {
        this.approvisionnementMPService = approvisionnementMPService;
    }

    @RequestMapping(value = "/approvisionnement/matierepremieres/{idSiteProd}", method = RequestMethod.POST)
    public void create(@RequestBody ApprovisionnementMatieresPremieresDto appMatieresPremieresDto, @PathVariable Long idSiteProd) throws ParseException, ResourceNotFoundException {
         approvisionnementMPService.create(appMatieresPremieresDto,idSiteProd);
    }
    @RequestMapping(value = "/approvisionnement/matierepremieres", method = RequestMethod.GET)
    public List<ApprovisionnementMatieresPremieres> getAll(){
        return approvisionnementMPService.getAll();
    }

    @RequestMapping(value = "/approvisionnement/matierepremieres/dates", method = RequestMethod.POST)
    public List<ApprovisionnementMatieresPremieres> getDataBetweenDates(@RequestBody DateDto dateDto){
        return approvisionnementMPService.getDataBetweenDates(dateDto.getStartDate(),dateDto.getEndDate());
    }
    @RequestMapping(value = "/approvisionnement/historiques", method = RequestMethod.GET)
    public List<HistoriqueStockApprovMPPD> getAllHistory(){
        return approvisionnementMPService.getAllHistorique();
    }
}
