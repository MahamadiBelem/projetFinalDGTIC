package ati.dgtic.repository;

import ati.dgtic.domain.Entreprise;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data  repository for the Entreprise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
    @Query("select e  from Entreprise e inner join Agrement a on e.agrement.id = a.id where :element is null or e.numeroRCCM = :element")
    Page<Entreprise> findAll(Pageable pageable, @Param("element") String element);
    
   /* Optional<Entreprise> compter(Long id);*/
}


