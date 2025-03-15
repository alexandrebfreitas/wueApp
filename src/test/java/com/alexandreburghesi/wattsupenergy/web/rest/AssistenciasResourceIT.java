package com.alexandreburghesi.wattsupenergy.web.rest;

import static com.alexandreburghesi.wattsupenergy.domain.AssistenciasAsserts.*;
import static com.alexandreburghesi.wattsupenergy.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.alexandreburghesi.wattsupenergy.IntegrationTest;
import com.alexandreburghesi.wattsupenergy.domain.Apiassistencia;
import com.alexandreburghesi.wattsupenergy.domain.Assistencias;
import com.alexandreburghesi.wattsupenergy.repository.AssistenciasRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AssistenciasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssistenciasResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/assistencias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AssistenciasRepository assistenciasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssistenciasMockMvc;

    private Assistencias assistencias;

    private Assistencias insertedAssistencias;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assistencias createEntity() {
        return new Assistencias().value(DEFAULT_VALUE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assistencias createUpdatedEntity() {
        return new Assistencias().value(UPDATED_VALUE);
    }

    @BeforeEach
    public void initTest() {
        assistencias = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAssistencias != null) {
            assistenciasRepository.delete(insertedAssistencias);
            insertedAssistencias = null;
        }
    }

    @Test
    @Transactional
    void createAssistencias() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Assistencias
        var returnedAssistencias = om.readValue(
            restAssistenciasMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assistencias))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Assistencias.class
        );

        // Validate the Assistencias in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAssistenciasUpdatableFieldsEquals(returnedAssistencias, getPersistedAssistencias(returnedAssistencias));

        insertedAssistencias = returnedAssistencias;
    }

    @Test
    @Transactional
    void createAssistenciasWithExistingId() throws Exception {
        // Create the Assistencias with an existing ID
        assistencias.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssistenciasMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assistencias)))
            .andExpect(status().isBadRequest());

        // Validate the Assistencias in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssistencias() throws Exception {
        // Initialize the database
        insertedAssistencias = assistenciasRepository.saveAndFlush(assistencias);

        // Get all the assistenciasList
        restAssistenciasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assistencias.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getAssistencias() throws Exception {
        // Initialize the database
        insertedAssistencias = assistenciasRepository.saveAndFlush(assistencias);

        // Get the assistencias
        restAssistenciasMockMvc
            .perform(get(ENTITY_API_URL_ID, assistencias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assistencias.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getAssistenciasByIdFiltering() throws Exception {
        // Initialize the database
        insertedAssistencias = assistenciasRepository.saveAndFlush(assistencias);

        Long id = assistencias.getId();

        defaultAssistenciasFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAssistenciasFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAssistenciasFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAssistenciasByApiassistenciaIsEqualToSomething() throws Exception {
        Apiassistencia apiassistencia;
        if (TestUtil.findAll(em, Apiassistencia.class).isEmpty()) {
            assistenciasRepository.saveAndFlush(assistencias);
            apiassistencia = ApiassistenciaResourceIT.createEntity();
        } else {
            apiassistencia = TestUtil.findAll(em, Apiassistencia.class).get(0);
        }
        em.persist(apiassistencia);
        em.flush();
        assistencias.setApiassistencia(apiassistencia);
        assistenciasRepository.saveAndFlush(assistencias);
        Long apiassistenciaId = apiassistencia.getId();
        // Get all the assistenciasList where apiassistencia equals to apiassistenciaId
        defaultAssistenciasShouldBeFound("apiassistenciaId.equals=" + apiassistenciaId);

        // Get all the assistenciasList where apiassistencia equals to (apiassistenciaId + 1)
        defaultAssistenciasShouldNotBeFound("apiassistenciaId.equals=" + (apiassistenciaId + 1));
    }

    private void defaultAssistenciasFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAssistenciasShouldBeFound(shouldBeFound);
        defaultAssistenciasShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAssistenciasShouldBeFound(String filter) throws Exception {
        restAssistenciasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assistencias.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restAssistenciasMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAssistenciasShouldNotBeFound(String filter) throws Exception {
        restAssistenciasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAssistenciasMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAssistencias() throws Exception {
        // Get the assistencias
        restAssistenciasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssistencias() throws Exception {
        // Initialize the database
        insertedAssistencias = assistenciasRepository.saveAndFlush(assistencias);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assistencias
        Assistencias updatedAssistencias = assistenciasRepository.findById(assistencias.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAssistencias are not directly saved in db
        em.detach(updatedAssistencias);
        updatedAssistencias.value(UPDATED_VALUE);

        restAssistenciasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAssistencias.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAssistencias))
            )
            .andExpect(status().isOk());

        // Validate the Assistencias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAssistenciasToMatchAllProperties(updatedAssistencias);
    }

    @Test
    @Transactional
    void putNonExistingAssistencias() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assistencias.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssistenciasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assistencias.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assistencias))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assistencias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssistencias() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assistencias.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssistenciasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assistencias))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assistencias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssistencias() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assistencias.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssistenciasMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assistencias)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assistencias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssistenciasWithPatch() throws Exception {
        // Initialize the database
        insertedAssistencias = assistenciasRepository.saveAndFlush(assistencias);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assistencias using partial update
        Assistencias partialUpdatedAssistencias = new Assistencias();
        partialUpdatedAssistencias.setId(assistencias.getId());

        partialUpdatedAssistencias.value(UPDATED_VALUE);

        restAssistenciasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssistencias.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssistencias))
            )
            .andExpect(status().isOk());

        // Validate the Assistencias in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssistenciasUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAssistencias, assistencias),
            getPersistedAssistencias(assistencias)
        );
    }

    @Test
    @Transactional
    void fullUpdateAssistenciasWithPatch() throws Exception {
        // Initialize the database
        insertedAssistencias = assistenciasRepository.saveAndFlush(assistencias);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assistencias using partial update
        Assistencias partialUpdatedAssistencias = new Assistencias();
        partialUpdatedAssistencias.setId(assistencias.getId());

        partialUpdatedAssistencias.value(UPDATED_VALUE);

        restAssistenciasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssistencias.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssistencias))
            )
            .andExpect(status().isOk());

        // Validate the Assistencias in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssistenciasUpdatableFieldsEquals(partialUpdatedAssistencias, getPersistedAssistencias(partialUpdatedAssistencias));
    }

    @Test
    @Transactional
    void patchNonExistingAssistencias() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assistencias.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssistenciasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assistencias.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assistencias))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assistencias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssistencias() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assistencias.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssistenciasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assistencias))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assistencias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssistencias() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assistencias.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssistenciasMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(assistencias))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assistencias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssistencias() throws Exception {
        // Initialize the database
        insertedAssistencias = assistenciasRepository.saveAndFlush(assistencias);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the assistencias
        restAssistenciasMockMvc
            .perform(delete(ENTITY_API_URL_ID, assistencias.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return assistenciasRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Assistencias getPersistedAssistencias(Assistencias assistencias) {
        return assistenciasRepository.findById(assistencias.getId()).orElseThrow();
    }

    protected void assertPersistedAssistenciasToMatchAllProperties(Assistencias expectedAssistencias) {
        assertAssistenciasAllPropertiesEquals(expectedAssistencias, getPersistedAssistencias(expectedAssistencias));
    }

    protected void assertPersistedAssistenciasToMatchUpdatableProperties(Assistencias expectedAssistencias) {
        assertAssistenciasAllUpdatablePropertiesEquals(expectedAssistencias, getPersistedAssistencias(expectedAssistencias));
    }
}
