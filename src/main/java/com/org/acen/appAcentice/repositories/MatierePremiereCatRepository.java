package com.org.acen.appAcentice.repositories;

import com.org.acen.appAcentice.entities.MatierePremiere;
import com.org.acen.appAcentice.entities.MatierePremiereCat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatierePremiereCatRepository extends JpaRepository<MatierePremiereCat, Long> {
    MatierePremiereCat findByType(String type);
}
