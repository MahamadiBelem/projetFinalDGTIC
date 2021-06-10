package ati.dgtic.web.rest;

import ati.dgtic.ProjetDgticApp;
import ati.dgtic.domain.Arrete;
import ati.dgtic.repository.ArreteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ArreteResource} REST controller.
 */
@SpringBootTest(classes = ProjetDgticApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArreteResourceIT {

    private static final String DEFAULT_INTITULE_ARRETE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE_ARRETE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_ARRETE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_ARRETE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_SIGNATURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SIGNATURE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NOMBRE_AGREMENT = 1;
    private static final Integer UPDATED_NOMBRE_AGREMENT = 2;

    @Autowired
    private ArreteRepository arreteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArreteMockMvc;

    private Arrete arrete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arrete createEntity(EntityManager em) {
        Arrete arrete = new Arrete()
            .intituleArrete(DEFAULT_INTITULE_ARRETE)
            .numeroArrete(DEFAULT_NUMERO_ARRETE)
            .dateSignature(DEFAULT_DATE_SIGNATURE)
            .nombreAgrement(DEFAULT_NOMBRE_AGREMENT);
        return arrete;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arrete createUpdatedEntity(EntityManager em) {
        Arrete arrete = new Arrete()
            .intituleArrete(UPDATED_INTITULE_ARRETE)
            .numeroArrete(UPDATED_NUMERO_ARRETE)
            .dateSignature(UPDATED_DATE_SIGNATURE)
            .nombreAgrement(UPDATED_NOMBRE_AGREMENT);
        return arrete;
    }

    @BeforeEach
    public void initTest() {
        arrete = createEntity(em);
    }

    @Test
    @Transactional
    public void createArrete() throws Exception {
        int databaseSizeBeforeCreate = arreteRepository.findAll().size();
        // Create the Arrete
        restArreteMockMvc.perform(post("/api/arretes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrete)))
            .andExpect(status().isCreated());

        // Validate the Arrete in the database
        List<Arrete> arreteList = arreteRepository.findAll();
        assertThat(arreteList).hasSize(databaseSizeBeforeCreate + 1);
        Arrete testArrete = arreteList.get(arreteList.size() - 1);
        assertThat(testArrete.getIntituleArrete()).isEqualTo(DEFAULT_INTITULE_ARRETE);
        assertThat(testArrete.getNumeroArrete()).isEqualTo(DEFAULT_NUMERO_ARRETE);
        assertThat(testArrete.getDateSignature()).isEqualTo(DEFAULT_DATE_SIGNATURE);
        assertThat(testArrete.getNombreAgrement()).isEqualTo(DEFAULT_NOMBRE_AGREMENT);
    }

    @Test
    @Transactional
    public void createArreteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = arreteRepository.findAll().size();

        // Create the Arrete with an existing ID
        arrete.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArreteMockMvc.perform(post("/api/arretes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrete)))
            .andExpect(status().isBadRequest());

        // Validate the Arrete in the database
        List<Arrete> arreteList = arreteRepository.findAll();
        assertThat(arreteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroArreteIsRequired() throws Exception {
        int databaseSizeBeforeTest = arreteRepository.findAll().size();
        // set the field null
        arrete.setNumeroArrete(null);

        // Create the Arrete, which fails.


        restArreteMockMvc.perform(post("/api/arretes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrete)))
            .andExpect(status().isBadRequest());

        List<Arrete> arreteList = arreteRepository.findAll();
        assertThat(arreteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateSignatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = arreteRepository.findAll().size();
        // set the field null
        arrete.setDateSignature(null);

        // Create the Arrete, which fails.


        restArreteMockMvc.perform(post("/api/arretes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrete)))
            .andExpect(status().isBadRequest());

        List<Arrete> arreteList = arreteRepository.findAll();
        assertThat(arreteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArretes() throws Exception {
        // Initialize the database
        arreteRepository.saveAndFlush(arrete);

        // Get all the arreteList
        restArreteMockMvc.perform(get("/api/arretes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arrete.getId().intValue())))
            .andExpect(jsonPath("$.[*].intituleArrete").value(hasItem(DEFAULT_INTITULE_ARRETE)))
            .andExpect(jsonPath("$.[*].numeroArrete").value(hasItem(DEFAULT_NUMERO_ARRETE)))
            .andExpect(jsonPath("$.[*].dateSignature").value(hasItem(DEFAULT_DATE_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].nombreAgrement").value(hasItem(DEFAULT_NOMBRE_AGREMENT)));
    }
    
    @Test
    @Transactional
    public void getArrete() throws Exception {
        // Initialize the database
        arreteRepository.saveAndFlush(arrete);

        // Get the arrete
        restArreteMockMvc.perform(get("/api/arretes/{id}", arrete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(arrete.getId().intValue()))
            .andExpect(jsonPath("$.intituleArrete").value(DEFAULT_INTITULE_ARRETE))
            .andExpect(jsonPath("$.numeroArrete").value(DEFAULT_NUMERO_ARRETE))
            .andExpect(jsonPath("$.dateSignature").value(DEFAULT_DATE_SIGNATURE.toString()))
            .andExpect(jsonPath("$.nombreAgrement").value(DEFAULT_NOMBRE_AGREMENT));
    }
    @Test
    @Transactional
    public void getNonExistingArrete() throws Exception {
        // Get the arrete
        restArreteMockMvc.perform(get("/api/arretes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArrete() throws Exception {
        // Initialize the database
        arreteRepository.saveAndFlush(arrete);

        int databaseSizeBeforeUpdate = arreteRepository.findAll().size();

        // Update the arrete
        Arrete updatedArrete = arreteRepository.findById(arrete.getId()).get();
        // Disconnect from session so that the updates on updatedArrete are not directly saved in db
        em.detach(updatedArrete);
        updatedArrete
            .intituleArrete(UPDATED_INTITULE_ARRETE)
            .numeroArrete(UPDATED_NUMERO_ARRETE)
            .dateSignature(UPDATED_DATE_SIGNATURE)
            .nombreAgrement(UPDATED_NOMBRE_AGREMENT);

        restArreteMockMvc.perform(put("/api/arretes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArrete)))
            .andExpect(status().isOk());

        // Validate the Arrete in the database
        List<Arrete> arreteList = arreteRepository.findAll();
        assertThat(arreteList).hasSize(databaseSizeBeforeUpdate);
        Arrete testArrete = arreteList.get(arreteList.size() - 1);
        assertThat(testArrete.getIntituleArrete()).isEqualTo(UPDATED_INTITULE_ARRETE);
        assertThat(testArrete.getNumeroArrete()).isEqualTo(UPDATED_NUMERO_ARRETE);
        assertThat(testArrete.getDateSignature()).isEqualTo(UPDATED_DATE_SIGNATURE);
        assertThat(testArrete.getNombreAgrement()).isEqualTo(UPDATED_NOMBRE_AGREMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingArrete() throws Exception {
        int databaseSizeBeforeUpdate = arreteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArreteMockMvc.perform(put("/api/arretes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arrete)))
            .andExpect(status().isBadRequest());

        // Validate the Arrete in the database
        List<Arrete> arreteList = arreteRepository.findAll();
        assertThat(arreteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArrete() throws Exception {
        // Initialize the database
        arreteRepository.saveAndFlush(arrete);

        int databaseSizeBeforeDelete = arreteRepository.findAll().size();

        // Delete the arrete
        restArreteMockMvc.perform(delete("/api/arretes/{id}", arrete.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Arrete> arreteList = arreteRepository.findAll();
        assertThat(arreteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
