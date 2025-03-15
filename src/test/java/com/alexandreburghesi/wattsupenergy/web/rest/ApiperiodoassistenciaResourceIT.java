package com.alexandreburghesi.wattsupenergy.web.rest;

import static com.alexandreburghesi.wattsupenergy.domain.ApiperiodoassistenciaAsserts.*;
import static com.alexandreburghesi.wattsupenergy.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.alexandreburghesi.wattsupenergy.IntegrationTest;
import com.alexandreburghesi.wattsupenergy.domain.Apiperiodoassistencia;
import com.alexandreburghesi.wattsupenergy.repository.ApiperiodoassistenciaRepository;
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
 * Integration tests for the {@link ApiperiodoassistenciaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApiperiodoassistenciaResourceIT {

    private static final String DEFAULT_DADOSPERIODO = "AAAAAAAAAA";
    private static final String UPDATED_DADOSPERIODO = "BBBBBBBBBB";

    private static final String DEFAULT_CHAVE = "AAAAAAAAAA";
    private static final String UPDATED_CHAVE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/apiperiodoassistencias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ApiperiodoassistenciaRepository apiperiodoassistenciaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApiperiodoassistenciaMockMvc;

    private Apiperiodoassistencia apiperiodoassistencia;

    private Apiperiodoassistencia insertedApiperiodoassistencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apiperiodoassistencia createEntity() {
        return new Apiperiodoassistencia().dadosperiodo(DEFAULT_DADOSPERIODO).chave(DEFAULT_CHAVE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apiperiodoassistencia createUpdatedEntity() {
        return new Apiperiodoassistencia().dadosperiodo(UPDATED_DADOSPERIODO).chave(UPDATED_CHAVE);
    }

    @BeforeEach
    public void initTest() {
        apiperiodoassistencia = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedApiperiodoassistencia != null) {
            apiperiodoassistenciaRepository.delete(insertedApiperiodoassistencia);
            insertedApiperiodoassistencia = null;
        }
    }

    @Test
    @Transactional
    void createApiperiodoassistencia() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Apiperiodoassistencia
        var returnedApiperiodoassistencia = om.readValue(
            restApiperiodoassistenciaMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(apiperiodoassistencia))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Apiperiodoassistencia.class
        );

        // Validate the Apiperiodoassistencia in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertApiperiodoassistenciaUpdatableFieldsEquals(
            returnedApiperiodoassistencia,
            getPersistedApiperiodoassistencia(returnedApiperiodoassistencia)
        );

        insertedApiperiodoassistencia = returnedApiperiodoassistencia;
    }

    @Test
    @Transactional
    void createApiperiodoassistenciaWithExistingId() throws Exception {
        // Create the Apiperiodoassistencia with an existing ID
        apiperiodoassistencia.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiperiodoassistenciaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiperiodoassistencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiperiodoassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApiperiodoassistencias() throws Exception {
        // Initialize the database
        insertedApiperiodoassistencia = apiperiodoassistenciaRepository.saveAndFlush(apiperiodoassistencia);

        // Get all the apiperiodoassistenciaList
        restApiperiodoassistenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiperiodoassistencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].dadosperiodo").value(hasItem(DEFAULT_DADOSPERIODO)))
            .andExpect(jsonPath("$.[*].chave").value(hasItem(DEFAULT_CHAVE)));
    }

    @Test
    @Transactional
    void getApiperiodoassistencia() throws Exception {
        // Initialize the database
        insertedApiperiodoassistencia = apiperiodoassistenciaRepository.saveAndFlush(apiperiodoassistencia);

        // Get the apiperiodoassistencia
        restApiperiodoassistenciaMockMvc
            .perform(get(ENTITY_API_URL_ID, apiperiodoassistencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apiperiodoassistencia.getId().intValue()))
            .andExpect(jsonPath("$.dadosperiodo").value(DEFAULT_DADOSPERIODO))
            .andExpect(jsonPath("$.chave").value(DEFAULT_CHAVE));
    }

    @Test
    @Transactional
    void getApiperiodoassistenciasByIdFiltering() throws Exception {
        // Initialize the database
        insertedApiperiodoassistencia = apiperiodoassistenciaRepository.saveAndFlush(apiperiodoassistencia);

        Long id = apiperiodoassistencia.getId();

        defaultApiperiodoassistenciaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultApiperiodoassistenciaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultApiperiodoassistenciaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApiperiodoassistenciasByChaveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedApiperiodoassistencia = apiperiodoassistenciaRepository.saveAndFlush(apiperiodoassistencia);

        // Get all the apiperiodoassistenciaList where chave equals to
        defaultApiperiodoassistenciaFiltering("chave.equals=" + DEFAULT_CHAVE, "chave.equals=" + UPDATED_CHAVE);
    }

    @Test
    @Transactional
    void getAllApiperiodoassistenciasByChaveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedApiperiodoassistencia = apiperiodoassistenciaRepository.saveAndFlush(apiperiodoassistencia);

        // Get all the apiperiodoassistenciaList where chave in
        defaultApiperiodoassistenciaFiltering("chave.in=" + DEFAULT_CHAVE + "," + UPDATED_CHAVE, "chave.in=" + UPDATED_CHAVE);
    }

    @Test
    @Transactional
    void getAllApiperiodoassistenciasByChaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedApiperiodoassistencia = apiperiodoassistenciaRepository.saveAndFlush(apiperiodoassistencia);

        // Get all the apiperiodoassistenciaList where chave is not null
        defaultApiperiodoassistenciaFiltering("chave.specified=true", "chave.specified=false");
    }

    @Test
    @Transactional
    void getAllApiperiodoassistenciasByChaveContainsSomething() throws Exception {
        // Initialize the database
        insertedApiperiodoassistencia = apiperiodoassistenciaRepository.saveAndFlush(apiperiodoassistencia);

        // Get all the apiperiodoassistenciaList where chave contains
        defaultApiperiodoassistenciaFiltering("chave.contains=" + DEFAULT_CHAVE, "chave.contains=" + UPDATED_CHAVE);
    }

    @Test
    @Transactional
    void getAllApiperiodoassistenciasByChaveNotContainsSomething() throws Exception {
        // Initialize the database
        insertedApiperiodoassistencia = apiperiodoassistenciaRepository.saveAndFlush(apiperiodoassistencia);

        // Get all the apiperiodoassistenciaList where chave does not contain
        defaultApiperiodoassistenciaFiltering("chave.doesNotContain=" + UPDATED_CHAVE, "chave.doesNotContain=" + DEFAULT_CHAVE);
    }

    private void defaultApiperiodoassistenciaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultApiperiodoassistenciaShouldBeFound(shouldBeFound);
        defaultApiperiodoassistenciaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApiperiodoassistenciaShouldBeFound(String filter) throws Exception {
        restApiperiodoassistenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiperiodoassistencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].dadosperiodo").value(hasItem(DEFAULT_DADOSPERIODO)))
            .andExpect(jsonPath("$.[*].chave").value(hasItem(DEFAULT_CHAVE)));

        // Check, that the count call also returns 1
        restApiperiodoassistenciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApiperiodoassistenciaShouldNotBeFound(String filter) throws Exception {
        restApiperiodoassistenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApiperiodoassistenciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApiperiodoassistencia() throws Exception {
        // Get the apiperiodoassistencia
        restApiperiodoassistenciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApiperiodoassistencia() throws Exception {
        // Initialize the database
        insertedApiperiodoassistencia = apiperiodoassistenciaRepository.saveAndFlush(apiperiodoassistencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiperiodoassistencia
        Apiperiodoassistencia updatedApiperiodoassistencia = apiperiodoassistenciaRepository
            .findById(apiperiodoassistencia.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedApiperiodoassistencia are not directly saved in db
        em.detach(updatedApiperiodoassistencia);
        updatedApiperiodoassistencia.dadosperiodo(UPDATED_DADOSPERIODO).chave(UPDATED_CHAVE);

        restApiperiodoassistenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApiperiodoassistencia.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedApiperiodoassistencia))
            )
            .andExpect(status().isOk());

        // Validate the Apiperiodoassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedApiperiodoassistenciaToMatchAllProperties(updatedApiperiodoassistencia);
    }

    @Test
    @Transactional
    void putNonExistingApiperiodoassistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiperiodoassistencia.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiperiodoassistenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apiperiodoassistencia.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiperiodoassistencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiperiodoassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApiperiodoassistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiperiodoassistencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiperiodoassistenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiperiodoassistencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiperiodoassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApiperiodoassistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiperiodoassistencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiperiodoassistenciaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apiperiodoassistencia))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apiperiodoassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApiperiodoassistenciaWithPatch() throws Exception {
        // Initialize the database
        insertedApiperiodoassistencia = apiperiodoassistenciaRepository.saveAndFlush(apiperiodoassistencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiperiodoassistencia using partial update
        Apiperiodoassistencia partialUpdatedApiperiodoassistencia = new Apiperiodoassistencia();
        partialUpdatedApiperiodoassistencia.setId(apiperiodoassistencia.getId());

        partialUpdatedApiperiodoassistencia.dadosperiodo(UPDATED_DADOSPERIODO);

        restApiperiodoassistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiperiodoassistencia.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApiperiodoassistencia))
            )
            .andExpect(status().isOk());

        // Validate the Apiperiodoassistencia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApiperiodoassistenciaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedApiperiodoassistencia, apiperiodoassistencia),
            getPersistedApiperiodoassistencia(apiperiodoassistencia)
        );
    }

    @Test
    @Transactional
    void fullUpdateApiperiodoassistenciaWithPatch() throws Exception {
        // Initialize the database
        insertedApiperiodoassistencia = apiperiodoassistenciaRepository.saveAndFlush(apiperiodoassistencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apiperiodoassistencia using partial update
        Apiperiodoassistencia partialUpdatedApiperiodoassistencia = new Apiperiodoassistencia();
        partialUpdatedApiperiodoassistencia.setId(apiperiodoassistencia.getId());

        partialUpdatedApiperiodoassistencia.dadosperiodo(UPDATED_DADOSPERIODO).chave(UPDATED_CHAVE);

        restApiperiodoassistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApiperiodoassistencia.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApiperiodoassistencia))
            )
            .andExpect(status().isOk());

        // Validate the Apiperiodoassistencia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApiperiodoassistenciaUpdatableFieldsEquals(
            partialUpdatedApiperiodoassistencia,
            getPersistedApiperiodoassistencia(partialUpdatedApiperiodoassistencia)
        );
    }

    @Test
    @Transactional
    void patchNonExistingApiperiodoassistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiperiodoassistencia.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiperiodoassistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apiperiodoassistencia.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apiperiodoassistencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiperiodoassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApiperiodoassistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiperiodoassistencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiperiodoassistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apiperiodoassistencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apiperiodoassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApiperiodoassistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apiperiodoassistencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApiperiodoassistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apiperiodoassistencia))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apiperiodoassistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApiperiodoassistencia() throws Exception {
        // Initialize the database
        insertedApiperiodoassistencia = apiperiodoassistenciaRepository.saveAndFlush(apiperiodoassistencia);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the apiperiodoassistencia
        restApiperiodoassistenciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, apiperiodoassistencia.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return apiperiodoassistenciaRepository.count();
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

    protected Apiperiodoassistencia getPersistedApiperiodoassistencia(Apiperiodoassistencia apiperiodoassistencia) {
        return apiperiodoassistenciaRepository.findById(apiperiodoassistencia.getId()).orElseThrow();
    }

    protected void assertPersistedApiperiodoassistenciaToMatchAllProperties(Apiperiodoassistencia expectedApiperiodoassistencia) {
        assertApiperiodoassistenciaAllPropertiesEquals(
            expectedApiperiodoassistencia,
            getPersistedApiperiodoassistencia(expectedApiperiodoassistencia)
        );
    }

    protected void assertPersistedApiperiodoassistenciaToMatchUpdatableProperties(Apiperiodoassistencia expectedApiperiodoassistencia) {
        assertApiperiodoassistenciaAllUpdatablePropertiesEquals(
            expectedApiperiodoassistencia,
            getPersistedApiperiodoassistencia(expectedApiperiodoassistencia)
        );
    }
}
