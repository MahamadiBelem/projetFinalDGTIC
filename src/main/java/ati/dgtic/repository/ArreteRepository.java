package ati.dgtic.repository;

import ati.dgtic.domain.Arrete;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Arrete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArreteRepository extends JpaRepository<Arrete, Long> {
}
