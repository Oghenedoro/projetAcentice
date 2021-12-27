package cm.acentice.ideale.services;

import cm.acentice.ideale.entities.*;
import cm.acentice.ideale.interfaces.DataPMapper;
import cm.acentice.ideale.interfaces.StockVirtuelInt;
import cm.acentice.ideale.models.StockVituel;
import cm.acentice.ideale.repositories.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Builder
@RequiredArgsConstructor
@Service
public class StockVirtuelService implements StockVirtuelInt {

    private final DataPMapper dataPMapper;
    private final CommandeRepository commandeRepository;
    private final LivraisonRepos livraisonRepos;
    private final ProduitRepos produitRepos;
    private final StockProduitFinisRepos stockProduitFinisRepos;

    @Override
    public List<String> checkStockWithCommande(StockVituel vituel) {
        List<String> stringList = new ArrayList<>();
        String res = null;

        int quantiteRestant = 0;

        for (String refProd : vituel.getRefProduits()) {
            Produit produit = produitRepos.findById(refProd).get();
            List<Livraison> livraisons = livraisonRepos.findByRefProduit(refProd);

            for (Livraison livraison : livraisons) {
                quantiteRestant = livraison.getQuantiteRestante();
                StockProduitFinis stockProduitFinis = stockProduitFinisRepos.findByProduit(produit);
               int quantiteEnStock = stockProduitFinis.getQuantite();

                int diff = quantiteEnStock - quantiteRestant;
                if (quantiteEnStock < quantiteRestant) {
                    stringList.add("-KO- " + "Le stock reference " + refProd + " est inférieur avec " + diff + " quantites");
                } else if (quantiteEnStock >= quantiteRestant) {
                    stringList.add("-OK- " + "Le stock reference " + refProd + " est disponible avec " + diff + " quantites");
                }
            }
        }
        return stringList;
    }
}