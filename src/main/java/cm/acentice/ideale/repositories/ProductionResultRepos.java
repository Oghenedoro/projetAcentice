package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.ProductionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionResultRepos extends JpaRepository<ProductionResult, Long> {
}
