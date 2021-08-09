package cm.acentice.ideale.repositories;

import cm.acentice.ideale.entities.ApprovisionnementMatieresPremieres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ApprovisionnementMatieresPremieresRep extends JpaRepository<ApprovisionnementMatieresPremieres, String> {

    @Query("Select appmp from ApprovisionnementMatieresPremieres appmp where appmp.dateApprovisionnement BETWEEN :stateDate AND :endDate")
    public List<ApprovisionnementMatieresPremieres> getListApprovMPBetweenDates(@Param("stateDate") Date stateDate, @Param("endDate") Date endDate);
}
