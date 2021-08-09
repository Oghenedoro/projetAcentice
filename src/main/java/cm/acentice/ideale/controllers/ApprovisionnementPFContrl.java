package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.ApprovisionnementProduitFinisDto;
import cm.acentice.ideale.entities.ApprovisionnementProduitFinis;
import cm.acentice.ideale.services.ApprovisionnementPFService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/v1")
@RestController
public class ApprovisionnementPFContrl {

private final ApprovisionnementPFService approvisionnementPFService;

    public ApprovisionnementPFContrl(ApprovisionnementPFService approvisionnementPFService) {
        this.approvisionnementPFService = approvisionnementPFService;
    }
    @RequestMapping(value = "/approvisionnement/produitfinis", method = RequestMethod.POST)
    public void create(@RequestBody ApprovisionnementProduitFinisDto approvisionnementPFDto) {
         approvisionnementPFService.create(approvisionnementPFDto);
    }
}
