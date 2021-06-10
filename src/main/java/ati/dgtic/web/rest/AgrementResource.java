package ati.dgtic.web.rest;

import ati.dgtic.domain.Agrement;
import ati.dgtic.repository.AgrementRepository;
import ati.dgtic.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

//


/**
 * REST controller for managing {@link ati.dgtic.domain.Agrement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AgrementResource {

    private final Logger log = LoggerFactory.getLogger(AgrementResource.class);

    private static final String ENTITY_NAME = "agrement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgrementRepository agrementRepository;

    public AgrementResource(AgrementRepository agrementRepository) {
        this.agrementRepository = agrementRepository;
    }

    /**
     * {@code POST  /agrements} : Create a new agrement.
     *
     * @param agrement the agrement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agrement, or with status {@code 400 (Bad Request)} if the agrement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agrements")
    public ResponseEntity<Agrement> createAgrement(@Valid @RequestBody Agrement agrement) throws URISyntaxException {
        log.debug("REST request to save Agrement : {}", agrement);
        if (agrement.getId() != null) {
            throw new BadRequestAlertException("A new agrement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Agrement result = agrementRepository.save(agrement);
        return ResponseEntity.created(new URI("/api/agrements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agrements} : Updates an existing agrement.
     *
     * @param agrement the agrement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agrement,
     * or with status {@code 400 (Bad Request)} if the agrement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agrement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agrements")
    public ResponseEntity<Agrement> updateAgrement(@Valid @RequestBody Agrement agrement) throws URISyntaxException {
        log.debug("REST request to update Agrement : {}", agrement);
        if (agrement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Agrement result = agrementRepository.save(agrement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, agrement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agrements} : get all the agrements.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agrements in body.
     */
    @GetMapping("/agrements")
    public ResponseEntity<List<Agrement>> getAllAgrements(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Agrements");
        Page<Agrement> page;
        if (eagerload) {
            page = agrementRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = agrementRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /agrements/:id} : get the "id" agrement.
     *
     * @param id the id of the agrement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agrement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agrements/{id}")
    public ResponseEntity<Agrement> getAgrement(@PathVariable Long id) {
        log.debug("REST request to get Agrement : {}", id);
        Optional<Agrement> agrement = agrementRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(agrement);
    }

    /**
     * {@code DELETE  /agrements/:id} : delete the "id" agrement.
     *
     * @param id the id of the agrement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agrements/{id}")
    public ResponseEntity<Void> deleteAgrement(@PathVariable Long id) {
        log.debug("REST request to delete Agrement : {}", id);
        agrementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

// my recherch function
    /*@GetMapping("/_search/agrements")
    public ResponseEntity<List<Agrement>> searchAgrements(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Agrements for query {}", query);
        Page<Agrement> page = agrementSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }*/
}
