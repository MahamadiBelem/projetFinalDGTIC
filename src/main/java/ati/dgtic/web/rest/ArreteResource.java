package ati.dgtic.web.rest;

import ati.dgtic.domain.Arrete;
import ati.dgtic.repository.ArreteRepository;
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
 * REST controller for managing {@link ati.dgtic.domain.Arrete}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ArreteResource {

    private final Logger log = LoggerFactory.getLogger(ArreteResource.class);

    private static final String ENTITY_NAME = "arrete";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArreteRepository arreteRepository;

    public ArreteResource(ArreteRepository arreteRepository) {
        this.arreteRepository = arreteRepository;
    }

    /**
     * {@code POST  /arretes} : Create a new arrete.
     *
     * @param arrete the arrete to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arrete, or with status {@code 400 (Bad Request)} if the arrete has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arretes")
    public ResponseEntity<Arrete> createArrete(@Valid @RequestBody Arrete arrete) throws URISyntaxException {
        log.debug("REST request to save Arrete : {}", arrete);
        if (arrete.getId() != null) {
            throw new BadRequestAlertException("A new arrete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Arrete result = arreteRepository.save(arrete);
        return ResponseEntity.created(new URI("/api/arretes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arretes} : Updates an existing arrete.
     *
     * @param arrete the arrete to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arrete,
     * or with status {@code 400 (Bad Request)} if the arrete is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arrete couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arretes")
    public ResponseEntity<Arrete> updateArrete(@Valid @RequestBody Arrete arrete) throws URISyntaxException {
        log.debug("REST request to update Arrete : {}", arrete);
        if (arrete.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Arrete result = arreteRepository.save(arrete);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, arrete.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /arretes} : get all the arretes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arretes in body.
     */
    @GetMapping("/arretes")
    public ResponseEntity<List<Arrete>> getAllArretes(Pageable pageable) {
        log.debug("REST request to get a page of Arretes");
        Page<Arrete> page = arreteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /arretes/:id} : get the "id" arrete.
     *
     * @param id the id of the arrete to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arrete, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arretes/{id}")
    public ResponseEntity<Arrete> getArrete(@PathVariable Long id) {
        log.debug("REST request to get Arrete : {}", id);
        Optional<Arrete> arrete = arreteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(arrete);
    }

    /**
     * {@code DELETE  /arretes/:id} : delete the "id" arrete.
     *
     * @param id the id of the arrete to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arretes/{id}")
    public ResponseEntity<Void> deleteArrete(@PathVariable Long id) {
        log.debug("REST request to delete Arrete : {}", id);
        arreteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
