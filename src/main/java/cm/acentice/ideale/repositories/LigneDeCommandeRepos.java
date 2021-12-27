package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.Commande;
import cm.acentice.ideale.entities.LigneDeCommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedList;
import java.util.List;

public interface LigneDeCommandeRepos extends JpaRepository<LigneDeCommande, Long> {
    LinkedList<LigneDeCommande> findByRefProduit(String refProduit);
    List<LigneDeCommande>findByCommandeAndRefProduit(Commande commande,String refProduit);
    List<LigneDeCommande> findByCommande(Commande commande);
}
