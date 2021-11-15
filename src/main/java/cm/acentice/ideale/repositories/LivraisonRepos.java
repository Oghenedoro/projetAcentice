package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.Commande;
import cm.acentice.ideale.entities.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedList;

public interface LivraisonRepos  extends JpaRepository<Livraison,Long> {

    LinkedList<Livraison>findByCommande(Commande commande);
    LinkedList<Livraison> findByRefProduit(String refProduit);
}
