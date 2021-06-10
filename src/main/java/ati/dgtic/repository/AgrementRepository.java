package ati.dgtic.repository;

import ati.dgtic.domain.Agrement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Agrement entity.
 */
@Repository
public interface AgrementRepository extends JpaRepository<Agrement, Long> {

    @Query(value = "select distinct agrement from Agrement agrement left join fetch agrement.qualifications",
        countQuery = "select count(distinct agrement) from Agrement agrement")
    Page<Agrement> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct agrement from Agrement agrement left join fetch agrement.qualifications")
    List<Agrement> findAllWithEagerRelationships();

    @Query("select agrement from Agrement agrement left join fetch agrement.qualifications where agrement.id =:id")
    Optional<Agrement> findOneWithEagerRelationships(@Param("id") Long id);
}
