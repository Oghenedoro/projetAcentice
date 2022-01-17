package cm.acentice.ideale.services;

import cm.acentice.ideale.dto.ProductionDto;
import cm.acentice.ideale.entities.Production;
import cm.acentice.ideale.entities.User;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.interfaces.DataPMapper;
import cm.acentice.ideale.interfaces.ProductionInt;
import cm.acentice.ideale.repositories.ProductionRepos;
import cm.acentice.ideale.repositories.UserRepos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductionService implements ProductionInt {

    private final ProductionRepos productionRepos;
    private final DataPMapper dataPMapper;
    private final UserRepos userRepos;

    @Override
    public ProductionDto create(ProductionDto productionDto) throws ResourceNotFoundException {

        Production production = dataPMapper.mapFromProductionDto_toProduction(productionDto);
        Optional<User> user = userRepos.findById(productionDto.getIdUser());
        if(!user.isPresent()){
            throw new ResourceNotFoundException("Utilisateur non trouvé !");
        }
        production.setIdUser(user.get().getUserId());
        production = productionRepos.save(production);
        return dataPMapper.mapFromProduction_toProductionDto(production);
    }
}
