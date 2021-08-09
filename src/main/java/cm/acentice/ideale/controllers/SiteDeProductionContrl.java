package cm.acentice.ideale.controllers;

import cm.acentice.ideale.entities.SiteDeProduction;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.services.SiteDeProductionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @RequestMapping(value = "/siteproductions", method = RequestMethod.GET)
    public List<SiteDeProduction> findAll(){
        return siteDeProductionService.findAll();
    }
    @RequestMapping(value = "/siteproductions/{idSiteProduction}", method = RequestMethod.GET)
    public Optional<SiteDeProduction> findById(@PathVariable Long idSiteProduction) throws ResourceNotFoundException {
        return siteDeProductionService.findById(idSiteProduction);
    }
}
