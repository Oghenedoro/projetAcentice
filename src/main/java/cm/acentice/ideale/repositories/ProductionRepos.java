package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.Production;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionRepos extends JpaRepository<Production, Long> {
}
