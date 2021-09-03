package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepos extends JpaRepository<Produit,String> {
}
