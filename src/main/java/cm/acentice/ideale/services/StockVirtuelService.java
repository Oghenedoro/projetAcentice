package cm.acentice.ideale.services;

import cm.acentice.ideale.constants.CommandeStatut;
import cm.acentice.ideale.dto.StockVirtuelDto;
import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.interfaces.DataPMapper;
import cm.acentice.ideale.interfaces.StockVirtuelInt;
import cm.acentice.ideale.repositories.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@RequiredArgsConstructor
@Service
public class StockVirtuelService implements StockVirtuelInt {

    private final StockVirtuelRepos stockVirtuelRepos;
    private final DataPMapper dataPMapper;
    private final CommandeRepository commandeRepository;
    private final LigneDeCommandeRepos ligneDeCommandeRepos;
    private final ProduitRepos produitRepos;
    private final StockProduitFinisRepos stockProduitFinisRepos;

    @Override
    public StockVirtuelDto create(StockVirtuelDto stockVirtuelDto) throws ResourceNotFoundException {

        StockVirtuel stockVirtuel = dataPMapper.mapFromStockVirtuelDto_toStockVirtuel(stockVirtuelDto);
        stockVirtuel.setCommandeStatut(CommandeStatut.STOCK_NON_VALIDEE);
        int qteCommandee = stockVirtuel.getQuantiteCommandee();
        int qteLivrer = stockVirtuel.getQuantiteLivre();

        stockVirtuel.setQuantiteCommandee(qteCommandee);
        stockVirtuel.setQuantiteLivre(qteLivrer);
        stockVirtuel.setQuantiteStockRestanteAlivrer(qteCommandee - qteLivrer);

        Optional<Commande> commande = commandeRepository.findById(stockVirtuel.getIdCommande());
        if(!commande.isPresent()){
            throw new ResourceNotFoundException("Commande non trouvé !");
        }
        stockVirtuel.setIdCommande(commande.get().getIdCommande());
        stockVirtuel.setDate(LocalDateTime.now());
        stockVirtuel = stockVirtuelRepos.save(stockVirtuel);
        StockVirtuelDto stockVirtuelDto1 = dataPMapper.mapFromStockVirtuel_toStockVirtuelDto(stockVirtuel);

        return stockVirtuelDto1;
    }
}
