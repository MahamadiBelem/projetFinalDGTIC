package ati.dgtic.web.rest;

import ati.dgtic.domain.Entreprise;
import ati.dgtic.repository.EntrepriseRepository;
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
 * REST controller for managing {@link ati.dgtic.domain.Entreprise}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EntrepriseResource {

    private final Logger log = LoggerFactory.getLogger(EntrepriseResource.class);

    private static final String ENTITY_NAME = "entreprise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseResource(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    /**
     * {@code POST  /entreprises} : Create a new entreprise.
     *
     * @param entreprise the entreprise to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entreprise, or with status {@code 400 (Bad Request)} if the entreprise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entreprises")
    public ResponseEntity<Entreprise> createEntreprise(@Valid @RequestBody Entreprise entreprise) throws URISyntaxException {
        log.debug("REST request to save Entreprise : {}", entreprise);
        if (entreprise.getId() != null) {
            throw new BadRequestAlertException("A new entreprise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Entreprise result = entrepriseRepository.save(entreprise);
        return ResponseEntity.created(new URI("/api/entreprises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entreprises} : Updates an existing entreprise.
     *
     * @param entreprise the entreprise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entreprise,
     * or with status {@code 400 (Bad Request)} if the entreprise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entreprise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entreprises")
    public ResponseEntity<Entreprise> updateEntreprise(@Valid @RequestBody Entreprise entreprise) throws URISyntaxException {
        log.debug("REST request to update Entreprise : {}", entreprise);
        if (entreprise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Entreprise result = entrepriseRepository.save(entreprise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entreprise.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entreprises} : get all the entreprises.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entreprises in body.
     */
   /* @GetMapping("/entreprises")
    public ResponseEntity<List<Entreprise>> getAllEntreprises(Pageable pageable) {
        log.debug("REST request to get a page of Entreprises");
        Page<Entreprise> page = entrepriseRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }*/

    //My ressources
    @GetMapping("/entreprises")
    public ResponseEntity<List<Entreprise>> getAllEntreprises(Pageable pageable, @RequestParam(required=false) String element){
        log.debug("REST request to get a page of Entreprises");
        Page<Entreprise> page = entrepriseRepository.findAll(pageable,element);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entreprises/:id} : get the "id" entreprise.
     *
     * @param id the id of the entreprise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entreprise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entreprises/{id}")
    public ResponseEntity<Entreprise> getEntreprise(@PathVariable Long id) {
        log.debug("REST request to get Entreprise : {}", id);
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(entreprise);
    }

/* ##############################################################
    public Optional<Entreprise> compter(Long id)
    {
       Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
       if(entreprise.isPresent()){
           int quantity = entreprise.get().getQuantity()
       }
       if(quantity > 0){
           entreprise.get().setQuantity(quantity - 1) ;
           entreprise = entrepriseRepository.save(entreprise)
           return entreprise;
       }
       else{
           throw new BadRequestAlertException("Entreprise is not in stock", "entreprise", "not in stock");
       }
       return Optional.empty(); 
    }
###############################################################*/

    /**
     * {@code DELETE  /entreprises/:id} : delete the "id" entreprise.
     *
     * @param id the id of the entreprise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entreprises/{id}")
    public ResponseEntity<Void> deleteEntreprise(@PathVariable Long id) {
        log.debug("REST request to delete Entreprise : {}", id);
        entrepriseRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
