package cm.acentice.ideale.services;

import cm.acentice.ideale.entities.SiteDeProduction;
import cm.acentice.ideale.repositories.SiteDeProductionRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
