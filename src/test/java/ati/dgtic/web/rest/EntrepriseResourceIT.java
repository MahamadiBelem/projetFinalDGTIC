package ati.dgtic.web.rest;

import ati.dgtic.ProjetDgticApp;
import ati.dgtic.domain.Entreprise;
import ati.dgtic.repository.EntrepriseRepository;

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
 * Integration tests for the {@link EntrepriseResource} REST controller.
 */
@SpringBootTest(classes = ProjetDgticApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EntrepriseResourceIT {

    private static final String DEFAULT_SIGLE_ENTREPRISE = "AAAAAAAAAA";
    private static final String UPDATED_SIGLE_ENTREPRISE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_ENTREPRISE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ENTREPRISE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_RCCM = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_RCCM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_IFU = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_IFU = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALISATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCALISATION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_2 = "BBBBBBBBBB";

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntrepriseMockMvc;

    private Entreprise entreprise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreprise createEntity(EntityManager em) {
        Entreprise entreprise = new Entreprise()
            .sigleEntreprise(DEFAULT_SIGLE_ENTREPRISE)
            .nomEntreprise(DEFAULT_NOM_ENTREPRISE)
            .numeroRCCM(DEFAULT_NUMERO_RCCM)
            .numeroIFU(DEFAULT_NUMERO_IFU)
            .ville(DEFAULT_VILLE)
            .localisation(DEFAULT_LOCALISATION)
            .telephone1(DEFAULT_TELEPHONE_1)
            .telephone2(DEFAULT_TELEPHONE_2);
        return entreprise;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreprise createUpdatedEntity(EntityManager em) {
        Entreprise entreprise = new Entreprise()
            .sigleEntreprise(UPDATED_SIGLE_ENTREPRISE)
            .nomEntreprise(UPDATED_NOM_ENTREPRISE)
            .numeroRCCM(UPDATED_NUMERO_RCCM)
            .numeroIFU(UPDATED_NUMERO_IFU)
            .ville(UPDATED_VILLE)
            .localisation(UPDATED_LOCALISATION)
            .telephone1(UPDATED_TELEPHONE_1)
            .telephone2(UPDATED_TELEPHONE_2);
        return entreprise;
    }

    @BeforeEach
    public void initTest() {
        entreprise = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntreprise() throws Exception {
        int databaseSizeBeforeCreate = entrepriseRepository.findAll().size();
        // Create the Entreprise
        restEntrepriseMockMvc.perform(post("/api/entreprises")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isCreated());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeCreate + 1);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getSigleEntreprise()).isEqualTo(DEFAULT_SIGLE_ENTREPRISE);
        assertThat(testEntreprise.getNomEntreprise()).isEqualTo(DEFAULT_NOM_ENTREPRISE);
        assertThat(testEntreprise.getNumeroRCCM()).isEqualTo(DEFAULT_NUMERO_RCCM);
        assertThat(testEntreprise.getNumeroIFU()).isEqualTo(DEFAULT_NUMERO_IFU);
        assertThat(testEntreprise.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testEntreprise.getLocalisation()).isEqualTo(DEFAULT_LOCALISATION);
        assertThat(testEntreprise.getTelephone1()).isEqualTo(DEFAULT_TELEPHONE_1);
        assertThat(testEntreprise.getTelephone2()).isEqualTo(DEFAULT_TELEPHONE_2);
    }

    @Test
    @Transactional
    public void createEntrepriseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entrepriseRepository.findAll().size();

        // Create the Entreprise with an existing ID
        entreprise.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntrepriseMockMvc.perform(post("/api/entreprises")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSigleEntrepriseIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrepriseRepository.findAll().size();
        // set the field null
        entreprise.setSigleEntreprise(null);

        // Create the Entreprise, which fails.


        restEntrepriseMockMvc.perform(post("/api/entreprises")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isBadRequest());

        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntreprises() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get all the entrepriseList
        restEntrepriseMockMvc.perform(get("/api/entreprises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entreprise.getId().intValue())))
            .andExpect(jsonPath("$.[*].sigleEntreprise").value(hasItem(DEFAULT_SIGLE_ENTREPRISE)))
            .andExpect(jsonPath("$.[*].nomEntreprise").value(hasItem(DEFAULT_NOM_ENTREPRISE.toString())))
            .andExpect(jsonPath("$.[*].numeroRCCM").value(hasItem(DEFAULT_NUMERO_RCCM)))
            .andExpect(jsonPath("$.[*].numeroIFU").value(hasItem(DEFAULT_NUMERO_IFU)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].localisation").value(hasItem(DEFAULT_LOCALISATION)))
            .andExpect(jsonPath("$.[*].telephone1").value(hasItem(DEFAULT_TELEPHONE_1)))
            .andExpect(jsonPath("$.[*].telephone2").value(hasItem(DEFAULT_TELEPHONE_2)));
    }
    
    @Test
    @Transactional
    public void getEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get the entreprise
        restEntrepriseMockMvc.perform(get("/api/entreprises/{id}", entreprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entreprise.getId().intValue()))
            .andExpect(jsonPath("$.sigleEntreprise").value(DEFAULT_SIGLE_ENTREPRISE))
            .andExpect(jsonPath("$.nomEntreprise").value(DEFAULT_NOM_ENTREPRISE.toString()))
            .andExpect(jsonPath("$.numeroRCCM").value(DEFAULT_NUMERO_RCCM))
            .andExpect(jsonPath("$.numeroIFU").value(DEFAULT_NUMERO_IFU))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.localisation").value(DEFAULT_LOCALISATION))
            .andExpect(jsonPath("$.telephone1").value(DEFAULT_TELEPHONE_1))
            .andExpect(jsonPath("$.telephone2").value(DEFAULT_TELEPHONE_2));
    }
    @Test
    @Transactional
    public void getNonExistingEntreprise() throws Exception {
        // Get the entreprise
        restEntrepriseMockMvc.perform(get("/api/entreprises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise
        Entreprise updatedEntreprise = entrepriseRepository.findById(entreprise.getId()).get();
        // Disconnect from session so that the updates on updatedEntreprise are not directly saved in db
        em.detach(updatedEntreprise);
        updatedEntreprise
            .sigleEntreprise(UPDATED_SIGLE_ENTREPRISE)
            .nomEntreprise(UPDATED_NOM_ENTREPRISE)
            .numeroRCCM(UPDATED_NUMERO_RCCM)
            .numeroIFU(UPDATED_NUMERO_IFU)
            .ville(UPDATED_VILLE)
            .localisation(UPDATED_LOCALISATION)
            .telephone1(UPDATED_TELEPHONE_1)
            .telephone2(UPDATED_TELEPHONE_2);

        restEntrepriseMockMvc.perform(put("/api/entreprises")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntreprise)))
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getSigleEntreprise()).isEqualTo(UPDATED_SIGLE_ENTREPRISE);
        assertThat(testEntreprise.getNomEntreprise()).isEqualTo(UPDATED_NOM_ENTREPRISE);
        assertThat(testEntreprise.getNumeroRCCM()).isEqualTo(UPDATED_NUMERO_RCCM);
        assertThat(testEntreprise.getNumeroIFU()).isEqualTo(UPDATED_NUMERO_IFU);
        assertThat(testEntreprise.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testEntreprise.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);
        assertThat(testEntreprise.getTelephone1()).isEqualTo(UPDATED_TELEPHONE_1);
        assertThat(testEntreprise.getTelephone2()).isEqualTo(UPDATED_TELEPHONE_2);
    }

    @Test
    @Transactional
    public void updateNonExistingEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc.perform(put("/api/entreprises")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeDelete = entrepriseRepository.findAll().size();

        // Delete the entreprise
        restEntrepriseMockMvc.perform(delete("/api/entreprises/{id}", entreprise.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
