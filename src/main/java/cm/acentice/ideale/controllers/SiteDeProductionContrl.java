package cm.acentice.ideale.controllers;

import cm.acentice.ideale.entities.SiteDeProduction;
import cm.acentice.ideale.services.SiteDeProductionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/v1")
@RestController
public class SiteDeProductionContrl {

    private final SiteDeProductionService siteDeProductionService;

    public SiteDeProductionContrl(SiteDeProductionService siteDeProductionService) {
        this.siteDeProductionService = siteDeProductionService;
    }


    @RequestMapping(value = "/siteproductions", method = RequestMethod.POST)
    public SiteDeProduction create(@RequestBody SiteDeProduction siteDeProduction){
        return siteDeProductionService.create(siteDeProduction);
    }

}
