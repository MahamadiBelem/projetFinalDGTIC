package ati.dgtic.web.rest;

import ati.dgtic.ProjetDgticApp;
import ati.dgtic.domain.Agrement;
import ati.dgtic.repository.AgrementRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AgrementResource} REST controller.
 */
@SpringBootTest(classes = ProjetDgticApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AgrementResourceIT {

    private static final String DEFAULT_CODE_AGREMENT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_AGREMENT = "BBBBBBBBBB";

    @Autowired
    private AgrementRepository agrementRepository;

    @Mock
    private AgrementRepository agrementRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgrementMockMvc;

    private Agrement agrement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agrement createEntity(EntityManager em) {
        Agrement agrement = new Agrement()
            .codeAgrement(DEFAULT_CODE_AGREMENT);
        return agrement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agrement createUpdatedEntity(EntityManager em) {
        Agrement agrement = new Agrement()
            .codeAgrement(UPDATED_CODE_AGREMENT);
        return agrement;
    }

    @BeforeEach
    public void initTest() {
        agrement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgrement() throws Exception {
        int databaseSizeBeforeCreate = agrementRepository.findAll().size();
        // Create the Agrement
        restAgrementMockMvc.perform(post("/api/agrements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agrement)))
            .andExpect(status().isCreated());

        // Validate the Agrement in the database
        List<Agrement> agrementList = agrementRepository.findAll();
        assertThat(agrementList).hasSize(databaseSizeBeforeCreate + 1);
        Agrement testAgrement = agrementList.get(agrementList.size() - 1);
        assertThat(testAgrement.getCodeAgrement()).isEqualTo(DEFAULT_CODE_AGREMENT);
    }

    @Test
    @Transactional
    public void createAgrementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agrementRepository.findAll().size();

        // Create the Agrement with an existing ID
        agrement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgrementMockMvc.perform(post("/api/agrements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agrement)))
            .andExpect(status().isBadRequest());

        // Validate the Agrement in the database
        List<Agrement> agrementList = agrementRepository.findAll();
        assertThat(agrementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeAgrementIsRequired() throws Exception {
        int databaseSizeBeforeTest = agrementRepository.findAll().size();
        // set the field null
        agrement.setCodeAgrement(null);

        // Create the Agrement, which fails.


        restAgrementMockMvc.perform(post("/api/agrements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agrement)))
            .andExpect(status().isBadRequest());

        List<Agrement> agrementList = agrementRepository.findAll();
        assertThat(agrementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgrements() throws Exception {
        // Initialize the database
        agrementRepository.saveAndFlush(agrement);

        // Get all the agrementList
        restAgrementMockMvc.perform(get("/api/agrements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agrement.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeAgrement").value(hasItem(DEFAULT_CODE_AGREMENT)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAgrementsWithEagerRelationshipsIsEnabled() throws Exception {
        when(agrementRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgrementMockMvc.perform(get("/api/agrements?eagerload=true"))
            .andExpect(status().isOk());

        verify(agrementRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAgrementsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(agrementRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgrementMockMvc.perform(get("/api/agrements?eagerload=true"))
            .andExpect(status().isOk());

        verify(agrementRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAgrement() throws Exception {
        // Initialize the database
        agrementRepository.saveAndFlush(agrement);

        // Get the agrement
        restAgrementMockMvc.perform(get("/api/agrements/{id}", agrement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agrement.getId().intValue()))
            .andExpect(jsonPath("$.codeAgrement").value(DEFAULT_CODE_AGREMENT));
    }
    @Test
    @Transactional
    public void getNonExistingAgrement() throws Exception {
        // Get the agrement
        restAgrementMockMvc.perform(get("/api/agrements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgrement() throws Exception {
        // Initialize the database
        agrementRepository.saveAndFlush(agrement);

        int databaseSizeBeforeUpdate = agrementRepository.findAll().size();

        // Update the agrement
        Agrement updatedAgrement = agrementRepository.findById(agrement.getId()).get();
        // Disconnect from session so that the updates on updatedAgrement are not directly saved in db
        em.detach(updatedAgrement);
        updatedAgrement
            .codeAgrement(UPDATED_CODE_AGREMENT);

        restAgrementMockMvc.perform(put("/api/agrements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgrement)))
            .andExpect(status().isOk());

        // Validate the Agrement in the database
        List<Agrement> agrementList = agrementRepository.findAll();
        assertThat(agrementList).hasSize(databaseSizeBeforeUpdate);
        Agrement testAgrement = agrementList.get(agrementList.size() - 1);
        assertThat(testAgrement.getCodeAgrement()).isEqualTo(UPDATED_CODE_AGREMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingAgrement() throws Exception {
        int databaseSizeBeforeUpdate = agrementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgrementMockMvc.perform(put("/api/agrements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agrement)))
            .andExpect(status().isBadRequest());

        // Validate the Agrement in the database
        List<Agrement> agrementList = agrementRepository.findAll();
        assertThat(agrementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgrement() throws Exception {
        // Initialize the database
        agrementRepository.saveAndFlush(agrement);

        int databaseSizeBeforeDelete = agrementRepository.findAll().size();

        // Delete the agrement
        restAgrementMockMvc.perform(delete("/api/agrements/{id}", agrement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agrement> agrementList = agrementRepository.findAll();
        assertThat(agrementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
