package ati.dgtic.web.rest;

import ati.dgtic.ProjetDgticApp;
import ati.dgtic.domain.Domaine;
import ati.dgtic.repository.DomaineRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DomaineResource} REST controller.
 */
@SpringBootTest(classes = ProjetDgticApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DomaineResourceIT {

    private static final String DEFAULT_CODE_DOMAINE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_DOMAINE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private DomaineRepository domaineRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDomaineMockMvc;

    private Domaine domaine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Domaine createEntity(EntityManager em) {
        Domaine domaine = new Domaine()
            .codeDomaine(DEFAULT_CODE_DOMAINE)
            .libelle(DEFAULT_LIBELLE);
        return domaine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Domaine createUpdatedEntity(EntityManager em) {
        Domaine domaine = new Domaine()
            .codeDomaine(UPDATED_CODE_DOMAINE)
            .libelle(UPDATED_LIBELLE);
        return domaine;
    }

    @BeforeEach
    public void initTest() {
        domaine = createEntity(em);
    }

    @Test
    @Transactional
    public void createDomaine() throws Exception {
        int databaseSizeBeforeCreate = domaineRepository.findAll().size();
        // Create the Domaine
        restDomaineMockMvc.perform(post("/api/domaines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(domaine)))
            .andExpect(status().isCreated());

        // Validate the Domaine in the database
        List<Domaine> domaineList = domaineRepository.findAll();
        assertThat(domaineList).hasSize(databaseSizeBeforeCreate + 1);
        Domaine testDomaine = domaineList.get(domaineList.size() - 1);
        assertThat(testDomaine.getCodeDomaine()).isEqualTo(DEFAULT_CODE_DOMAINE);
        assertThat(testDomaine.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createDomaineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = domaineRepository.findAll().size();

        // Create the Domaine with an existing ID
        domaine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDomaineMockMvc.perform(post("/api/domaines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(domaine)))
            .andExpect(status().isBadRequest());

        // Validate the Domaine in the database
        List<Domaine> domaineList = domaineRepository.findAll();
        assertThat(domaineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeDomaineIsRequired() throws Exception {
        int databaseSizeBeforeTest = domaineRepository.findAll().size();
        // set the field null
        domaine.setCodeDomaine(null);

        // Create the Domaine, which fails.


        restDomaineMockMvc.perform(post("/api/domaines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(domaine)))
            .andExpect(status().isBadRequest());

        List<Domaine> domaineList = domaineRepository.findAll();
        assertThat(domaineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDomaines() throws Exception {
        // Initialize the database
        domaineRepository.saveAndFlush(domaine);

        // Get all the domaineList
        restDomaineMockMvc.perform(get("/api/domaines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domaine.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeDomaine").value(hasItem(DEFAULT_CODE_DOMAINE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getDomaine() throws Exception {
        // Initialize the database
        domaineRepository.saveAndFlush(domaine);

        // Get the domaine
        restDomaineMockMvc.perform(get("/api/domaines/{id}", domaine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(domaine.getId().intValue()))
            .andExpect(jsonPath("$.codeDomaine").value(DEFAULT_CODE_DOMAINE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDomaine() throws Exception {
        // Get the domaine
        restDomaineMockMvc.perform(get("/api/domaines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomaine() throws Exception {
        // Initialize the database
        domaineRepository.saveAndFlush(domaine);

        int databaseSizeBeforeUpdate = domaineRepository.findAll().size();

        // Update the domaine
        Domaine updatedDomaine = domaineRepository.findById(domaine.getId()).get();
        // Disconnect from session so that the updates on updatedDomaine are not directly saved in db
        em.detach(updatedDomaine);
        updatedDomaine
            .codeDomaine(UPDATED_CODE_DOMAINE)
            .libelle(UPDATED_LIBELLE);

        restDomaineMockMvc.perform(put("/api/domaines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDomaine)))
            .andExpect(status().isOk());

        // Validate the Domaine in the database
        List<Domaine> domaineList = domaineRepository.findAll();
        assertThat(domaineList).hasSize(databaseSizeBeforeUpdate);
        Domaine testDomaine = domaineList.get(domaineList.size() - 1);
        assertThat(testDomaine.getCodeDomaine()).isEqualTo(UPDATED_CODE_DOMAINE);
        assertThat(testDomaine.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingDomaine() throws Exception {
        int databaseSizeBeforeUpdate = domaineRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDomaineMockMvc.perform(put("/api/domaines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(domaine)))
            .andExpect(status().isBadRequest());

        // Validate the Domaine in the database
        List<Domaine> domaineList = domaineRepository.findAll();
        assertThat(domaineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDomaine() throws Exception {
        // Initialize the database
        domaineRepository.saveAndFlush(domaine);

        int databaseSizeBeforeDelete = domaineRepository.findAll().size();

        // Delete the domaine
        restDomaineMockMvc.perform(delete("/api/domaines/{id}", domaine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Domaine> domaineList = domaineRepository.findAll();
        assertThat(domaineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
