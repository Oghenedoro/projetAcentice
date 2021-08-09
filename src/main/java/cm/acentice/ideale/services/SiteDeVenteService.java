package cm.acentice.ideale.services;

import cm.acentice.ideale.entities.SiteDeVente;
import cm.acentice.ideale.repositories.SiteDeVenteRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Service
public class SiteDeVenteService {

    private final SiteDeVenteRepos siteDeVenteRepos;

    @Autowired
    public SiteDeVenteService(SiteDeVenteRepos siteDeVenteRepos) {
        this.siteDeVenteRepos = siteDeVenteRepos;
    }

    public SiteDeVente create(SiteDeVente siteDeVente){
        return siteDeVenteRepos.save(siteDeVente);
    }

    public List<SiteDeVente> getAll(){
        return siteDeVenteRepos.findAll();
    }
}
