package cm.acentice.ideale.controllers;

import cm.acentice.ideale.entities.SiteDeVente;
import cm.acentice.ideale.services.SiteDeVenteService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/v1")
@RestController
public class SiteDeVenteContrl {

    private final SiteDeVenteService siteDeVenteService;

    public SiteDeVenteContrl(SiteDeVenteService siteDeVenteService) {
        this.siteDeVenteService = siteDeVenteService;
    }
    @RequestMapping(value = "/sitedeventes", method = RequestMethod.POST)
    public SiteDeVente create(@RequestBody SiteDeVente siteDeVente){
        return siteDeVenteService.create(siteDeVente);
    }
    @RequestMapping(value = "/sitedeventes", method = RequestMethod.GET)
    public List<SiteDeVente> getAll(){
        return siteDeVenteService.getAll();
    }
}
