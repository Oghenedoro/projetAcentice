package com.org.acen.appAcentice.repositories;

import com.org.acen.appAcentice.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface FournisseurRepository extends JpaRepository<Fournisseur, String> {
}
