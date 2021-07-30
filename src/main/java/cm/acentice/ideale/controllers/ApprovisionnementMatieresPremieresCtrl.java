package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.ApprovisionnementMatieresPremieresDto;
import cm.acentice.ideale.entities.ApprovisionnementMatieresPremieres;
import cm.acentice.ideale.entities.StockMatierePremiere;
import cm.acentice.ideale.services.ApprovisionnementMPService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/v1")
@RestController
public class ApprovisionnementMatieresPremieresCtrl {

    private final ApprovisionnementMPService approvisionnementMPService;

    public ApprovisionnementMatieresPremieresCtrl(ApprovisionnementMPService approvisionnementMPService) {
        this.approvisionnementMPService = approvisionnementMPService;
    }

    @RequestMapping(value = "/approvisionnement/matierepremieres", method = RequestMethod.POST)
    public void create(@RequestBody ApprovisionnementMatieresPremieresDto appMatieresPremieresDto){
         approvisionnementMPService.create(appMatieresPremieresDto);
    }
    @RequestMapping(value = "/approvisionnement/matierepremieres", method = RequestMethod.GET)
    public List<ApprovisionnementMatieresPremieres> getAll(){
        return approvisionnementMPService.getAll();
    }

}
