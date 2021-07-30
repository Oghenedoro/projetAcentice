package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<User, Long> {
}
