package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
