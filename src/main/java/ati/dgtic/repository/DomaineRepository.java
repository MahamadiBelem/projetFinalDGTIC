package ati.dgtic.repository;

import ati.dgtic.domain.Domaine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Domaine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DomaineRepository extends JpaRepository<Domaine, Long> {
}
