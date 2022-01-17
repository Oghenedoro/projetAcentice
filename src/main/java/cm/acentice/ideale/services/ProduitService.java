package cm.acentice.ideale.services;

import cm.acentice.ideale.dto.ProduitDto;
import cm.acentice.ideale.entities.Produit;
import cm.acentice.ideale.repositories.ProduitRepos;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProduitService {

    private final ProduitRepos produitRepos;
    private final ModelMapper modelMapper;


    public ProduitService(ProduitRepos produitRepos, ModelMapper modelMapper) {
        this.produitRepos = produitRepos;
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
