package ati.dgtic.repository;

import ati.dgtic.domain.Qualification;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Qualification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long> {
}
