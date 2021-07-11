package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, String> {
}
