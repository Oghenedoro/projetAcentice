package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface FournisseurRepository extends JpaRepository<Fournisseur, String> {
}
