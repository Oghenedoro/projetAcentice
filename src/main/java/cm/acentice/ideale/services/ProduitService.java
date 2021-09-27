package cm.acentice.ideale.services;

import cm.acentice.ideale.dto.ProduitDto;
import cm.acentice.ideale.entities.Produit;
import cm.acentice.ideale.repositories.ProductionProduitFinisRepos;
import cm.acentice.ideale.repositories.ProduitRepos;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProduitService {

    private final ProduitRepos produitRepos;
    private final ProductionProduitFinisRepos productionProduitFinisRepos;
    private final ModelMapper modelMapper;

   // @Autowired
    public ProduitService(ProduitRepos produitRepos, ProductionProduitFinisRepos productionProduitFinisRepos, ModelMapper modelMapper) {
        this.produitRepos = produitRepos;
        this.productionProduitFinisRepos = productionProduitFinisRepos;
        this.modelMapper = modelMapper;
    }

    public ProduitDto create(ProduitDto produitDto) {
        Produit produit = modelMapper.map(produitDto, Produit.class);
        produit.setId(UUID.randomUUID().toString());
        produit = produitRepos.save(produit);
        produitDto = modelMapper.map(produit,ProduitDto.class );
        return produitDto;
    }

    public List<Produit> getAllProduits(){
        return produitRepos.findAll();
    }
}
