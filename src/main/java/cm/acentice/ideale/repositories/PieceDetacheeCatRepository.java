package cm.acentice.ideale.repositories;


import cm.acentice.ideale.entities.PieceDetacheeCat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceDetacheeCatRepository extends JpaRepository<PieceDetacheeCat, Long> {
    PieceDetacheeCat findByType(String type);
}
