package ati.dgtic.web.rest;

import ati.dgtic.ProjetDgticApp;
import ati.dgtic.domain.Qualification;
import ati.dgtic.repository.QualificationRepository;

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
 * Integration tests for the {@link QualificationResource} REST controller.
 */
@SpringBootTest(classes = ProjetDgticApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QualificationResourceIT {

    private static final String DEFAULT_SERVICE_CONCERNE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_CONCERNE = "BBBBBBBBBB";

    @Autowired
    private QualificationRepository qualificationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQualificationMockMvc;

    private Qualification qualification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Qualification createEntity(EntityManager em) {
        Qualification qualification = new Qualification()
            .serviceConcerne(DEFAULT_SERVICE_CONCERNE);
        return qualification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Qualification createUpdatedEntity(EntityManager em) {
        Qualification qualification = new Qualification()
            .serviceConcerne(UPDATED_SERVICE_CONCERNE);
        return qualification;
    }

    @BeforeEach
    public void initTest() {
        qualification = createEntity(em);
    }

    @Test
    @Transactional
    public void createQualification() throws Exception {
        int databaseSizeBeforeCreate = qualificationRepository.findAll().size();
        // Create the Qualification
        restQualificationMockMvc.perform(post("/api/qualifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qualification)))
            .andExpect(status().isCreated());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeCreate + 1);
        Qualification testQualification = qualificationList.get(qualificationList.size() - 1);
        assertThat(testQualification.getServiceConcerne()).isEqualTo(DEFAULT_SERVICE_CONCERNE);
    }

    @Test
    @Transactional
    public void createQualificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qualificationRepository.findAll().size();

        // Create the Qualification with an existing ID
        qualification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQualificationMockMvc.perform(post("/api/qualifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qualification)))
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQualifications() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        // Get all the qualificationList
        restQualificationMockMvc.perform(get("/api/qualifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qualification.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceConcerne").value(hasItem(DEFAULT_SERVICE_CONCERNE.toString())));
    }
    
    @Test
    @Transactional
    public void getQualification() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        // Get the qualification
        restQualificationMockMvc.perform(get("/api/qualifications/{id}", qualification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qualification.getId().intValue()))
            .andExpect(jsonPath("$.serviceConcerne").value(DEFAULT_SERVICE_CONCERNE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingQualification() throws Exception {
        // Get the qualification
        restQualificationMockMvc.perform(get("/api/qualifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQualification() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        int databaseSizeBeforeUpdate = qualificationRepository.findAll().size();

        // Update the qualification
        Qualification updatedQualification = qualificationRepository.findById(qualification.getId()).get();
        // Disconnect from session so that the updates on updatedQualification are not directly saved in db
        em.detach(updatedQualification);
        updatedQualification
            .serviceConcerne(UPDATED_SERVICE_CONCERNE);

        restQualificationMockMvc.perform(put("/api/qualifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQualification)))
            .andExpect(status().isOk());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeUpdate);
        Qualification testQualification = qualificationList.get(qualificationList.size() - 1);
        assertThat(testQualification.getServiceConcerne()).isEqualTo(UPDATED_SERVICE_CONCERNE);
    }

    @Test
    @Transactional
    public void updateNonExistingQualification() throws Exception {
        int databaseSizeBeforeUpdate = qualificationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQualificationMockMvc.perform(put("/api/qualifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qualification)))
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQualification() throws Exception {
        // Initialize the database
        qualificationRepository.saveAndFlush(qualification);

        int databaseSizeBeforeDelete = qualificationRepository.findAll().size();

        // Delete the qualification
        restQualificationMockMvc.perform(delete("/api/qualifications/{id}", qualification.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Qualification> qualificationList = qualificationRepository.findAll();
        assertThat(qualificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
