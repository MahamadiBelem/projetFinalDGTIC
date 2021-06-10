package ati.dgtic.web.rest;

import ati.dgtic.domain.Domaine;
import ati.dgtic.repository.DomaineRepository;
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

/**
 * REST controller for managing {@link ati.dgtic.domain.Domaine}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DomaineResource {

    private final Logger log = LoggerFactory.getLogger(DomaineResource.class);

    private static final String ENTITY_NAME = "domaine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DomaineRepository domaineRepository;

    public DomaineResource(DomaineRepository domaineRepository) {
        this.domaineRepository = domaineRepository;
    }

    /**
     * {@code POST  /domaines} : Create a new domaine.
     *
     * @param domaine the domaine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new domaine, or with status {@code 400 (Bad Request)} if the domaine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/domaines")
    public ResponseEntity<Domaine> createDomaine(@Valid @RequestBody Domaine domaine) throws URISyntaxException {
        log.debug("REST request to save Domaine : {}", domaine);
        if (domaine.getId() != null) {
            throw new BadRequestAlertException("A new domaine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Domaine result = domaineRepository.save(domaine);
        return ResponseEntity.created(new URI("/api/domaines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /domaines} : Updates an existing domaine.
     *
     * @param domaine the domaine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated domaine,
     * or with status {@code 400 (Bad Request)} if the domaine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the domaine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/domaines")
    public ResponseEntity<Domaine> updateDomaine(@Valid @RequestBody Domaine domaine) throws URISyntaxException {
        log.debug("REST request to update Domaine : {}", domaine);
        if (domaine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Domaine result = domaineRepository.save(domaine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, domaine.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /domaines} : get all the domaines.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of domaines in body.
     */
    @GetMapping("/domaines")
    public ResponseEntity<List<Domaine>> getAllDomaines(Pageable pageable) {
        log.debug("REST request to get a page of Domaines");
        Page<Domaine> page = domaineRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /domaines/:id} : get the "id" domaine.
     *
     * @param id the id of the domaine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the domaine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/domaines/{id}")
    public ResponseEntity<Domaine> getDomaine(@PathVariable Long id) {
        log.debug("REST request to get Domaine : {}", id);
        Optional<Domaine> domaine = domaineRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(domaine);
    }

    /**
     * {@code DELETE  /domaines/:id} : delete the "id" domaine.
     *
     * @param id the id of the domaine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/domaines/{id}")
    public ResponseEntity<Void> deleteDomaine(@PathVariable Long id) {
        log.debug("REST request to delete Domaine : {}", id);
        domaineRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
