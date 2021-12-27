package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.Commande;
import cm.acentice.ideale.entities.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedList;
import java.util.List;

public interface LivraisonRepos  extends JpaRepository<Livraison,Long> {

    List<Livraison> findByCommande(Commande commande);
    LinkedList<Livraison> findByRefProduitAndCommande(String refProduit, Commande commande);
    List<Livraison> findByRefProduit(String refProd);
}
