package cm.acentice.ideale.services;

import cm.acentice.ideale.entities.SiteDeProduction;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.SiteDeProductionRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SiteDeProductionService {

    private final SiteDeProductionRepos siteDeProductionRepos;

    @Autowired
    public SiteDeProductionService(SiteDeProductionRepos siteDeProductionRepos) {
        this.siteDeProductionRepos = siteDeProductionRepos;
    }

    public SiteDeProduction create(SiteDeProduction siteDeProduction){
        return siteDeProductionRepos.save(siteDeProduction);
    }
    public List<SiteDeProduction> findAll(){
        return siteDeProductionRepos.findAll();
    }

    public Optional<SiteDeProduction> findById(Long idSiteProduction) throws ResourceNotFoundException {
        Optional<SiteDeProduction>siteDeProduction = siteDeProductionRepos.findById(idSiteProduction);
        if(!siteDeProduction.isPresent()){
            throw new ResourceNotFoundException("SiteDeProduction non trouvé !");
        }
        return siteDeProduction;
    }
}
